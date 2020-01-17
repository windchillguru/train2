set echo on
REM Creating table PGMemberLink for ext.example.pg.model.PGMemberLink
set echo off
CREATE TABLE PGMemberLink (
   comments   VARCHAR2(4000),
   classnamekeyroleAObjectRef   VARCHAR2(600),
   idA3A5   NUMBER,
   classnamekeyroleBObjectRef   VARCHAR2(600),
   idA3B5   NUMBER,
   createStampA2   DATE,
   markForDeleteA2   NUMBER NOT NULL,
   modifyStampA2   DATE,
   classnameA2A2   VARCHAR2(600),
   idA2A2   NUMBER NOT NULL,
   updateCountA2   NUMBER,
   updateStampA2   DATE,
 CONSTRAINT PK_PGMemberLink PRIMARY KEY (idA2A2))
 STORAGE ( INITIAL 20k NEXT 20k PCTINCREASE 0 )
ENABLE PRIMARY KEY USING INDEX
 TABLESPACE INDX
 STORAGE ( INITIAL 20k NEXT 20k PCTINCREASE 0 )
/
COMMENT ON TABLE PGMemberLink IS 'Table PGMemberLink created for ext.example.pg.model.PGMemberLink'
/
REM @//ext/example/pg/model/PGMemberLink_UserAdditions
