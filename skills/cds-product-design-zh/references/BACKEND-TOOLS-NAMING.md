# 后端命名和编码规范工具指南

## 概述

本指南用于统一 CDS 后端技术方案中的命名、接口 URL、请求头以及 API 请求响应示例规范。

适用场景：

- 后端技术方案设计
- API 接口设计
- Controller 接口命名与路径规划
- 请求参数与响应结果示例编写

## 命名规范

### 模块关键标识

所有后端命名都必须基于用户最终确认后的以下关键标识：

- `moduleCode`
- `acronym`

这些值将用于：

- 包名
- 类名
- 接口路径前缀
- 注解参数
- 数据库建表前缀

## API 调用规范

### 请求头规范

```javascript
// 获取认证Token
const token = localStorage.getItem('ticket') || '';

// 请求头
const headers = {
    'Authorization': 'Bearer ' + token,
    'x-nebule-model-code': '{modelCode}',
    'x-nebule-module-code': '{moduleCode}'
};
```

### 接口 URL 规范

后端接口路径必须根据是否需要权限控制，使用不同的 URL 前缀；前端实际调用时，则需要在后端接口 URL 的基础上统一追加 SupOS 网关前缀 `/msService`。

- **不需要权限控制的后端接口**：一律使用 `/public/{moduleCode}/` 开头
- **需要权限控制的后端接口**：一律使用 `/{moduleCode}/` 开头
- **前端实际调用规则**：一律在后端接口 URL 前添加 `/msService` 前缀，作为业务 SupOS 网关路由规则

### URL 设计规则

1. 不得直接使用无模块前缀的裸路径，例如 `/save`、`/info`、`/delete`
2. 公共开放接口必须显式使用 `/public/{moduleCode}/` 前缀，避免与受控接口混淆
3. 需要认证、鉴权、菜单权限或业务权限控制的接口，统一使用 `/{moduleCode}/` 前缀
4. 前端实际调用后端接口时，统一使用 `/msService` 作为网关前缀
5. 同一功能模块内的接口路径命名应保持风格一致

### URL 示例

#### 需要权限控制的接口

- **后端接口 URL**：`/{moduleCode}/save`
- **前端调用 URL**：`/msService/{moduleCode}/save`
- **后端接口 URL**：`/{moduleCode}/info`
- **前端调用 URL**：`/msService/{moduleCode}/info`
- **后端接口 URL**：`/{moduleCode}/delete`
- **前端调用 URL**：`/msService/{moduleCode}/delete`
- **后端接口 URL**：`/{moduleCode}/list`
- **前端调用 URL**：`/msService/{moduleCode}/list`

#### 不需要权限控制的接口

- **后端接口 URL**：`/public/{moduleCode}/health`
- **前端调用 URL**：`/msService/public/{moduleCode}/health`
- **后端接口 URL**：`/public/{moduleCode}/callback`
- **前端调用 URL**：`/msService/public/{moduleCode}/callback`
- **后端接口 URL**：`/public/{moduleCode}/query`
- **前端调用 URL**：`/msService/public/{moduleCode}/query`

## API 接口设计

### 基础增删改查接口

| 接口路径 | 方法 | 描述 | 参数 | 返回值 |
|---------|------|------|------|--------|
| `/{moduleCode}/save` | POST | 保存记录 | Custom{ModuleCode}{ModelCode}VO | Result<Long> |
| `/{moduleCode}/info` | GET | 根据ID获取 | id: Long | Result<Custom{ModuleCode}{ModelCode}VO> |
| `/{moduleCode}/delete` | POST | 删除记录 | id: Long | Result<Boolean> |

> 若某个接口明确不需要权限控制，则将对应后端接口路径前缀替换为 `/public/{moduleCode}/`；若为前端实际调用地址，则在此基础上继续添加 `/msService` 网关前缀。

## 请求参数示例

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

## 响应结果示例

```json
// 成功响应
{
    "code": 200,
    "message": "请求成功",
    "data": {
        "id": 123,
        "name": "示例数据"
    }
}

// 资源不存在
{
    "code": 404,
    "message": "请求的资源不存在",
    "data": null
}

// 网关异常
{
    "code": 502,
    "message": "网关服务异常，请稍后重试",
    "data": null
}
```
### 响应结果格式
```json
// 成功响应
{
    "code": 100000000,
    "message": "操作成功",
    "data": {...}
}

// 失败响应
{
    "code": 500000000,
    "message": "系统异常",
    "data": null
}
```

## 使用建议

1. 先确认 `moduleCode` 与 `acronym`
2. 再设计接口路径前缀是否需要权限控制
3. 最后编写 API 接口定义、请求参数示例和响应结果示例

## 相关文档

- [项目结构工具指南](BACKEND-TOOLS-PROJECT-STRUCTURE.md)
- [数据库规范工具指南](BACKEND-TOOLS-DATABASE.md)
- [配置管理工具指南](BACKEND-TOOLS-CONFIGURATION.md)
