#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "storage_mgr.h"
#include "dberror.h"
#include <fcntl.h>

#include <unistd.h>
#include <errno.h>
#include<sys/stat.h>

static int initSM;
SM_FileHandle *fh;
extern RC checkFile(SM_FileHandle *fHandle);
extern RC pageNumbers(int pno);

/**************************Page Manipulation***************************************/

void initStorageManager(void)   //Initializing storage manager
{
    initSM = 1;
}

RC createPageFile(char* fileName)
{
    FILE *filePtr = fopen(fileName, "wb");
    if( initSM == 1)
    {
		fseek(filePtr, 0L, SEEK_SET);
		char *page = malloc(PAGE_SIZE); //allocating memory of page size
		memset(page, 0, PAGE_SIZE);
		fwrite(page,PAGE_SIZE,1, filePtr);
        if(filePtr  != NULL)
        {
            free(page);
            return RC_OK;
        }
        else
        {
            return RC_FILE_CREATION_FAILED;
        }

    }
    else
    {
        return RC_STORAGE_MANAGER_NOT_INITIALIZED;
    }

}

RC openPageFile (char *fileName, SM_FileHandle *fHandle){
	FILE *filePtr =NULL;
	fHandle -> mgmtInfo = (void *) malloc(PAGE_SIZE);

    if (fHandle -> mgmtInfo == NULL) {
     	return RC_FILE_INFO_UNAVAILABLE;
    }
	struct stat buff;
    if (stat(fileName, &buff) != 0) {
        if (errno == ENOENT) {
            return RC_FILE_NOT_FOUND;
        }
    }
	int f = open(fileName, O_RDWR);
    filePtr = fdopen(f, "wb");
    if(f == -1){ 
	return RC_FILE_NOT_OPENED;
	}
    else
    {
		/* get the file name, position and to find the totalNumPages- move pointer to point the end of the file,
        calculate the total bytes of this file and move pointer back */
		
        fHandle -> fileName = fileName;
		fHandle -> mgmtInfo = filePtr;
        fHandle->totalNumPages = PAGE_SIZE;
		fHandle->curPagePos = 0;
        fHandle->totalNumPages = 1;
        fseek(filePtr, 0L, SEEK_SET);
        fwrite(fHandle,PAGE_SIZE,1, filePtr);
        return RC_OK;

    }

}

RC closePageFile(SM_FileHandle *fHandle){
    FILE *filePtr = fHandle->mgmtInfo;
	
    if (filePtr == NULL) {
		return RC_FILE_NOT_OPENED;
	} 
	else {
        if (fclose(filePtr) == 0) {
            filePtr = 0;
            fHandle->mgmtInfo = NULL;
            free(fHandle->mgmtInfo);
           
        }
    }
	
	return RC_OK;

}

RC destroyPageFile(char *fileName){
	int result = remove(fileName);
	if (result != 0){
		return RC_FILE_DESTROY_FAILED;
		}
	else{ 
	return RC_OK;
	}
}

/**************************Reading file************************************/

/* getBlockPos gives the current pointer of the file handler*/

int getBlockPos(SM_FileHandle *fHandle) {
    int pos = -1;
    FILE *fptr = NULL;
    if (fHandle->fileName == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    } else {
        fptr = fHandle->mgmtInfo;
        if (fptr == NULL) {
            return RC_FILE_NOT_OPENED;
        } else {
            pos = ftell(fptr);
            if (pos == -1L) {
                return RC_INVALID_POSITION;
            }
        }
    }
    return RC_OK;
}

/*Below functions read current/First/Previous/Next/Last block of pages */

RC readCurrentBlock(SM_FileHandle* fHandle, SM_PageHandle memPage) {
    int curPos = -1;
    FILE *fptr = NULL;
    if (fHandle->fileName == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    } else {
        fptr = fHandle->mgmtInfo;
        if (fptr == NULL) {
            return RC_FILE_NOT_OPENED;
        } else {
            curPos = fHandle->curPagePos;
            if (curPos == -1L) {
                return RC_INVALID_POSITION;
            } else {
                if ((fseek(fptr, (curPos + 1) * PAGE_SIZE, SEEK_SET)) < 0) {
                    return RC_FILE_SEEK_ERROR;
                } else {
                    if ((fread(memPage, PAGE_SIZE, 1, fptr)) == -1) {
                        return RC_FILE_NOT_READ_COMPLETELY;
                    } else {
                        curPos = (ftell(fptr) / PAGE_SIZE) - 1;
                        fHandle->curPagePos = curPos;
                    }
                }
            }
        }
    }
    return RC_OK;
}


RC readPreviousBlock(SM_FileHandle* fHandle, SM_PageHandle memPage) {
    int curPos = -1;
    FILE *fptr = NULL;
    if (fHandle->fileName == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    } else {
        fptr = fHandle->mgmtInfo;
        if (fptr == NULL) {
            return RC_FILE_NOT_OPENED;
        } else {
            curPos = fHandle->curPagePos;
            if (curPos == -1L) {
                return RC_INVALID_POSITION;
            } else {
                if ((fseek(fptr, curPos * PAGE_SIZE, SEEK_SET)) < 0) {
                    return RC_FILE_SEEK_ERROR;
                } else {
                    if ((fread(memPage, PAGE_SIZE, 1, fptr)) == -1) {
                        return RC_FILE_NOT_READ_COMPLETELY;
                    } else {
                        curPos = (ftell(fptr) / PAGE_SIZE) - 1;
                        fHandle->curPagePos = curPos;
                    }
                }
            }
        }
    }
    return RC_OK;
}

RC readNextBlock(SM_FileHandle* fHandle, SM_PageHandle memPage) {
    int curPos = -1;
    FILE *fptr = NULL;
    if (fHandle->fileName == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    } else {
        fptr = fHandle->mgmtInfo;
        if (fptr == NULL) {
            return RC_FILE_NOT_OPENED;
        } else {
            curPos = fHandle->curPagePos;
            if (curPos == -1L) {
                return RC_INVALID_POSITION;
            } else {
                if ((fseek(fptr, (curPos + 2) * PAGE_SIZE, SEEK_SET)) < 0) {
                    return RC_FILE_SEEK_ERROR;
                } else {
                    if ((fread(memPage, PAGE_SIZE, 1, fptr)) == -1) {
                        return RC_FILE_NOT_READ_COMPLETELY;
                    } else {
                        curPos = (ftell(fptr) / PAGE_SIZE) - 1;
                        fHandle->curPagePos = curPos;
                    }
                }
            }
        }
    }
    return RC_OK;
}

RC readLastBlock(SM_FileHandle* fHandle, SM_PageHandle memPage) {
    int curPos = -1;
    FILE *fptr = NULL;
    if (fHandle->fileName == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    } else {
        fptr = fHandle->mgmtInfo;
        if (fptr == NULL) {
            return RC_FILE_NOT_OPENED;
        } else {
			
            if ((fseek(fptr, 0l, SEEK_END)) < 0) {
                return RC_FILE_SEEK_ERROR;
            } else {
                if ((fread(memPage, PAGE_SIZE, 1, fptr)) == -1) {
                    return RC_FILE_NOT_READ_COMPLETELY;
                } else {
                    curPos = (ftell(fptr) / PAGE_SIZE);
                    fHandle->curPagePos = curPos - 1;
                }
            }
        }
    }
    return RC_OK;
}


RC readFirstBlock(SM_FileHandle* fHandle, SM_PageHandle memPage) {
    int curPos = -1;
    FILE *fptr = NULL;
    if (fHandle->fileName == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    } else {
        fptr = fHandle->mgmtInfo;
        if (fptr == NULL) {
            return RC_FILE_NOT_OPENED;
        } else {
			
            if ((fseek(fptr, PAGE_SIZE, SEEK_SET)) < 0) {
                return RC_FILE_SEEK_ERROR;
            } else {
                if ((fread(memPage, PAGE_SIZE, 1, fptr)) == -1) {
                    return RC_FILE_NOT_READ_COMPLETELY;
                } else {
                    curPos = (ftell(fptr) / PAGE_SIZE) - 1;
                    fHandle->curPagePos = curPos;
                }
            }
        }
    }
    return RC_OK;
}


RC readBlock(int pageNum, SM_FileHandle* fHandle, SM_PageHandle memPage) {
    int curPos = -1;
    FILE *fptr = NULL;
    if (fHandle->fileName == NULL) {
        return RC_FILE_HANDLE_NOT_INIT;
    } else {
        fptr = fHandle->mgmtInfo;
        if (fptr == NULL) {
            return RC_FILE_NOT_OPENED;
        } else {
            if ((fseek(fptr, (pageNum + 1) * PAGE_SIZE, SEEK_SET)) < 0) {
                return RC_FILE_SEEK_ERROR;
            } else {
                if ((fread(memPage, PAGE_SIZE, 1, fptr)) == -1) {
                    return RC_FILE_NOT_READ_COMPLETELY;
                } else {
                    curPos = (ftell(fptr) / PAGE_SIZE) - 1;
                    fHandle->curPagePos = curPos;
                }
            }
        }
    }
    return RC_OK;
}


/*********************Writing to the block********************************************/

RC writeBlock(int pageNum, SM_FileHandle *fHandle, SM_PageHandle memPag) 
{
	int curPos = -1;
    FILE *fptr = fHandle->mgmtInfo;
 RC numval = pageNumbers(pageNum);	//checking if the page number is valid
	if (numval != RC_OK) 
		return numval;
	RC filestat = checkFile(fHandle);	//checking intial conditions related to file
	if (filestat != RC_OK) 
		return filestat;
	
            if ((fseek(fptr, (pageNum + 1) * PAGE_SIZE, SEEK_SET)) < 0) {
                
				return RC_FILE_SEEK_ERROR;
            } 
			else {
                if ((fwrite(memPag, PAGE_SIZE, 1, fptr)) == -1) {
                    return RC_WRITE_FAILED;
                } 
				else {
                    curPos = (ftell(fptr) / PAGE_SIZE) - 1;
                    fHandle->curPagePos = curPos;
                    fHandle->totalNumPages = fHandle->totalNumPages + 1;
					//printf("total num of pages after writing a block:%d\n ",fHandle->totalNumPages);
    
                }
            }
    return RC_OK;
}
	

RC writeCurrentBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) 
{
	int curPos = -1;
    FILE *fptr = fHandle->mgmtInfo;
  RC filestat = checkFile(fHandle);	//checking intial contidions related to file
	if (filestat!= RC_OK) 
		return filestat;
	
            curPos = fHandle->curPagePos;  //// get the current page position
            if (curPos < 0) {    // if position value is negative return error
                return RC_INVALID_POSITION;
				} else {
                
                if ((fseek(fptr, (curPos + 1) * PAGE_SIZE, SEEK_SET)) < 0) {
                    return RC_FILE_SEEK_ERROR;
                } else {
                    if (-1 == (fwrite(memPage, PAGE_SIZE, 1, fptr)) ) {     //if the write function fails
                        return RC_WRITE_FAILED;
                    } else {
                        curPos = (ftell(fptr) / PAGE_SIZE) - 1;
                        fHandle->curPagePos = curPos;
                        fHandle->totalNumPages = fHandle->totalNumPages + 1;
                    }
                }
            }
      
  
    return RC_OK;
	}
	


RC appendEmptyBlock(SM_FileHandle *fHandle) 
{
	 char buf[PAGE_SIZE];
    FILE *fptr = NULL;          //create a pointer to the file
    fptr = fHandle->mgmtInfo;
    if (fptr == NULL) {
        return RC_FILE_INFO_UNAVAILABLE;
    } else {
        int totalNumberPages = fHandle->totalNumPages;
        if (totalNumberPages == 0) {
            return RC_NO_PAGES_ERROR;
        } else {
            if ((fseek(fptr, 0L, SEEK_END)) < 0) {
                return RC_FILE_SEEK_ERROR;
            } else {
                if ((fwrite(buf, PAGE_SIZE, 1, fptr)) == -1) {
                    return RC_WRITE_FAILED;
                } else {
                    int curPos = (ftell(fptr) / PAGE_SIZE);
                    fHandle->curPagePos = curPos;
                    fHandle->totalNumPages = fHandle->totalNumPages + 1;
                }
            }
        }
    }
    return RC_OK;
}


RC ensureCapacity(int numberOfPages, SM_FileHandle *fHandle) 
{    
	char buf[PAGE_SIZE];
    FILE *fptr = NULL;
    int i;
    fptr = fHandle->mgmtInfo;
    if (fptr == NULL) {
        return RC_FILE_INFO_UNAVAILABLE;
    } else {
        int totalNumberPages = fHandle->totalNumPages;
        if (totalNumberPages < numberOfPages) {         // check if total number of pages is less than the requirement
            fseek(fptr, 0L, SEEK_END);
            for (i = 0; i < (numberOfPages - totalNumberPages); i++) {
                fwrite(buf, PAGE_SIZE, 1, fptr);
            }
            fHandle->totalNumPages = numberOfPages;
        }
    }
    return RC_OK;

}


RC checkFile(SM_FileHandle *fHandle) 
{
	FILE *fptr = (FILE*) fHandle->mgmtInfo;

	if (fHandle == NULL || fHandle->fileName == NULL|| fptr== NULL || fHandle->totalNumPages < 0 || fHandle->curPagePos < 0) 		
		return RC_FILE_HANDLE_NOT_INITIATED;
	else 
return RC_OK;
}

RC pageNumbers(int pno) 
{
	if (pno >= 0) 	// page number can't be negative
		return RC_OK;
	else 
		return RC_PAGENUM_INVALID;
}

