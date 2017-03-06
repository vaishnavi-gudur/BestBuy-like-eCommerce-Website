***********************************
Problem Statement		
***********************************
The goal of this assignment is to implement a simple storage manager - a module that is capable of reading blocks 
from a file on disk into memory and writing blocks from memory to a file on disk. The storage manager deals with
pages (blocks) of fixed size (PAGE_SIZE). In addition to reading and writing pages from a file, it provides methods
for creating, opening, and closing files. The storage manager has to maintain several types of information for an
open file: The number of total pages in the file, the current page position (for reading and writing), the file name,
and a POSIX file descriptor or FILE pointer.

***********************************
File List
***********************************
1)storage_mgr.c
2)storage_mgr.h
3)dberror.c
4)dberror.h
5)test_helper.h
6)test_assign2_1.c
7)README.txt
8)Makefile
9)buffer_mgr.h
10)buffer_mgr.c
11)buffer_mgr_stat.c
12)buffer_mgr_stat.h
13)dt.h


**************************************
Instructions To Run The Code
**************************************
In order to setup the environment to run test cases
----------------------------------------------------
Run : make -f Makefile

With default test cases
--------------------------
Compile : make assign2_1
Run : ./assign2_1

Clean up the old output files: make clean


****************************************
Description of Data Types and Structures
****************************************
typedef int PageNumber;
#define NO_PAGE -1

typedef struct BM_BufferPool {
	char *pageFile;
	int numPages;
	ReplacementStrategy strategy;
	void *mgmtData; // use this one to store the bookkeeping info your buffer
	// manager needs for a buffer pool
} BM_BufferPool;

typedef struct BM_PageHandle {
	PageNumber pageNum;
	char *data;
} BM_PageHandle;

/* struct for Frames and Pages in the BufferPool */
typedef struct BM_FP_Data {
  BM_PageHandle **pageHandle; //Page handler for page data of a page in a frame in BP
  SM_FileHandle smFHandle; //Storage manager file handler
  int dirtyPageCount; // number of dirty pages in a frame in BP
  bool *dirtyBit;   // true=1 if content of page is modified and false=0 not dirty
  int pinnedPageCount; //number of pages pinned in a frame
  PageNumber *pageIndex; //page index in the frame
  PageNumber *fixedCount; //number of users accessing a given page in a frame
  int readIOCount; // number of pages BP is reading from disk
  int writeIOCount; //  number of pages BP is writing back to disk
  int *freqOfPage; // times the page is being used after it is pinned

} BM_FP_Data;



****************************************
Description of the Functions
****************************************

Buffer Pool Functions:
_______________________

FUNCTION :  initBufferPool
------------------------------------
1) Creates a new buffer pool with numPages page frames using the page replacement strategy strategy. 
2) The pool is used to cache pages from the page file with name pageFileName. Initially, all page frames should be empty. The page file should already exist, i.e., this method should not generate a new page file. 
3) stratData can be used to pass parameters for the page replacement strategy. For example, for LRU-k this could be the parameter k.


FUNCTION :  shutdownBufferPool
-------------------------------------
1) Destroys a buffer pool. 
2) This method frees up all resources associated with buffer pool. For example, it should free the memory allocated for page frames. If the buffer pool contains any dirty pages, then these pages should be written back to disk before destroying the pool. 
3) Gives an error to shutdown a buffer pool that has pinned pages.


FUNCTION  :  forceFlushPool
--------------------------------------
1) Causes all dirty pages (with fix count 0) from the buffer pool to be written to disk.


Page Management Functions
___________________________


FUNCTION : pinPage
-----------------------------
1) pins the page with page number pageNum. 
2) The buffer manager is responsible to set the pageNum field of the page handle passed to the method. 
3) Similarly, the data field should point to the page frame the page is stored in (the area in memory storing the content of the page).


FUNCTION : unpinPage 
-------------------------------
1) unpins the page page. 
2) The pageNum field of page is used to figure out which page to unpin. 


FUNCTION : markDirty
-------------------------------
1) Marks a page as dirty.


FUNCTION : forcePage 
-------------------------------
1) Writes the current content of the page back to the page file on disk.



Statistics Functions
____________________________


FUNCTION : getFrameContents
---------------------------
1) Returns an array of PageNumbers (of size numPages) where the ith element is the number of the page stored in the ith page frame. 
2) An empty page frame is represented using the constant NO_PAGE. 


FUNCTION : getDirtyFlags
------------------------------
1) Returns an array of bools (of size numPages) where the ith element is TRUE if the page stored in the ith page frame is dirty. Empty page frames are considered as clean.


FUNCTION : getFixCounts
-----------------------------
1) Returns an array of ints (of size numPages) where the ith element is the fix count of the page stored in the ith page frame. Return 0 for empty page frames


FUNCTION : getNumReadIO
---------------------------
1) Returns the number of pages that have been read from disk since a buffer pool has been initialized. The code is responsible to initializing this statistic at pool creating time and update whenever a page is read from the page file into a page frame.


FUNCTION : getNumWriteIO
---------------------------
1) Returns the number of pages written to the page file since the buffer pool has been initialized.



***********************************
Additional Features
***********************************

Implementation of Threading
----------------------------
pthread_mutex_t GLOBAL_LOCK;
pthread_mutex_t PAGE_FRAME_LOCK;

Additional Functions
---------------------
1) writingDPtoDisk - To write all dirty pages to the disk.
2) len_fifo_buffer - Length of fifo buffer
3) len_lru_buffer - Length of lru buffer
4) len_clock_buffer - Length of clock buffer
5)MAKE_LRU_BUFF() - Make lru buffer
6)MAKE_FIFO_BUFF() - Make fifo buffer
7)MAKE_CLOCK_BUFF() - Make clock buffer
8)insert_frame_lru- fifo logic
9)insert_frame_fifo-lru logic
10)insert_frame_clock-clock logic


**********************************
Group Members
**********************************
1)Sharel Clavy Pereira (A20382007,spereira2@hawk.iit.edu)(Team Leader)
2)Navjot Sharma (A20376692, nsharma11@hawk.iit.edu)
3)Harshaa Bajaj (A20378813,hbajaj@hawk.iit.edu)
4)Vaishnavi Gudur (A20384900,vgudur@hawk.iit.edu)

***********************************
