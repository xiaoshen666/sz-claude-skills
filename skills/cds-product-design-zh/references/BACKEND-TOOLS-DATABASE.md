# 后端数据库规范工具指南

## 概述

本指南详细说明了 CDS 后端项目的数据库设计规范，包括表结构设计、SQL 脚本规范、数据库初始化等。

## 数据库表设计规范

### 基础表结构

```sql
-- 基础表示例
CREATE TABLE {acronym}_{modelCode小写} (
    id NUMBER(19) PRIMARY KEY,
    name VARCHAR2(255) NOT NULL COMMENT '名称',
    code VARCHAR2(100) COMMENT '编码',
    status VARCHAR2(50) COMMENT '状态',
    remark VARCHAR2(1000) COMMENT '备注',
    cid NUMBER(19) COMMENT '租户ID',
    create_date TIMESTAMP COMMENT '创建时间',
    create_user_id NUMBER(19) COMMENT '创建人ID',
    create_user_code VARCHAR2(100) COMMENT '创建人编码',
    create_user_name VARCHAR2(255) COMMENT '创建人名称',
    edit_date TIMESTAMP COMMENT '编辑时间',
    edit_user_id NUMBER(19) COMMENT '编辑人ID',
    edit_user_code VARCHAR2(100) COMMENT '编辑人编码',
    edit_user_name VARCHAR2(255) COMMENT '编辑人名称',
    version NUMBER(10) DEFAULT 1 COMMENT '版本号'
);
```

### 表命名规范

#### 表名规则
- **格式**: `{acronym}_{modelCode小写}`
- **示例**: 
  - 模块缩略码: `aipro`
  - 模型编码: `Qjdst`
  - 表名: `aipro_qjdst`

#### 字段命名规范

| 字段类型 | 命名规则 | 示例 |
|---------|---------|------|
| 主键 | `id` | `id` |
| 业务字段 | 小写字母+下划线 | `user_name` |
| 状态字段 | 小写字母+下划线 | `status` |
| 时间字段 | `{action}_date` | `create_date` |
| 用户字段 | `{action}_user_{type}` | `create_user_id` |
| 版本字段 | `version` | `version` |

### 标准字段说明

#### 基础字段

| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|-------|------|------|--------|------|
| `id` | NUMBER(19) | 是 | 无 | 主键，唯一标识 |
| `cid` | NUMBER(19) | 否 | 无 | 租户ID，多租户支持 |
| `version` | NUMBER(10) | 否 | 1 | 版本号，乐观锁 |

#### 审计字段

| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|-------|------|------|--------|------|
| `create_date` | TIMESTAMP | 否 | 当前时间 | 创建时间 |
| `create_user_id` | NUMBER(19) | 否 | 无 | 创建人ID |
| `create_user_code` | VARCHAR2(100) | 否 | 无 | 创建人编码 |
| `create_user_name` | VARCHAR2(255) | 否 | 无 | 创建人名称 |
| `edit_date` | TIMESTAMP | 否 | 当前时间 | 编辑时间 |
| `edit_user_id` | NUMBER(19) | 否 | 无 | 编辑人ID |
| `edit_user_code` | VARCHAR2(100) | 否 | 无 | 编辑人编码 |
| `edit_user_name` | VARCHAR2(255) | 否 | 无 | 编辑人名称 |

### 字段类型规范

#### 常用字段类型

| 业务场景 | 推荐类型 | 长度 | 说明 |
|---------|---------|------|------|
| 主键ID | NUMBER(19) | 19位 | 支持大数量级 |
| 名称 | VARCHAR2(255) | 255字符 | 通用名称字段 |
| 编码 | VARCHAR2(100) | 100字符 | 业务编码 |
| 状态 | VARCHAR2(50) | 50字符 | 状态标识 |
| 备注 | VARCHAR2(1000) | 1000字符 | 长文本说明 |
| 创建时间 | TIMESTAMP | - | 精确到毫秒 |
| 数值 | NUMBER(p,s) | 自定义 | 精度可控 |
| 金额 | NUMBER(19,2) | 19位,2位小数 | 财务相关 |

#### 类型选择原则

1. **主键选择**:
   - 优先使用 `NUMBER(19)` 作为主键类型
   - 支持自增或序列生成
   - 确保唯一性和性能

2. **字符串类型**:
   - 固定长度使用 `CHAR`
   - 可变长度使用 `VARCHAR2`
   - 大文本使用 `CLOB`

3. **数值类型**:
   - 整数使用 `NUMBER(p)`
   - 小数使用 `NUMBER(p,s)`
   - 金额使用 `NUMBER(19,2)`

4. **时间类型**:
   - 日期时间使用 `TIMESTAMP`
   - 仅日期使用 `DATE`

## SQL 脚本规范

### 文件命名规范

#### 初始化脚本
```
{moduleCode}CustomInit.sql

示例:
aiproctsCustomInit.sql        # 请假单模块初始化脚本
salesCustomInit.sql           # 销售模块初始化脚本
```

#### 版本升级脚本
```
{moduleCode}CustomUpgrade_{version}.sql

示例:
aiproctsCustomUpgrade_1.1.0.sql
salesCustomUpgrade_2.0.0.sql
```

### 脚本内容规范

#### 版本控制格式
```sql
## 1.0.0
CREATE TABLE {TABLE_NAME}(
    -- 初始表结构
);

## 1.0.1
ALTER TABLE {TABLE_NAME} ADD COLUMN xxx;

## 1.1.0
CREATE INDEX idx_xxx ON {TABLE_NAME}(xxx);
```

#### 版本规范说明

| 版本格式 | 说明 | 示例 |
|----------|------|------|
| `## X.Y.Z` | 语义化版本 | `## 1.0.0`, `## 1.0.1`, `## 1.1.0` |
| `X` | 主版本号，重大变更 | 表结构重构 |
| `Y` | 次版本号，功能新增 | 新增字段/索引 |
| `Z` | 修订号，问题修复 | 修复数据问题 |

### SQL 编写规范

#### DDL 语句规范

```sql
-- 建表语句
CREATE TABLE {table_name} (
    id NUMBER(19) PRIMARY KEY,
    name VARCHAR2(255) NOT NULL COMMENT '名称',
    code VARCHAR2(100) COMMENT '编码',
    status VARCHAR2(50) COMMENT '状态',
    remark VARCHAR2(1000) COMMENT '备注',
    cid NUMBER(19) COMMENT '租户ID',
    create_date TIMESTAMP DEFAULT SYSTIMESTAMP COMMENT '创建时间',
    create_user_id NUMBER(19) COMMENT '创建人ID',
    create_user_code VARCHAR2(100) COMMENT '创建人编码',
    create_user_name VARCHAR2(255) COMMENT '创建人名称',
    edit_date TIMESTAMP DEFAULT SYSTIMESTAMP COMMENT '编辑时间',
    edit_user_id NUMBER(19) COMMENT '编辑人ID',
    edit_user_code VARCHAR2(100) COMMENT '编辑人编码',
    edit_user_name VARCHAR2(255) COMMENT '编辑人名称',
    version NUMBER(10) DEFAULT 1 COMMENT '版本号'
);

-- 添加注释
COMMENT ON TABLE {table_name} IS '表名说明';
COMMENT ON COLUMN {table_name}.id IS '主键ID';
COMMENT ON COLUMN {table_name}.name IS '名称';
```

#### DML 语句规范

```sql
-- 插入数据
INSERT INTO {table_name} (
    id, name, code, status, create_date, create_user_id, version
) VALUES (
    {seq}.nextval, '示例名称', 'SAMPLE001', 'ENABLED', 
    SYSTIMESTAMP, 1, 1
);

-- 更新数据
UPDATE {table_name} 
SET name = '新名称', 
    edit_date = SYSTIMESTAMP, 
    edit_user_id = 1, 
    version = version + 1
WHERE id = 1 AND version = 1;

-- 删除数据（逻辑删除推荐）
UPDATE {table_name} 
SET status = 'DELETED', 
    edit_date = SYSTIMESTAMP, 
    edit_user_id = 1, 
    version = version + 1
WHERE id = 1 AND status != 'DELETED';
```

#### 索引创建规范

```sql
-- 唯一索引
CREATE UNIQUE INDEX idx_{table_name}_code 
ON {table_name}(code) 
WHERE status != 'DELETED';

-- 普通索引
CREATE INDEX idx_{table_name}_status 
ON {table_name}(status);

-- 复合索引
CREATE INDEX idx_{table_name}_user_date 
ON {table_name}(create_user_id, create_date);

-- 函数索引（Oracle）
CREATE INDEX idx_{table_name}_upper_name 
ON {table_name}(UPPER(name));
```

## 数据库设计最佳实践

### 表设计原则

#### 1. 范式化设计
- **第一范式**: 每个字段都是原子的，不可再分
- **第二范式**: 消除部分函数依赖
- **第三范式**: 消除传递函数依赖

#### 2. 性能优化
- **适度冗余**: 在查询性能和数据一致性之间平衡
- **合理分区**: 大数据量表考虑分区策略
- **索引优化**: 为常用查询条件创建合适索引

#### 3. 扩展性考虑
- **预留字段**: 可适当预留扩展字段
- **灵活设计**: 使用 JSON 字段存储动态属性
- **版本控制**: 表结构变更要有版本管理

### 索引设计策略

#### 索引类型选择

| 场景 | 索引类型 | 说明 |
|------|---------|------|
| 主键 | 主键索引 | 自动创建，唯一且非空 |
| 唯一约束 | 唯一索引 | 保证数据唯一性 |
| 查询条件 | 普通索引 | 提高查询性能 |
| 排序字段 | 普通索引 | 避免排序操作 |
| 外键关联 | 普通索引 | 提高关联查询性能 |

#### 索引设计原则

1. **选择性原则**:
   - 选择区分度高的字段建立索引
   - 避免在低区分度字段上建立索引

2. **最左前缀原则**:
   - 复合索引要遵循最左前缀匹配
   - 查询条件要包含索引的最左列

3. **覆盖索引原则**:
   - 尽量让查询只需要扫描索引
   - 减少回表操作

4. **索引维护原则**:
   - 定期分析索引使用情况
   - 删除无用索引，减少维护开销

### 数据完整性约束

#### 主键约束
```sql
ALTER TABLE {table_name} 
ADD CONSTRAINT pk_{table_name} 
PRIMARY KEY (id);
```

#### 唯一约束
```sql
ALTER TABLE {table_name} 
ADD CONSTRAINT uk_{table_name}_code 
UNIQUE (code);
```

#### 外键约束（谨慎使用）
```sql
ALTER TABLE {child_table} 
ADD CONSTRAINT fk_{child_table}_{parent_table} 
FOREIGN KEY (parent_id) 
REFERENCES {parent_table}(id);
```

#### 检查约束
```sql
ALTER TABLE {table_name} 
ADD CONSTRAINT ck_{table_name}_status 
CHECK (status IN ('ENABLED', 'DISABLED', 'DELETED'));
```

## 数据库初始化流程

### 1. 环境准备

```sql
-- 创建表空间（DBA操作）
CREATE TABLESPACE {moduleCode}_ts 
DATAFILE '{moduleCode}_01.dbf' SIZE 100M AUTOEXTEND ON;

-- 创建用户（DBA操作）
CREATE USER {moduleCode} IDENTIFIED BY password 
DEFAULT TABLESPACE {moduleCode}_ts;

-- 授权
GRANT CONNECT, RESOURCE TO {moduleCode};
```

### 2. 表结构创建

```sql
-- 执行初始化脚本
@aiproctsCustomInit.sql
```

### 3. 基础数据插入

```sql
-- 插入必要的初始化数据
INSERT INTO aipro_sys_config (id, config_key, config_value, status) 
VALUES (1, 'SYSTEM_NAME', 'AI流程管理系统', 'ENABLED');

-- 提交事务
COMMIT;
```

### 4. 索引和约束

```sql
-- 创建必要的索引
CREATE INDEX idx_aipro_qjdst_status ON aipro_qjdst(status);
CREATE INDEX idx_aipro_qjdst_create_date ON aipro_qjdst(create_date);

-- 添加约束
ALTER TABLE aipro_qjdst ADD CONSTRAINT ck_aipro_qjdst_status 
CHECK (status IN ('ENABLED', 'DISABLED', 'DELETED'));
```

## 数据库维护

### 日常维护任务

#### 统计信息收集
```sql
-- Oracle
EXEC DBMS_STATS.GATHER_TABLE_STATS('SCHEMA_NAME', 'TABLE_NAME');

-- MySQL
ANALYZE TABLE table_name;
```

#### 索引重建
```sql
-- Oracle
ALTER INDEX index_name REBUILD;

-- MySQL
ALTER TABLE table_name DROP INDEX index_name, ADD INDEX index_name (column_name);
```

#### 表空间监控
```sql
-- 检查表空间使用情况
SELECT tablespace_name, 
       ROUND(SUM(bytes)/1024/1024,2) "Size(MB)",
       ROUND(SUM(bytes)/1024/1024 - SUM(decode(maxbytes,0,bytes,maxbytes))/1024/1024,2) "Used(MB)"
FROM dba_data_files 
GROUP BY tablespace_name;
```

### 性能监控

#### 慢查询监控
```sql
-- Oracle
SELECT sql_id, executions, elapsed_time/1000000 elapsed_seconds,
       sql_text
FROM v$sql 
WHERE elapsed_time/1000000 > 1
ORDER BY elapsed_time DESC;

-- MySQL
SHOW FULL PROCESSLIST;
```

#### 锁监控
```sql
-- Oracle
SELECT * FROM v$locked_object;
SELECT * FROM v$session WHERE blocking_session IS NOT NULL;

-- MySQL
SHOW ENGINE INNODB STATUS;
```

## 多数据库支持

### Oracle 特定语法

```sql
-- 序列创建
CREATE SEQUENCE seq_{table_name}
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- 触发器（自动设置创建时间）
CREATE OR REPLACE TRIGGER trg_{table_name}_create
BEFORE INSERT ON {table_name}
FOR EACH ROW
BEGIN
    :NEW.create_date := SYSTIMESTAMP;
    :NEW.edit_date := SYSTIMESTAMP;
    IF :NEW.id IS NULL THEN
        :NEW.id := seq_{table_name}.nextval;
    END IF;
END;
```

### MySQL 特定语法

```sql
-- 自增主键
CREATE TABLE {table_name} (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL COMMENT '名称',
    code VARCHAR(100) COMMENT '编码',
    status VARCHAR(50) COMMENT '状态',
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    edit_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '编辑时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表说明';

-- 索引创建
CREATE INDEX idx_{table_name}_status ON {table_name}(status);
CREATE UNIQUE INDEX uk_{table_name}_code ON {table_name}(code);
```

## 使用指南

### 数据库设计检查清单

- [ ] 表名符合命名规范
- [ ] 字段类型和长度合理
- [ ] 主键设计正确
- [ ] 必要的索引已创建
- [ ] 约束条件完整
- [ ] 审计字段齐全
- [ ] 注释完整
- [ ] 版本控制规范

### 常见问题解决

#### 性能问题
- **问题**: 查询缓慢
- **检查**: 执行计划、索引使用
- **解决**: 优化SQL、添加索引、重构查询

#### 空间问题
- **问题**: 表空间不足
- **检查**: 表空间使用情况
- **解决**: 扩展表空间、清理历史数据

#### 锁问题
- **问题**: 死锁或锁等待
- **检查**: 锁监控视图
- **解决**: 优化事务、调整隔离级别