# 后端业务代码生成工具指南

## 概述

本指南用于承载 `cds-product-develop-zh` 在后端实现阶段需要直接复用的业务代码模板，统一整理以下代码示例：

- Entity
- BO / VO
- DAO / Mapper
- Service 接口与实现
- Controller

> 说明：本文件仅用于**业务逻辑代码生成**。
> 
> **Feign API 接口**（跨服务调用）为可选功能，默认不生成。如需生成，请参考：[Feign API 接口生成指南](FEIGN-API-GENERATION.md)
> 
> 若当前任务涉及**模块注册、菜单注册、工作流注册**，请引用 `../../cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md`。

## 使用前提

在生成具体业务代码前，优先结合以下输入一起使用：

- `{功能名称}-需求详细设计.md`
- `{功能名称}-后端架构设计.md`
- `{功能名称}-API设计文档.md`
- 用户最终确认的 `moduleCode`
- 用户最终确认的 `acronym`

## 适用场景

- 根据需求详细设计生成实体与业务对象
- 根据 API 设计文档生成 Controller 接口
- 根据领域模型生成 DAO / Mapper / Service
- 在已有目录结构下快速补齐标准 CRUD 基础代码

## 实体与对象模板

### Entity 模板

```java
package com.supcon.nebule.{moduleCode}.custom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.supcon.nebule.fr.annotation.EnCodeField;

import lombok.Data;

@Data
@TableName("{tableName}")
@EnCodeField(module = "{moduleCode}", model = "{modelCode}", acronym = "{acronym}", srcAcronym = "")
public class Custom{ModuleCode}{ModelCode}Entity {

    @TableField("name")
    private String name;

    @TableField("code")
    private String code;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;
}
```

### BO 模板

```java
package com.supcon.nebule.{moduleCode}.custom.bo;

import lombok.Data;

@Data
public class Custom{ModuleCode}{ModelCode}BO {

    private String name;

    private String code;

    private String status;

    private String remark;
}
```

### VO 模板

```java
package com.supcon.nebule.{moduleCode}.custom.vo;

import lombok.Data;

@Data
public class Custom{ModuleCode}{ModelCode}VO {

    private Long id;

    private String name;

    private String code;

    private String status;

    private String remark;
}
```

## DAO / Mapper 模板

### DAO / Mapper 接口模板

```java
package com.supcon.nebule.{moduleCode}.custom.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Custom{ModuleCode}{ModelCode}Dao {
}
```

### 使用说明

- CDS 开发中，DAO 接口通常同时承担 Mapper 接口角色
- 若项目需要额外 XML 映射，请结合数据库规范单独补充
- 命名建议保持 `Custom{ModuleCode}{ModelCode}Dao`

## Service 模板

### Service 接口模板

```java
package com.supcon.nebule.{moduleCode}.custom.service;

public interface Custom{ModuleCode}{ModelCode}Service {
}
```

### Service 实现模板

```java
package com.supcon.nebule.{moduleCode}.custom.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.supcon.nebule.{moduleCode}.custom.service.Custom{ModuleCode}{ModelCode}Service;

@Service
@Slf4j
public class Custom{ModuleCode}{ModelCode}ServiceImpl implements Custom{ModuleCode}{ModelCode}Service {
}
```

## Controller 模板

### 基础 Controller 模板

```java
package com.supcon.nebule.{moduleCode}.custom.controller;

import org.springframework.web.bind.annotation.*;

import com.supcon.supfusion.framework.cloud.annotation.InternalApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;

@RestController
@InternalApi(path = HttpConstants.URL_SPLITER + "{moduleCode}" + HttpConstants.URL_SPLITER + "{modelCodeLower}")
public class Custom{ModuleCode}{ModelCode}Controller {
}
```

## 对象转换规范

### 重要说明

⚠️ **禁止使用 PojoUtil.copy() 或其他工具类进行对象拷贝**

**必须使用手动 setter 方式**进行 VO、BO、Entity、DTO 之间的转换，确保代码可读性和可控性。

### Entity → BO 转换示例

```java
// 单个对象转换
public Custom{ModuleCode}{ModelCode}BO entityToBO(Custom{ModuleCode}{ModelCode}Entity entity) {
    if (entity == null) {
        return null;
    }
    
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

// 列表对象转换
public List<Custom{ModuleCode}{ModelCode}BO> entitiesToBOs(List<Custom{ModuleCode}{ModelCode}Entity> entities) {
    if (entities == null || entities.isEmpty()) {
        return new ArrayList<>();
    }
    
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
```

### BO → Entity 转换示例

```java
public Custom{ModuleCode}{ModelCode}Entity boToEntity(Custom{ModuleCode}{ModelCode}BO bo) {
    if (bo == null) {
        return null;
    }
    
    Custom{ModuleCode}{ModelCode}Entity entity = new Custom{ModuleCode}{ModelCode}Entity();
    entity.setId(bo.getId());
    entity.setName(bo.getName());
    entity.setCode(bo.getCode());
    entity.setStatus(bo.getStatus());
    entity.setRemark(bo.getRemark());
    
    return entity;
}
```

### BO → VO 转换示例

```java
public Custom{ModuleCode}{ModelCode}VO boToVO(Custom{ModuleCode}{ModelCode}BO bo) {
    if (bo == null) {
        return null;
    }
    
    Custom{ModuleCode}{ModelCode}VO vo = new Custom{ModuleCode}{ModelCode}VO();
    vo.setId(bo.getId());
    vo.setName(bo.getName());
    vo.setCode(bo.getCode());
    vo.setStatus(bo.getStatus());
    vo.setRemark(bo.getRemark());
    
    return vo;
}

// 列表转换
public List<Custom{ModuleCode}{ModelCode}VO> bosToVOs(List<Custom{ModuleCode}{ModelCode}BO> bos) {
    if (bos == null || bos.isEmpty()) {
        return new ArrayList<>();
    }
    
    List<Custom{ModuleCode}{ModelCode}VO> result = new ArrayList<>();
    for (Custom{ModuleCode}{ModelCode}BO bo : bos) {
        Custom{ModuleCode}{ModelCode}VO vo = new Custom{ModuleCode}{ModelCode}VO();
        vo.setId(bo.getId());
        vo.setName(bo.getName());
        vo.setCode(bo.getCode());
        vo.setStatus(bo.getStatus());
        vo.setRemark(bo.getRemark());
        result.add(vo);
    }
    
    return result;
}
```

### Service 中使用转换示例

```java
package com.supcon.nebule.{moduleCode}.custom.service.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supcon.nebule.{moduleCode}.custom.bo.Custom{ModuleCode}{ModelCode}BO;
import com.supcon.nebule.{moduleCode}.custom.dao.Custom{ModuleCode}{ModelCode}Dao;
import com.supcon.nebule.{moduleCode}.custom.entity.Custom{ModuleCode}{ModelCode}Entity;
import com.supcon.nebule.{moduleCode}.custom.service.Custom{ModuleCode}{ModelCode}Service;

@Service
@Slf4j
public class Custom{ModuleCode}{ModelCode}ServiceImpl implements Custom{ModuleCode}{ModelCode}Service {

    @Autowired
    private Custom{ModuleCode}{ModelCode}Dao custom{ModuleCode}{ModelCode}Dao;

    @Override
    public Long saveBO(Custom{ModuleCode}{ModelCode}BO bo) {
        // BO → Entity
        Custom{ModuleCode}{ModelCode}Entity entity = new Custom{ModuleCode}{ModelCode}Entity();
        entity.setName(bo.getName());
        entity.setCode(bo.getCode());
        entity.setStatus(bo.getStatus());
        entity.setRemark(bo.getRemark());
        
        // TODO: 调用 DAO 保存
        // custom{ModuleCode}{ModelCode}Dao.insert(entity);
        
        return entity.getId();
    }

    @Override
    public Custom{ModuleCode}{ModelCode}BO getByIdBO(Long id) {
        // TODO: 调用 DAO 查询
        // Custom{ModuleCode}{ModelCode}Entity entity = custom{ModuleCode}{ModelCode}Dao.selectById(id);
        Custom{ModuleCode}{ModelCode}Entity entity = null;
        
        if (entity == null) {
            return null;
        }
        
        // Entity → BO
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
    public List<Custom{ModuleCode}{ModelCode}BO> listBO(Custom{ModuleCode}{ModelCode}BO queryBO) {
        // TODO: 调用 DAO 查询列表
        // List<Custom{ModuleCode}{ModelCode}Entity> entities = custom{ModuleCode}{ModelCode}Dao.selectList(queryBO);
        List<Custom{ModuleCode}{ModelCode}Entity> entities = new ArrayList<>();
        
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        
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
        
        return result;
    }
}
```

### 转换规范说明

1. **禁止使用工具类拷贝**：
   - ❌ 不使用 `PojoUtil.copy()`
   - ❌ 不使用 `BeanUtils.copyProperties()`
   - ❌ 不使用其他反射拷贝工具

2. **必须手动 setter**：
   - ✅ 使用 `new` 创建目标对象
   - ✅ 逐个字段调用 `setXxx()` 方法
   - ✅ 明确每个字段的转换逻辑

3. **空值处理**：
   - 转换前检查源对象是否为 null
   - 列表转换前检查是否为 null 或 empty

4. **优势**：
   - 代码可读性强，一眼看出转换了哪些字段
   - 便于调试，可以清晰看到转换过程
   - 类型安全，编译时就能发现问题
   - 可控性高，可以对特定字段做特殊处理

## 推荐使用顺序

1. 先确认前序设计产物是否齐全
2. 再确认 `moduleCode` 与 `acronym`
3. 根据需求详细设计与 API 设计文档替换模板中的占位符
4. 优先生成 Entity / BO / VO
5. 再生成 DAO / Service / Controller
6. 如需暴露服务接口，参考 [Feign API 接口生成指南](FEIGN-API-GENERATION.md) 生成 Feign API 和 DTO
7. 最后补充配置类、模块注册与数据库脚本

## 相关文档

- 项目结构工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-PROJECT-STRUCTURE.md`
- 配置管理工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-CONFIGURATION.md`
- 数据库规范工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-DATABASE.md`
- 模块注册工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md`
- Feign API 接口生成指南：`FEIGN-API-GENERATION.md`（可选功能）
