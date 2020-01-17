set echo on
REM Creating table PGInformation for ext.example.pg.model.PGInformation
set echo off
CREATE TABLE PGInformation (
   comments   VARCHAR2(4000),
   employeeEmail   VARCHAR2(600),
   employeeName   VARCHAR2(600),
   employeeNo   VARCHAR2(600) NOT NULL,
   employeePhone   VARCHAR2(600),
   employeeUserName   VARCHAR2(600),
   experienced   NUMBER(1),
   informationNo   VARCHAR2(600),
   informationSource   VARCHAR2(600),
   leader   NUMBER(1),
   pgName   VARCHAR2(600),
   classnamekeyA4   VARCHAR2(600),
   idA3A4   NUMBER,
   createStampA2   DATE,
   markForDeleteA2   NUMBER NOT NULL,
   modifyStampA2   DATE,
   classnameA2A2   VARCHAR2(600),
   idA2A2   NUMBER NOT NULL,
   updateCountA2   NUMBER,
   updateStampA2   DATE,
 CONSTRAINT PK_PGInformation PRIMARY KEY (idA2A2))
 STORAGE ( INITIAL 20k NEXT 20k PCTINCREASE 0 )
ENABLE PRIMARY KEY USING INDEX
 TABLESPACE INDX
 STORAGE ( INITIAL 20k NEXT 20k PCTINCREASE 0 )
/
COMMENT ON TABLE PGInformation IS 'Table PGInformation created for ext.example.pg.model.PGInformation'
/
REM @//ext/example/pg/model/PGInformation_UserAdditions
