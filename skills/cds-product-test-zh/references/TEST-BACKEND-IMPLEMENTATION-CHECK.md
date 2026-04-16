# 后端代码实现检查指南

## 概述

本指南用于检查后端代码实现是否符合后端设计文档的要求，包括类命名、文件位置、初始化语句等。检查完成后，结果输出到 `design-docs/testing-validation/` 目录。

## 检查前提

1. ✅ 后端代码已生成完成（选项B已执行）
2. ✅ 后端架构设计文档已存在
3. ✅ API设计文档已存在
4. ✅ 已确认 `moduleCode` 和 `acronym`

## 检查步骤

### 步骤1：读取后端设计文档

**查找位置**：
- `design-docs/backend-architecture/{功能名称}后端架构设计.md`
- `design-docs/backend-architecture/{功能名称}-API设计文档.md`
- `design-docs/data-architecture/DATA.md`

**提取内容**：
- 类命名规范
- 包结构要求
- 注解使用要求
- 初始化语句要求
- API接口设计要求

### 步骤2：检查类命名规范

#### 2.1 Controller命名

```markdown
### Controller命名检查

| 类名 | 命名规范 | 实际命名 | 是否符合 | 问题描述 |
|------|---------|---------|---------|---------|
| UserController | Custom{ModuleCode}Controller | CustomUserManagementController | ✅ 符合 | - |
| RoleController | Custom{ModuleCode}Controller | RoleController | ❌ 不符合 | 应为 CustomRoleManagementController |
```

#### 2.2 Service命名

```markdown
### Service命名检查

| 类名 | 命名规范 | 实际命名 | 是否符合 | 问题描述 |
|------|---------|---------|---------|---------|
| UserService | Custom{ModuleCode}Service | CustomUserManagementService | ✅ 符合 | - |
| UserServiceImpl | Custom{ModuleCode}ServiceImpl | CustomUserManagementServiceImpl | ✅ 符合 | - |
```

#### 2.3 DAO/Mapper命名

```markdown
### DAO/Mapper命名检查

| 类名 | 命名规范 | 实际命名 | 是否符合 | 问题描述 |
|------|---------|---------|---------|---------|
| UserDAO | Custom{ModuleCode}DAO | CustomUserManagementDAO | ✅ 符合 | - |
```

#### 2.4 Entity/BO/VO命名

```markdown
### Entity/BO/VO命名检查

| 类型 | 命名规范 | 实际命名 | 是否符合 | 问题描述 |
|------|---------|---------|---------|---------|
| Entity | Custom{ModuleCode}Entity | CustomUserManagementEntity | ✅ 符合 | - |
| BO | Custom{ModuleCode}BO | CustomUserManagementBO | ✅ 符合 | - |
| VO | Custom{ModuleCode}VO | CustomUserManagementVO | ✅ 符合 | - |
```

### 步骤3：检查文件位置

#### 3.1 Controller位置

```markdown
### Controller位置检查

| 类名 | 期望位置 | 实际位置 | 是否符合 | 问题描述 |
|------|---------|---------|---------|---------|
| CustomUserManagementController | custom.controller | custom.controller | ✅ 符合 | - |
| CustomRoleManagementController | custom.controller | custom.controller.role | ❌ 不符合 | 应在 custom.controller 包下 |
```

#### 3.2 Service位置

```markdown
### Service位置检查

| 类名 | 期望位置 | 实际位置 | 是否符合 | 问题描述 |
|------|---------|---------|---------|---------|
| CustomUserManagementService | custom.service | custom.service | ✅ 符合 | - |
| CustomUserManagementServiceImpl | custom.service.impl | custom.service.impl | ✅ 符合 | - |
```

#### 3.3 DAO位置

```markdown
### DAO位置检查

| 类名 | 期望位置 | 实际位置 | 是否符合 | 问题描述 |
|------|---------|---------|---------|---------|
| CustomUserManagementDAO | custom.dao | custom.dao | ✅ 符合 | - |
```

#### 3.4 Entity/BO/VO位置

```markdown
### Entity/BO/VO位置检查

| 类名 | 类型 | 期望位置 | 实际位置 | 是否符合 | 问题描述 |
|------|------|---------|---------|---------|---------|
| CustomUserManagementEntity | Entity | custom.entity | custom.entity | ✅ 符合 | - |
| CustomUserManagementBO | BO | custom.bo | custom.bo | ✅ 符合 | - |
| CustomUserManagementVO | VO | custom.vo | custom.vo | ✅ 符合 | - |
```

### 步骤4：检查注解使用

#### 4.1 Controller注解

```markdown
### Controller注解检查

| 类名 | @RestController | @InternalApi | @RequestMapping | 是否符合 | 问题描述 |
|------|----------------|-------------|----------------|---------|---------|
| CustomUserManagementController | ✅ 有 | ✅ 有 | ✅ 有 | ✅ 符合 | - |
| CustomRoleManagementController | ✅ 有 | ❌ 无 | ✅ 有 | ❌ 不符合 | 缺少 @InternalApi 注解 |
```

#### 4.2 Entity注解

```markdown
### Entity注解检查

| 类名 | @Data | @TableName | @EnCodeField | 是否符合 | 问题描述 |
|------|-------|-----------|-------------|---------|---------|
| CustomUserManagementEntity | ✅ 有 | ✅ 有 | ✅ 有 | ✅ 符合 | - |
| CustomRoleManagementEntity | ✅ 有 | ✅ 有 | ❌ 无 | ❌ 不符合 | 缺少 @EnCodeField 注解 |
```

#### 4.3 Service注解

```markdown
### Service注解检查

| 类名 | @Service | @Transactional | 是否符合 | 问题描述 |
|------|---------|---------------|---------|---------|
| CustomUserManagementServiceImpl | ✅ 有 | ✅ 有 | ✅ 符合 | - |
```

### 步骤5：检查初始化语句

#### 5.1 数据库初始化语句

```markdown
### 数据库初始化语句检查

| 检查项 | 设计要求 | 实际实现 | 是否符合 | 问题描述 |
|--------|---------|---------|---------|---------|
| 建表语句 | 包含所有字段 | 包含所有字段 | ✅ 符合 | - |
| 主键约束 | 使用id作为主键 | 使用id作为主键 | ✅ 符合 | - |
| 索引创建 | 关键字段有索引 | 部分字段有索引 | ⚠️ 部分 | 缺少查询字段索引 |
| 默认值 | 设置合理默认值 | 设置默认值 | ✅ 符合 | - |
| 注释 | 表和字段有注释 | 部分有注释 | ⚠️ 部分 | 部分字段缺少注释 |
```

#### 5.2 模块注册初始化

```markdown
### 模块注册初始化检查

| 检查项 | 设计要求 | 实际实现 | 是否符合 | 问题描述 |
|--------|---------|---------|---------|---------|
| ModuleRegisterInitialize | 实现注册接口 | 已实现 | ✅ 符合 | - |
| 模块注册 | 注册模块信息 | 已注册 | ✅ 符合 | - |
| 菜单注册 | 注册菜单结构 | 已注册 | ✅ 符合 | - |
| 工作流注册 | 注册工作流 | 未注册 | ❌ 不符合 | 缺少工作流注册 |
```

#### 5.3 配置初始化

```markdown
### 配置初始化检查

| 检查项 | 设计要求 | 实际实现 | 是否符合 | 问题描述 |
|--------|---------|---------|---------|---------|
| application.yml | 配置数据源 | 已配置 | ✅ 符合 | - |
| bootstrap.properties | 配置服务发现 | 已配置 | ✅ 符合 | - |
| mybatis-config.xml | 配置MyBatis | 已配置 | ✅ 符合 | - |
| 包扫描配置 | 配置组件扫描 | 已配置 | ✅ 符合 | - |
```

### 步骤6：检查API实现

#### 6.1 API接口完整性

```markdown
### API接口完整性检查

| 接口功能 | 设计要求 | 实际实现 | 参数正确 | 返回值正确 | 是否符合 |
|---------|---------|---------|---------|-----------|---------|
| 保存用户 | ✅ 需要 | ✅ 已实现 | ✅ 正确 | ✅ 正确 | ✅ 符合 |
| 查询列表 | ✅ 需要 | ✅ 已实现 | ✅ 正确 | ✅ 正确 | ✅ 符合 |
| 删除用户 | ✅ 需要 | ✅ 已实现 | ❌ 错误 | ✅ 正确 | ❌ 不符合 |
| 更新用户 | ✅ 需要 | ❌ 未实现 | - | - | ❌ 不符合 |
```

#### 6.2 API规范性

```markdown
### API规范性检查

| 检查项 | 规范要求 | 实际实现 | 是否符合 | 问题描述 |
|--------|---------|---------|---------|---------|  
| URL规范 | 使用/{moduleCode}/前缀 | 使用前缀 | ✅ 符合 | - |
| 请求方法 | GET/POST正确使用 | 正确使用 | ✅ 符合 | - |
| 参数校验 | 使用@Valid校验 | 部分使用 | ⚠️ 部分 | 部分接口缺少校验 |
| 异常处理 | 统一异常处理 | 统一处理 | ✅ 符合 | - |
| 返回值 | 使用Result包装 | 使用Result | ✅ 符合 | - |
```

### 步骤7：检查Service-DAO-Mapper调用链路完整性

> **重要说明**：此步骤检查后端代码的调用链路是否完整，确保Controller调用的Service方法在Service接口中有声明，Service实现类中有实现，Service调用的DAO方法在DAO接口中有声明，Mapper XML中有对应的SQL实现。

#### 7.1 Controller → Service 调用检查

**检查规则**：
1. 查找Controller中所有调用的Service方法
2. 验证这些方法在Service接口中是否有声明
3. 验证这些方法在ServiceImpl实现类中是否有实现

```markdown
### Controller → Service 调用链路检查

| Controller类 | 调用的Service方法 | Service接口声明 | ServiceImpl实现 | 是否完整 | 问题描述 |
|-------------|------------------|----------------|----------------|---------|---------|
| UserController | saveBO(bo) | ✅ 有声明 | ✅ 有实现 | ✅ 完整 | - |
| UserController | updateBO(bo) | ✅ 有声明 | ✅ 有实现 | ✅ 完整 | - |
| UserController | getByIdBO(id) | ✅ 有声明 | ✅ 有实现 | ✅ 完整 | - |
| UserController | listBO(bo) | ✅ 有声明 | ❌ 无实现 | ❌ 不完整 | ServiceImpl缺少listBO实现 |
| UserController | delete(id) | ❌ 无声明 | - | ❌ 不完整 | Service接口缺少delete声明 |
```

**检查步骤**：

1. **提取Controller中的Service调用**：
```java
// UserController.java
public Result<Long> save(@RequestBody CustomUserManagementDTO dto) {
    CustomUserManagementBO bo = PojoUtil.copy(dto, CustomUserManagementBO.class);
    return Result.success(customUserManagementService.saveBO(bo)); // ← 提取 saveBO
}
```

2. **验证Service接口声明**：
```java
// UserService.java
public interface CustomUserManagementService {
    Long saveBO(CustomUserManagementBO bo); // ✅ 有声明
    Boolean updateBO(CustomUserManagementBO bo);
    CustomUserManagementBO getByIdBO(Long id);
    // ❌ 缺少 listBO 声明
    // ❌ 缺少 delete 声明
}
```

3. **验证ServiceImpl实现**：
```java
// UserServiceImpl.java
@Service
public class CustomUserManagementServiceImpl implements CustomUserManagementService {
    
    @Override
    public Long saveBO(CustomUserManagementBO bo) { // ✅ 有实现
        // 实现代码
    }
    
    @Override
    public Boolean updateBO(CustomUserManagementBO bo) { // ✅ 有实现
        // 实现代码
    }
    
    @Override
    public CustomUserManagementBO getByIdBO(Long id) { // ✅ 有实现
        // 实现代码
    }
    
    // ❌ 缺少 listBO 实现
    // ❌ 缺少 delete 实现
}
```

#### 7.2 Service → DAO 调用检查

**检查规则**：
1. 查找ServiceImpl中所有调用的DAO方法
2. 验证这些方法在DAO接口中是否有声明
3. 验证这些方法在Mapper XML中是否有对应的SQL实现

```markdown
### Service → DAO 调用链路检查

| ServiceImpl类 | 调用的DAO方法 | DAO接口声明 | Mapper XML实现 | 是否完整 | 问题描述 |
|--------------|--------------|------------|---------------|---------|---------|
| UserServiceImpl | insert(entity) | ✅ 有声明 | ✅ 有SQL | ✅ 完整 | - |
| UserServiceImpl | updateById(entity) | ✅ 有声明 | ✅ 有SQL | ✅ 完整 | - |
| UserServiceImpl | selectById(id) | ✅ 有声明 | ✅ 有SQL | ✅ 完整 | - |
| UserServiceImpl | selectList(query) | ✅ 有声明 | ❌ 无SQL | ❌ 不完整 | Mapper XML缺少selectList SQL |
| UserServiceImpl | deleteById(id) | ❌ 无声明 | - | ❌ 不完整 | DAO接口缺少deleteById声明 |
```

**检查步骤**：

1. **提取ServiceImpl中的DAO调用**：
```java
// UserServiceImpl.java
@Override
public Long saveBO(CustomUserManagementBO bo) {
    CustomUserManagementEntity entity = PojoUtil.copy(bo, CustomUserManagementEntity.class);
    customUserManagementDAO.insert(entity); // ← 提取 insert
    return entity.getId();
}

@Override
public CustomUserManagementBO getByIdBO(Long id) {
    CustomUserManagementEntity entity = customUserManagementDAO.selectById(id); // ← 提取 selectById
    return PojoUtil.copy(entity, CustomUserManagementBO.class);
}
```

2. **验证DAO接口声明**：
```java
// UserDAO.java
public interface CustomUserManagementDAO extends BaseMapper<CustomUserManagementEntity> {
    // insert 继承自 BaseMapper ✅
    // selectById 继承自 BaseMapper ✅
    
    // ❌ 缺少 selectList 声明
    // ❌ 缺少 deleteById 声明
}
```

3. **验证Mapper XML实现**：
```xml
<!-- UserDAO.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.supcon.nebule.usermanagement.custom.dao.CustomUserManagementDAO">
    
    <!-- insert 继承自 BaseMapper，无需编写 ✅ -->
    <!-- selectById 继承自 BaseMapper，无需编写 ✅ -->
    
    <!-- ❌ 缺少 selectList 的 SQL -->
    <!-- ❌ 缺少 deleteById 的 SQL -->
    
</mapper>
```

#### 7.3 完整调用链路追踪

**链路示例**：

```markdown
### 完整调用链路示例：保存用户功能

Controller → Service → DAO → Mapper XML

1. **Controller层**：UserController.save()
   ```java
   @PostMapping("/save")
   public Result<Long> save(@RequestBody CustomUserManagementDTO dto) {
       CustomUserManagementBO bo = PojoUtil.copy(dto, CustomUserManagementBO.class);
       return Result.success(customUserManagementService.saveBO(bo)); // 调用 saveBO
   }
   ```

2. **Service接口**：UserService.saveBO()
   ```java
   Long saveBO(CustomUserManagementBO bo); // ✅ 有声明
   ```

3. **Service实现**：UserServiceImpl.saveBO()
   ```java
   @Override
   public Long saveBO(CustomUserManagementBO bo) {
       CustomUserManagementEntity entity = PojoUtil.copy(bo, CustomUserManagementEntity.class);
       customUserManagementDAO.insert(entity); // 调用 insert
       return entity.getId();
   }
   ```

4. **DAO接口**：UserDAO.insert()
   ```java
   // 继承自 BaseMapper<CustomUserManagementEntity>
   int insert(T entity); // ✅ 有声明（继承）
   ```

5. **Mapper XML**：UserDAO.xml
   ```xml
   <!-- insert 由 MyBatis-Plus 自动实现，无需编写 ✅ -->
   ```

**链路完整性**：✅ 完整
```

#### 7.4 调用链路问题汇总

```markdown
### 调用链路问题汇总

| 序号 | 功能 | 问题位置 | 问题描述 | 严重程度 | 修复建议 |
|------|------|---------|---------|---------|---------|
| 1 | 查询列表 | ServiceImpl | UserServiceImpl缺少listBO方法实现 | 高 | 添加listBO实现 |
| 2 | 删除用户 | Service接口 | Service接口缺少delete方法声明 | 高 | 添加delete声明 |
| 3 | 查询列表 | Mapper XML | Mapper XML缺少selectList的SQL | 高 | 编写selectList SQL |
| 4 | 删除用户 | DAO接口 | DAO接口缺少deleteById方法声明 | 高 | 添加deleteById声明 |
| 5 | 批量删除 | Controller | Controller调用了batchDelete但Service未实现 | 中 | 补充完整链路 |

**问题统计**：
- 高严重程度：X个（导致编译错误或运行时异常）
- 中严重程度：X个（功能不完整）
- 低严重程度：X个（代码规范问题）
- 总计：X个
```

### 步骤8：生成检查报告

**输出位置**：`design-docs/testing-validation/`

**报告文件名**：`{功能名称}-后端代码实现检查报告.md`

**报告模板**：

```markdown
# {功能名称} - 后端代码实现检查报告

## 基本信息

- **模块名称**：{功能模块名称}
- **moduleCode**：{模块编码}
- **acronym**：{模块缩略码}
- **检查时间**：{YYYY-MM-DD HH:mm:ss}
- **检查人员**：{检查人员}

## 检查概述

### 实现符合性统计

| 检查项 | 总数 | 符合 | 不符合 | 符合率 |
|--------|------|------|--------|-------|
| 类命名规范 | X | X | X | X% |
| 文件位置 | X | X | X | X% |
| 注解使用 | X | X | X | X% |
| 数据库初始化 | X | X | X | X% |
| 模块注册初始化 | X | X | X | X% |
| 配置初始化 | X | X | X | X% |
| API接口完整性 | X | X | X | X% |
| API规范性 | X | X | X | X% |
| **Controller→Service调用链路** | **X** | **X** | **X** | **X%** |
| **Service→DAO调用链路** | **X** | **X** | **X** | **X%** |
| **Mapper SQL实现** | **X** | **X** | **X** | **X%** |
| **总计** | **X** | **X** | **X** | **X%** |

## 详细检查结果

### 类命名规范检查

（插入步骤2的检查表格）

### 文件位置检查

（插入步骤3的检查表格）

### 注解使用检查

（插入步骤4的检查表格）

### 初始化语句检查

（插入步骤5的检查表格）

### API实现检查

（插入步骤6的检查表格）

### 调用链路完整性检查

（插入步骤7的检查表格）

#### Controller → Service 调用链路

（插入步骤7.1的检查表格）

#### Service → DAO 调用链路

（插入步骤7.2的检查表格）

#### 完整调用链路示例

（插入步骤7.3的链路示例）

#### 调用链路问题汇总

（插入步骤7.4的问题汇总）

## 发现的问题

| 序号 | 问题类型 | 问题描述 | 严重程度 | 修复建议 | 状态 |
|------|---------|---------|---------|---------|------|
| 1 | 命名不规范 | RoleController 应为 CustomRoleManagementController | 高 | 重命名Controller | 待修复 |
| 2 | 注解缺失 | CustomRoleManagementEntity 缺少 @EnCodeField | 高 | 添加注解 | 待修复 |
| 3 | 初始化不完整 | 缺少工作流注册 | 中 | 补充工作流注册 | 待修复 |
| 4 | API缺失 | 更新用户接口未实现 | 高 | 实现更新接口 | 待修复 |
| 5 | **调用链路不完整** | UserServiceImpl缺少listBO方法实现 | 高 | 添加listBO实现 | 待修复 |
| 6 | **调用链路不完整** | Mapper XML缺少selectList的SQL | 高 | 编写selectList SQL | 待修复 |

**问题统计**：
- 高严重程度：X个
- 中严重程度：X个
- 低严重程度：X个
- 总计：X个

## 检查结论

### 整体评估

- **类命名规范**：✅ 通过 / ❌ 不通过
- **文件位置**：✅ 通过 / ❌ 不通过
- **注解使用**：✅ 通过 / ❌ 不通过
- **初始化语句**：✅ 通过 / ❌ 不通过
- **API实现**：✅ 通过 / ❌ 不通过
- **Controller→Service调用链路**：✅ 完整 / ❌ 不完整
- **Service→DAO调用链路**：✅ 完整 / ❌ 不完整
- **Mapper SQL实现**：✅ 完整 / ❌ 不完整

### 修复建议

**P0（必须修复）**：
1. {问题描述}

**P1（建议修复）**：
1. {问题描述}

**P2（可选修复）**：
1. {问题描述}

### 检查结论

✅ **后端代码实现检查通过**：模块开发完成

或

❌ **后端代码实现检查不通过**：需要修复问题后重新检查
```

## 检查执行规范

### 检查前准备

1. **清理上下文**：移除与后端代码检查无关的历史对话
2. **加载指南**：读取本检查指南
3. **读取设计文档**：读取后端架构设计、API设计、数据架构文档
4. **确认moduleCode和acronym**：从 dev-task.md 或 metadata 文件读取

### 检查完成后

1. **生成报告**：将检查报告保存到 `design-docs/testing-validation/{功能名称}-后端代码实现检查报告.md`
2. **更新进度文件**：将检查结论写入 dev-task.md
3. **Git提交**：提交检查报告
4. **上下文清理**：仅保留关键结论到进度文件
5. **最终确认**：如果所有检查通过，标记模块开发完成

## 相关文档

- [后端架构设计文档](design-docs/backend-architecture/{功能名称}后端架构设计.md)
- [API设计文档](design-docs/backend-architecture/{功能名称}-API设计文档.md)
- [后端代码实现指南](BACKEND-IMPL.md)
- [后端代码生成流程控制](BACKEND-CODE-GEN.md)
- [上下文清理规范](CONTEXT-CLEANING.md)
