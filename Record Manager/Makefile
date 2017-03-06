CC=clang 
CFLAGS =-I.
DEPS= 
 

dberror.o: dberror.c
	cc -o dberror.o -c dberror.c

expr.o: expr.c
	cc -o expr.o -c expr.c
	
rm_serializer.o: rm_serializer.c
	cc -o rm_serializer.o -c rm_serializer.c

test_assign3_1.o: test_assign3_1.c
	cc -o test_assign3_1.o -c test_assign3_1.c

buffer_mgr_stat.o: buffer_mgr_stat.c
	cc -o buffer_mgr_stat.o -c buffer_mgr_stat.c

record_mgr.o: record_mgr.c
	cc -o record_mgr.o -c record_mgr.c

buffer_mgr.o: buffer_mgr.c 
	cc -o buffer_mgr.o -c buffer_mgr.c

storage_mgr.o: storage_mgr.c  
	cc -o storage_mgr.o -c storage_mgr.c

assign3_1: test_assign3_1.o storage_mgr.o dberror.o buffer_mgr.o buffer_mgr_stat.o record_mgr.o rm_serializer.o expr.o
	cc -o assign3_1 test_assign3_1.o storage_mgr.o dberror.o buffer_mgr.o buffer_mgr_stat.o record_mgr.o rm_serializer.o expr.o -I.


clean: 
	rm assign3_1 test_assign3_1.o storage_mgr.o dberror.o buffer_mgr.o buffer_mgr_stat.o record_mgr.o rm_serializer.o expr.o


