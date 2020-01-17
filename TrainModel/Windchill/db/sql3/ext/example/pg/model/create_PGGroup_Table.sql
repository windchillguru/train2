set echo on
REM Creating table PGGroup for ext.example.pg.model.PGGroup
set echo off
CREATE TABLE PGGroup (
   comments   VARCHAR2(4000),
   enabled   NUMBER(1),
   pgGroupName   VARCHAR2(600) NOT NULL,
   pgName   VARCHAR2(600),
   root   NUMBER(1),
   createStampA2   DATE,
   markForDeleteA2   NUMBER NOT NULL,
   modifyStampA2   DATE,
   classnameA2A2   VARCHAR2(600),
   idA2A2   NUMBER NOT NULL,
   updateCountA2   NUMBER,
   updateStampA2   DATE,
 CONSTRAINT PK_PGGroup PRIMARY KEY (idA2A2))
 STORAGE ( INITIAL 20k NEXT 20k PCTINCREASE 0 )
ENABLE PRIMARY KEY USING INDEX
 TABLESPACE INDX
 STORAGE ( INITIAL 20k NEXT 20k PCTINCREASE 0 )
/
COMMENT ON TABLE PGGroup IS 'Table PGGroup created for ext.example.pg.model.PGGroup'
/
REM @//ext/example/pg/model/PGGroup_UserAdditions
