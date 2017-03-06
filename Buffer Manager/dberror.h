#ifndef DBERROR_H
#define DBERROR_H

#include "stdio.h"

/* module wide constants */
#define PAGE_SIZE 4096 
#define PAGE_SIZE_BM 4096 

/* return code definitions */
typedef int RC;

#define RC_OK 0
#define RC_FILE_NOT_FOUND 1
#define RC_FILE_HANDLE_NOT_INIT 2
#define RC_WRITE_FAILED 3
#define RC_READ_NON_EXISTING_PAGE 4
#define RC_STORAGE_MANAGER_NOT_INITIALIZED 5
#define RC_FILE_CREATION_FAILED 6
#define RC_WRITE_TO_OUTPUTSTREAM_FAILED 7
#define RC_INCOMPATIBLE_BLOCKSIZE 8
#define RC_FILE_NOT_INITIALIZED 9
#define RC_FILE_OFFSET_FAILED 10
#define RC_FILE_READ_FAILED 11
#define RC_FILE_NOT_CLOSED 12
#define RC_FILE_DESTROY_FAILED 13


#define RC_NOMEM    504
#define RC_FILE_NOT_OPENED 505
#define RC_FILE_NOT_DELETED 506
#define RC_FILE_SEEK_ERROR 507
#define RC_FILE_CUR_POS_ERROR 508
#define RC_NO_PAGES_ERROR 509
#define RC_BUFFER_CREATE_ERROR 510
#define RC_READ_FAILED 511
#define RC_BUFFER_DEL_ERROR 512
#define RC_DUMMY_PAGE_CREATE_ERROR 513
#define RC_BUFFER_DIRTY 514
#define RC_BUFFER_UNDEFINED_STRATEGY 515
#define RC_UNPIN_ERROR 516
#define RC_BUFFER_EXCEEDED 517
#define RC_RM_COMPARE_VALUE_OF_DIFFERENT_DATATYPE 200
#define RC_RM_EXPR_RESULT_IS_NOT_BOOLEAN 201
#define RC_RM_BOOLEAN_EXPR_ARG_IS_NOT_BOOLEAN 202
#define RC_RM_NO_MORE_TUPLES 203
#define RC_RM_NO_PRINT_FOR_DATATYPE 204
#define RC_RM_UNKOWN_DATATYPE 205
#define RC_PAGE_SPECIFIED_NOT_FOUND 206
#define RC_FILE_NOT_READ_COMPLETELY 207
#define RC_SUCCESSFULLY_READ 208
#define RC_FILE_INFO_UNAVAILABLE 209
#define RC_PAGE_NOT_AVAILABLE 210




#define RC_IM_KEY_NOT_FOUND 300
#define RC_IM_KEY_ALREADY_EXISTS 301
#define RC_IM_N_TO_LAGE 302
#define RC_IM_NO_MORE_ENTRIES 303
#define RC_INVALID_POSITION 304
#define RC_FILE_HANDLE_NOT_INITIATED 305
#define RC_PAGENUM_INVALID 306

#define RC_SHUTDOWN_FAIL 401
#define RC_INVALID_HANDLE 402
#define RC_PAGE_NOT_PINNED 403



/* holder for error messages */
extern char *RC_message;

/* print a message to standard out describing the error */
extern void printError (RC error);
extern char *errorMessage (RC error);

#define THROW(rc,message) \
  do {			  \
    RC_message=message;	  \
    return rc;		  \
  } while (0)		  \

// check the return code and exit if it is an error
#define CHECK(code)							\
  do {									\
    int rc_internal = (code);						\
    if (rc_internal != RC_OK)						\
      {									\
	char *message = errorMessage(rc_internal);			\
	printf("[%s-L%i-%s] ERROR: Operation returned error: %s\n",__FILE__, __LINE__, __TIME__, message); \
	free(message);							\
	exit(1);							\
      }									\
  } while(0);


#endif
