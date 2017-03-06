#include<string.h>
#include<stdio.h>
#include<stdlib.h>
#include <time.h>

#include "storage_mgr.h"
#include "buffer_mgr.h"
#include "buffer_mgr_stat.h"
#include "dberror.h"
#include "dt.h"
#include "db.h"
#include "tables.h"
#include "record_mgr.h"

RC updateTableInfo(Schema *schema, char *data, int dataBlock, TableInfoUpdate type);

/*
-----------------------------------
Table and Record Manager Functions
-----------------------------------
*/


//Initialzing the record manager
RC initRecordManager(void* mgmtData) {
    return RC_OK;
}

//Shutdown the record manager
RC shutdownRecordManager() {
    return RC_OK;
}

//Creating the table 
RC createTable(char* name, Schema* schema) {
	
		if (name == NULL || strlen(name) == 0) {
		return RC_REC_MGR_INVALID_TBL_NAME;
	}

	if (schema == NULL) {
		return RC_INVALID_SCHEMA_HANDLE;
	}
	
    BM_BufferPool *bm = MAKE_POOL();
    BM_PageHandle *bh = MAKE_PAGE_HANDLE();    
    createPageFile(name);               //creating the page file
    initBufferPool(bm, name, 3, RS_FIFO, NULL);    
    pinPage(bm, bh, TABLE_INFO_BLOCK);    //Inserting the table mgmt info into TABLE INFO BLOCK
    updateTableInfo(schema, bh->data, 0, TBL_NEW);
    markDirty(bm, bh);
    unpinPage(bm, bh);  
    pinPage(bm, bh, DATA_INFO_BLOCK);     //Initializing data block 
    updateBlockHeader(bh->data, BLK_NEW, 0, 0, 0);
    markDirty(bm, bh);
    unpinPage(bm, bh);    
    forceFlushPool(bm);                //Writing the content to the file    
    shutdownBufferPool(bm);            //Shutting down the buffer pool
    free(bm);
    return RC_OK;
}

 //Opening the table 

RC openTable(RM_TableData* rel, char* name) {
	
		if (name == NULL || strlen(name) == 0) {
		return RC_REC_MGR_INVALID_TBL_NAME;
	}

	if (rel == NULL) {
		return RC_INVALID_HANDLE;
	}
	    int i = 0;
    BM_BufferPool *bm = MAKE_POOL();
    BM_PageHandle *bh = MAKE_PAGE_HANDLE();

    rel->name = (char *) malloc(sizeof (name));
    rel->mgmtData = malloc(sizeof (bm));
    initBufferPool(bm, name, 3, RS_FIFO, NULL);

    pinPage(bm, bh, TABLE_INFO_BLOCK);    
    ReadSchema(rel, bh->data);                  //reading schema from  the file
    unpinPage(bm, bh);   
    memcpy(rel->name, name, sizeof (name));     //copy the name and buffer pool
    rel->mgmtData = bm;
    free(bh);
    return RC_OK;
}

//Closing the table 

RC closeTable(RM_TableData *rel) {
	if (rel == NULL) {
		return RC_INVALID_HANDLE;
	}	
    free(rel->schema->dataTypes);
    free(rel->schema->attrNames);
    free(rel->schema->keyAttrs);
    free(rel->schema->typeLength);
    free(rel->schema);
    free(rel->name);
    shutdownBufferPool(rel->mgmtData);
    return RC_OK;
}

 //Deleting the table 

RC deleteTable(char *name) {
	
		if (name == NULL || strlen(name) == 0) {
		return RC_REC_MGR_INVALID_TBL_NAME;
	}
    destroyPageFile(name);
    return RC_OK;
}

//Fetching the number of tuples 

int getNumTuples(RM_TableData *rel) {
	
	if (rel == NULL) {
		return RC_INVALID_HANDLE;
	}   
     int numBlocks = 0, i = 1, recordsCount = 0, totalRecords = 0, offset = 0;      //variable declaration	 
    BM_PageHandle *bh = MAKE_PAGE_HANDLE();
    pinPage(rel->mgmtData, bh, TABLE_INFO_BLOCK);        //pinning the Table info block to get the no of blocks
    memcpy(bh->data, &numBlocks, sizeof (int));
    unpinPage(rel->mgmtData, bh);

    for (i = 1; i <= numBlocks; i++) {
        pinPage(rel->mgmtData, bh, i);
        offset = offset + (2 * (sizeof (int)));
        memcpy(bh->data + offset, &recordsCount, sizeof (int));
        unpinPage(rel->mgmtData, bh);
        totalRecords = totalRecords + recordsCount;
    }
    free(bh);
    return totalRecords;
}


/*
-----------------------------------
Record Functions
-----------------------------------
*/


// inserting the record

RC insertRecord(RM_TableData* rel, Record* record) {
		if (rel == NULL) {
		return RC_INVALID_HANDLE;
	}
		if (record == NULL) {
		return RC_INVALID_RECORD_HANDLE;
	}
	
     int blockNumber = 0, blockSize = 0, slotNumber = 0, slotSize = 0, recordsCount = 0, recordSize = 0,recordOffset = PAGE_SIZE;

    BM_PageHandle *bh = MAKE_PAGE_HANDLE();
    RID *rid = (RID *) malloc(sizeof (RID));
    pinPage(rel->mgmtData, bh, TABLE_INFO_BLOCK);       //pin the table info block to get the block number to insert the record
    memcpy(&blockNumber, bh->data, sizeof (int));
    unpinPage(rel->mgmtData, bh);
    recordSize = getRecordSize(rel->schema);			    //get record size
    slotSize = slotSize + sizeof (int);   
    pinPage(rel->mgmtData, bh, blockNumber + 1);			 //get the offset to insert the record
     int offset = 0;
    memcpy(&blockNumber, bh->data, sizeof (int));
    offset = offset + sizeof (int);
    memcpy(&slotNumber, bh->data + offset, sizeof (int));
    offset = offset + sizeof (int);
    memcpy(&recordsCount, bh->data + offset, sizeof (int));
    offset = offset + sizeof (int);

    blockSize = (PAGE_SIZE - offset)-((slotNumber)*(recordSize + slotSize));    
    if ((recordSize + slotSize) > blockSize) {  //check if the block has space to add record
        blockNumber = blockNumber + 1;
        slotNumber = 0;
        recordsCount = 0;
        unpinPage(rel->mgmtData, bh);
        pinPage(rel->mgmtData, bh, TABLE_INFO_BLOCK);
        updateTableInfo(rel->schema, bh->data, blockNumber, TBL_ADD);
        markDirty(rel->mgmtData, bh);
        unpinPage(rel->mgmtData, bh);
        pinPage(rel->mgmtData, bh, blockNumber + 1);
        updateBlockHeader(bh->data, BLK_NEW, blockNumber, slotNumber, recordsCount);        
        recordOffset = recordOffset - ((slotNumber + 1) * recordSize);     //calculate the record offset
        insertingSlot(bh->data, slotNumber, recordOffset);                  //insert the slot offset
        memmove(bh->data + recordOffset, record->data, recordSize);
        updateBlockHeader(bh->data, BLK_UPD_SLOT, blockNumber, slotNumber + 1, recordsCount);
        updateBlockHeader(bh->data, BLK_UPD_REC, blockNumber, slotNumber, recordsCount + 1);
        markDirty(rel->mgmtData, bh);
        unpinPage(rel->mgmtData, bh);
    } else {        
        recordOffset = recordOffset - ((slotNumber + 1) * recordSize);    //calcuate the record offset
        insertingSlot(bh->data, slotNumber, recordOffset);                //insert the slot offset
        memmove(bh->data + recordOffset, record->data, recordSize);
        updateBlockHeader(bh->data, BLK_UPD_SLOT, blockNumber, slotNumber + 1, recordsCount);
        updateBlockHeader(bh->data, BLK_UPD_REC, blockNumber, slotNumber, recordsCount + 1);
        markDirty(rel->mgmtData, bh);
        unpinPage(rel->mgmtData, bh);
    }
    rid->page = blockNumber;
    rid->slot = slotNumber;
    record->id.page = rid->page;
    record->id.slot = rid->slot;

    forceFlushPool(rel->mgmtData);
    free(bh);
    free(rid);
    return RC_OK;
}

  //Deleting the record
 
RC deleteRecord(RM_TableData *rel, RID id) {
	if (rel == NULL) {
		return RC_INVALID_HANDLE;
	}
     int offset = 0, slotOffset = 0, recordOffset = 0, recordSize = 0, tombmark = TOMBSTONE;
    BM_PageHandle *bh = MAKE_PAGE_HANDLE();
    recordSize = getRecordSize(rel->schema);
    offset = offset + (3 * sizeof (int));
    slotOffset = id.slot;
    pinPage(rel->mgmtData, bh, id.page + 1);
    memmove(bh->data + (offset + (slotOffset * sizeof (int))), &tombmark, sizeof (int));        //marking the tombstone
    markDirty(rel->mgmtData, bh);
    unpinPage(rel->mgmtData, bh);
    forceFlushPool(rel->mgmtData);
    free(bh);
    return RC_OK;
}

  //Updating the record
 
RC updateRecord(RM_TableData *rel, Record *record) {
			if (rel == NULL) {
		return RC_INVALID_HANDLE;
	}
		if (record == NULL) {
		return RC_INVALID_RECORD_HANDLE;
	}
     int offset = 0,slotOffset = 0, recordOffset = 0, recordSize = 0;
      
    BM_PageHandle *bh = MAKE_PAGE_HANDLE();
    recordSize = getRecordSize(rel->schema);
    offset = offset + (3 * sizeof (int));
    slotOffset = record->id.slot;
    pinPage(rel->mgmtData, bh, record->id.page + 1);
    memcpy(&recordOffset, bh->data + (offset + (slotOffset * sizeof (int))), sizeof (int));
    memmove(bh->data + recordOffset, record->data, recordSize);
    markDirty(rel->mgmtData, bh);
    unpinPage(rel->mgmtData, bh);
    forceFlushPool(rel->mgmtData);
    free(bh);
    return RC_OK;
}

//Fetching the record
  
RC getRecord(RM_TableData *rel, RID id, Record *record) {
			if (rel == NULL) {
		return RC_INVALID_HANDLE;
	}
		if (record == NULL) {
		return RC_INVALID_RECORD_HANDLE;
	}
     int offset = 0,slotOffset = 0, recordOffset = 0, recordSize = 0;     
    BM_PageHandle *bh = MAKE_PAGE_HANDLE();
    recordSize = getRecordSize(rel->schema);
    offset = offset + (3 * sizeof (int));
    slotOffset = id.slot;
    pinPage(rel->mgmtData, bh, id.page + 1);
    memcpy(&recordOffset, bh->data + (offset + (slotOffset * sizeof (int))), sizeof (int));
    if (recordOffset == TOMBSTONE) {
        free(bh);
        return RC_RM_RECORD_DELETED;
    }
    memcpy(record->data, bh->data + recordOffset, recordSize);
    record->id.page = id.page;
    record->id.slot = id.slot;
    unpinPage(rel->mgmtData, bh);
    free(bh);
    return RC_OK;
}

/*
-----------------------------------
Schema Handler Functions
-----------------------------------
*/


//  Calculating the record size

int getRecordSize(Schema *schema) {
				if (schema == NULL) {
		return RC_INVALID_SCHEMA_HANDLE;
	}
	int i, sizeofRecord = 0;
    for (i = 0; i < schema->numAttr; i++) {
        switch (schema->dataTypes[i]) {
            case DT_INT:
                sizeofRecord = sizeofRecord + sizeof (int);
                break;
            case DT_STRING:
                sizeofRecord = sizeofRecord + schema->typeLength[i];
                break;
            case DT_FLOAT:
                sizeofRecord = sizeofRecord + sizeof (float);
                break;
            case DT_BOOL:
                sizeofRecord = sizeofRecord + sizeof (bool);
                break;
        }
    }
    return sizeofRecord;
}

Schema *createSchema(int numAttr, char** attrNames, DataType* dataTypes, int* typeLength, int keySize, int* keys) {
    Schema *schema;
    schema = (Schema *) malloc(sizeof (Schema));
    schema->numAttr = numAttr;
    schema->attrNames = attrNames;
    schema->dataTypes = dataTypes;
    schema->typeLength = typeLength;
    schema->keySize = keySize;
    schema->keyAttrs = keys;
    return schema;
}

RC freeSchema(Schema* schema) {
    free(schema);
    return RC_OK;
}

/*
-----------------------------------
Attribute Functions
-----------------------------------
*/

// creating record
RC createRecord(Record** record, Schema* schema) {
	
				if (record == NULL) {
		return RC_INVALID_RECORD_HANDLE;
	}
					if (schema == NULL) {
		return RC_INVALID_SCHEMA_HANDLE;
	}	
     int i, recSize = 0;
    recSize = getRecordSize(schema);
    *record = (Record *) malloc(sizeof (Record));
    (*record)->data = (char *) malloc(recSize);

    return RC_OK;
}

//Deleting record
RC freeRecord(Record *record) {
					if (record == NULL) {
		return RC_INVALID_RECORD_HANDLE;
	}
				
    free(record->data);
    free(record);
}


 //setting attribute

RC setAttr(Record* record, Schema* schema, int attrNum, Value* value) {
					if (record == NULL) {
		return RC_INVALID_RECORD_HANDLE;
	}
					if (schema == NULL) {
		return RC_INVALID_SCHEMA_HANDLE;
	}
     int i = 0, offset = 0;
    attrOffset(schema, attrNum, &offset);
    switch (schema->dataTypes[attrNum]) {
        case DT_INT:
        {
            int num = value->v.intV;
            memmove(record->data + offset, &num, sizeof (int));
        }
            break;
        case DT_STRING:
        {
            char *buf;
            int length = schema->typeLength[attrNum];
            buf = (char *) malloc(length);
            buf = value->v.stringV;
            memmove(record->data + offset, buf, length);
        }
            break;
        case DT_FLOAT:
        {
            float num = value->v.floatV;
            memmove(record->data + offset, &num, sizeof (float));
        }
            break;
        case DT_BOOL:
        {
            bool num = value->v.boolV;
            memmove(record->data + offset, &num, sizeof (bool));
        }
            break;
        default:
            return RC_FUNC_INVALIDARG;
    }

    return RC_OK;
}


// getting attribute

RC getAttr(Record *record, Schema *schema, int attrNum, Value **value) {
					if (record == NULL) {
		return RC_INVALID_RECORD_HANDLE;
	}
					if (schema == NULL) {
		return RC_INVALID_SCHEMA_HANDLE;
	}
    int i = 0, length = 0, offset = 0,  intNum  = 0 ;
	char *attrData;
    Value *val;
    float floatNum ;
    bool boolNum ;
    val = (Value *) malloc(sizeof (Value));
    attrOffset(schema, attrNum, &offset);
    val->dt = schema->dataTypes[attrNum];
    switch (schema->dataTypes[attrNum]) {
        case DT_INT:
            memcpy(&intNum , record->data + offset, sizeof (int));
            val->v.intV = intNum ;
            break;
        case DT_STRING:
            attrData = record->data + offset;
            length = schema->typeLength[attrNum];
            val->v.stringV = (char *) malloc(length + 1);
            strncpy(val->v.stringV, attrData, length);
            val->v.stringV[length] = '\0';
            break;
        case DT_FLOAT:
            memcpy(&floatNum , record->data + offset, sizeof (float));
            val->v.floatV = floatNum ;
            break;
        case DT_BOOL:
            memcpy(&boolNum , record->data + offset, sizeof (bool));
            val->v.boolV = boolNum ;
            break;
        default:
            free(val);
            return RC_FUNC_INVALIDARG;
    }
    *value = val;
    return RC_OK;
}


//  copying Expr

static RC copyExpr(Expr *expr, Expr **result_expr) {  
   
     Operator *op = NULL;
     Expr *cpExpr = NULL;
	 Value *val = NULL;	 
	 RC errorType = RC_OK;
    if (result_expr == NULL || expr == NULL)
        return RC_FUNC_INVALIDARG;
    if (expr == NULL)
        goto End;
    cpExpr = (Expr *) malloc(sizeof (Expr));
    if (cpExpr == NULL)
        return RC_NOMEM;
    cpExpr->type = expr->type;
    switch (expr->type) {
        case EXPR_CONST:
            val = (Value *) malloc(sizeof (Value));
            if (val == NULL) {
                errorType = RC_NOMEM;
                goto End;
            }
            *val = *(expr->expr.cons);
            cpExpr->expr.cons = val;
            break;
        case EXPR_ATTRREF:
            cpExpr->expr.attrRef = expr->expr.attrRef;
            break;
        case EXPR_OP:
            op = (Operator *) malloc(sizeof (Operator));
            if (op == NULL) {
                errorType = RC_NOMEM;
                goto End;
            }
            op->type = expr->expr.op->type;
            op->args = expr->expr.op->args;
            copyExpr(expr->expr.op->args[0], &op->args[0]);
            if (op->args[0] != NULL && op->type != OP_BOOL_NOT) {
                copyExpr(expr->expr.op->args[1], &op->args[1]);
            }
            if (op->args[0] == NULL || (op->type != OP_BOOL_NOT && op->args[1] == NULL)) {
                errorType = RC_NOMEM;
                goto End;
            }
            break;
        default:
            errorType = RC_FUNC_INVALIDARG;
    }
End:
    if (errorType != RC_OK) {
        freeExpr(cpExpr);
        cpExpr = NULL;
    }
    *result_expr = cpExpr;
    return errorType;
}

//  Start scanning table
 
RC startScan(RM_TableData *rel, RM_ScanHandle *scan, Expr *cond) {

    if ( rel == NULL)
        return RC_INVALID_HANDLE;
    if ( scan == NULL)
        return RC_FUNC_INVALIDARG;	
	BM_PageHandle *bh = MAKE_PAGE_HANDLE();
	ScanData *data = NULL;
    data = (ScanData *) malloc(sizeof (ScanData));
    if (data == NULL)
        return RC_NOMEM;
    pinPage(rel->mgmtData, bh, TABLE_INFO_BLOCK);
    memcpy(&data->numBlocks, bh->data, sizeof (int));
    unpinPage(rel->mgmtData, bh);
    data->numBlocks++;
    data->cond = cond;
    data->currRecord = 0;
    data->currentblock = 1;
    data->numRecords = 0;
    data->parsedRecords = 0;
    scan->mgmtData = (void *) data;
    scan->rel = rel;
    free(bh);
    return RC_OK;
}

// Find the next record in the table
RC next(RM_ScanHandle *scan, Record *record) {
	 if ( record == NULL)
        return RC_INVALID_RECORD_HANDLE;
    if ( scan == NULL)
        return RC_FUNC_INVALIDARG;
     ScanData *data;
     int offset = 0, blockOffset = 0, slotOffset = 0, recordSize = 0,recordsCount = 0;
     BM_BufferPool *bm;
	BM_PageHandle *bh = MAKE_PAGE_HANDLE();
    Schema *scheme;
    RID *rids;
    Value *value;   
   scheme = scan->rel->schema;
    data = scan->mgmtData;
    Expr *cond = data->cond;
    bm = scan->rel->mgmtData;
    data->numRecords = getNumTuples(scan->rel);
    blockOffset = 3 * (sizeof (int));
    pinPage(bm, bh, data->currentblock);
    offset = offset + (2 * (sizeof (int)));
    memcpy(&recordsCount, bh->data + offset, sizeof (int));
    unpinPage(bm, bh);
    free(bh);
    rids = (RID *) malloc(sizeof (RID) * recordsCount);
    if (data->currentblock < data->numBlocks + 1) {
        if (data->currRecord <= recordsCount) {
            Expr *attrnum, *temp;
            if (cond->expr.op->type == OP_BOOL_NOT) {
                temp = cond->expr.op->args[0];
                attrnum = temp->expr.op->args[0];
            } else {
                attrnum = cond->expr.op->args[1];
            }
            record->id.page = data->currentblock - 1;
            record->id.slot = data->currRecord;
            RID rid = record->id;
            getRecord(scan->rel, rid, record);

            getAttr(record, scheme, attrnum->expr.attrRef, &value);

            evalExpr(record, scheme, cond, &value);
            data->currRecord = data->currRecord + 1;
            scan->mgmtData = data;
            if (value->v.boolV == 1) {
                return RC_OK;
            } else {next(scan, record);}
        } else {
            if (data->currentblock + 1 < data->numBlocks) {
                data->currentblock = data->currentblock + 1;
                data->currRecord = 0;
                next(scan, record);
            } else {return RC_RM_NO_MORE_TUPLES;}
        }
    } else {return RC_RM_NO_MORE_TUPLES;}

}

//Closing scan

RC closeScan(RM_ScanHandle *scan) {
	    if ( scan == NULL)
        return RC_FUNC_INVALIDARG;
    free(scan->mgmtData);
    return RC_OK;
}

//Updating the table mgmt info
RC updateTableInfo(Schema *schema, char *data, int dataBlock, TableInfoUpdate type) {	
	    if ( schema == NULL)
        return RC_INVALID_SCHEMA_HANDLE;	
		 char *serializeSchema = malloc(100);
		 int offset = 0, sizeofSchema = 0;
		 switch (type) {
        case 0:
            serializeSchema = serializeSchema1(schema);
            sizeofSchema = strlen(serializeSchema);
            memmove(data, &dataBlock, sizeof (int));
            offset = offset + sizeof (int);
            memmove(data + offset, &sizeofSchema, sizeof (int));
            offset = offset + sizeof (int);
            memmove(data + offset, serializeSchema, sizeofSchema);
            break;
        case 1:
            memmove(data, &dataBlock, sizeof (int));
            break;
        case 2:
            break;
    }
    free(serializeSchema);
}

//Updating the block header
RC updateBlockHeader(char *data, TableInfoUpdate type, int blockNumber, int num_slots, int num_records) {
     int offset = 0;
     switch (type) {
        case 0:
            memmove(data, &blockNumber, sizeof (int));
            offset = offset + sizeof (int);
            memmove(data + offset, &num_slots, sizeof (int));
            offset = offset + sizeof (int);
            memmove(data + offset, &num_records, sizeof (int));
            break;
        case 1:
            offset = offset + (2 * (sizeof (int)));
            memmove(data + offset, &num_records, sizeof (int));
            break;
        case 2:
            offset = offset + sizeof (int);
            memmove(data + offset, &num_slots, sizeof (int));
            break;
    }
}

//Inserting the record offset
RC insertingSlot(char *data, int num_slots, int recordOffset) {
    int slotOffset = 3 * (sizeof (int));
    slotOffset = slotOffset + ((num_slots)*(sizeof (int)));
    memmove(data + slotOffset, &recordOffset, sizeof (int));
    return RC_OK;
}

RC ReadSchema(RM_TableData* rel, char *schemadata) {
	  if ( rel == NULL)
        return RC_INVALID_HANDLE;
	int i, sizeOfSchema = 0, offset = 0;
    char *tempSchema, *tempA, *tempB, *tempC,  *columns[50];
    Schema *schema;
    rel->schema = malloc(sizeof (Schema));
    offset = offset + sizeof (int);
    memcpy(&sizeOfSchema, schemadata + offset, sizeof (int));
    tempSchema = (char *) malloc(sizeOfSchema);
    offset = offset + sizeof (int);
    memcpy(tempSchema, schemadata + offset, sizeOfSchema);
    rel->schema->numAttr = atoi(strtok(tempSchema, ";"));
    rel->schema->typeLength = malloc((rel->schema->numAttr) * sizeof (int));
    tempA = strtok(NULL, ";");
    rel->schema->keySize = atoi(strtok(NULL, ";"));
    rel->schema->keyAttrs = malloc((rel->schema->keySize) * sizeof (int));
    tempB = strtok(NULL, ";");
    i = 0;
    tempB = strtok(tempB, ",");
    while (tempB != NULL) {
        rel->schema->keyAttrs[i] = atoi(tempB);
        tempB = strtok(NULL, ",");
        i++;
    }
    tempB = strtok(tempA, ",");
    i = 0;
    while (tempB != NULL) {
        columns[i] = tempB;
        tempB = strtok(NULL, ",");
        i++;
    }
    rel->schema->attrNames = (char **) malloc(rel->schema->numAttr * sizeof (char*));
    (rel->schema)->dataTypes = (DataType *) malloc(rel->schema->numAttr * sizeof (DataType));
    for (i = 0; i < rel->schema->numAttr; i++) {
        rel->schema->attrNames[i] = strtok(columns[i], ":");
        tempB = strtok(NULL, ":");
        if (strstr(tempB, "INT")) {
            rel->schema->dataTypes[i] = DT_INT;
        } else if (strstr(tempB, "STR")) {
            rel->schema->dataTypes[i] = DT_STRING;
            tempC = strstr(tempB, "[");
            tempC = strtok(tempC, "]");
            tempC++;
            rel->schema->typeLength[i] = atoi(tempC);
        } else if (strstr(tempB, "FLOAT")) {
            rel->schema->dataTypes[i] = DT_FLOAT;
        } else if (strstr(tempB, "BOOL")) {
            rel->schema->dataTypes[i] = DT_BOOL;
        }
    }
    free(tempSchema);
    return RC_OK;
}
