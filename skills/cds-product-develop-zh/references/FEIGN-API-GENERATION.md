# Feign API 接口生成指南

## 概述

本指南用于生成跨服务调用的 Feign API 接口代码。仅在用户明确要求生成 Feign 接口时才使用本指南。

> **重要说明**：Feign 接口为**可选功能**，默认不生成。只有在用户明确要求"需要跨服务调用"或"需要暴露Feign接口"时才生成。

## 使用前提

在生成 Feign API 接口前，必须满足以下条件：

1. ✅ 用户明确要求生成 Feign 接口
2. ✅ 已确认后端的 `moduleCode` 和 `acronym`
3. ✅ 已有后端业务代码（Entity、BO、Service等）
4. ✅ 明确需要暴露的业务接口列表

## 生成前确认

在生成 Feign API 接口前，必须向用户确认：

```markdown
### Feign 接口生成确认

您是否需要生成 Feign API 接口（用于其他微服务调用当前模块）？

如需生成，请确认以下内容：

1. **需要暴露的接口列表**：
   - [ ] save（保存）
   - [ ] update（更新）
   - [ ] getById（根据ID查询）
   - [ ] list（列表查询）
   - [ ] delete（删除）
   - [ ] 其他自定义接口：_______

2. **接口访问范围**：
   - [ ] 仅内部服务调用
   - [ ] 需要外部系统调用（需额外配置）

3. **DTO 字段确认**：
   - 用于服务间传输的字段列表（可能与前端 VO 不同）

请确认以上信息，或说明您的具体需求。
```

## Feign API 接口模板

### 1. Feign API 接口定义

```java
package com.supcon.nebule.{moduleCode}.custom.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.supcon.nebule.{moduleCode}.custom.dto.Custom{ModuleCode}{ModelCode}DTO;
import com.supcon.supfusion.framework.cloud.annotation.ServiceApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.result.Result;

@FeignClient(name = "{moduleCode}", contextId = "Custom{ModuleCode}{ModelCode}Api")
@ServiceApi(path = HttpConstants.URL_SERVICEAPI + HttpConstants.URL_SPLITER 
        + "{moduleCode}" + HttpConstants.URL_SPLITER + "{modelCodeLower}" + HttpConstants.URL_SPLITER + "rpc")
public interface Custom{ModuleCode}{ModelCode}Api {

    @PostMapping("/save")
    Result<Long> save(@RequestBody Custom{ModuleCode}{ModelCode}DTO dto);

    @PostMapping("/update")
    Result<Boolean> update(@RequestBody Custom{ModuleCode}{ModelCode}DTO dto);

    @GetMapping("/getById")
    Result<Custom{ModuleCode}{ModelCode}DTO> getById(@RequestParam("id") Long id);

    @PostMapping("/list")
    Result<List<Custom{ModuleCode}{ModelCode}DTO>> list(@RequestBody Custom{ModuleCode}{ModelCode}DTO dto);

    @GetMapping("/delete")
    Result<Boolean> delete(@RequestParam("id") Long id);
}
```

**存放位置**：`{moduleCode}-custom-api/src/main/java/com/supcon/nebule/{moduleCode}/custom/api/`

**命名规范**：`Custom{ModuleCode}{ModelCode}Api`

### 2. DTO 模板（服务间传输）

```java
package com.supcon.nebule.{moduleCode}.custom.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Custom{ModuleCode}{ModelCode}DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String code;

    private String status;

    private String remark;
}
```

**存放位置**：`{moduleCode}-custom-api/src/main/java/com/supcon/nebule/{moduleCode}/custom/dto/`

**命名规范**：`Custom{ModuleCode}{ModelCode}DTO`

**说明**：
- 必须实现 `Serializable` 接口（支持网络传输）
- 只包含服务间传输的必要字段
- 与前端 VO 的区别：更简洁，不包含展示相关字段

### 3. Controller 实现 Feign API

```java
package com.supcon.nebule.{moduleCode}.custom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.supcon.nebule.{moduleCode}.custom.api.Custom{ModuleCode}{ModelCode}Api;
import com.supcon.nebule.{moduleCode}.custom.bo.Custom{ModuleCode}{ModelCode}BO;
import com.supcon.nebule.{moduleCode}.custom.dto.Custom{ModuleCode}{ModelCode}DTO;
import com.supcon.nebule.{moduleCode}.custom.service.Custom{ModuleCode}{ModelCode}Service;
import com.supcon.supfusion.framework.cloud.annotation.InternalApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import com.supcon.supfusion.framework.common.utils.PojoUtil;

@RestController
@InternalApi(path = HttpConstants.URL_SPLITER + "{moduleCode}" + HttpConstants.URL_SPLITER + "{modelCodeLower}")
public class Custom{ModuleCode}{ModelCode}Controller implements Custom{ModuleCode}{ModelCode}Api {

    @Autowired
    private Custom{ModuleCode}{ModelCode}Service custom{ModuleCode}{ModelCode}Service;

    @Override
    @PostMapping("/save")
    public Result<Long> save(@RequestBody Custom{ModuleCode}{ModelCode}DTO dto) {
        Custom{ModuleCode}{ModelCode}BO bo = PojoUtil.copy(dto, Custom{ModuleCode}{ModelCode}BO.class);
        return Result.success(custom{ModuleCode}{ModelCode}Service.saveBO(bo));
    }

    @Override
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Custom{ModuleCode}{ModelCode}DTO dto) {
        Custom{ModuleCode}{ModelCode}BO bo = PojoUtil.copy(dto, Custom{ModuleCode}{ModelCode}BO.class);
        return Result.success(custom{ModuleCode}{ModelCode}Service.updateBO(bo));
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
        return Result.success(custom{ModuleCode}{ModelCode}Service.delete(id));
    }
}
```

**说明**：
- Controller 需要实现 Feign API 接口
- 使用 `@InternalApi` 注解声明内部 API 路径
- DTO 与 BO 之间使用 `PojoUtil.copy()` 进行转换

### 4. 配置类中启用 Feign 客户端扫描

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

## Feign 客户端调用示例

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

## 生成后说明

Feign API 接口生成完成后，需要：

1. **更新进度文件**：记录已生成 Feign 接口
2. **Git 提交**：提交 Feign API 相关代码
3. **生成说明文档**：在 `{moduleCode}-custom-api/` 目录下创建 `README.md`，包含：
   - Feign API 接口列表
   - 调用示例
   - DTO 字段说明
   - 注意事项

## 注意事项

1. **按需生成**：默认不生成 Feign 接口，仅在用户明确要求时生成
2. **DTO 设计**：服务间传输的 DTO 可能与前端 VO 不同，需根据实际需求设计字段
3. **版本兼容**：Feign API 接口变更需考虑版本兼容性
4. **安全控制**：敏感接口需要添加权限控制
5. **性能考虑**：避免暴露粒度过细的接口，减少网络调用次数
