***********************************
Problem Statement		
***********************************
The goal of this assignment is to create a record manager. The record manager handles tables with a fixed schema. Clients can insert records, delete records, update records, and scan through the records in a table. A scan is associated with a search condition and only returns records that match the search condition. Each table should be stored in a separate page file and your record manager should access the pages of the file through the buffer manager implemented in the last assignment.

***********************************
File List
***********************************

1.Makefile
2.buffer_mgr.h
3.buffer_mgr_stat.c
4.buffer_mgr_stat.h
5.dberror.c
6.dberror.h
7.expr.c
8.expr.h
9.record_mgr.h
10.rm_serializer.c
11.storage_mgr.h
12.tables.h
13.test_assign3_1.c
14.test_expr.c
15.test_helper.h
16.basicuserinterface.c
17.testuserinterface.txt
18.record_mgr.c
19.GUI.sh
20.README.txt

**************************************
Instructions To Run The Code
**************************************

In order to setup the environment to run test cases
----------------------------------------------------
Run : make -f Makefile

With default test cases
--------------------------
Compile : make assign3_1
Run : ./assign3_1

Clean up the old output files: make clean

***********************************
Features
***********************************

>> record_mgr.c

The interfaces of the record manager are defined in record_mgr.h. There are five types of functions in the record manager: functions for table and record manager management, functions for handling the records in a table, functions related to scans, functions for dealing with schemas, and function for dealing with attribute values and creating records. 

Additional Helper methods added to enhance the modularity of code.
Record format is Fixed length. Records are inserted at the end of the block file and slot directory starts after block header. Tombstone concept implemented by making the record offset 0 in the record directory.

Scan functions for scaning the records with eval expressions.

Table Management info has no. of blocks and schema of the file.
Data block header has block no, no of slots and no of records.


***********************************
Additional Features
***********************************

>> Test supports for bool and float data types in addition.

>> basicuserinterface.c 
Allows user to create table, insert, update, delete records from the table.

Test input is provided in testuserinterface.txt


**********************************
Group Members
**********************************
1)Sharel Clavy Pereira (A20382007,spereira2@hawk.iit.edu)(Team Leader)
2)Navjot Sharma (A20376692, nsharma11@hawk.iit.edu)
3)Harshaa Bajaj (A20378813,hbajaj@hawk.iit.edu)
4)Vaishnavi Gudur (A20384900,vgudur@hawk.iit.edu)
***********************************
