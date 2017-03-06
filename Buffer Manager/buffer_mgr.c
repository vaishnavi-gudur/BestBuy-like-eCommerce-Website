#include "storage_mgr.h"
#include<string.h>
#include<stdio.h>
#include<stdlib.h>
#include <time.h>
#include "buffer_mgr.h"
#include "buffer_mgr_stat.h"
#include "dberror.h"
#include "dt.h"

/*Struct for replacement strategy*/
struct buffLRU {
    int pos;
    int pageNum;
    int fixCount;
    bool dirty;
    int age;
    char *data;
    struct buffLRU *next;
} *startLRU = NULL;

struct buffQueue {
    int pos;
    int pageNum;
    int fixCount;
    bool dirty;
    char *data;
    struct buffQueue *next;
} *startFIFO = NULL, *rearFIFO = NULL, *handler = NULL;

struct buffClk {
    int pos;
    int pageNum;
    int fixCount;
    bool dirty;
    char *data;
    int ref;
    struct buffClk *next;
} *startCLK = NULL, *clkHandler = NULL;

/* struct for Frames and Pages in the BufferPool */
typedef struct BPStat {
	
    int readIOCount;// number of pages BP is reading from disk
    int writeIOCount; //  number of pages BP is writing back to disk
    PageNumber *pageNumStat; // times the page is being used after it is pinned
    int *fixCountStat;
    bool *dirtyPageStat;  // true=1 if content of page is modified and false=0 not dirty
} BPStat;

typedef struct bufferFrameStat {
    void *buf;
    struct BPStat *poolStat;
    SM_FileHandle *fh;
} bufferFrameStat;
typedef enum UpdateFrameData {
    UP_Pin = 0,
    UP_Dirty = 1,
    UP_Unpin = 2,
    UP_NOT_Dirty = 3
} UpdateFrameData;


// convenience macros
#define MAKE_LRU_BUFFER()					\
  ((struct buffLRU *) malloc (sizeof(struct buffLRU)))

#define MAKE_QUEUE_BUFFER()				\
  ((struct buffQueue *) malloc (sizeof(struct buffQueue)))

/*
#define MAKE_CLOCK_BUFFER()				\
  ((struct buffClk *) malloc (sizeof(struct buffClk)))
 */

#define MAKE_BUFFER_STAT()				\
  ((bufferFrameStat *) malloc (sizeof(bufferFrameStat)))

#define MAKE_BUFFER_POOL_STAT()				\
  ((BPStat *) malloc (sizeof(BPStat)))


//LRU Page replacement methods

/*
 * AUTHOR: Rajasekhar
 * METHOD: Calculate Length of Buffer
 * INPUT: NONE
 * OUTPUT: Length of buffer
 */
int lengthofBuffer() {
    struct buffLRU *root;
    root = startLRU;
    int count = 0;
    while (root != NULL) {
        count++;
        root = root->next;
    }
    return count;
}

/*
 * AUTHOR: Rajasekhar
 * METHOD: Search for Frame
 * INPUT: PageNUmber
 * OUTPUT: Page Frame Position
 */
int searchForFrame_LRU(PageNumber pNum) {
    int flag = -1;
    struct buffLRU *temp;
    temp = startLRU;
    while (temp != NULL) {
        if (temp->pageNum == pNum) {
            flag = temp->pos;
            break;
        }
        temp = temp->next;
    }
    return flag;
}

/*
 * AUTHOR: Rajasekhar
 * METHOD: LRU algorithm
 * INPUT: NONE
 * OUTPUT: position
 */
int LRU() {
    struct buffLRU *temp;
    temp = startLRU;
    int pos;
    int max;
    max = temp->age; //Sets max to first value
    pos = temp->pos;
    while (temp != NULL) {
        if (temp->age > max) {
            max = temp->age;
            pos = temp->pos;
        }
        temp = temp->next;
    }
    return pos;
}

/*
 * AUTHOR: Rajasekhar
 * METHOD: increment age
 * INPUT: NONE
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
RC increment_age() {
    struct buffLRU *root;
    root = startLRU;
    while (root != NULL) {
        root->age = root->age + 1;
        root = root->next;
    }
    return RC_OK;
}

/*
 * AUTHOR: Rajasekhar
 * METHOD: max position
 * INPUT: NONE
 * OUTPUT: position of the file
 */
int maxpos() {
    struct buffLRU *temp;
    temp = startLRU;
    int pos;
    int max;
    max = temp->pos; //Sets max to first value
    while (temp != NULL) {
        if (temp->pos > max) {
            max = temp->pos;
        }
        temp = temp->next;
    }
    return max;

}

/*
 * AUTHOR: Rajasekhar
 * METHOD: insert into LRU buffer
 * INPUT: File Structure
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
RC insert_into_buffLRU(BM_BufferPool * const bm, BM_PageHandle * const page) {
    bufferFrameStat *stat;
    struct buffLRU *temp, *temp1;
    BPStat *poolStat;
    int searchRes;
    temp = MAKE_LRU_BUFFER();
    temp1 = MAKE_LRU_BUFFER();
    stat = bm->mgmtData;
    poolStat = stat->poolStat;
    int sizeOfBuffer;
    int pos = 0;
    sizeOfBuffer = lengthofBuffer();
    if (startLRU == NULL) {
        temp->pos = 0;
        temp->age = 1;
        temp->dirty = false;
        temp->fixCount = 1;
        temp->data = page->data;
        temp->pageNum = page->pageNum;
        startLRU = temp;
        startLRU->next = NULL;
        poolStat->pageNumStat[0] = temp->pageNum;

    } else {

        searchRes = searchForFrame_LRU(page->pageNum);
        if (searchRes != -1) {
            //element found. page pos returned
            temp = startLRU;
            while (temp != NULL) {
                if (temp->pos == searchRes) {
                    break;
                }
                temp = temp->next;
            }
            increment_age();
            temp->age = 1;
            temp->dirty = false;
            temp->fixCount = temp->fixCount + 1;
            temp->data = page->data;
            poolStat->pageNumStat[searchRes] = temp->pageNum;
        } else {
            //element not found
            if (sizeOfBuffer == bm->numPages) {
                pos = LRU();
                temp = startLRU;
                while (temp != NULL) {
                    if (temp->pos == pos) {
                        break;
                    }
                    temp = temp->next;
                }
                if (temp->dirty == true) {
                    writeBlock(temp->pageNum, stat->fh, temp->data);
                    poolStat->writeIOCount = poolStat->writeIOCount + 1;
                }
                increment_age();
                temp->age = 1;
                temp->pos = pos;
                temp->dirty = false;
                temp->fixCount = 1;
                temp->data = page->data;
                temp->pageNum = page->pageNum;
                poolStat->pageNumStat[pos] = temp->pageNum;
            } else {
                temp1 = startLRU;
                while (temp1 != NULL) {
                    pos = temp1->pos;
                    temp1 = temp1->next;
                }
                //temp = (struct buffLRU *) malloc(sizeof (struct buffLRU));
                pos = maxpos();
                temp->pos = pos + 1;
                temp->dirty = false;
                temp->fixCount = 1;
                increment_age();
                temp->age = 1;
                temp->data = page->data;
                temp->pageNum = page->pageNum;
                temp->next = startLRU;
                startLRU = temp;
                poolStat->pageNumStat[temp->pos] = temp->pageNum;
            }
        }
    }
    return RC_OK;

}

//FIFO Page replacement methods

/*
 * AUTHOR: Amruta
 * METHOD: Length of Fifo Buffer
 * INPUT: NONE
 * OUTPUT: length of the buffer
 */
int len_fifo_buffer() {
    struct buffQueue *temp;
    int count = 0;
    temp = MAKE_QUEUE_BUFFER();
    temp = startFIFO;
    while (temp != NULL) {
        count++;
        temp = temp->next;
    }
    return count;
}

/*
 * AUTHOR: Amruta
 * METHOD: Insert new frame in Fifo Buffer
 * INPUT: BM_BufferPool,BM_PageHandle
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
RC insert_frame_fifo_buffer(BM_BufferPool * const bm, BM_PageHandle * const page) {
    int cur_buffer_size;
    int cur_free_frame=0;
    bufferFrameStat *stat;
    char *pagedata;
    struct buffQueue *temp, *temp1;
    BPStat *poolStat;
    temp = MAKE_QUEUE_BUFFER();
    temp1=MAKE_QUEUE_BUFFER();
    stat = bm->mgmtData;
    poolStat = stat->poolStat;
    if (startFIFO == NULL && rearFIFO == NULL) {
        temp->pos = 0;
        temp->dirty = false;
        temp->fixCount = 1;
        //memcpy(temp->data, page->data, PAGE_SIZE);
        temp->data=page->data;
        temp->pageNum = page->pageNum;
        rearFIFO = temp;
        startFIFO = temp;
        startFIFO->next = rearFIFO;
        rearFIFO->next = NULL;
        poolStat->pageNumStat[temp->pos] = temp->pageNum;
        poolStat->fixCountStat[temp->pos] = temp->fixCount;
        poolStat->dirtyPageStat[temp->pos] = temp->dirty;
        handler = startFIFO;
        poolStat->readIOCount++;
    } else {
        //if the page frame is full
        cur_buffer_size = len_fifo_buffer();
        if (bm->numPages == cur_buffer_size) {
            //temp1 = handler;
            while (cur_free_frame != bm->numPages) {
                if (handler->fixCount == 0) {
                    if (handler->dirty==true) {
                      
                        writeBlock(handler->pageNum, stat->fh, handler->data);
                        poolStat->writeIOCount++;
                    }
                    handler->dirty = false;
                    handler->fixCount = 1;
                    //memcpy(temp->data, page->data, PAGE_SIZE);
                    handler->data=page->data;
                    handler->pageNum = page->pageNum;
                    poolStat->pageNumStat[handler->pos] = handler->pageNum;
                    poolStat->fixCountStat[handler->pos] = handler->fixCount;
                    poolStat->dirtyPageStat[handler->pos] = handler->dirty;
                    poolStat->readIOCount++;
                    break;
                } else {
                    if (handler->next == NULL) {
                        handler = startFIFO;
                    } else {
                        handler = handler->next;
                    }
                    //temp1 = handler;
                    cur_free_frame++;
                }
            }
            if (cur_free_frame == bm->numPages) {
                return RC_BUFFER_EXCEEDED;
            } else {
                if (handler->next == NULL) {
                    handler = startFIFO;
                } else {
                    handler = handler->next;
                }
            }
        }// else add the new page frame
        else {
            temp->next = NULL;
            temp->pos = rearFIFO->pos + 1;
            temp->dirty = false;
            temp->fixCount = 1;
            //memcpy(temp->data, page->data, PAGE_SIZE);
            temp->data=page->data;
            temp->pageNum = page->pageNum;
            rearFIFO->next = temp;
            rearFIFO = temp;
            poolStat->pageNumStat[temp->pos] = temp->pageNum;
            poolStat->fixCountStat[temp->pos] = temp->fixCount;
            poolStat->dirtyPageStat[temp->pos] = temp->dirty;
            poolStat->readIOCount++;
        }
    }
    return RC_OK;
}

/*
 * AUTHOR: Amruta
 * METHOD: Search frame in Fifo Buffer
 * INPUT: PageNUmber
 * OUTPUT: Position of the Page in Frame
 */
int search_frame_fifo_buffer(PageNumber pNum) {
    struct buffQueue *temp;
    int pos = -1;
    temp = MAKE_QUEUE_BUFFER();
    temp = startFIFO;
    while (temp != NULL) {
        if (temp->pageNum == pNum) {
            pos = temp->pos;
            break;
        }
        temp = temp->next;
    }
    return pos;
}

/*
 * AUTHOR: Amruta
 * METHOD: Update frame status in Fifo Buffer
 * INPUT: buffQueue,bufferFrameStat,UpdateFrameData,Position
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
RC update_frame_stat_fifo_buffer(struct buffQueue *buf, bufferFrameStat *stat, UpdateFrameData update_value, int pos) {
    BPStat *poolStat;
    poolStat = stat->poolStat;
    while (buf != NULL) {
        if (buf->pos == pos) {
            switch (update_value) {
                case(UP_Dirty):
                    buf->dirty = true;
                    poolStat->dirtyPageStat[pos] = buf->dirty;
                    break;
                case(UP_Pin):
                    buf->fixCount = buf->fixCount + 1;
                    poolStat->fixCountStat[pos] = buf->fixCount;
                    break;
                case(UP_Unpin):
                    buf->fixCount = buf->fixCount - 1;
                    poolStat->fixCountStat[pos] = buf->fixCount;
                    break;
                case(UP_NOT_Dirty):
                    buf->dirty = false;
                    poolStat->dirtyPageStat[pos] = buf->dirty;
                    break;
            }
        }
        buf = buf->next;
    }
    return RC_OK;
}


//Clock replacement method;

/*
 * AUTHOR: Dharmik
 * METHOD: length of the buffer
 * INPUT: NONE
 * OUTPUT: length of the buffer
 */
int length_Clock_buffer() {
    struct buffClk * tmpClock;
    int count = 0;
    tmpClock = (struct buffClk *) malloc(sizeof (struct buffClk));
    tmpClock = startCLK;
    if (startCLK != NULL) {
        if (tmpClock->next == startCLK) {
            return 1;
        } else {
            while (tmpClock-> next != startCLK) {
                count++;
                tmpClock = tmpClock->next;
            }
        }
    }
    return count;
}

/*
 * AUTHOR: Dharmik
 * METHOD: Search for a frame.
 * INPUT: Page NUmber
 * OUTPUT: position of the Page
 */
int search_frame_clock_buffer(PageNumber pNum) {
    struct buffClk *temp;
    int pos = -1;
    temp = (struct buffClk *) malloc(sizeof (struct buffClk));

    temp = startCLK;
    if (startCLK != NULL) {
        if (temp->next == startCLK && startCLK->pageNum==pNum) {
            pos = startCLK->pos;
        } else {
            while (temp->next != startCLK) {
                if (temp->pageNum == pNum) {
                    pos = temp->pos;
                    break;
                }
                temp = temp->next;
            }
        }
    }
    return pos;
}

/*
 * AUTHOR: Dharmik
 * METHOD:insert frame clock buffer
 * INPUT: BM_BufferPool,BM_PageHandle
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
RC insert_frame_clock_buffer(BM_BufferPool * const bm, BM_PageHandle * const page) {
    bufferFrameStat *stat;
    struct buffClk *temp, *temp1;
    BPStat *poolStat;

    temp = (struct buffClk *) malloc(sizeof (struct buffClk));
    temp1 = (struct buffClk *) malloc(sizeof (struct buffClk));

    stat = bm->mgmtData;
    poolStat = stat->poolStat;

    int pageFound;
    int cur_buffer_size;
    int cur_free_frame = 0;
    temp->data = (char *) malloc(PAGE_SIZE);
    if (startCLK == NULL) {
        temp->pos = 0;
        temp->dirty = false;
        //memcpy(temp->data, page->data, PAGE_SIZE);
        temp->data = page->data;
        temp->pageNum = page->pageNum;
        temp->ref = 1;
        temp->fixCount = 1;
        startCLK = temp;
        startCLK->next = startCLK;
        poolStat->pageNumStat[temp->pos] = temp->pageNum;
        clkHandler = startCLK;
    } else {//search for the requested page
        cur_buffer_size = length_Clock_buffer();
        if (bm->numPages == cur_buffer_size) {
            temp = clkHandler;
            while (cur_free_frame != bm->numPages) {
                if (temp->ref == 0 && temp->fixCount == 0) {
                    if (temp->dirty) {
                        writeBlock(temp->pageNum, stat->fh, temp->data);
                        poolStat->writeIOCount++;
                    }

                    temp->dirty = false;
                    temp->fixCount = 1;
                    //memcpy(temp->data, page->data, PAGE_SIZE);
                    temp->data=page->data;
                    temp->pageNum = page->pageNum;
                    poolStat->pageNumStat[temp->pos] = temp->pageNum;
                    poolStat->fixCountStat[temp->pos] = temp->fixCount;
                    poolStat->dirtyPageStat[temp->pos] = temp->dirty;
                    break;
                } else if (temp->ref == 0 && temp->fixCount == 1) {
                    temp = temp->next;
                } else if (temp->ref == 1) {
                    temp->ref = 0;
                    temp = temp->next;
                }
                cur_free_frame++;
            }
            if (cur_free_frame == bm->numPages) {
                if (temp == startCLK) {
                    if (temp->ref == 0 && temp->fixCount == 0) {
                        if (temp->dirty) {
                            writeBlock(temp->pageNum, stat->fh, temp->data);
                            poolStat->writeIOCount++;
                        }

                        temp->dirty = false;
                        temp->fixCount = 1;
                        //memcpy(temp->data, page->data, PAGE_SIZE);
                        temp->data=page->data;
                        temp->pageNum = page->pageNum;
                        poolStat->pageNumStat[temp->pos] = temp->pageNum;
                        poolStat->fixCountStat[temp->pos] = temp->fixCount;
                        poolStat->dirtyPageStat[temp->pos] = temp->dirty;
                        clkHandler = temp->next;
                    } else {
                        return RC_BUFFER_EXCEEDED;
                    }
                } else {
                    return RC_BUFFER_EXCEEDED;
                }
            } else {
                clkHandler = temp->next;
            }
        } else {
            temp1 = startCLK;
            while (temp1->next != startCLK) {
                temp1 = temp1->next;
            }
            temp->pos = temp1->pos + 1;
            temp->dirty = false;
            //memcpy(temp->data, page->data, PAGE_SIZE);
            temp->data=page->data;
            temp->pageNum = page->pageNum;
            temp->ref = 1;
            temp->fixCount = 1;
            temp->next = startCLK;
            temp1->next = temp;
            poolStat->pageNumStat[temp->pos] = temp->pageNum;
            poolStat->fixCountStat[temp->pos] = temp->fixCount;
            poolStat->dirtyPageStat[temp->pos] = temp->dirty;
        }
    }
    return RC_OK;
}

// Buffer Manager Interface Pool Handling

/*
 * AUTHOR: Rajasekhar & Amruta & Dharmik
 * METHOD: Initialise Buffer Pool
 * INPUT: Buffer POOL;PAGE FRAME;BUFFER SIZE;REPLACEMENT STRATEGY;STRAT DATA
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
RC initBufferPool(BM_BufferPool * const bm, const char *const pageFileName, const int numPages, ReplacementStrategy strategy, void* stratData) {

    SM_FileHandle *fh = (SM_FileHandle *) malloc(PAGE_SIZE);
    memset(fh,0,PAGE_SIZE);
    bufferFrameStat *stat;
    BPStat *poolStat;
    struct buffQueue *temp;
    struct buffClk *tmpClock;
    PageNumber* pageNumArray;
    int* fixCountsArray;
    bool* dirtyMarked;
    int i = 0;
    stat = MAKE_BUFFER_STAT();
    temp = MAKE_QUEUE_BUFFER();
    tmpClock = (struct buffClk *) malloc(sizeof (struct buffClk));
    poolStat = MAKE_BUFFER_POOL_STAT();

    bm->pageFile = (char *) pageFileName;
    bm->numPages = numPages;
    bm->strategy = strategy;

    //initialize the poolStat members
    pageNumArray = (PageNumber *) malloc(sizeof (PageNumber) * bm->numPages);
    fixCountsArray = (int *) malloc(sizeof (int)*numPages);
    dirtyMarked = (bool *) malloc(sizeof (bool) * numPages);
    for (i = 0; i < bm->numPages; i++) {
        pageNumArray[i] = NO_PAGE;
        fixCountsArray[i] = 0;
        dirtyMarked[i] = false;
    }
    poolStat->readIOCount = 0;
    poolStat->writeIOCount = 0;
    poolStat->pageNumStat = pageNumArray;
    poolStat->dirtyPageStat = dirtyMarked;
    poolStat->fixCountStat = fixCountsArray;

    //Open the Page File 
    openPageFile(bm->pageFile, fh);
    switch (strategy) {
        case RS_FIFO:
            stat->fh = fh;
            stat->buf = startFIFO;
            stat->poolStat = poolStat;
            break;
        case RS_LRU:
            stat->fh = fh;
            stat->buf = startLRU;
            stat->poolStat = poolStat;
            break;
        case RS_CLOCK:
            stat->fh = fh;
            stat->buf = startCLK;
            stat->poolStat = poolStat;
            break;
        case RS_LFU:
            //printf("LFU");
            break;
        case RS_LRU_K:
            //printf("LRU-K");
            break;
        default:
            return RC_BUFFER_UNDEFINED_STRATEGY;
    }
    bm->mgmtData = stat;
    return RC_OK;
}

/*
 * AUTHOR: Rajasekhar & Amruta & Dharmik
 * METHOD: shutdownBufferPool
 * INPUT: BM_BufferPool
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
RC shutdownBufferPool(BM_BufferPool * const bm) {
    bufferFrameStat *stat;
    BPStat *poolStat;
    struct buffQueue *temp;
    struct buffLRU *temp1;
    struct buffClk *tmpClock;
    temp = MAKE_QUEUE_BUFFER();
    temp1 = MAKE_LRU_BUFFER();
    tmpClock = (struct buffClk *) malloc(sizeof (struct buffClk));

    stat = bm->mgmtData;
    poolStat = stat->poolStat;

    int pos = 0;
    int numofPages = bm->numPages;

    switch (bm->strategy) {
        case RS_FIFO:
            temp = startFIFO;
            while (temp != NULL) {
                if (temp->dirty) {
                    writeBlock(temp->pageNum, stat->fh, temp->data);
                    poolStat->writeIOCount = poolStat->writeIOCount + 1;
                }
                temp = temp->next;
            }
            break;

        case RS_LRU:
            temp1 = startLRU;
            while (temp1 != NULL) {
                if (temp1->dirty) {
                    writeBlock(temp1->pageNum, stat->fh, temp1->data);
                    poolStat->writeIOCount = poolStat->writeIOCount + 1;
                }
                temp1 = temp1->next;
            }
            break;
        case RS_CLOCK:
            tmpClock = startCLK;
            if (tmpClock->next == startCLK) {
                writeBlock(startCLK->pageNum, stat->fh, startCLK->data);
                poolStat->writeIOCount = poolStat->writeIOCount + 1;
            } else {
                while (tmpClock->next != startCLK) {  
                    if (tmpClock->dirty) {
                        writeBlock(tmpClock->pageNum, stat->fh, tmpClock->data);
                        poolStat->writeIOCount = poolStat->writeIOCount + 1;
                    }
                    tmpClock = tmpClock->next;
                }
            }
            break;
        case RS_LFU:
            //printf("LFU");
            break;
        case RS_LRU_K:
            //printf("LRU-K");
            break;
        default:
            return RC_BUFFER_UNDEFINED_STRATEGY;
    }

    startFIFO = NULL;
    rearFIFO = NULL;
    handler = NULL;
    startLRU = NULL;
    //startCLK = NULL;
    closePageFile(stat->fh);
    free(temp);
    free(temp1);
    free(poolStat->dirtyPageStat);
    free(poolStat->fixCountStat);
    free(poolStat->pageNumStat);
    free(stat->fh);
    free(stat->buf);
    free(stat->poolStat);
    free(bm->mgmtData);
  
    //free(stat);
    
    return RC_OK;
}

/*
 * AUTHOR: Dharmik
 * METHOD: forceFlushPool
 * INPUT: BM_BufferPool
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */

RC forceFlushPool(BM_BufferPool * const bm) {

    bufferFrameStat *stat;
    BPStat *poolStat;
    struct buffLRU *temp1;
    struct buffClk *tmpClock;
    struct buffQueue *temp;
    int numberofPages = bm->numPages;
    int pos;
    temp = MAKE_QUEUE_BUFFER();
    temp1 = MAKE_LRU_BUFFER();
    tmpClock = (struct buffClk *) malloc(sizeof (struct buffClk));

    stat = bm->mgmtData;
    poolStat = stat->poolStat;

    switch (bm->strategy) {
        case RS_FIFO:
            temp = startFIFO;
            while (temp != NULL) {
                if (temp->dirty) {
                    writeBlock(temp->pageNum, stat->fh, temp->data);
                    poolStat->writeIOCount++;
                    temp->dirty = false;
                    poolStat->dirtyPageStat[temp->pos] = temp->dirty;
                }
                temp = temp->next;
            }
            break;
        case RS_LRU:
            temp1 = startLRU;
            while (temp1 != NULL) {
                if (temp1->dirty) {
                    writeBlock(temp1->pageNum, stat->fh, temp1->data);
                    poolStat->writeIOCount = poolStat->writeIOCount + 1;
                }
                temp1 = temp1->next;
            }
            break;
        case RS_CLOCK:
            tmpClock = startCLK;
            if (tmpClock->next == startCLK) {
                writeBlock(startCLK->pageNum, stat->fh, startCLK->data);
                poolStat->writeIOCount = poolStat->writeIOCount + 1;
            } else {
                while (tmpClock->next != startCLK) {
                    if (tmpClock->dirty) {
                        writeBlock(tmpClock->pageNum, stat->fh, tmpClock->data);
                        poolStat->writeIOCount = poolStat->writeIOCount + 1;
                    }
                    tmpClock = tmpClock->next;
                }
            }
            break;
        case RS_LFU:
            //printf("LFU");
            break;
        case RS_LRU_K:
            //printf("LRU-K");
            break;
        default:
            return RC_BUFFER_UNDEFINED_STRATEGY;
    }
    free(temp);
    free(temp1);
    return RC_OK;
}

// Buffer Manager Interface Access Pages

/*
 * AUTHOR: Rajasekhar & Amruta & Dharmik
 * METHOD: Pin Pages
 * INPUT: BM_BufferPool,BM_PageHandle,PageNumber
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
RC pinPage(BM_BufferPool * const bm, BM_PageHandle * const page, const PageNumber pageNum) {
    SM_PageHandle ph;

    bufferFrameStat *stat;
    struct buffLRU *temp;
    struct buffClk *bufClock;
    struct buffLRU *node;
    BPStat *poolStat;
    int pos = -1;

    ph = (SM_PageHandle) malloc(PAGE_SIZE);
    memset(ph,0,PAGE_SIZE);
    temp = MAKE_LRU_BUFFER();
    bufClock = (struct buffClk *) malloc(sizeof (struct buffClk));
    node = MAKE_LRU_BUFFER();


    stat = bm->mgmtData;
    poolStat = stat->poolStat;
    node = stat->buf;

    page->pageNum = pageNum;
    switch (bm->strategy) {
        case RS_FIFO:
            pos = search_frame_fifo_buffer(page->pageNum);
             //if(searchForFrame_LRU(page->pageNum)==-1){
            if(searchForFrame_LRU(page->pageNum)==-1){
             readBlock(pageNum, stat->fh, ph);
             page->data=ph;
           
            }
            if (pos != -1) {
                update_frame_stat_fifo_buffer(startFIFO, stat, UP_Pin, pos);
            } else {
                insert_frame_fifo_buffer(bm, page);
            }
            break;
        case RS_LRU:
             if(searchForFrame_LRU(page->pageNum)==-1){
             readBlock(pageNum, stat->fh, ph);
             poolStat->readIOCount = poolStat->readIOCount+1;
             page->data=ph;
            }
            insert_into_buffLRU(bm,page);
            break;
        case RS_CLOCK:
            pos = search_frame_clock_buffer(page->pageNum);
            bufClock = startCLK;
            if (pos != -1) {
                if (pos == startCLK->pos) {
                    startCLK->fixCount++;
                    poolStat->dirtyPageStat[startCLK->pos] = startCLK->fixCount;
                } else {
                    while (bufClock->next != startCLK) {
                        if (bufClock->pos == pos) {
                            bufClock->fixCount++;
                            poolStat->fixCountStat[bufClock->pos] = bufClock->fixCount;
                            poolStat->readIOCount++;
                        }
                        bufClock = bufClock->next;
                    }
                }
            } else {
                insert_frame_clock_buffer(bm, page);
                poolStat->readIOCount++;
            }
            break;
        case RS_LFU:
            //printf("LFU");
            break;
        case RS_LRU_K:
            //printf("LRU-K");
            break;
        default:
            return RC_BUFFER_UNDEFINED_STRATEGY;
    }
    free(temp);
    free(node);
    return RC_OK;
}

/*
 * AUTHOR: Rajasekhar & Amruta & Dharmik
 * METHOD: markDirty
 * INPUT: BM_BufferPool,BM_PageHandle
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
RC markDirty(BM_BufferPool * const bm, BM_PageHandle * const page) {
    bufferFrameStat *stat;
    BPStat *poolStat;
    struct buffLRU *node;
    struct buffQueue *buf;
    struct buffClk *bufClock;
    int pos = -1;
    buf = MAKE_QUEUE_BUFFER();
    bufClock = (struct buffClk *) malloc(sizeof (struct buffClk));
    node = MAKE_LRU_BUFFER();

    stat = bm->mgmtData;
    poolStat = stat->poolStat;

    switch (bm->strategy) {
        case RS_FIFO:
            pos = search_frame_fifo_buffer(page->pageNum);
            buf = startFIFO;
            if (pos != -1) {
                while (buf != NULL) {
                    if (buf->pos == pos) {
                        //memcpy(buf->data, page->data, PAGE_SIZE);
                        buf->data=page->data;
                        update_frame_stat_fifo_buffer(startFIFO, stat, UP_Dirty, pos);
                    }
                    buf = buf->next;
                }
            }
            break;
        case RS_LRU:
            pos = LRU(bm, page->pageNum);
            node = startLRU;
            while (node != NULL) {
                if (node->pos == pos) {
                    node->dirty = true;
                    poolStat->dirtyPageStat[pos] = node->dirty;
                }
                node = node->next;
            }
            break;
        case RS_CLOCK:
            pos = search_frame_clock_buffer(page->pageNum);
            bufClock = startCLK;
            if (pos != -1) {
                if (pos == startCLK->pos) {
                    //memcpy(startCLK->data, page->data, PAGE_SIZE);
                    startCLK->data=page->data;
                    startCLK->dirty = true;
                    poolStat->dirtyPageStat[pos] = bufClock->dirty;
                } else {
                    while (bufClock->next != startCLK) {
                        if (bufClock->pos == pos) {
                            //memcpy(buf->data, page->data, PAGE_SIZE);
                            buf->data=page->data;
                            bufClock->dirty = true;
                            poolStat->dirtyPageStat[pos] = bufClock->dirty;
                        }
                        bufClock = bufClock->next;
                    }
                }
            }
            break;
        case RS_LFU:
            //printf("LFU");
            break;
        case RS_LRU_K:
            //printf("LRU-K");
            break;
        default:
            return RC_BUFFER_UNDEFINED_STRATEGY;
    }

    return RC_OK;
}

/*
 
 * METHOD: Unpin page 
 * INPUT: BM_BufferPool,BM_PageHandle
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
RC unpinPage(BM_BufferPool * const bm, BM_PageHandle * const page) {
    bufferFrameStat *stat;
    struct buffQueue *buf;
    struct buffLRU *bufLRU;
    struct buffClk *bufClock;
    BPStat *poolStat;
    int pos = -1;
    int pinCount = 0;
    buf = MAKE_QUEUE_BUFFER();
    bufLRU = MAKE_LRU_BUFFER();
    bufClock = (struct buffClk *) malloc(sizeof (struct buffClk));

    stat = bm->mgmtData;
    poolStat = stat->poolStat;
    switch (bm->strategy) {
        case RS_FIFO:
            pos = search_frame_fifo_buffer(page->pageNum);
            buf = startFIFO;
            while (buf != NULL) {
                if (buf->pos == pos) {
                    update_frame_stat_fifo_buffer(startFIFO, stat, UP_Unpin, pos);
                }
                buf = buf->next;
            }
            break;
        case RS_LRU:
            pos = searchForFrame_LRU(page->pageNum);
            bufLRU = startLRU;
            while (bufLRU != NULL) {
                if (bufLRU->pos == pos) {
                    bufLRU->fixCount = bufLRU->fixCount - 1;
                    poolStat->fixCountStat[bufLRU->pos] = bufLRU->fixCount;
                }
                bufLRU = bufLRU->next;
            }
            break;
        case RS_CLOCK:
            pos = search_frame_clock_buffer(page->pageNum);
            bufClock = startCLK;
            if (pos != -1) {
                if (pos == startCLK->pos) {
                    startCLK->fixCount = startCLK->fixCount - 1;
                    poolStat->fixCountStat[startCLK->pos] = startCLK->fixCount;
                } else {
                    while (bufClock->next != startCLK) {
                        if (bufClock->pos == pos) {
                            bufClock->fixCount = bufClock->fixCount - 1;
                            poolStat->fixCountStat[bufClock->pos] = bufClock->fixCount;
                        }
                        bufClock = bufClock->next;
                    }
                }
            }
            break;
        case RS_LFU:
            //printf("LFU");
            break;
        case RS_LRU_K:
            //printf("LRU-K");
            break;
        default:
            return RC_BUFFER_UNDEFINED_STRATEGY;
    }
    free(bufLRU);
    free(buf);
    free(bufClock);
    return RC_OK;
}

/*
 
 * METHOD: write date to disk
 * INPUT: BM_BufferPool,BM_PageHandle
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
RC forcePage(BM_BufferPool * const bm, BM_PageHandle * const page) {
    bufferFrameStat *stat;
    BPStat *poolStat;
    struct buffQueue *temp;
    struct buffLRU *bufLru;
    struct buffClk *bufClock;

    bufClock = (struct buffClk *) malloc(sizeof (struct buffClk));
    bufLru = MAKE_LRU_BUFFER();
    temp = MAKE_QUEUE_BUFFER();

    stat = bm->mgmtData;
    poolStat = stat->poolStat;

    int pos = -1;
    writeBlock(page->pageNum, stat->fh, page->data);

    switch (bm->strategy) {
        case RS_FIFO:
            pos = search_frame_fifo_buffer(page->pageNum);
            temp = startFIFO;
            if (pos != -1) {
                while (temp != NULL) {
                    if (temp->pos == pos) {
                        //memcpy(temp->data, page->data, PAGE_SIZE);
                        temp->data=page->data;
                        writeBlock(temp->pageNum, stat->fh, temp->data);
                        poolStat->writeIOCount++;
                        update_frame_stat_fifo_buffer(stat->buf, stat, UP_NOT_Dirty, pos);
                    }
                    temp = temp->next;
                }
            }
            break;
        case RS_LRU:
            pos = searchForFrame_LRU(page->pageNum);
            bufLru = startLRU;
            if (pos != -1) {
                while (bufLru != NULL) {
                    if (bufLru->pos == pos) {
                        //memcpy(bufLru->data, page->data, PAGE_SIZE);
                        bufLru->data=page->data;
                        writeBlock(bufLru->pageNum, stat->fh, bufLru->data);
                        bufLru->dirty = false;
                        poolStat->dirtyPageStat[pos] = bufLru->dirty;
                        poolStat->writeIOCount++;
                    }
                    bufLru = bufLru->next;
                }
            }
            break;
        case RS_CLOCK:

            pos = search_frame_clock_buffer(page->pageNum);
            bufClock = startCLK;
            if (pos != -1) {
                if (pos == startCLK->pos) {
                    startCLK->fixCount = startCLK->fixCount - 1;
                    poolStat->fixCountStat[startCLK->pos] = startCLK->fixCount;
                } else {

                    while (bufClock->next != startCLK) {
                        if (bufClock->pos == pos) {
                            //memcpy(bufClock->data, page->data, PAGE_SIZE);
                            bufClock->data=page->data;
                            writeBlock(bufClock->pageNum, stat->fh, bufClock->data);
                            bufClock->dirty = false;
                            poolStat->dirtyPageStat[pos] = bufClock->dirty;
                            poolStat->writeIOCount++;
                        }
                        bufClock = bufClock->next;
                    }
                }
            }

            break;
        case RS_LFU:
            //printf("LFU");
            break;
        case RS_LRU_K:
            //printf("LRU-K");
            break;
        default:
            return RC_BUFFER_UNDEFINED_STRATEGY;
    }
    free(bufLru);
    return RC_OK;
}

// Statistics Interface

/*

 * METHOD: get Frame Counts
 * INPUT: BM_BufferPool 
 * OUTPUT: PageNumber *
 */
PageNumber *getFrameContents(BM_BufferPool * const bm) {
    bufferFrameStat *stat;
    BPStat *poolStat;

    stat = bm->mgmtData;
    poolStat = stat->poolStat;
    return poolStat->pageNumStat;
}

/*

 * METHOD: get Fix Counts
 * INPUT: BM_BufferPool
 * OUTPUT: int *
 */
int *getFixCounts(BM_BufferPool * const bm) {
    bufferFrameStat *stat;
    BPStat *poolStat;

    stat = bm->mgmtData;
    poolStat = stat->poolStat;

    return poolStat->fixCountStat;

}

/*

 * METHOD: dirty marked statistics
 * INPUT: BM_BufferPool
 * OUTPUT: bool *
 */
bool *getDirtyFlags(BM_BufferPool * const bm) {
    bufferFrameStat *stat;
    BPStat *poolStat;

    stat = bm->mgmtData;
    poolStat = stat->poolStat;

    return poolStat->dirtyPageStat;
}

/*
 * METHOD: get Number of read from Disk
 * INPUT: BM_BufferPool
 * OUTPUT: int
 */
int getNumReadIO(BM_BufferPool * const bm) {
    bufferFrameStat *stat;
    BPStat *poolStat;
    stat = bm->mgmtData;
    poolStat = stat->poolStat;

    return poolStat->readIOCount;
}

/* get Number of writes to disk */
int getNumWriteIO(BM_BufferPool * const bm) {
    bufferFrameStat *stat;
    BPStat *poolStat;

    stat = bm->mgmtData;
    poolStat = stat->poolStat;

    return poolStat->writeIOCount;
}

