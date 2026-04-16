# Service和ServiceImpl生成指南

## 概述

本文档定义Service接口和ServiceImpl实现类的生成规范和模板。

## 文件目录说明

### Service接口存放位置

**模块**: `{moduleCode}-custom-service`

**路径**: `{moduleCode}/{moduleCode}-custom-service/src/main/java/com/supcon/nebule/{moduleCode}/custom/service/`

**完整示例**:
```
{moduleCode}-custom-service/
└── src/main/java/com/supcon/nebule/{moduleCode}/custom/
    └── service/
        └── Custom{ModuleCode}{ModelCode}Service.java
```

### ServiceImpl存放位置

**模块**: `{moduleCode}-custom-service`

**路径**: `{moduleCode}/{moduleCode}-custom-service/src/main/java/com/supcon/nebule/{moduleCode}/custom/service/impl/`

**完整示例**:
```
{moduleCode}-custom-service/
└── src/main/java/com/supcon/nebule/{moduleCode}/custom/
    └── service/
        └── impl/
            └── Custom{ModuleCode}{ModelCode}ServiceImpl.java
```

**详细说明**: 参考 [后端模块代码目录查找规则](BACKEND-MODULE-STRUCT-RULES.md)

## Service接口模板

```java
package com.supcon.nebule.{moduleCode}.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.supcon.nebule.{moduleCode}.custom.bo.Custom{ModuleCode}{ModelCode}BO;
import com.supcon.nebule.{moduleCode}.custom.entity.Custom{ModuleCode}{ModelCode}Entity;

import java.util.List;

/**
 * {ModelName}服务接口
 */
public interface Custom{ModuleCode}{ModelCode}Service extends IService<Custom{ModuleCode}{ModelCode}Entity> {

    /**
     * 保存业务对象
     * @param bo 业务对象
     * @return 保存后的ID
     */
    Long saveBO(Custom{ModuleCode}{ModelCode}BO bo);

    /**
     * 根据ID查询业务对象
     * @param id 主键ID
     * @return 业务对象
     */
    Custom{ModuleCode}{ModelCode}BO getByIdBO(Long id);

    /**
     * 删除业务对象
     * @param id 主键ID
     */
    void deleteBO(Long id);

    /**
     * 列表查询业务对象
     * @param queryBO 查询条件
     * @return 业务对象列表
     */
    List<Custom{ModuleCode}{ModelCode}BO> listBO(Custom{ModuleCode}{ModelCode}BO queryBO);
}
```

## ServiceImpl实现模板

```java
package com.supcon.nebule.{moduleCode}.custom.service.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supcon.nebule.{moduleCode}.custom.bo.Custom{ModuleCode}{ModelCode}BO;
import com.supcon.nebule.{moduleCode}.custom.dao.Custom{ModuleCode}{ModelCode}Dao;
import com.supcon.nebule.{moduleCode}.custom.entity.Custom{ModuleCode}{ModelCode}Entity;
import com.supcon.nebule.{moduleCode}.custom.service.Custom{ModuleCode}{ModelCode}Service;

/**
 * {ModelName}服务实现类
 */
@Service
@Slf4j
public class Custom{ModuleCode}{ModelCode}ServiceImpl 
    extends ServiceImpl<Custom{ModuleCode}{ModelCode}Dao, Custom{ModuleCode}{ModelCode}Entity>
    implements Custom{ModuleCode}{ModelCode}Service {

    @Autowired
    private Custom{ModuleCode}{ModelCode}Dao custom{ModuleCode}{ModelCode}Dao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveBO(Custom{ModuleCode}{ModelCode}BO bo) {
        // BO → Entity（手动setter）
        Custom{ModuleCode}{ModelCode}Entity entity = new Custom{ModuleCode}{ModelCode}Entity();
        entity.setName(bo.getName());
        entity.setCode(bo.getCode());
        entity.setStatus(bo.getStatus());
        entity.setRemark(bo.getRemark());
        
        // 调用父类方法保存
        save(entity);
        
        log.info("保存{ModelName}成功，ID: {}", entity.getId());
        return entity.getId();
    }

    @Override
    public Custom{ModuleCode}{ModelCode}BO getByIdBO(Long id) {
        // 查询Entity
        Custom{ModuleCode}{ModelCode}Entity entity = getById(id);
        
        if (entity == null) {
            log.warn("未找到{ModelName}，ID: {}", id);
            return null;
        }
        
        // Entity → BO（手动setter）
        Custom{ModuleCode}{ModelCode}BO bo = new Custom{ModuleCode}{ModelCode}BO();
        bo.setId(entity.getId());
        bo.setName(entity.getName());
        bo.setCode(entity.getCode());
        bo.setStatus(entity.getStatus());
        bo.setRemark(entity.getRemark());
        bo.setCreateTime(entity.getCreateTime());
        bo.setUpdateTime(entity.getUpdateTime());
        
        return bo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBO(Long id) {
        // 调用父类方法删除
        removeById(id);
        
        log.info("删除{ModelName}成功，ID: {}", id);
    }

    @Override
    public List<Custom{ModuleCode}{ModelCode}BO> listBO(Custom{ModuleCode}{ModelCode}BO queryBO) {
        // TODO: 根据查询条件构建查询
        // List<Custom{ModuleCode}{ModelCode}Entity> entities = list(queryWrapper);
        List<Custom{ModuleCode}{ModelCode}Entity> entities = new ArrayList<>();
        
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Entity列表 → BO列表（for循环手动转换）
        List<Custom{ModuleCode}{ModelCode}BO> result = new ArrayList<>();
        for (Custom{ModuleCode}{ModelCode}Entity entity : entities) {
            Custom{ModuleCode}{ModelCode}BO bo = new Custom{ModuleCode}{ModelCode}BO();
            bo.setId(entity.getId());
            bo.setName(entity.getName());
            bo.setCode(entity.getCode());
            bo.setStatus(entity.getStatus());
            bo.setRemark(entity.getRemark());
            bo.setCreateTime(entity.getCreateTime());
            bo.setUpdateTime(entity.getUpdateTime());
            result.add(bo);
        }
        
        return result;
    }
}
```

## 对象转换规范

### 重要说明

⚠️ **禁止使用 PojoUtil.copy() 或其他工具类进行对象拷贝**

**必须使用手动 setter 方式**进行 VO、BO、Entity 之间的转换。

### 转换方法位置

对象转换方法**直接在ServiceImpl中实现**，不需要单独的转换类。

### Entity → BO 转换（单个对象）

```java
// Entity → BO
Custom{ModuleCode}{ModelCode}BO bo = new Custom{ModuleCode}{ModelCode}BO();
bo.setId(entity.getId());
bo.setName(entity.getName());
bo.setCode(entity.getCode());
bo.setStatus(entity.getStatus());
bo.setRemark(entity.getRemark());
bo.setCreateTime(entity.getCreateTime());
bo.setUpdateTime(entity.getUpdateTime());
```

### Entity → BO 转换（列表）

```java
// Entity列表 → BO列表
List<Custom{ModuleCode}{ModelCode}BO> result = new ArrayList<>();
for (Custom{ModuleCode}{ModelCode}Entity entity : entities) {
    Custom{ModuleCode}{ModelCode}BO bo = new Custom{ModuleCode}{ModelCode}BO();
    bo.setId(entity.getId());
    bo.setName(entity.getName());
    bo.setCode(entity.getCode());
    bo.setStatus(entity.getStatus());
    bo.setRemark(entity.getRemark());
    bo.setCreateTime(entity.getCreateTime());
    bo.setUpdateTime(entity.getUpdateTime());
    result.add(bo);
}
```

### BO → Entity 转换

```java
// BO → Entity
Custom{ModuleCode}{ModelCode}Entity entity = new Custom{ModuleCode}{ModelCode}Entity();
entity.setName(bo.getName());
entity.setCode(bo.getCode());
entity.setStatus(bo.getStatus());
entity.setRemark(bo.getRemark());
```

## 业务方法规范

### saveBO（保存）
- **事务控制**: 必须添加 `@Transactional(rollbackFor = Exception.class)`
- **转换方向**: BO → Entity
- **返回值**: 返回保存后的ID
- **日志**: 记录保存成功的日志

### getByIdBO（查询详情）
- **事务控制**: 只读操作，不需要事务
- **转换方向**: Entity → BO
- **空值处理**: 如果Entity为null，返回null并记录warn日志
- **返回值**: 返回BO对象

### deleteBO（删除）
- **事务控制**: 必须添加 `@Transactional(rollbackFor = Exception.class)`
- **转换方向**: 不需要转换
- **日志**: 记录删除成功的日志

### listBO（列表查询）
- **事务控制**: 只读操作，不需要事务
- **转换方向**: Entity列表 → BO列表
- **空值处理**: 如果列表为空，返回空列表
- **返回值**: 返回BO列表

## 包路径规范

| 类型 | 包路径 |
|------|--------|
| Service接口 | com.supcon.nebule.{moduleCode}.custom.service |
| ServiceImpl | com.supcon.nebule.{moduleCode}.custom.service.impl |

## 命名规范

| 类型 | 命名格式 | 示例 |
|------|---------|------|
| Service接口 | Custom{ModuleCode}{ModelCode}Service | CustomOrderOrderService |
| ServiceImpl | Custom{ModuleCode}{ModelCode}ServiceImpl | CustomOrderOrderServiceImpl |

## 继承规范

### Service接口
- 继承 `IService<Custom{ModuleCode}{ModelCode}Entity>`
- 使用MyBatis-Plus的IService，不继承自定义BaseService

### ServiceImpl
- 继承 `ServiceImpl<Custom{ModuleCode}{ModelCode}Dao, Custom{ModuleCode}{ModelCode}Entity>`
- 实现 `Custom{ModuleCode}{ModelCode}Service`
- 使用MyBatis-Plus的ServiceImpl，不继承自定义BaseService
