# 后端业务代码生成工具指南

## 概述

本指南用于承载 `cds-product-develop-zh` 在后端实现阶段需要直接复用的业务代码模板，统一整理以下代码示例：

- Entity
- BO / VO / DTO
- DAO / Mapper
- Service 接口与实现
- Controller
- Feign API 接口（供其他服务调用）

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

## Feign API 接口模板（供其他服务调用）

### 适用场景

- 需要暴露当前模块的业务接口供其他微服务调用
- 跨服务 RPC 调用
- 服务间数据同步和业务流程编排

### Feign API 接口模板

```java
package com.supcon.nebule.{moduleCode}.custom.api;

import com.supcon.nebule.{moduleCode}.custom.dto.Custom{ModuleCode}{ModelCode}DTO;
import com.supcon.supfusion.framework.cloud.annotation.ServiceApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * {模型名称} Feign API 接口
 * 供其他微服务调用
 */
@FeignClient(name = "{moduleCode}", contextId = "Custom{ModuleCode}{ModelCode}Api")
@ServiceApi(path = HttpConstants.URL_SERVICEAPI + HttpConstants.URL_SPLITER 
        + "{moduleCode}" + HttpConstants.URL_SPLITER + "{modelCodeLower}" + HttpConstants.URL_SPLITER + "rpc")
public interface Custom{ModuleCode}{ModelCode}Api {

    @PostMapping("/save")
    @ApiOperation("保存{模型名称}")
    Result<Long> save(@RequestBody @Valid Custom{ModuleCode}{ModelCode}DTO dto);

    @PostMapping("/update")
    @ApiOperation("更新{模型名称}")
    Result<Boolean> update(@RequestBody @Valid Custom{ModuleCode}{ModelCode}DTO dto);

    @GetMapping("/getById")
    @ApiOperation("根据ID获取{模型名称}")
    Result<Custom{ModuleCode}{ModelCode}DTO> getById(@RequestParam("id") Long id);

    @PostMapping("/list")
    @ApiOperation("查询{模型名称}列表")
    Result<List<Custom{ModuleCode}{ModelCode}DTO>> list(@RequestBody Custom{ModuleCode}{ModelCode}DTO dto);

    @GetMapping("/delete")
    @ApiOperation("删除{模型名称}")
    Result<Boolean> delete(@RequestParam("id") Long id);
}
```

### 使用说明

1. **存放位置**：`{moduleCode}-custom-api/src/main/java/com/supcon/nebule/{moduleCode}/custom/api/`
2. **命名规范**：`Custom{ModuleCode}{ModelCode}Api`
3. **注解说明**：
   - `@FeignClient`：声明 Feign 客户端，`name` 为服务名，`contextId` 为唯一标识
   - `@ServiceApi`：声明服务 API 路径，使用 `HttpConstants.URL_SERVICEAPI` 前缀
4. **路径规范**：
   - 基础路径：`/serviceapi/{moduleCode}/{modelCodeLower}/rpc`
   - 接口方法路径：相对路径，如 `/save`、`/update`、`/getById`
5. **参数规范**：
   - 复杂对象使用 `@RequestBody` + DTO
   - 简单参数使用 `@RequestParam`
   - 需要校验的参数添加 `@Valid` 注解
6. **返回值规范**：统一使用 `Result<T>` 包装返回数据

### DTO 模板（Feign 接口数据传输对象）

```java
package com.supcon.nebule.{moduleCode}.custom.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * {模型名称} DTO
 * 用于 Feign 接口数据传输
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Custom{ModuleCode}{ModelCode}DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("备注")
    private String remark;
}
```

### DTO 使用说明

1. **存放位置**：`{moduleCode}-custom-api/src/main/java/com/supcon/nebule/{moduleCode}/custom/dto/`
2. **命名规范**：`Custom{ModuleCode}{ModelCode}DTO`
3. **必须实现**：`Serializable` 接口（支持网络传输）
4. **字段校验**：使用 `javax.validation` 注解进行参数校验
5. **与 VO 的区别**：
   - DTO 用于服务间传输，更简洁，只包含必要字段
   - VO 用于前端展示，可能包含格式化字段、展示字段
   - BO 用于业务逻辑层，包含业务相关字段

### Controller 实现对应的 Feign API

> **重要**：Feign API 接口定义后，需要在对应的 Controller 中实现相同的接口方法。

```java
package com.supcon.nebule.{moduleCode}.custom.controller;

import com.supcon.nebule.{moduleCode}.custom.api.Custom{ModuleCode}{ModelCode}Api;
import com.supcon.nebule.{moduleCode}.custom.bo.Custom{ModuleCode}{ModelCode}BO;
import com.supcon.nebule.{moduleCode}.custom.dto.Custom{ModuleCode}{ModelCode}DTO;
import com.supcon.nebule.{moduleCode}.custom.service.Custom{ModuleCode}{ModelCode}Service;
import com.supcon.supfusion.framework.cloud.annotation.InternalApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import com.supcon.supfusion.framework.common.utils.PojoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {模型名称} Controller
 * 同时实现 Feign API 接口，供其他服务调用
 */
@RestController
@InternalApi(path = HttpConstants.URL_SPLITER + "{moduleCode}" + HttpConstants.URL_SPLITER + "{modelCodeLower}")
public class Custom{ModuleCode}{ModelCode}Controller implements Custom{ModuleCode}{ModelCode}Api {

    @Autowired
    private Custom{ModuleCode}{ModelCode}Service custom{ModuleCode}{ModelCode}Service;

    @Override
    @PostMapping("/save")
    public Result<Long> save(@RequestBody Custom{ModuleCode}{ModelCode}DTO dto) {
        Custom{ModuleCode}{ModelCode}BO bo = PojoUtil.copy(dto, Custom{ModuleCode}{ModelCode}BO.class);
        Long id = custom{ModuleCode}{ModelCode}Service.saveBO(bo);
        return Result.success(id);
    }

    @Override
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Custom{ModuleCode}{ModelCode}DTO dto) {
        Custom{ModuleCode}{ModelCode}BO bo = PojoUtil.copy(dto, Custom{ModuleCode}{ModelCode}BO.class);
        Boolean result = custom{ModuleCode}{ModelCode}Service.updateBO(bo);
        return Result.success(result);
    }

    @Override
    @GetMapping("/getById")
    public Result<Custom{ModuleCode}{ModelCode}DTO> getById(@RequestParam("id") Long id) {
        Custom{ModuleCode}{ModelCode}BO bo = custom{ModuleCode}{ModelCode}Service.getByIdBO(id);
        return Result.success(PojoUtil.copy(bo, Custom{ModuleCode}{ModelCode}DTO.class));
    }

    @Override
    @PostMapping("/list")
    public Result<List<Custom{ModuleCode}{ModelCode}DTO>> list(@RequestBody Custom{ModuleCode}{ModelCode}DTO dto) {
        Custom{ModuleCode}{ModelCode}BO bo = PojoUtil.copy(dto, Custom{ModuleCode}{ModelCode}BO.class);
        List<Custom{ModuleCode}{ModelCode}BO> list = custom{ModuleCode}{ModelCode}Service.listBO(bo);
        return Result.success(PojoUtil.copyList(list, Custom{ModuleCode}{ModelCode}DTO.class));
    }

    @Override
    @GetMapping("/delete")
    public Result<Boolean> delete(@RequestParam("id") Long id) {
        Boolean result = custom{ModuleCode}{ModelCode}Service.delete(id);
        return Result.success(result);
    }
}
```

### Feign 客户端调用示例

其他服务需要调用此 Feign API 时：

```java
package com.supcon.nebule.othermodule.service;

import com.supcon.nebule.{moduleCode}.custom.api.Custom{ModuleCode}{ModelCode}Api;
import com.supcon.nebule.{moduleCode}.custom.dto.Custom{ModuleCode}{ModelCode}DTO;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtherModuleService {

    @Autowired
    private Custom{ModuleCode}{ModelCode}Api custom{ModuleCode}{ModelCode}Api;

    public void callOtherService() {
        // 调用其他服务的接口
        Custom{ModuleCode}{ModelCode}DTO dto = new Custom{ModuleCode}{ModelCode}DTO();
        dto.setName("示例数据");
        
        Result<Long> result = custom{ModuleCode}{ModelCode}Api.save(dto);
        if (result.isSuccess()) {
            Long id = result.getData();
            // 处理成功逻辑
        }
    }
}
```

### 在配置类中启用 Feign 客户端扫描

> **⚠️ 重要**：由于运行环境限制，**不能修改启动类** `Bootstrap.java`，必须通过配置类来实现 Feign API 包扫描。

在 `{moduleCode}-custom-common` 模块的公共服务配置类中添加 Feign API 包扫描：

```java
package com.supcon.nebule.{moduleCode}.custom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * {moduleCode}模块公共服务配置类
 * 用于扫描自定义模块的包，替代启动类中的包扫描配置
 */
@Configuration
@ComponentScan(
    basePackages = {
        "com.supcon.supfusion.module.registry",
        "com.supcon.nebule.ecentity"
    }
)
@EnableFeignClients({
    "com.supcon.supfusion.module.registry.api",
    "com.supcon.nebule.{moduleCode}.custom.api"  // 添加自定义 Feign API 包扫描
})
@MapperScan({"com.supcon.nebule.{moduleCode}.custom.dao"})
public class {ModuleCode}PubServiceConfiguration {
}
```

**配置说明**：
- 在 `@EnableFeignClients` 注解中添加 `"com.supcon.nebule.{moduleCode}.custom.api"` 包路径
- 该配置类会在应用启动时自动加载，无需修改启动类
- 详细配置类说明请参考：[配置管理工具指南](../../cds-product-design-zh/references/BACKEND-TOOLS-CONFIGURATION.md)

## 推荐使用顺序

1. 先确认前序设计产物是否齐全
2. 再确认 `moduleCode` 与 `acronym`
3. 根据需求详细设计与 API 设计文档替换模板中的占位符
4. 优先生成 Entity / BO / VO
5. 再生成 DAO / Service / Controller
6. 如需暴露服务接口，生成 Feign API 和 DTO
7. 最后补充配置类、模块注册与数据库脚本

## 相关文档

- 项目结构工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-PROJECT-STRUCTURE.md`
- 配置管理工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-CONFIGURATION.md`
- 数据库规范工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-DATABASE.md`
- 模块注册工具指南：`../../cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md`
