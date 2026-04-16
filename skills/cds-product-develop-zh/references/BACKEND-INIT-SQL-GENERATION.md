# 初始化SQL生成指南

## 概述

本文档定义初始化SQL（建表SQL）的生成规范和模板，支持多种数据库类型。

## 文件目录说明

### SQL文件存放位置

**模块**: `{moduleCode}-custom-dao`

**路径**: `{moduleCode}/{moduleCode}-custom-dao/src/main/resources/META-INF/{databaseType}/`

**数据库类型目录**:
- `oracle/` - Oracle数据库SQL
- `sqlserver/` - SQL Server数据库SQL
- `mariadb/` - MariaDB数据库SQL
- `dm/` - DM（达梦）数据库SQL

**完整示例**:
```
{moduleCode}-custom-dao/
└── src/main/resources/
    └── META-INF/
        └── oracle/  -- 根据选择的数据库类型
            └── {moduleCode}CustomInit.sql
```

**文件命名规范**: `{moduleCode}CustomInit.sql`

**详细说明**: 参考 [后端模块代码目录查找规则](BACKEND-MODULE-STRUCT-RULES.md)

## 建表SQL模板

### Oracle数据库（默认）

```sql
## 1.0.0
-- 创建表
CREATE TABLE {acronym}_{modelCode} (
    id NUMBER(19) NOT NULL,
    name VARCHAR2(200),
    code VARCHAR2(100),
    status VARCHAR2(50),
    remark VARCHAR2(500),
    create_time TIMESTAMP DEFAULT SYSTIMESTAMP,
    update_time TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (id)
);

-- 添加注释
COMMENT ON TABLE {acronym}_{modelCode} IS '{tableComment}';
COMMENT ON COLUMN {acronym}_{modelCode}.id IS '主键ID';
COMMENT ON COLUMN {acronym}_{modelCode}.name IS '名称';
COMMENT ON COLUMN {acronym}_{modelCode}.code IS '编码';
COMMENT ON COLUMN {acronym}_{modelCode}.status IS '状态';
COMMENT ON COLUMN {acronym}_{modelCode}.remark IS '备注';
COMMENT ON COLUMN {acronym}_{modelCode}.create_time IS '创建时间';
COMMENT ON COLUMN {acronym}_{modelCode}.update_time IS '更新时间';

-- 创建序列
CREATE SEQUENCE {acronym}_{modelCode}_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- 创建索引
CREATE INDEX idx_{acronym}_{modelCode}_code ON {acronym}_{modelCode}(code);
CREATE INDEX idx_{acronym}_{modelCode}_status ON {acronym}_{modelCode}(status);
```

### SQL Server数据库

```sql
## 1.0.0
-- 创建表
CREATE TABLE {acronym}_{modelCode} (
    id BIGINT IDENTITY(1,1) NOT NULL,
    name NVARCHAR(200),
    code NVARCHAR(100),
    status NVARCHAR(50),
    remark NVARCHAR(500),
    create_time DATETIME DEFAULT GETDATE(),
    update_time DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (id)
);

-- 添加注释
EXEC sp_addextendedproperty 'MS_Description', '{tableComment}', 'SCHEMA', 'dbo', 'TABLE', '{acronym}_{modelCode}';
EXEC sp_addextendedproperty 'MS_Description', '主键ID', 'SCHEMA', 'dbo', 'TABLE', '{acronym}_{modelCode}', 'COLUMN', 'id';
EXEC sp_addextendedproperty 'MS_Description', '名称', 'SCHEMA', 'dbo', 'TABLE', '{acronym}_{modelCode}', 'COLUMN', 'name';
EXEC sp_addextendedproperty 'MS_Description', '编码', 'SCHEMA', 'dbo', 'TABLE', '{acronym}_{modelCode}', 'COLUMN', 'code';
EXEC sp_addextendedproperty 'MS_Description', '状态', 'SCHEMA', 'dbo', 'TABLE', '{acronym}_{modelCode}', 'COLUMN', 'status';
EXEC sp_addextendedproperty 'MS_Description', '备注', 'SCHEMA', 'dbo', 'TABLE', '{acronym}_{modelCode}', 'COLUMN', 'remark';
EXEC sp_addextendedproperty 'MS_Description', '创建时间', 'SCHEMA', 'dbo', 'TABLE', '{acronym}_{modelCode}', 'COLUMN', 'create_time';
EXEC sp_addextendedproperty 'MS_Description', '更新时间', 'SCHEMA', 'dbo', 'TABLE', '{acronym}_{modelCode}', 'COLUMN', 'update_time';

-- 创建索引
CREATE INDEX idx_{acronym}_{modelCode}_code ON {acronym}_{modelCode}(code);
CREATE INDEX idx_{acronym}_{modelCode}_status ON {acronym}_{modelCode}(status);
```

### MariaDB数据库

```sql
## 1.0.0
-- 创建表
CREATE TABLE {acronym}_{modelCode} (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(200) COMMENT '名称',
    code VARCHAR(100) COMMENT '编码',
    status VARCHAR(50) COMMENT '状态',
    remark VARCHAR(500) COMMENT '备注',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='{tableComment}';

-- 创建索引
CREATE INDEX idx_{acronym}_{modelCode}_code ON {acronym}_{modelCode}(code);
CREATE INDEX idx_{acronym}_{modelCode}_status ON {acronym}_{modelCode}(status);
```

### DM数据库（达梦）

```sql
## 1.0.0
-- 创建表
CREATE TABLE {acronym}_{modelCode} (
    id BIGINT NOT NULL,
    name VARCHAR(200),
    code VARCHAR(100),
    status VARCHAR(50),
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT SYSDATE,
    update_time TIMESTAMP DEFAULT SYSDATE,
    PRIMARY KEY (id)
);

-- 添加注释
COMMENT ON TABLE {acronym}_{modelCode} IS '{tableComment}';
COMMENT ON COLUMN {acronym}_{modelCode}.id IS '主键ID';
COMMENT ON COLUMN {acronym}_{modelCode}.name IS '名称';
COMMENT ON COLUMN {acronym}_{modelCode}.code IS '编码';
COMMENT ON COLUMN {acronym}_{modelCode}.status IS '状态';
COMMENT ON COLUMN {acronym}_{modelCode}.remark IS '备注';
COMMENT ON COLUMN {acronym}_{modelCode}.create_time IS '创建时间';
COMMENT ON COLUMN {acronym}_{modelCode}.update_time IS '更新时间';

-- 创建序列
CREATE SEQUENCE {acronym}_{modelCode}_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- 创建索引
CREATE INDEX idx_{acronym}_{modelCode}_code ON {acronym}_{modelCode}(code);
CREATE INDEX idx_{acronym}_{modelCode}_status ON {acronym}_{modelCode}(status);
```

## 数据库类型差异对照表

| 特性 | Oracle | SQL Server | MariaDB | DM |
|------|--------|-----------|---------|-----|
| 主键自增 | SEQUENCE | IDENTITY | AUTO_INCREMENT | SEQUENCE |
| 日期函数 | SYSTIMESTAMP | GETDATE() | CURRENT_TIMESTAMP | SYSDATE |
| 字符串类型 | VARCHAR2 | NVARCHAR | VARCHAR | VARCHAR |
| 大文本 | CLOB | NTEXT | TEXT | CLOB |
| 注释语法 | COMMENT ON | sp_addextendedproperty | COMMENT | COMMENT ON |
| 索引语法 | CREATE INDEX | CREATE INDEX | CREATE INDEX | CREATE INDEX |

## SQL文件命名规范

- **格式**: `{moduleCode}CustomInit.sql`
- **示例**: `aiproctsCustomInit.sql`、`orderCustomInit.sql`

## SQL文件存放位置

```
{moduleCode}-custom-dao/
└── src/main/resources/
    └── META-INF/
        └── {databaseType}/  -- oracle/sqlserver/mariadb/dm
            └── {moduleCode}CustomInit.sql
```

## 版本控制格式

### 核心机制

所有版本都放在同一个 `{moduleCode}CustomInit.sql` 文件中，通过 `## X.Y.Z` 标记来区分不同版本。

```sql
## 1.0.0
-- 第一版本：创建基础表
CREATE TABLE {acronym}_{modelCode} (
    id NUMBER(19) PRIMARY KEY,
    name VARCHAR2(255) NOT NULL,
    -- ... 其他字段
);
COMMENT ON TABLE {acronym}_{modelCode} IS '表说明';
COMMENT ON COLUMN {acronym}_{modelCode}.id IS '主键ID';

## 1.1.0
-- 第二版本：新增业务表
CREATE TABLE {acronym}_{modelCode2} (
    id NUMBER(19) PRIMARY KEY,
    title VARCHAR2(255) NOT NULL,
    -- ... 其他字段
);
COMMENT ON TABLE {acronym}_{modelCode2} IS '表说明2';

## 1.2.0
-- 第三版本：为已有表添加新字段
ALTER TABLE {acronym}_{modelCode} ADD remark VARCHAR2(1000);
COMMENT ON COLUMN {acronym}_{modelCode}.remark IS '备注信息';

## 2.0.0
-- 第四版本：重大变更，创建新索引
CREATE INDEX idx_{acronym}_{modelCode}_status ON {acronym}_{modelCode}(status);
```

**版本标记规则**：
- 使用 `## X.Y.Z` 格式标记版本号
- 每个版本下方写对应的SQL语句
- 版本之间用空行分隔
- 版本号遵循语义化版本规范：
  - **X**: 主版本号（重大变更，不向后兼容）
  - **Y**: 次版本号（新增功能，向后兼容）
  - **Z**: 修订版本号（Bug修复，向后兼容）

## 生成规范

### 字段映射规则

| Java类型 | Oracle | SQL Server | MariaDB | DM |
|---------|--------|-----------|---------|-----|
| Long | NUMBER(19) | BIGINT | BIGINT | BIGINT |
| String | VARCHAR2(200) | NVARCHAR(200) | VARCHAR(200) | VARCHAR(200) |
| Integer | NUMBER(10) | INT | INT | INT |
| Boolean | NUMBER(1) | BIT | TINYINT | NUMBER(1) |
| LocalDateTime | TIMESTAMP | DATETIME | TIMESTAMP | TIMESTAMP |
| LocalDate | DATE | DATE | DATE | DATE |
| BigDecimal | NUMBER(19,2) | DECIMAL(19,2) | DECIMAL(19,2) | NUMBER(19,2) |

### 必填字段

每个表必须包含以下字段：
- **id**: 主键ID
- **create_time**: 创建时间
- **update_time**: 更新时间

### 审计字段（可选）

- **create_by**: 创建人
- **update_by**: 更新人
- **del_flag**: 删除标记（0-正常，1-删除）

### 索引创建规则

1. **主键索引**：自动创建（PRIMARY KEY）
2. **唯一索引**：编码字段（code）通常创建唯一索引
3. **普通索引**：状态字段（status）、外键字段等
4. **组合索引**：根据查询场景创建

## 初始化数据SQL（可选）

如果需要插入初始化数据，在对应版本下方添加：

### Oracle
```sql
## 1.0.0
-- 创建表
CREATE TABLE {acronym}_{modelCode} (
    id NUMBER(19) NOT NULL,
    name VARCHAR2(200),
    code VARCHAR2(100),
    status VARCHAR2(50),
    remark VARCHAR2(500),
    create_time TIMESTAMP DEFAULT SYSTIMESTAMP,
    update_time TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (id)
);

-- 添加注释
COMMENT ON TABLE {acronym}_{modelCode} IS '{tableComment}';
COMMENT ON COLUMN {acronym}_{modelCode}.id IS '主键ID';
COMMENT ON COLUMN {acronym}_{modelCode}.name IS '名称';
COMMENT ON COLUMN {acronym}_{modelCode}.code IS '编码';
COMMENT ON COLUMN {acronym}_{modelCode}.status IS '状态';
COMMENT ON COLUMN {acronym}_{modelCode}.remark IS '备注';
COMMENT ON COLUMN {acronym}_{modelCode}.create_time IS '创建时间';
COMMENT ON COLUMN {acronym}_{modelCode}.update_time IS '更新时间';

-- 创建序列
CREATE SEQUENCE {acronym}_{modelCode}_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- 创建索引
CREATE INDEX idx_{acronym}_{modelCode}_code ON {acronym}_{modelCode}(code);
CREATE INDEX idx_{acronym}_{modelCode}_status ON {acronym}_{modelCode}(status);

-- 插入初始化数据
INSERT INTO {acronym}_{modelCode} (id, name, code, status, remark, create_time, update_time)
VALUES ({acronym}_{modelCode}_SEQ.NEXTVAL, '示例名称', 'EXAMPLE_CODE', 'ACTIVE', '备注信息', SYSTIMESTAMP, SYSTIMESTAMP);

COMMIT;
```

### SQL Server
```sql
## 1.0.0
-- 插入初始化数据
INSERT INTO {acronym}_{modelCode} (name, code, status, remark, create_time, update_time)
VALUES ('示例名称', 'EXAMPLE_CODE', 'ACTIVE', '备注信息', GETDATE(), GETDATE());
```

### MariaDB
```sql
## 1.0.0
-- 插入初始化数据
INSERT INTO {acronym}_{modelCode} (name, code, status, remark)
VALUES ('示例名称', 'EXAMPLE_CODE', 'ACTIVE', '备注信息');
```

### DM
```sql
## 1.0.0
-- 插入初始化数据
INSERT INTO {acronym}_{modelCode} (id, name, code, status, remark, create_time, update_time)
VALUES ({acronym}_{modelCode}_SEQ.NEXTVAL, '示例名称', 'EXAMPLE_CODE', 'ACTIVE', '备注信息', SYSDATE, SYSDATE);

COMMIT;
```

## 注意事项

1. **数据库类型一致性**：整个项目必须使用同一种数据库类型
2. **字符集**：建议使用UTF-8/UTF8MB4
3. **排序规则**：根据业务需求选择合适的排序规则
4. **事务控制**：批量插入时使用COMMIT
5. **幂等性**：SQL脚本应该支持重复执行（使用IF NOT EXISTS等）
