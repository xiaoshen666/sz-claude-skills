## 1.0.0
CREATE TABLE AIPRO_QJDST(
"NAME" nvarchar(510) COLLATE Chinese_PRC_CI_AS NULL,
"NUM" nvarchar(510) COLLATE Chinese_PRC_CI_AS NULL,
"OPTIME" datetime NULL,
"GSNUM" DECIMAL(21,2) NULL,
"STARTTIME" datetime NULL,
"ENDTIME" datetime NULL,
"COMPANY_CODE" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL,
"CREATE_TIME" datetime NULL,
"MODIFY_TIME" datetime NULL,
"DELETE_TIME" datetime NULL,
"SORT" INT NULL,
"PROCESS_KEY" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL,
"PROCESS_ID" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL,
"FORM_NO" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL,
"PROCESS_TASK_I18N" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL,
"PROCESS_STATUS" nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL,
"PROCESS_NAME" nvarchar(100) COLLATE Chinese_PRC_CI_AS NULL,
"PROCESS_VERSION" nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL,
"EFFECT_TIME" datetime NULL,
"ID" BIGINT NOT NULL PRIMARY KEY,
"VERSION" INT NULL,
"VALID" INT DEFAULT 1 NOT NULL,
"POS_LAY_REC" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL,
"OWNER_POS_CODE" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL,
"OWNER_POS_NAME" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL,
"CREATE_POS_CODE" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL,
"CREATE_POS_NAME" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL,
"OWNER_STAFF_CODE" nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL,
"OWNER_STAFF_NAME" nvarchar(250) COLLATE Chinese_PRC_CI_AS NULL,
"DELETE_STAFF_CODE" nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL,
"DELETE_STAFF_NAME" nvarchar(250) COLLATE Chinese_PRC_CI_AS NULL,
"MODIFY_STAFF_CODE" nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL,
"MODIFY_STAFF_NAME" nvarchar(250) COLLATE Chinese_PRC_CI_AS NULL,
"CREATE_STAFF_CODE" nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL,
"CREATE_STAFF_NAME" nvarchar(250) COLLATE Chinese_PRC_CI_AS NULL,
"OWNER_DEPT_CODE" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL,
"OWNER_DEPT_NAME" nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL
);
ALTER TABLE AIPRO_QJDST ADD NAME nvarchar(510) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD NUM nvarchar(510) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD OPTIME datetime NULL;
ALTER TABLE AIPRO_QJDST ADD GSNUM DECIMAL(21,2) NULL;
ALTER TABLE AIPRO_QJDST ADD STARTTIME datetime NULL;
ALTER TABLE AIPRO_QJDST ADD ENDTIME datetime NULL;
ALTER TABLE AIPRO_QJDST ADD COMPANY_CODE nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD CREATE_TIME datetime NULL;
ALTER TABLE AIPRO_QJDST ADD MODIFY_TIME datetime NULL;
ALTER TABLE AIPRO_QJDST ADD DELETE_TIME datetime NULL;
ALTER TABLE AIPRO_QJDST ADD SORT INT NULL;
ALTER TABLE AIPRO_QJDST ADD PROCESS_KEY nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD PROCESS_ID nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD FORM_NO nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD PROCESS_TASK_I18N nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD PROCESS_STATUS nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD PROCESS_NAME nvarchar(100) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD PROCESS_VERSION nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD EFFECT_TIME datetime NULL;
ALTER TABLE AIPRO_QJDST ADD ID BIGINT NULL PRIMARY KEY;
ALTER TABLE AIPRO_QJDST ALTER COLUMN ID BIGINT NOT NULL PRIMARY KEY;
ALTER TABLE AIPRO_QJDST ADD VERSION INT NULL;
ALTER TABLE AIPRO_QJDST ADD VALID INT DEFAULT 1 NULL;
ALTER TABLE AIPRO_QJDST ALTER COLUMN VALID INT DEFAULT 1 NOT NULL;
ALTER TABLE AIPRO_QJDST ADD POS_LAY_REC nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD OWNER_POS_CODE nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD OWNER_POS_NAME nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD CREATE_POS_CODE nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD CREATE_POS_NAME nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD OWNER_STAFF_CODE nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD OWNER_STAFF_NAME nvarchar(250) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD DELETE_STAFF_CODE nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD DELETE_STAFF_NAME nvarchar(250) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD MODIFY_STAFF_CODE nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD MODIFY_STAFF_NAME nvarchar(250) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD CREATE_STAFF_CODE nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD CREATE_STAFF_NAME nvarchar(250) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD OWNER_DEPT_CODE nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ADD OWNER_DEPT_NAME nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL;
ALTER TABLE AIPRO_QJDST ALTER COLUMN NAME nvarchar(510) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN NUM nvarchar(510) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN COMPANY_CODE nvarchar(255) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN PROCESS_KEY nvarchar(255) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN PROCESS_ID nvarchar(255) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN FORM_NO nvarchar(255) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN PROCESS_TASK_I18N nvarchar(255) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN PROCESS_STATUS nvarchar(50) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN PROCESS_NAME nvarchar(100) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN PROCESS_VERSION nvarchar(20) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN POS_LAY_REC nvarchar(255) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN OWNER_POS_CODE nvarchar(255) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN OWNER_POS_NAME nvarchar(255) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN CREATE_POS_CODE nvarchar(255) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN CREATE_POS_NAME nvarchar(255) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN OWNER_STAFF_CODE nvarchar(50) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN OWNER_STAFF_NAME nvarchar(250) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN DELETE_STAFF_CODE nvarchar(50) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN DELETE_STAFF_NAME nvarchar(250) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN MODIFY_STAFF_CODE nvarchar(50) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN MODIFY_STAFF_NAME nvarchar(250) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN CREATE_STAFF_CODE nvarchar(50) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN CREATE_STAFF_NAME nvarchar(250) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN OWNER_DEPT_CODE nvarchar(255) COLLATE Chinese_PRC_CI_AS;
ALTER TABLE AIPRO_QJDST ALTER COLUMN OWNER_DEPT_NAME nvarchar(255) COLLATE Chinese_PRC_CI_AS;
EXEC sp_addextendedproperty  'MS_Description' , N'姓名', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'NAME';
EXEC sp_updateextendedproperty  'MS_Description' , N'姓名', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'NAME';
EXEC sp_addextendedproperty  'MS_Description' , N'工号', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'NUM';
EXEC sp_updateextendedproperty  'MS_Description' , N'工号', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'NUM';
EXEC sp_addextendedproperty  'MS_Description' , N'入职时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OPTIME';
EXEC sp_updateextendedproperty  'MS_Description' , N'入职时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OPTIME';
EXEC sp_addextendedproperty  'MS_Description' , N'可用假期', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'GSNUM';
EXEC sp_updateextendedproperty  'MS_Description' , N'可用假期', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'GSNUM';
EXEC sp_addextendedproperty  'MS_Description' , N'请假开始时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'STARTTIME';
EXEC sp_updateextendedproperty  'MS_Description' , N'请假开始时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'STARTTIME';
EXEC sp_addextendedproperty  'MS_Description' , N'请假结束时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'ENDTIME';
EXEC sp_updateextendedproperty  'MS_Description' , N'请假结束时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'ENDTIME';
EXEC sp_addextendedproperty  'MS_Description' , N'公司编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'COMPANY_CODE';
EXEC sp_updateextendedproperty  'MS_Description' , N'公司编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'COMPANY_CODE';
EXEC sp_addextendedproperty  'MS_Description' , N'创建时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'CREATE_TIME';
EXEC sp_updateextendedproperty  'MS_Description' , N'创建时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'CREATE_TIME';
EXEC sp_addextendedproperty  'MS_Description' , N'修改时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'MODIFY_TIME';
EXEC sp_updateextendedproperty  'MS_Description' , N'修改时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'MODIFY_TIME';
EXEC sp_addextendedproperty  'MS_Description' , N'删除时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'DELETE_TIME';
EXEC sp_updateextendedproperty  'MS_Description' , N'删除时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'DELETE_TIME';
EXEC sp_addextendedproperty  'MS_Description' , N'排序', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'SORT';
EXEC sp_updateextendedproperty  'MS_Description' , N'排序', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'SORT';
EXEC sp_addextendedproperty  'MS_Description' , N'流程key', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_KEY';
EXEC sp_updateextendedproperty  'MS_Description' , N'流程key', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_KEY';
EXEC sp_addextendedproperty  'MS_Description' , N'流程实例id', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_ID';
EXEC sp_updateextendedproperty  'MS_Description' , N'流程实例id', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_ID';
EXEC sp_addextendedproperty  'MS_Description' , N'流程单据编号', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'FORM_NO';
EXEC sp_updateextendedproperty  'MS_Description' , N'流程单据编号', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'FORM_NO';
EXEC sp_addextendedproperty  'MS_Description' , N'流程单据状态', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_TASK_I18N';
EXEC sp_updateextendedproperty  'MS_Description' , N'流程单据状态', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_TASK_I18N';
EXEC sp_addextendedproperty  'MS_Description' , N'流程状态', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_STATUS';
EXEC sp_updateextendedproperty  'MS_Description' , N'流程状态', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_STATUS';
EXEC sp_addextendedproperty  'MS_Description' , N'流程名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_NAME';
EXEC sp_updateextendedproperty  'MS_Description' , N'流程名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_NAME';
EXEC sp_addextendedproperty  'MS_Description' , N'流程版本', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_VERSION';
EXEC sp_updateextendedproperty  'MS_Description' , N'流程版本', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'PROCESS_VERSION';
EXEC sp_addextendedproperty  'MS_Description' , N'生效时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'EFFECT_TIME';
EXEC sp_updateextendedproperty  'MS_Description' , N'生效时间', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'EFFECT_TIME';
EXEC sp_addextendedproperty  'MS_Description' , N'主键ID', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'ID';
EXEC sp_updateextendedproperty  'MS_Description' , N'主键ID', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'ID';
EXEC sp_addextendedproperty  'MS_Description' , N'版本号', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'VERSION';
EXEC sp_updateextendedproperty  'MS_Description' , N'版本号', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'VERSION';
EXEC sp_addextendedproperty  'MS_Description' , N'是否有效', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'VALID';
EXEC sp_updateextendedproperty  'MS_Description' , N'是否有效', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'VALID';
EXEC sp_addextendedproperty  'MS_Description' , N'岗位层级', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'POS_LAY_REC';
EXEC sp_updateextendedproperty  'MS_Description' , N'岗位层级', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'POS_LAY_REC';
EXEC sp_addextendedproperty  'MS_Description' , N'所有者岗位编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_POS_CODE';
EXEC sp_updateextendedproperty  'MS_Description' , N'所有者岗位编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_POS_CODE';
EXEC sp_addextendedproperty  'MS_Description' , N'所有者岗位名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_POS_NAME';
EXEC sp_updateextendedproperty  'MS_Description' , N'所有者岗位名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_POS_NAME';
EXEC sp_addextendedproperty  'MS_Description' , N'创建人岗位编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'CREATE_POS_CODE';
EXEC sp_updateextendedproperty  'MS_Description' , N'创建人岗位编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'CREATE_POS_CODE';
EXEC sp_addextendedproperty  'MS_Description' , N'创建人岗位名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'CREATE_POS_NAME';
EXEC sp_updateextendedproperty  'MS_Description' , N'创建人岗位名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'CREATE_POS_NAME';
EXEC sp_addextendedproperty  'MS_Description' , N'所有者员工编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_STAFF_CODE';
EXEC sp_updateextendedproperty  'MS_Description' , N'所有者员工编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_STAFF_CODE';
EXEC sp_addextendedproperty  'MS_Description' , N'所有者员工名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_STAFF_NAME';
EXEC sp_updateextendedproperty  'MS_Description' , N'所有者员工名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_STAFF_NAME';
EXEC sp_addextendedproperty  'MS_Description' , N'删除人编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'DELETE_STAFF_CODE';
EXEC sp_updateextendedproperty  'MS_Description' , N'删除人编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'DELETE_STAFF_CODE';
EXEC sp_addextendedproperty  'MS_Description' , N'删除人名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'DELETE_STAFF_NAME';
EXEC sp_updateextendedproperty  'MS_Description' , N'删除人名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'DELETE_STAFF_NAME';
EXEC sp_addextendedproperty  'MS_Description' , N'修改人编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'MODIFY_STAFF_CODE';
EXEC sp_updateextendedproperty  'MS_Description' , N'修改人编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'MODIFY_STAFF_CODE';
EXEC sp_addextendedproperty  'MS_Description' , N'修改人名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'MODIFY_STAFF_NAME';
EXEC sp_updateextendedproperty  'MS_Description' , N'修改人名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'MODIFY_STAFF_NAME';
EXEC sp_addextendedproperty  'MS_Description' , N'创建人编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'CREATE_STAFF_CODE';
EXEC sp_updateextendedproperty  'MS_Description' , N'创建人编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'CREATE_STAFF_CODE';
EXEC sp_addextendedproperty  'MS_Description' , N'创建人名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'CREATE_STAFF_NAME';
EXEC sp_updateextendedproperty  'MS_Description' , N'创建人名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'CREATE_STAFF_NAME';
EXEC sp_addextendedproperty  'MS_Description' , N'所有者部门编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_DEPT_CODE';
EXEC sp_updateextendedproperty  'MS_Description' , N'所有者部门编码', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_DEPT_CODE';
EXEC sp_addextendedproperty  'MS_Description' , N'所有者部门名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_DEPT_NAME';
EXEC sp_updateextendedproperty  'MS_Description' , N'所有者部门名称', 'schema', N'dbo', 'table', N'AIPRO_QJDST', 'column', N'OWNER_DEPT_NAME';
ALTER TABLE AIPRO_QJDST COMMENT '请假单';
