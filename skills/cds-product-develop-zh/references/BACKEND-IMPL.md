# 后端工程师角色实现

## 角色定位

你是一位专业的 CDS 后端开发工程师，负责根据后端技术方案实现具体的 Java Spring Boot 后端代码。你精通 CDS 框架、Spring Boot、MyBatis-Plus 等技术栈，能够高效地开发企业级后端应用。

## 核心技术能力

### 技术栈掌握
- **Java JDK 1.8**：熟练使用 Java 语言特性
- **Spring Boot 2.1.5.RELEASE**：Spring Boot 框架开发
- **MyBatis 3.5.6 + MyBatis-Plus 3.4.2**：ORM 框架和增强工具
- **Spring Cloud Greenwich.SR1**：微服务架构
- **Maven 3.x**：项目构建和依赖管理
- **Oracle11g/MySQL**：关系型数据库

### CDS 框架专精
- **项目结构**：熟练掌握 {moduleCode}-custom 目录结构（详见 [项目结构工具指南](BACKEND-TOOLS-PROJECT-STRUCTURE.md)）
- **Entity 设计**：基于 AbstractConfigurationWorkflowEntity 的实体类设计
- **Service 架构**：基于 BaseService 的业务服务设计
- **Controller 开发**：RESTful API 设计和 InternalApi 注解使用
- **模块注册**：ModuleRegisterInitialize 实现（详见 [模块注册工具指南](BACKEND-TOOLS-MODULE-REGISTER.md)）
- **配置管理**：公共服务配置和自动扫描配置（详见 [配置管理工具指南](BACKEND-TOOLS-CONFIGURATION.md)）
- **数据库设计**：表结构设计和SQL脚本规范（详见 [数据库规范工具指南](BACKEND-TOOLS-DATABASE.md)）

## 开发规范

### Entity 开发规范

#### 基础实体模板
```java
package com.supcon.nebule.{moduleCode}.custom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.supcon.nebule.fr.annotation.EnCodeField;
import com.supcon.nebule.framework.common.annotation.audit.AuditModel;
import com.supcon.nebule.framework.common.entity.AbstractConfigurationWorkflowEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("{tableName}")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@EnCodeField(module = "{moduleCode}", model = "{modelCode}", acronym="{acronym}", srcAcronym="")
@AuditModel(module = "{moduleCode}", model = "{modelCode}")
public class Custom{ModuleCode}{ModelCode}Entity extends AbstractConfigurationWorkflowEntity {

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

> **详细规范请参考**: [数据库规范工具指南](BACKEND-TOOLS-DATABASE.md)

#### BO 业务对象模板
```java
package com.supcon.nebule.{moduleCode}.custom.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Custom{ModuleCode}{ModelCode}BO extends Custom{ModuleCode}{ModelCode}BaseBO {

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

#### VO 视图对象模板
```java
package com.supcon.nebule.{moduleCode}.custom.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Custom{ModuleCode}{ModelCode}VO extends Custom{ModuleCode}{ModelCode}BaseVO {

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

### DAO 开发规范

#### 基础 DAO 接口
```java
package com.supcon.nebule.{moduleCode}.custom.dao;

import com.supcon.nebule.{moduleCode}.custom.entity.Custom{ModuleCode}{ModelCode}Entity;
import com.supcon.nebule.framework.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Custom{ModuleCode}{ModelCode}Dao extends BaseDao<Custom{ModuleCode}{ModelCode}Entity> {
    // 继承BaseDao已包含基础的增删改查方法
    // 可按需添加自定义查询方法
}
```

### Service 开发规范

#### Service 接口
```java
package com.supcon.nebule.{moduleCode}.custom.service;

import com.supcon.nebule.{moduleCode}.custom.bo.Custom{ModuleCode}{ModelCode}BO;
import com.supcon.nebule.{moduleCode}.custom.entity.Custom{ModuleCode}{ModelCode}Entity;
import com.supcon.nebule.framework.common.service.IBaseService;

public interface Custom{ModuleCode}{ModelCode}Service extends IBaseService<Custom{ModuleCode}{ModelCode}Entity> {

    /**
     * 保存业务对象
     */
    Long saveBO(Custom{ModuleCode}{ModelCode}BO bo);

    /**
     * 根据ID获取业务对象
     */
    Custom{ModuleCode}{ModelCode}BO getByIdBO(Long id);

    /**
     * 删除记录
     */
    Boolean delete(Long id);
}
```

#### Service 实现
```java
package com.supcon.nebule.{moduleCode}.custom.service.impl;

import com.supcon.nebule.{moduleCode}.custom.bo.Custom{ModuleCode}{ModelCode}BO;
import com.supcon.nebule.{moduleCode}.custom.dao.Custom{ModuleCode}{ModelCode}Dao;
import com.supcon.nebule.{moduleCode}.custom.entity.Custom{ModuleCode}{ModelCode}Entity;
import com.supcon.nebule.{moduleCode}.custom.service.Custom{ModuleCode}{ModelCode}Service;
import com.supcon.nebule.framework.common.service.BaseService;
import com.supcon.nebule.framework.common.utils.PojoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Slf4j
public class Custom{ModuleCode}{ModelCode}ServiceImpl extends BaseService<Custom{ModuleCode}{ModelCode}Dao, Custom{ModuleCode}{ModelCode}Entity>
        implements Custom{ModuleCode}{ModelCode}Service {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveBO(Custom{ModuleCode}{ModelCode}BO bo) {
        Custom{ModuleCode}{ModelCode}Entity entity = PojoUtil.copy(bo, Custom{ModuleCode}{ModelCode}Entity.class);
        
        if (bo.getId() == null) {
            // 新增
            this.save(entity);
        } else {
            // 更新
            this.updateById(entity);
        }
        
        return entity.getId();
    }

    @Override
    public Custom{ModuleCode}{ModelCode}BO getByIdBO(Long id) {
        Custom{ModuleCode}{ModelCode}Entity entity = this.getById(id);
        return PojoUtil.copy(entity, Custom{ModuleCode}{ModelCode}BO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id) {
        return this.removeById(id);
    }
}
```

### Controller 开发规范

#### 基础 Controller
```java
package com.supcon.nebule.{moduleCode}.custom.controller;

import com.supcon.nebule.{moduleCode}.custom.bo.Custom{ModuleCode}{ModelCode}BO;
import com.supcon.nebule.{moduleCode}.custom.service.Custom{ModuleCode}{ModelCode}Service;
import com.supcon.nebule.{moduleCode}.custom.vo.Custom{ModuleCode}{ModelCode}VO;
import com.supcon.nebule.framework.common.annotation.audit.AuditLog;
import com.supcon.nebule.framework.common.annotation.nullupdate.ModifiedSerialization;
import com.supcon.nebule.framework.common.utils.PojoUtil;
import com.supcon.supfusion.framework.cloud.annotation.InternalApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@InternalApi(path = HttpConstants.URL_SPLITER + "{moduleCode}" + HttpConstants.URL_SPLITER + "{modelCodeLower}")
public class Custom{ModuleCode}{ModelCode}Controller {

    @Autowired
    private Custom{ModuleCode}{ModelCode}Service service;

    @PostMapping("/save")
    @ApiOperation("保存记录")
    @ModifiedSerialization
    @AuditLog
    public Result<Long> save(@RequestBody Custom{ModuleCode}{ModelCode}VO vo) {
        Custom{ModuleCode}{ModelCode}BO bo = PojoUtil.copy(vo, Custom{ModuleCode}{ModelCode}BO.class);
        return Result.success(service.saveBO(bo));
    }

    @GetMapping("/info")
    @ApiOperation("根据ID获取")
    public Result<Custom{ModuleCode}{ModelCode}VO> getById(@RequestParam("id") Long id) {
        Custom{ModuleCode}{ModelCode}BO bo = service.getByIdBO(id);
        return Result.success(PojoUtil.copy(bo, Custom{ModuleCode}{ModelCode}VO.class));
    }

    @PostMapping("/delete")
    @ApiOperation("删除记录")
    @AuditLog
    public Result<Boolean> delete(@RequestParam("id") Long id) {
        return Result.success(service.delete(id));
    }
}
```

### 配置类开发规范

> **详细配置说明请参考**: [配置管理工具指南](BACKEND-TOOLS-CONFIGURATION.md)
> **详细注册机制请参考**: [模块注册工具指南](BACKEND-TOOLS-MODULE-REGISTER.md)

## 数据库开发规范

> **详细数据库设计规范请参考**: [数据库规范工具指南](BACKEND-TOOLS-DATABASE.md)

## API 开发规范

### 接口设计
| 接口路径 | 方法 | 描述 | 参数 | 返回值 |
|---------|------|------|------|--------|
| `/save` | POST | 保存记录 | Custom{ModuleCode}{ModelCode}VO | Result<Long> |
| `/info` | GET | 根据ID获取 | id: Long | Result<Custom{ModuleCode}{ModelCode}VO> |
| `/delete` | POST | 删除记录 | id: Long | Result<Boolean> |

### 请求参数示例
```json
// 保存请求体
{
    "id": 1,
    "name": "示例名称",
    "code": "SAMPLE001",
    "status": "ENABLED",
    "remark": "备注信息"
}
```

### 响应结果示例
```json
// 成功响应
{
    "success": true,
    "errorCode": null,
    "errorMessage": null,
    "data": 123
}

// 失败响应
{
    "success": false,
    "errorCode": "SYSTEM_ERROR",
    "errorMessage": "系统异常",
    "data": null
}
```

## 代码质量保证

### Java 编码规范
- 使用4空格缩进（不要使用Tab）
- 每行代码不超过120个字符
- 类必须有JavaDoc注释
- 公共方法必须有JavaDoc注释
- 注释使用中文

### 命名规范
- **包命名**：`com.supcon.nebule.{moduleCode}.custom`
- **Entity**：`Custom{ModuleCode}{ModelCode}Entity`
- **DAO**：`Custom{ModuleCode}{ModelCode}Dao`
- **Service**：`Custom{ModuleCode}{ModelCode}Service`
- **Controller**：`Custom{ModuleCode}{ModelCode}Controller`
- **VO**：`Custom{ModuleCode}{ModelCode}VO`
- **BO**：`Custom{ModuleCode}{ModelCode}BO`

### 事务管理
- 使用 `@Transactional(rollbackFor = Exception.class)`
- 业务方法添加事务控制
- 异常时自动回滚

## 开发流程

### 1. 环境准备
```bash
# 编译项目
mvn clean compile

# 打包项目
mvn clean package

# 运行测试
mvn test

# 代码检查
mvn checkstyle:check
```

### 2. 开发步骤
1. **阅读技术方案**：理解需求和设计规范
2. **创建数据库表**：执行SQL初始化脚本
3. **生成基础代码**：创建Entity、DAO、Service、Controller
4. **实现业务逻辑**：编写核心业务代码
5. **添加配置类**：配置扫描和模块注册
6. **代码审查**：检查代码质量和规范
7. **测试验证**：确保功能正确性

### 3. 代码提交
- 遵循 Git 提交规范
- 提交信息清晰明确
- 代码审查通过后合并

## 最佳实践

### 实体设计原则
1. **继承基类**：继承 AbstractConfigurationWorkflowEntity
2. **注解完整**：添加必要的 MyBatis-Plus 和 CDS 注解
3. **字段规范**：遵循数据库字段命名规范
4. **类型安全**：使用合适的 Java 类型

### 服务设计原则
1. **单一职责**：每个 Service 只负责一个业务领域
2. **事务控制**：合理配置事务传播和隔离级别
3. **异常处理**：统一异常处理和错误码
4. **性能优化**：合理使用缓存和批量操作

### 控制器设计原则
1. **RESTful 风格**：遵循 RESTful API 设计规范
2. **参数校验**：使用注解进行参数验证
3. **统一响应**：使用 Result 包装响应结果
4. **日志记录**：添加必要的操作日志

### 错误处理
1. **业务异常**：定义业务相关的异常类型
2. **系统异常**：处理系统级别的异常
3. **参数校验**：统一参数校验和错误提示
4. **日志记录**：详细的错误日志记录

## 工具文档引用

### 后端开发工具指南

| 工具文档 | 用途 | 使用场景 |
|---------|------|----------|
| [项目结构工具指南](BACKEND-TOOLS-PROJECT-STRUCTURE.md) | CDS项目目录结构和模块划分 | 新建项目、理解项目结构时 |
| [配置管理工具指南](BACKEND-TOOLS-CONFIGURATION.md) | 公共服务配置和模块配置 | 配置类开发、包扫描配置时 |
| [数据库规范工具指南](BACKEND-TOOLS-DATABASE.md) | 数据库设计和SQL脚本规范 | 数据库设计、表结构创建时 |
| [模块注册工具指南](BACKEND-TOOLS-MODULE-REGISTER.md) | 模块注册、菜单注册、工作流注册 | 模块初始化、系统集成时 |

### 使用建议

1. **新手入门**: 先阅读项目结构工具指南，理解整体架构
2. **配置开发**: 参考配置管理工具指南，正确配置扫描和注册
3. **数据库设计**: 使用数据库规范工具指南，确保表结构规范
4. **模块集成**: 查看模块注册工具指南，完成系统注册配置

### 工具文档特点

- **模块化**: 每个工具文档专注于特定领域
- **实用性强**: 提供可直接使用的代码模板和配置示例
- **详细规范**: