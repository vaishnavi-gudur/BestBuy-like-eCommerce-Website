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
/*****************************************************************/
/********************Manipulation of Page*************************/
/****************************************************************/

// extern void initStorageManager(void); => Initializing the storage manager
void initStorageManager(void)
{
    initSM = 1;
}

//extern RC createPageFile(char* fileName); =>  Creating a file in File System
RC createPageFile(char* fileName)
{
    printf("Page File Manipulation begins...\n");
    FILE *filePtr = fopen(fileName, "wb");
    if( initSM == 1)
    {
		fseek(filePtr, 0L, SEEK_SET);
		char *page = malloc(PAGE_SIZE);
		memset(page, 0, PAGE_SIZE);
		fwrite(page,PAGE_SIZE,1, filePtr);
        if(filePtr  != NULL)
        {
            free(page);
            return RC_OK;
        }
        else
        {
            printf("Error: File is not getting created\n");
            return RC_FILE_CREATION_FAILED;
        }

    }
    else
    {
	printf("Error: Storage Manager is not initialized");
        return RC_STORAGE_MANAGER_NOT_INITIALIZED;
    }

}

/* extern RC openPageFile (char *fileName, SM_FileHandle *fHandle); => Open file to read and write
The second parameter is an existing file handle. If opening the file is successful,
then the fields of this file handle should be initialized with the information about the opened file.
*/
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
    if(f == -1){ return RC_FILE_NOT_OPENED;}
    else
    {
        /* file exists and set the values */
        
        fHandle -> fileName = fileName;
		fHandle -> mgmtInfo = filePtr;
		printf("mgmtInfo: %p\n", filePtr);
        fHandle->totalNumPages = PAGE_SIZE;
		fHandle->curPagePos = 0;
        fHandle->totalNumPages = 1;
        fseek(filePtr, 0L, SEEK_SET);
        fwrite(fHandle,PAGE_SIZE,1, filePtr);
        return RC_OK;

    }

}

//extern RC closePageFile (SM_FileHandle *fHandle); => close an open page file
RC closePageFile(SM_FileHandle *fHandle){
    FILE *filePtr = fHandle->mgmtInfo;
	
    if (filePtr == NULL) {return RC_FILE_NOT_OPENED;} 
	else {
        if (fclose(filePtr) == 0) {
            filePtr = 0;
            fHandle->mgmtInfo = NULL;
			printf("***CLosing file...\n");
            free(fHandle->mgmtInfo);
           
        }
    }
	
	return RC_OK;

}

//extern RC destroyPageFile (char *fileName); => destroy a page file
RC destroyPageFile(char *fileName){
	int result = remove(fileName);
	if (result != 0){ printf("file not destroyed at end\n"); return RC_FILE_DESTROY_FAILED;}
	else{ printf("file removed successfully!\n");return RC_OK;}
}

/*****************************************************************/
/********************Read file code form here*********************/
/****************************************************************/

/*Below is a generic function to read a specified block of page*/
/*
 * AUTHOR: Rajasekhar 
 * METHOD: Get the current block position
 * INPUT: File Structure
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
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

/*
 * AUTHOR: Rajasekhar 
 * METHOD: Read the current Block from the File
 * INPUT: File Structure, Content of the data to be read
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
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
                //Seeking to the current block of the file
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

/*
 * AUTHOR: Rajasekhar 
 * METHOD: Read the previous Block from the current block in the File
 * INPUT: File Structure, Content of the data to be read
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
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
                //Seeking to the previous block in the file
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

/*
 * AUTHOR: Rajasekhar 
 * METHOD: Read the next Block from the current block in the File
 * INPUT: File Structure, Content of the data to be read
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
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
                //seeking to the next block in the file
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

/*
 * AUTHOR: Rajasekhar 
 * METHOD: Read Last Block from File
 * INPUT: Page Number,File Structure, Content of the data to be read
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
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

            //Seeking to the last block of the file
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

/*
 * AUTHOR: Amruta 
 * METHOD:Read First Block from File
 * INPUT: File Structure, Content of the data to be read
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
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
            //Seeking to the first block of the file
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

/*
 * AUTHOR: Rajasekhar 
 * METHOD:Read Block from File
 * INPUT: Page Number,File Structure, Content of the data to be read
 * OUTPUT: RC_OK-SUCESSS;RC_OTHERS-ON FAIL;
 */
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


/************************************************************/
/*Write block from here*/

/***************************************************/

/***Writing a page to disk using the absolute position
  input paramerters:   
	pageNum => the page number in the file on the disk where the data has to be written
	fHandle => pointer to SM_FileHandle which holds information about the file like file name, file pointer, number of pages and current page location 
    memPag  => location in memory from where the data has to be written
  return values:
  	RC  => values defined in dberror.h as per the operational status of the function
***/
RC writeBlock(int pageNum, SM_FileHandle *fHandle, SM_PageHandle memPag) 
{
	int curPos = -1;
    FILE *fptr = fHandle->mgmtInfo;
 RC numval = pageNumbers(pageNum);	//check in the page number is valid
	if (numval != RC_OK) 
		return numval;
	RC filestat = checkFile(fHandle);	//checking intial contidions related to file
	if (filestat != RC_OK) 
		return filestat;
	printf(" Conditions checked.\n Writing block to page number %d\n", pageNum);
	printf("total num of pages before writing a block:%d\n ",fHandle->totalNumPages);
    
            //Seeking to the given block 
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
					printf("total num of pages after writing a block:%d\n ",fHandle->totalNumPages);
    
                }
            }
    return RC_OK;
}
	

/***Writing a page to disk using the relative position
  input paramerters:   
	fHandle => pointer to SM_FileHandle which holds information about the file like file name, file pointer, number of pages and current page location 
    memPag  => location in memory from where the data has to be written
  return values:
  	RC => values defined in dberror.h as per the operational status of the function
***/
RC writeCurrentBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) 
{
	int curPos = -1;
    FILE *fptr = fHandle->mgmtInfo;
  RC filestat = checkFile(fHandle);	//checking intial contidions related to file
	if (filestat!= RC_OK) 
		return filestat;
	printf("Writing current block to %s", fHandle->fileName);
	printf("total num of pages before writing to current block:%d\n ",fHandle->totalNumPages);
            curPos = fHandle->curPagePos;
            if (curPos < 0) {
                return RC_INVALID_POSITION;
				} else {
                //Seeking to the current block in the file
                if ((fseek(fptr, (curPos + 1) * PAGE_SIZE, SEEK_SET)) < 0) {
                    return RC_FILE_SEEK_ERROR;
                } else {
                    if (-1 == (fwrite(memPage, PAGE_SIZE, 1, fptr)) ) {
                        return RC_WRITE_FAILED;
                    } else {
                        curPos = (ftell(fptr) / PAGE_SIZE) - 1;
                        fHandle->curPagePos = curPos;
                        fHandle->totalNumPages = fHandle->totalNumPages + 1;
						printf("total num of pages after writing to current block:%d\n ",fHandle->totalNumPages);
                    }
                }
            }
      
  
    return RC_OK;
	}
	

/***This function adds a zero bytes empty block at the end and increases the number of pages by one.
 input paramerters:   
	fHandle => pointer to SM_FileHandle which holds information about the file like file name, file pointer, number of pages and current page location 
 return values:
  	RC => values defined in dberror.h as per the operational status of the function
***/
RC appendEmptyBlock(SM_FileHandle *fHandle) 
{
	 char buf[PAGE_SIZE];
    FILE *fptr = NULL;
    fptr = fHandle->mgmtInfo;
    if (fptr == NULL) {
        return RC_FILE_INFO_UNAVAILABLE;
    } else {
        int totalNumberPages = fHandle->totalNumPages;
		printf("total num of pages before appending empty block:%d\n ",fHandle->totalNumPages);
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
					printf("total num of pages after appending empty block:%d\n ",fHandle->totalNumPages);
                }
            }
        }
    }
    return RC_OK;

	/*FILE *fptr = (FILE*) fHandle->mgmtInfo;

	RC filestat = checkFile(fHandle);   		//checking intial contidions related to file
	if (filestat != RC_OK)      
		return filestat;
	printf("Adding an empty block to file %s", fHandle->fileName);
	//totalPages = (char *) calloc(PAGE_SIZE, sizeof(char));  /* (the number of elements to be allocated,  size of elements. )allocate "first" page to store total number of pages information */
       /*     SM_PageHandle temp;
	fseek(fptr, (fHandle->totalNumPages * PAGE_SIZE), SEEK_END);
	temp = (char *)calloc(PAGE_SIZE, sizeof(char));
	fwrite(temp, sizeof(char), PAGE_SIZE, fptr); 		//append empty char to the page
	fHandle->totalNumPages = fHandle->totalNumPages + 1; 
	free(temp);
	fclose(fptr);				//close after adding empty page
	return RC_OK;
	*/ 
}

/***This function is to check and update the number of pages available.
 input paramerters:   
    numberOfPages => the number of pages we want the file to have
	fHandle => pointer to SM_FileHandle which holds information about the file like file name, file pointer, number of pages and current page location 
 return values:
  	RC => values defined in dberror.h as per the operational status of the function
***/
RC ensureCapacity(int numberOfPages, SM_FileHandle *fHandle) 
{    char buf[PAGE_SIZE];
    FILE *fptr = NULL;
    int i;
    fptr = fHandle->mgmtInfo;
    if (fptr == NULL) {
        return RC_FILE_INFO_UNAVAILABLE;
    } else {
        int totalNumberPages = fHandle->totalNumPages;
        if (totalNumberPages < numberOfPages) {
            fseek(fptr, 0L, SEEK_END);
            for (i = 0; i < (numberOfPages - totalNumberPages); i++) {
                fwrite(buf, PAGE_SIZE, 1, fptr);
            }
            fHandle->totalNumPages = numberOfPages;
        }
    }
    return RC_OK;

	/*FILE *fptr = (FILE*) fHandle->mgmtInfo;

	RC isval = pageNumbers(numberOfPages);    // check if the input number of pages is valid
	if (isval != RC_OK) 
		return isval;
	RC filestat = checkFile(fHandle);     	//checking intial contidions related to file
	if (filestat != RC_OK) 
		return filestat;
	if (fHandle->totalNumPages < numberOfPages)     // check if total number of pages is less than the requirement
	{
	int deficit = numberOfPages - fHandle->totalNumPages;   //calculate the deficit
 	int i = 0 ;
	SM_PageHandle ph;
	    for (i = 0; i < deficit; i++){
		ph = (char*)malloc(PAGE_SIZE*sizeof(char));
	        memset(ph,0,PAGE_SIZE*sizeof(char));
		int d = fwrite(ph, sizeof(char),PAGE_SIZE,fptr);
		if(d!=PAGE_SIZE){return RC_WRITE_FAILED;}
		fHandle->totalNumPages= fHandle->totalNumPages + 1;
		rewind(fptr);
	     }

	fHandle->curPagePos = fHandle->totalNumPages - 1 ;
	}
	
	return RC_OK;*/
}

/***This function is for checking intial contidions related to file.
 input paramerters:   
	fHandle => pointer to SM_FileHandle which holds information about the file like file name, file pointer, number of pages and current page location 
 return values:
  	RC => values defined in dberror.h as per the operational status of the function
***/
RC checkFile(SM_FileHandle *fHandle) 
{
	FILE *fptr = (FILE*) fHandle->mgmtInfo;

	if (fHandle == NULL || fHandle->fileName == NULL|| fptr== NULL || fHandle->totalNumPages < 0 || fHandle->curPagePos < 0) 		
		return RC_FILE_HANDLE_NOT_INITIATED;
	else 
		printf("file proper\n");
return RC_OK;
}

/***This function is for checking if the page number is valid.
 input paramerters:   
	pno => page number
 return values:
  	RC => values defined in dberror.h as per the operational status of the function
***/
RC pageNumbers(int pno) 
{
	if (pno >= 0) 	// page number can't be negative
		return RC_OK;
	else 
		return RC_PAGENUM_INVALID;
}

