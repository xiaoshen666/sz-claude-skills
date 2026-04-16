# Controller生成指南

## 概述

本文档定义Controller类的生成规范和模板。

## 文件目录说明

### Controller存放位置

**模块**: `{moduleCode}-custom-webapi`

**路径**: `{moduleCode}/{moduleCode}-custom-webapi/src/main/java/com/supcon/nebule/{moduleCode}/custom/controller/`

**完整示例**:
```
{moduleCode}-custom-webapi/
└── src/main/java/com/supcon/nebule/{moduleCode}/custom/
    └── controller/
        └── Custom{ModuleCode}{ModelCode}Controller.java
```

**详细说明**: 参考 [后端模块代码目录查找规则](BACKEND-MODULE-STRUCT-RULES.md)

## Controller模板

```java
package com.supcon.nebule.{moduleCode}.custom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.supcon.supfusion.framework.cloud.annotation.InternalApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.model.Result;

import com.supcon.nebule.{moduleCode}.custom.bo.Custom{ModuleCode}{ModelCode}BO;
import com.supcon.nebule.{moduleCode}.custom.service.Custom{ModuleCode}{ModelCode}Service;

import java.util.List;

@RestController
@InternalApi(path = HttpConstants.URL_SPLITER + "{moduleCode}" + HttpConstants.URL_SPLITER + "{modelCodeLower}")
public class Custom{ModuleCode}{ModelCode}Controller {

    @Autowired
    private Custom{ModuleCode}{ModelCode}Service custom{ModuleCode}{ModelCode}Service;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result<Long> saveBO(@RequestBody Custom{ModuleCode}{ModelCode}BO bo) {
        Long id = custom{ModuleCode}{ModelCode}Service.saveBO(bo);
        return Result.success(id);
    }

    /**
     * 查询详情
     */
    @GetMapping("/info")
    public Result<Custom{ModuleCode}{ModelCode}BO> info(@RequestParam Long id) {
        Custom{ModuleCode}{ModelCode}BO bo = custom{ModuleCode}{ModelCode}Service.getByIdBO(id);
        return Result.success(bo);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result<Void> delete(@RequestParam Long id) {
        custom{ModuleCode}{ModelCode}Service.deleteBO(id);
        return Result.success();
    }

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public Result<List<Custom{ModuleCode}{ModelCode}BO>> list(@RequestBody Custom{ModuleCode}{ModelCode}BO queryBO) {
        List<Custom{ModuleCode}{ModelCode}BO> list = custom{ModuleCode}{ModelCode}Service.listBO(queryBO);
        return Result.success(list);
    }
}
```

## URL规范

### 内部接口路径
- **格式**: `/{moduleCode}/{modelCodeLower}/{action}`
- **示例**: `/order/order/save`、`/order/order/info`

### 完整路径（经过网关）
- **格式**: `/msService/{moduleCode}/{modelCodeLower}/{action}`
- **示例**: `/msService/order/order/save`

## 注解说明

### @InternalApi
- **用途**: 标记内部服务API
- **path属性**: 定义API的基础路径
- **格式**: `HttpConstants.URL_SPLITER + "{moduleCode}" + HttpConstants.URL_SPLITER + "{modelCodeLower}"`

### @RestController
- **用途**: 标记这是一个RESTful控制器
- **效果**: 所有方法返回值自动转换为JSON

### @Autowired
- **用途**: 自动注入Service
- **规范**: 使用字段注入方式

## 方法规范

### saveBO（保存）
- **HTTP方法**: POST
- **参数**: @RequestBody Custom{ModuleCode}{ModelCode}BO
- **返回值**: Result<Long>（返回保存后的ID）

### info（查询详情）
- **HTTP方法**: GET
- **参数**: @RequestParam Long id
- **返回值**: Result<Custom{ModuleCode}{ModelCode}BO>

### delete（删除）
- **HTTP方法**: POST
- **参数**: @RequestParam Long id
- **返回值**: Result<Void>

### list（列表查询）
- **HTTP方法**: POST
- **参数**: @RequestBody Custom{ModuleCode}{ModelCode}BO（查询条件）
- **返回值**: Result<List<Custom{ModuleCode}{ModelCode}BO>>

## 返回值规范

### Result包装
- **成功**: `Result.success(data)`
- **失败**: `Result.fail(message)`
- **带错误码**: `Result.fail(code, message)`

### 状态码规范
- **成功**: code=200
- **参数错误**: code=400
- **业务错误**: code=500
- **未授权**: code=401

## 包路径规范

- **包路径**: `com.supcon.nebule.{moduleCode}.custom.controller`
- **类名**: `Custom{ModuleCode}{ModelCode}Controller`

## 对象转换说明

Controller层**不需要**进行对象转换，直接：
- 接收BO作为参数
- 调用Service方法
- 返回Result包装的结果

对象转换在Service层完成。
