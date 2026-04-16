# Dao和Mapper生成指南

## 概述

本文档定义Dao接口和Mapper XML的生成规范和模板。

## 文件目录说明

### Dao接口存放位置

**模块**: `{moduleCode}-custom-dao`

**路径**: `{moduleCode}/{moduleCode}-custom-dao/src/main/java/com/supcon/nebule/{moduleCode}/custom/dao/`

**完整示例**:
```
{moduleCode}-custom-dao/
└── src/main/java/com/supcon/nebule/{moduleCode}/custom/
    └── dao/
        └── Custom{ModuleCode}{ModelCode}Dao.java
```

### Mapper XML存放位置

**模块**: `{moduleCode}-custom-resource`

**路径**: `{moduleCode}/{moduleCode}-custom-resource/src/main/resources/mapper/`

**完整示例**:
```
{moduleCode}-custom-resource/
└── src/main/resources/
    └── mapper/
        └── Custom{ModuleCode}{ModelCode}Mapper.xml
```

**详细说明**: 参考 [后端模块代码目录查找规则](BACKEND-MODULE-STRUCT-RULES.md)

## Dao接口模板

```java
package com.supcon.nebule.{moduleCode}.custom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supcon.nebule.{moduleCode}.custom.entity.Custom{ModuleCode}{ModelCode}Entity;
import org.apache.ibatis.annotations.Mapper;

/**
 * {ModelName}数据访问接口
 */
@Mapper
public interface Custom{ModuleCode}{ModelCode}Dao extends BaseMapper<Custom{ModuleCode}{ModelCode}Entity> {
    
    // 自定义查询方法可以在这里添加
    // 例如：
    // List<Custom{ModuleCode}{ModelCode}Entity> selectByCondition(@Param("query") Custom{ModuleCode}{ModelCode}BO queryBO);
}
```

## Mapper XML模板（可选）

如果需要自定义SQL，可以创建Mapper XML文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.supcon.nebule.{moduleCode}.custom.dao.Custom{ModuleCode}{ModelCode}Dao">

    <resultMap id="BaseResultMap" type="com.supcon.nebule.{moduleCode}.custom.entity.Custom{ModuleCode}{ModelCode}Entity">
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="code" property="code"/>
        <result column="status" property="status"/>
        <result column="remark" property="remark"/>
        <result column="create_time" property="createTime"/>
        <result column="update_time" property="updateTime"/>
    </resultMap>

    <sql id="Base_Column_List">
        id, name, code, status, remark, create_time, update_time
    </sql>

    <!-- 自定义查询示例 -->
    <!--
    <select id="selectByCondition" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM {tableName}
        <where>
            <if test="query.name != null and query.name != ''">
                AND name LIKE CONCAT('%', #{query.name}, '%')
            </if>
            <if test="query.code != null and query.code != ''">
                AND code = #{query.code}
            </if>
            <if test="query.status != null and query.status != ''">
                AND status = #{query.status}
            </if>
        </where>
        ORDER BY create_time DESC
    </select>
    -->

</mapper>
```

## 生成规范

### Dao接口设计原则
1. **继承BaseMapper**：继承MyBatis-Plus的BaseMapper接口
2. **不继承自定义基类**：不使用BaseDao等自定义基类
3. **@Mapper注解**：必须添加@Mapper注解
4. **泛型指定**：指定Entity类型参数

### Mapper XML规范
1. **按需创建**：只有需要自定义SQL时才创建XML文件
2. **namespace**：必须与Dao接口全限定名一致
3. **resultMap**：定义基础的结果映射
4. **Base_Column_List**：定义基础字段列表，便于复用

## 数据库类型适配

根据用户选择的数据库类型，调整SQL语法：

### Oracle数据库（默认）
```sql
-- 分页查询
SELECT * FROM (
    SELECT a.*, ROWNUM rn FROM (
        SELECT * FROM {tableName} ORDER BY create_time DESC
    ) a WHERE ROWNUM <= #{endRow}
) WHERE rn > #{startRow}

-- 序列
{tableName}_SEQ.NEXTVAL

-- 日期函数
SYSDATE
```

### SQL Server数据库
```sql
-- 分页查询
SELECT * FROM (
    SELECT *, ROW_NUMBER() OVER (ORDER BY create_time DESC) AS rn 
    FROM {tableName}
) t WHERE rn > #{startRow} AND rn <= #{endRow}

-- 自增ID
IDENTITY(1,1)

-- 日期函数
GETDATE()
```

### MariaDB数据库
```sql
-- 分页查询
SELECT * FROM {tableName} 
ORDER BY create_time DESC 
LIMIT #{startRow}, #{pageSize}

-- 自增ID
AUTO_INCREMENT

-- 日期函数
NOW()
```

### DM数据库（达梦）
```sql
-- 分页查询
SELECT * FROM (
    SELECT a.*, ROWNUM rn FROM (
        SELECT * FROM {tableName} ORDER BY create_time DESC
    ) a WHERE ROWNUM <= #{endRow}
) WHERE rn > #{startRow}

-- 序列
{tableName}_SEQ.NEXTVAL

-- 日期函数
SYSDATE
```

## 包路径规范

- **Dao接口包路径**: `com.supcon.nebule.{moduleCode}.custom.dao`
- **Mapper XML路径**: `{moduleCode}-custom-dao/src/main/resources/mapper/`

## 文件命名规范

| 类型 | 命名格式 | 示例 |
|------|---------|------|
| Dao接口 | Custom{ModuleCode}{ModelCode}Dao | CustomOrderOrderDao |
| Mapper XML | Custom{ModuleCode}{ModelCode}Mapper.xml | CustomOrderOrderMapper.xml |

## 常用方法

MyBatis-Plus的BaseMapper已提供以下方法，无需重复定义：

- `int insert(T entity)`: 插入一条记录
- `int deleteById(Serializable id)`: 根据ID删除
- `int deleteByMap(@Param("cm") Map<String, Object> columnMap)`: 根据条件删除
- `int updateById(T entity)`: 根据ID更新
- `T selectById(Serializable id)`: 根据ID查询
- `List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList)`: 批量查询
- `List<T> selectByMap(@Param("cm") Map<String, Object> columnMap)`: 根据条件查询
- `T selectOne(@Param("ew") Wrapper<T> queryWrapper)`: 查询一条记录
- `Integer selectCount(@Param("ew") Wrapper<T> queryWrapper)`: 查询总记录数
- `List<T> selectList(@Param("ew") Wrapper<T> queryWrapper)`: 查询列表
- `List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> queryWrapper)`: 查询列表（Map形式）

## 自定义方法示例

如果需要自定义查询方法：

### Dao接口
```java
@Mapper
public interface Custom{ModuleCode}{ModelCode}Dao extends BaseMapper<Custom{ModuleCode}{ModelCode}Entity> {
    
    /**
     * 根据条件查询
     * @param queryBO 查询条件
     * @return 实体列表
     */
    List<Custom{ModuleCode}{ModelCode}Entity> selectByCondition(
        @Param("query") Custom{ModuleCode}{ModelCode}BO queryBO
    );
}
```

### Mapper XML
```xml
<select id="selectByCondition" resultMap="BaseResultMap">
    SELECT
    <include refid="Base_Column_List"/>
    FROM {tableName}
    <where>
        <if test="query.name != null and query.name != ''">
            AND name LIKE CONCAT('%', #{query.name}, '%')
        </if>
        <if test="query.status != null and query.status != ''">
            AND status = #{query.status}
        </if>
    </where>
    ORDER BY create_time DESC
</select>
```
