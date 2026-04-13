## 1773.8017.21889

CREATE TABLE AIPRO_SYS_COUNTER(
"ID" nvarchar(32) NOT NULL, 
"NAME" nvarchar(400),     
"TYPECODE" nvarchar(400), 
"CATEGORY" nvarchar(400), 
"PATTERN" nvarchar(400),  
"VALUE" BIGINT,     
"VERSION" BIGINT,   
 PRIMARY KEY ("ID")       
 );


CREATE TABLE AIPRO_SYS_BIZ_PRIMARY(
"ID" DECIMAL(19) NOT NULL PRIMARY KEY,
"MODULE_CODE" VARCHAR(32) NOT NULL,
"BIZ_ID" DECIMAL(19) NOT NULL,
"BIZ_TABLE" VARCHAR(128) NOT NULL,
"BIZ_PRIMARY_VALUE" VARCHAR(400) NOT NULL,
"VERSION" DECIMAL(19) NULL 
 );
CREATE UNIQUE INDEX UN_INDEX_AIPRO ON AIPRO_SYS_BIZ_PRIMARY("BIZ_TABLE","BIZ_PRIMARY_VALUE");
EXEC sp_addextendedproperty 'MS_Description', N'主键ID', 'schema', N'dbo', 'table', N'AIPRO_SYS_BIZ_PRIMARY', 'column', N'ID';
EXEC sp_addextendedproperty 'MS_Description', N'模块编码', 'schema', N'dbo', 'table', N'AIPRO_SYS_BIZ_PRIMARY', 'column', N'MODULE_CODE';
EXEC sp_addextendedproperty 'MS_Description', N'业务主键ID', 'schema', N'dbo', 'table', N'AIPRO_SYS_BIZ_PRIMARY', 'column', N'BIZ_ID';
EXEC sp_addextendedproperty 'MS_Description', N'业务表', 'schema', N'dbo', 'table', N'AIPRO_SYS_BIZ_PRIMARY', 'column', N'BIZ_TABLE';
EXEC sp_addextendedproperty 'MS_Description', N'业务主键值', 'schema', N'dbo', 'table', N'AIPRO_SYS_BIZ_PRIMARY', 'column', N'BIZ_PRIMARY_VALUE';
EXEC sp_addextendedproperty 'MS_Description', N'版本号', 'schema', N'dbo', 'table', N'AIPRO_SYS_BIZ_PRIMARY', 'column', N'VERSION';
ALTER TABLE AIPRO_SYS_BIZ_PRIMARY ALTER COLUMN BIZ_PRIMARY_VALUE varchar(4000) COLLATE Chinese_PRC_CI_AS NOT NULL;
CREATE TABLE aipro_WF_TASK(
"ID" DECIMAL(19) NOT NULL PRIMARY KEY, 
"TASK_NAME" nvarchar(255),     
"TASK_NAME_I18N" nvarchar(255),  
"TASK_ID" DECIMAL(19), 
"PROCESS_ID" nvarchar(255), 
"USER_ID" DECIMAL(19),     
"USER_NAME" nvarchar(255),   
"PERSON_CODE" nvarchar(255),   
"PERSON_ID" DECIMAL(19),   
"NODE_ID" nvarchar(255) 
 );
ALTER TABLE aipro_WF_TASK ADD CREATE_TIME DATETIME2(3) NULL;
ALTER TABLE aipro_WF_TASK ADD MODEL_CODE nvarchar(255) NULL;
ALTER TABLE aipro_WF_TASK ADD TASK_TYPE nvarchar(255) NULL;
ALTER TABLE aipro_WF_TASK ADD NODE_ID nvarchar(255) NULL;
ALTER TABLE aipro_WF_TASK ADD NODE_TYPE nvarchar(255) NULL;
CREATE INDEX IDX_AIPRO_PROCESS_ID ON aipro_WF_TASK (PROCESS_ID);

CREATE INDEX IDX_AIPRO_USER_NAME ON aipro_WF_TASK (USER_NAME);

CREATE INDEX IDX_AIPRO_MODEL_CODE ON aipro_WF_TASK (MODEL_CODE);
