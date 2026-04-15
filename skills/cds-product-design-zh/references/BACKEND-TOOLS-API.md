# 后端API设计工具指南

## 概述

本指南详细说明了 CDS 后端项目的 API 接口设计规范，包括 RESTful 接口规范、请求响应格式、参数说明以及最佳实践。

适用场景：
- 后端技术方案设计
- API 接口详细设计
- Controller 接口实现规范
- 前端与后端接口对接规范

## 项目关键标识

所有 API 设计都必须基于用户最终确认的以下关键标识：

- `moduleCode`: 模块编码
- `acronym`: 模块缩略码
- `modelCode`: 模型编码（可选）

这些值将用于：
- 接口路径前缀
- 包名结构
- 类命名规范
- 数据库表名
- DTO/VO 类命名

## API调用规范

### 请求头规范

```javascript
// 获取认证Token
const token = localStorage.getItem('ticket') || '';

// 基础请求头
const headers = {
    'Authorization': 'Bearer ' + token,
    'Content-Type': 'application/json',
    'x-nebule-model-code': '{modelCode}',
    'x-nebule-module-code': '{moduleCode}'
};

// 文件上传请求头
const fileHeaders = {
    'Authorization': 'Bearer ' + token,
    'x-nebule-model-code': '{modelCode}',
    'x-nebule-module-code': '{moduleCode}',
    'Content-Type': 'multipart/form-data'
};
```

### 接口URL规范

#### URL前缀规则

| 权限控制需求 | 后端接口URL | 前端调用URL |
|-------------|------------|------------|
| 不需要权限控制 | `/public/{moduleCode}/` | `/msService/public/{moduleCode}/` |
| 需要权限控制 | `/{moduleCode}/` | `/msService/{moduleCode}/` |

#### URL设计原则

1. **模块化**：所有接口必须包含模块前缀 `{moduleCode}`
2. **语义化**：使用动词+名词的结构，如 `save`, `delete`, `query`
3. **一致性**：同一模块内接口风格统一
4. **版本控制**：建议在路径中包含版本号（可选）

#### URL示例

```
# 需要权限控制的接口
POST   /{moduleCode}/save
GET    /{moduleCode}/info?id=123
GET    /{moduleCode}/list?page=1&size=20
DELETE /{moduleCode}/delete?id=123

# 不需要权限控制的接口
GET    /public/{moduleCode}/health
POST   /public/{moduleCode}/callback
GET    /public/{moduleCode}/version

# 带版本的接口（推荐）
POST   /{moduleCode}/v1/save
GET    /{moduleCode}/v1/list
```

## 基础增删改查接口设计

### 标准CRUD接口

| 接口路径 | HTTP方法 | 描述 | 请求参数 | 响应格式 |
|---------|----------|------|----------|----------|
| `/{moduleCode}/save` | POST | 保存记录 | Custom{ModuleCode}{ModelCode}VO | Result<Long> |
| `/{moduleCode}/info` | GET | 根据ID查询详情 | id: Long | Result<Custom{ModuleCode}{ModelCode}VO> |
| `/{moduleCode}/list` | GET | 查询列表 | Query条件对象 | Result<List<Custom{ModuleCode}{ModelCode}VO>> |
| `/{moduleCode}/delete` | POST | 删除记录 | id: Long | Result<Boolean> |

### cURL 验证示例

#### 1. 保存接口 (POST)

```bash
# 需要权限控制的保存接口
curl -X POST "https://api.example.com/msService/{moduleCode}/save" \
  -H "Authorization: Bearer {your_token}" \
  -H "Content-Type: application/json" \
  -H "x-nebule-model-code: {modelCode}" \
  -H "x-nebule-module-code: {moduleCode}" \
  -d '{
    "id": 1,
    "name": "示例名称",
    "code": "SAMPLE001",
    "status": "ENABLED",
    "remark": "备注信息",
    "createUserId": 123,
    "editUserId": 123
  }'

# 不需要权限控制的保存接口
curl -X POST "https://api.example.com/msService/public/{moduleCode}/save" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "公开数据",
    "code": "PUBLIC001"
  }'
```

#### 2. 详情查询接口 (GET)

```bash
# 根据ID查询详情
curl -X GET "https://api.example.com/msService/{moduleCode}/info?id=123" \
  -H "Authorization: Bearer {your_token}" \
  -H "x-nebule-model-code: {modelCode}" \
  -H "x-nebule-module-code: {moduleCode}"
```

#### 3. 列表查询接口 (GET)

```bash
# 基础列表查询
curl -X GET "https://api.example.com/msService/{moduleCode}/list?pageNum=1&pageSize=20" \
  -H "Authorization: Bearer {your_token}" \
  -H "x-nebule-model-code: {modelCode}" \
  -H "x-nebule-module-code: {moduleCode}"

# 带过滤条件的列表查询
curl -X GET "https://api.example.com/msService/{moduleCode}/list?pageNum=1&pageSize=20&name=示例&status=ENABLED&sort=name,asc" \
  -H "Authorization: Bearer {your_token}" \
  -H "x-nebule-model-code: {modelCode}" \
  -H "x-nebule-module-code: {moduleCode}"

# 分页查询接口
curl -X GET "https://api.example.com/msService/{moduleCode}/page?pageNum=1&pageSize=20&sort=name,asc&filter=status:ENABLED,name:示例" \
  -H "Authorization: Bearer {your_token}" \
  -H "x-nebule-model-code: {modelCode}" \
  -H "x-nebule-module-code: {moduleCode}"
```

#### 4. 删除接口 (POST)

```bash
# 单个删除
curl -X POST "https://api.example.com/msService/{moduleCode}/delete" \
  -H "Authorization: Bearer {your_token}" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 123
  }'

# 批量删除
curl -X POST "https://api.example.com/msService/{moduleCode}/batchDelete" \
  -H "Authorization: Bearer {your_token}" \
  -H "Content-Type: application/json" \
  -d '{
    "ids": [1, 2, 3, 4, 5]
  }'
```

#### 5. 健康检查接口 (GET)

```bash
# 服务健康检查
curl -X GET "https://api.example.com/msService/public/{moduleCode}/health"

# 获取服务版本信息
curl -X GET "https://api.example.com/msService/public/{moduleCode}/version"

# 获取性能指标
curl -X GET "https://api.example.com/msService/public/{moduleCode}/metrics"
```

### 高级 cURL 示例

#### 文件上传接口

```bash
# 单文件上传
curl -X POST "https://api.example.com/msService/{moduleCode}/upload" \
  -H "Authorization: Bearer {your_token}" \
  -H "x-nebule-model-code: {modelCode}" \
  -H "x-nebule-module-code: {moduleCode}" \
  -F "file=@/path/to/your/file.xlsx" \
  -F "description=测试文件上传"

# 多文件上传
curl -X POST "https://api.example.com/msService/{moduleCode}/upload" \
  -H "Authorization: Bearer {your_token}" \
  -H "x-nebule-model-code: {modelCode}" \
  -H "x-nebule-module-code: {moduleCode}" \
  -F "files=@file1.xlsx" \
  -F "files=@file2.xlsx"
```

#### 数据导入导出

```bash
# 数据导出
curl -X GET "https://api.example.com/msService/{moduleCode}/export?format=xlsx&ids=1,2,3" \
  -H "Authorization: Bearer {your_token}" \
  --output exported_data.xlsx

# 数据导入
curl -X POST "https://api.example.com/msService/{moduleCode}/import" \
  -H "Authorization: Bearer {your_token}" \
  -H "x-nebule-model-code: {modelCode}" \
  -H "x-nebule-module-code: {moduleCode}" \
  -F "file=@import_data.xlsx" \
  -F "templateFlag=false"
```

#### 批量操作

```bash
# 批量更新状态
curl -X POST "https://api.example.com/msService/{moduleCode}/batchUpdate" \
  -H "Authorization: Bearer {your_token}" \
  -H "Content-Type: application/json" \
  -d '{
    "updateData": {
      "status": "DELETED"
    },
    "ids": [1, 2, 3, 4, 5]
  }'

# 批量启用/禁用
curl -X POST "https://api.example.com/msService/{moduleCode}/batchStatus" \
  -H "Authorization: Bearer {your_token}" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "ENABLED",
    "ids": [1, 2, 3]
  }'
```

### 错误处理 cURL 示例

#### 认证失败

```bash
# 无效token
curl -X POST "https://api.example.com/msService/{moduleCode}/save" \
  -H "Authorization: Bearer invalid_token" \
  -H "Content-Type: application/json" \
  -d '{"name": "test"}'

# 响应示例
# HTTP/1.1 401 Unauthorized
# {"code":401,"message":"认证失败","data":null}
```

#### 参数验证失败

```bash
# 缺少必填参数
curl -X POST "https://api.example.com/msService/{moduleCode}/save" \
  -H "Authorization: Bearer {valid_token}" \
  -H "Content-Type: application/json" \
  -d '{"code": "TEST001"}'  # 缺少name字段

# 响应示例
# {"code":400,"message":"请求参数错误","errors":[{"field":"name","message":"名称不能为空"}]}
```

#### 资源不存在

```bash
# 查询不存在的ID
curl -X GET "https://api.example.com/msService/{moduleCode}/info?id=99999" \
  -H "Authorization: Bearer {valid_token}"

# 响应示例
# {"code":404,"message":"请求的资源不存在","data":null}
```

### 使用技巧

#### 1. 设置默认超时时间

```bash
# 设置连接超时和读取超时
curl --connect-timeout 30 --max-time 60 -X GET "https://api.example.com/msService/{moduleCode}/list" \
  -H "Authorization: Bearer {your_token}"
```

#### 2. 输出格式化JSON

```bash
# 美化输出JSON响应
curl -X GET "https://api.example.com/msService/{moduleCode}/list" \
  -H "Authorization: Bearer {your_token}" \
  --output - | python -m json.tool

# 或者使用jq工具（如果安装了）
curl -X GET "https://api.example.com/msService/{moduleCode}/list" \
  -H "Authorization: Bearer {your_token}" | jq '.'
```

#### 3. 保存响应到文件

```bash
# 将响应保存到本地文件
curl -X GET "https://api.example.com/msService/{moduleCode}/list" \
  -H "Authorization: Bearer {your_token}" \
  --output response.json

# 同时显示响应内容并保存
curl -X GET "https://api.example.com/msService/{moduleCode}/list" \
  -H "Authorization: Bearer {your_token}" \
  --output response.json -v
```

#### 4. 调试模式

```bash
# 显示详细的通信过程
curl -X POST "https://api.example.com/msService/{moduleCode}/save" \
  -H "Authorization: Bearer {your_token}" \
  -H "Content-Type: application/json" \
  -d '{"name": "test"}' \
  -v  # verbose模式

# 只显示头部信息
curl -I "https://api.example.com/msService/{moduleCode}/health"

# 显示头部和响应体，但不显示进度
curl -s -i "https://api.example.com/msService/{moduleCode}/health"
```

### PowerShell 示例（Windows）

```powershell
# PowerShell 中的 cURL 示例
$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
    "x-nebule-model-code" = "$modelCode"
    "x-nebule-module-code" = "$moduleCode"
}

Invoke-RestMethod -Uri "https://api.example.com/msService/$moduleCode/save" `
    -Method Post `
    -Headers $headers `
    -Body (@{
        name = "PowerShell测试"
        code = "PS001"
    } | ConvertTo-Json) `
    -ContentType "application/json"
```

### 环境变量配置

为了便于管理和复用，建议使用环境变量：

```bash
# 设置环境变量
export API_BASE_URL="https://api.example.com"
export API_TOKEN="your_jwt_token_here"
export MODULE_CODE="your_module_code"
export MODEL_CODE="your_model_code"

# 使用变量调用API
curl -X GET "$API_BASE_URL/msService/$MODULE_CODE/list" \
  -H "Authorization: Bearer $API_TOKEN" \
  -H "x-nebule-model-code: $MODEL_CODE" \
  -H "x-nebule-module-code: $MODULE_CODE"
```

这些 cURL 示例涵盖了从基础到高级的各种使用场景，可以帮助开发者和测试人员快速验证 API 接口的功能和性能。

## 业务API接口设计模板

### 1. 新增/保存接口

```http
POST /{moduleCode}/save
Content-Type: application/json

{
    "id": 1,
    "name": "示例名称",
    "code": "SAMPLE001",
    "status": "ENABLED",
    "remark": "备注信息",
    "createUserId": 123,
    "editUserId": 123
}
```

**成功响应：**
```json
{
    "code": 200,
    "message": "保存成功",
    "data": 123
}
```

### 2. 详情查询接口

```http
GET /{moduleCode}/info?id=123
```

**成功响应：**
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 123,
        "name": "示例数据",
        "code": "SAMPLE001",
        "status": "ENABLED",
        "remark": "备注信息",
        "createDate": "2024-01-01 10:00:00",
        "editDate": "2024-01-01 10:00:00"
    }
}
```

### 3. 列表查询接口

```http
GET /{moduleCode}/list?pageNum=1&pageSize=20&name=示例&status=ENABLED
```

**成功响应：**
```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "id": 1,
            "name": "示例数据1",
            "status": "ENABLED",
            "createDate": "2024-01-01 10:00:00"
        },
        {
            "id": 2,
            "name": "示例数据2",
            "status": "DISABLED",
            "createDate": "2024-01-01 11:00:00"
        }
    ]
}
```

### 4. 删除接口

```http
POST /{moduleCode}/delete
Content-Type: application/json

{
    "id": 123
}
```

**成功响应：**
```json
{
    "code": 200,
    "message": "删除成功",
    "data": true
}
```

## 特殊业务接口设计

### 批量操作接口

#### 批量删除
```http
POST /{moduleCode}/batchDelete
Content-Type: application/json

{
    "ids": [1, 2, 3]
}
```

#### 批量更新
```http
POST /{moduleCode}/batchUpdate
Content-Type: application/json

{
    "updateData": {
        "status": "DELETED"
    },
    "ids": [1, 2, 3]
}
```

### 导入导出接口

#### 数据导入
```http
POST /{moduleCode}/import
Content-Type: multipart/form-data

form-data:
- file: (Excel文件)
- templateFlag: false
```

#### 数据导出
```http
GET /{moduleCode}/export?format=xlsx&ids=1,2,3
```

### 统计查询接口

#### 数量统计
```http
GET /{moduleCode}/count?status=ENABLED
```

**响应：**
```json
{
    "code": 200,
    "message": "success",
    "data": 150
}
```

#### 分组统计
```http
GET /{moduleCode}/statistics?groupBy=status
```

## 错误处理规范

### 错误响应格式

```json
{
    "code": 400,
    "message": "请求参数错误",
    "data": null,
    "errors": [
        {
            "field": "name",
            "message": "名称不能为空"
        }
    ]
}
```

### 错误码定义

| 错误码 | 错误类型 | 描述 | 建议 |
|--------|----------|------|------|
| 200 | 成功 | 请求成功 | - |
| 400 | 客户端错误 | 请求参数错误 | 检查参数格式 |
| 401 | 未授权 | 认证失败 | 重新登录获取token |
| 403 | 禁止访问 | 权限不足 | 联系管理员 |
| 404 | 资源不存在 | 请求的资源不存在 | 检查资源ID |
| 409 | 数据冲突 | 数据唯一性约束冲突 | 修改重复数据 |
| 422 | 不可处理 | 请求体格式错误 | 检查JSON格式 |
| 500 | 服务器错误 | 系统内部错误 | 稍后重试或联系运维 |
| 502 | 网关错误 | 网关服务异常 | 稍后重试 |

## 性能优化建议

### 1. 分页查询
- 所有列表接口必须支持分页
- 默认页大小不超过100条记录
- 提供总页数和总数统计

### 2. 缓存策略
```java
// Redis缓存注解
@Cacheable(value = "{moduleCode}:config", key = "#id")
public ConfigInfo getConfig(Long id) {
    // 查询数据库
}

@CacheEvict(value = "{moduleCode}:config", key = "#id")
public void updateConfig(ConfigInfo config) {
    // 更新数据库并清除缓存
}
```

### 3. 异步处理
```java
// 耗时操作异步执行
@Async
public void processLargeData(Long taskId) {
    // 处理大数据量任务
}
```

## 安全考虑

### 1. 输入验证
```java
// 参数校验注解
public class SaveRequest {
    @NotNull(message = "名称不能为空")
    private String name;
    
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "编码只能包含字母数字")
    private String code;
}
```

### 2. SQL注入防护
- 使用 MyBatis-Plus 的 Wrapper 构建查询条件
- 避免直接拼接 SQL 语句
- 使用预编译语句

### 3. XSS防护
- 对所有用户输入进行转义处理
- 设置合适的 Content-Type 头部
- 对富文本内容进行安全过滤

## 接口测试

### 单元测试示例

```java
@SpringBootTest
@AutoConfigureMockMvc
class {ModuleCode}ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnSuccessWhenSaveValidData() throws Exception {
        // given
        SaveRequest request = new SaveRequest();
        request.setName("测试数据");
        request.setCode("TEST001");

        // when & then
        mockMvc.perform(post("/{moduleCode}/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
```

### Postman 集合示例

```json
{
    "info": {
        "name": "{ModuleCode} API Collection",
        "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
    },
    "item": [
        {
            "name": "保存接口",
            "request": {
                "method": "POST",
                "url": "{{baseUrl}}/{moduleCode}/save",
                "header": [
                    {
                        "key": "Authorization",
                        "value": "Bearer {{token}}"
                    }
                ],
                "body": {
                    "mode": "raw",
                    "raw": "{\n  \"name\": \"测试数据\",\n  \"code\": \"TEST001\"\n}"
                }
            }
        }
    ]
}
```

## 文档生成

### Swagger/OpenAPI 配置

```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("{moduleCode}.webapi"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("{ModuleName} API 文档")
                .description("API 接口详细说明")
                .version("1.0.0")
                .build();
    }
}
```

### API文档地址
- Swagger UI: `/swagger-ui.html`
- OpenAPI JSON: `/v2/api-docs`
- Swagger YAML: `/v2/api-docs.yaml`

## 监控和日志

### 1. 接口监控指标

```java
@Component
public class ApiMetrics {

    private final MeterRegistry meterRegistry;

    public void recordApiCall(String moduleCode, String apiName, long duration, boolean success) {
        Timer.builder("api.call.duration")
                .tag("module", moduleCode)
                .tag("api", apiName)
                .register(meterRegistry)
                .record(duration, TimeUnit.MILLISECONDS);

        Counter.builder("api.call.count")
                .tag("module", moduleCode)
                .tag("api", apiName)
                .tag("success", String.valueOf(success))
                .register(meterRegistry)
                .increment();
    }
}
```

### 2. 日志记录

```java
@RestController
@RequestMapping("/{moduleCode}")
public class {ModuleCode}Controller {

    private static final Logger logger = LoggerFactory.getLogger({ModuleCode}Controller.class);

    @PostMapping("/save")
    public Result<Long> save(@RequestBody SaveRequest request, HttpServletRequest httpRequest) {
        try {
            logger.info("保存请求: {}, 用户: {}", request, getUserInfo(httpRequest));
            // 业务逻辑
            return Result.success(id);
        } catch (Exception e) {
            logger.error("保存失败: {}", request, e);
            return Result.fail("保存失败");
        }
    }
}
```

## 版本管理策略

### 1. URL版本控制
```
/{moduleCode}/v1/save      # 版本1
/{moduleCode}/v2/list      # 版本2（新增功能）
/{moduleCode}/v3/batch     # 版本3（重构后）
```

### 2. 兼容性考虑
- 新版本接口应保持旧版本的兼容性
- 废弃的接口应有明确的弃用通知
- 重大变更应在文档中明确标注

## 部署注意事项

### 1. CORS配置
```properties
# application.properties
cors.allow-origin=*
cors.allow-methods=GET,POST,PUT,DELETE,OPTIONS
cors.allow-headers=*
cors.allow-credentials=true
```

### 2. 健康检查
```http
GET /public/{moduleCode}/health
GET /public/{moduleCode}/metrics
```


## 相关文档

- [项目结构工具指南](BACKEND-TOOLS-PROJECT-STRUCTURE.md)
- [后端命名和编码规范工具指南](BACKEND-TOOLS-NAMING.md)
- [数据库规范工具指南](BACKEND-TOOLS-DATABASE.md)
- [配置管理工具指南](BACKEND-TOOLS-CONFIGURATION.md)

---

本指南提供了完整的后端API设计规范，涵盖了从接口设计到部署上线的全流程指导。在实际项目中，应根据具体业务需求进行调整和补充。