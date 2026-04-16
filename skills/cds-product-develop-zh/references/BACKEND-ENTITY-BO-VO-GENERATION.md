# Entity、BO、VO生成指南

## 概述

本文档定义Entity、BO、VO类的生成规范和模板。

## 文件目录说明

### Entity存放位置

**模块**: `{moduleCode}-custom-dao`

**路径**: `{moduleCode}/{moduleCode}-custom-dao/src/main/java/com/supcon/nebule/{moduleCode}/custom/entity/`

**完整示例**:
```
{moduleCode}-custom-dao/
└── src/main/java/com/supcon/nebule/{moduleCode}/custom/
    └── entity/
        └── Custom{ModuleCode}{ModelCode}Entity.java
```

### BO存放位置

**模块**: `{moduleCode}-custom-service`

**路径**: `{moduleCode}/{moduleCode}-custom-service/src/main/java/com/supcon/nebule/{moduleCode}/custom/bo/`

**完整示例**:
```
{moduleCode}-custom-service/
└── src/main/java/com/supcon/nebule/{moduleCode}/custom/
    └── bo/
        └── Custom{ModuleCode}{ModelCode}BO.java
```

### VO存放位置

**模块**: `{moduleCode}-custom-webapi`

**路径**: `{moduleCode}/{moduleCode}-custom-webapi/src/main/java/com/supcon/nebule/{moduleCode}/custom/vo/`

**完整示例**:
```
{moduleCode}-custom-webapi/
└── src/main/java/com/supcon/nebule/{moduleCode}/custom/
    └── vo/
        └── Custom{ModuleCode}{ModelCode}VO.java
```

**详细说明**: 参考 [后端模块代码目录查找规则](BACKEND-MODULE-STRUCT-RULES.md)

## Entity模板

```java
package com.supcon.nebule.{moduleCode}.custom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.supcon.nebule.fr.annotation.EnCodeField;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("{tableName}")
@EnCodeField(module = "{moduleCode}", model = "{modelCode}", acronym = "{acronym}", srcAcronym = "")
public class Custom{ModuleCode}{ModelCode}Entity implements Serializable {

    @TableId
    private Long id;

    @TableField("name")
    private String name;

    @TableField("code")
    private String code;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
```

## BO模板

```java
package com.supcon.nebule.{moduleCode}.custom.bo;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Custom{ModuleCode}{ModelCode}BO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private String status;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
```

## VO模板

```java
package com.supcon.nebule.{moduleCode}.custom.vo;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Custom{ModuleCode}{ModelCode}VO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private String status;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
```

## 生成规范

### Entity设计原则
1. **原生实现**：不继承任何基类，使用MyBatis-Plus注解独立实现
2. **注解完整**：添加必要的 MyBatis-Plus 注解（@TableName、@TableId、@TableField等）
3. **审计字段**：包含创建时间、更新时间、创建人、更新人等审计字段
4. **实现Serializable**：所有实体类必须实现Serializable接口
5. **类型安全**：使用合适的 Java 类型

### BO设计原则
1. **业务对象**：用于接收前端请求参数和业务逻辑处理
2. **字段完整**：包含所有业务需要的字段
3. **实现Serializable**：所有BO必须实现Serializable接口
4. **校验注解**：根据需要添加@Validated、@NotBlank等校验注解

### VO设计原则
1. **视图对象**：用于返回给前端展示的数据
2. **按需暴露**：只包含前端需要展示的字段
3. **实现Serializable**：所有VO必须实现Serializable接口
4. **格式化字段**：可以对日期、数字等字段进行格式化

## 对象转换规范

### 重要说明

⚠️ **禁止使用 PojoUtil.copy() 或其他工具类进行对象拷贝**

**必须使用手动 setter 方式**进行 VO、BO、Entity 之间的转换，确保代码可读性和可控性。

### Entity → BO 转换示例

```java
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
    vo.setCreateTime(bo.getCreateTime());
    vo.setUpdateTime(bo.getUpdateTime());
    
    return vo;
}
```

## 命名规范

| 类型 | 命名格式 | 示例 |
|------|---------|------|
| Entity | Custom{ModuleCode}{ModelCode}Entity | CustomOrderOrderEntity |
| BO | Custom{ModuleCode}{ModelCode}BO | CustomOrderOrderBO |
| VO | Custom{ModuleCode}{ModelCode}VO | CustomOrderOrderVO |

## 包路径规范

| 类型 | 包路径 |
|------|--------|
| Entity | com.supcon.nebule.{moduleCode}.custom.entity |
| BO | com.supcon.nebule.{moduleCode}.custom.bo |
| VO | com.supcon.nebule.{moduleCode}.custom.vo |
