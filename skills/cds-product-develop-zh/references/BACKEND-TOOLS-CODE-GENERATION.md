# 后端业务代码生成工具指南

## 概述

本指南用于承载 `cds-product-develop-zh` 在后端实现阶段需要直接复用的业务代码模板，统一整理以下代码示例：

- Entity
- BO / VO
- DAO / Mapper
- Service 接口与实现
- Controller

> 说明：本文件仅用于**业务逻辑代码生成**。
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
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("{tableName}")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@EnCodeField(module = "{moduleCode}", model = "{modelCode}", acronym="{acronym}", srcAcronym="")
public class Custom{ModuleCode}{ModelCode}Entity  {

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("编码")
    @TableField("code")
    private String code;

    @ApiModelProperty("状态")
    @TableField("status")
    private String status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
```

### BO 模板

```java
package com.supcon.nebule.{moduleCode}.custom.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Custom{ModuleCode}{ModelCode}BO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("备注")
    private String remark;
}
```

### VO 模板

```java
package com.supcon.nebule.{moduleCode}.custom.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Custom{ModuleCode}{ModelCode}VO {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("备注")
    private String remark;
}
```

## DAO / Mapper 模板

### DAO / Mapper 接口模板

```java
package com.supcon.nebule.{moduleCode}.custom.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Custom{ModuleCode}{ModelCode}Dao  {
    // 继承 BaseDao 已包含基础增删改查方法
    // 可按需添加自定义查询方法
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

public interface Custom{ModuleCode}{ModelCode}Service  {

}
```

### Service 实现模板

```java
package com.supcon.nebule.{moduleCode}.custom.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Slf4j
public class Custom{ModuleCode}{ModelCode}ServiceImpl implements Custom{ModuleCode}{ModelCode}Service {

}
```

## Controller 模板

### 基础 Controller 模板

```java
package com.supcon.nebule.{moduleCode}.custom.controller;

import com.supcon.supfusion.framework.cloud.annotation.InternalApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@InternalApi(path = HttpConstants.URL_SPLITER + "{moduleCode}" + HttpConstants.URL_SPLITER + "{modelCodeLower}")
public class Custom{ModuleCode}{ModelCode}Controller {

  
}
```

## 推荐使用顺序

1. 先确认前序设计产物是否齐全
2. 再确认 `moduleCode` 与 `acronym`
3. 根据需求详细设计与 API 设计文档替换模板中的占位符
4. 优先生成 Entity / BO / VO
5. 再生成 DAO / Service / Controller
6. 最后补充配置类、模块注册与数据库脚本

## 相关文档

- 项目结构工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-PROJECT-STRUCTURE.md`
- 配置管理工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-CONFIGURATION.md`
- 数据库规范工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-DATABASE.md`
- 模块注册工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md`
