# 后端代码生成流程控制

## 概述

本文档定义后端代码生成的完整流程控制机制，支持断点续传和状态跟踪。

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
- 支持断点续传和状态跟踪

## 启动恢复流程（最高优先级）

每次进入时**必须首先执行**以下流程：

### 步骤0：读取跟踪文件
```
IF 项目根目录存在 backend-code-gen.md THEN
    读取 backend-code-gen.md
    判断当前阶段状态
    执行对应的恢复操作
ELSE
    创建新的 backend-code-gen.md
    进入数据库类型选择步骤
END IF
```

### 恢复操作决策表
| backend-code-gen.md状态 | 当前阶段 | 恢复操作 |
|------------------------|---------|---------|  
| 文件不存在 | - | 创建文件，确认可选功能，从阶段1开始 |
| 阶段1状态为“未开始” | 阶段1 | 从阶段1：VO、BO和Entity生成开始 |
| 阶段1状态为“进行中” | 阶段1 | 继续阶段1的生成工作 |
| 阶段1状态为“已完成”，阶段2“未开始” | 阶段2 | 从阶段2：Controller生成开始 |
| 阶段2状态为“进行中” | 阶段2 | 继续阶段2的生成工作 |
| 阶段2状态为“已完成”，阶段3“未开始” | 阶段3 | 从阶段3：Service和ServiceImpl生成开始 |
| 阶段3状态为“进行中” | 阶段3 | 继续阶段3的生成工作 |
| 阶段3状态为“已完成”，阶段4“未开始” | 阶段4 | 从阶段4：Dao和Mapper生成开始 |
| 阶段4状态为“进行中” | 阶段4 | 继续阶段4的生成工作 |
| 阶段4状态为“已完成”，阶段5“未开始”且已选择 | 阶段5 | 从阶段5：初始化SQL生成开始 |
| 阶段4状态为“已完成”，阶段5“未选择” | 阶段6 | 跳到阶段6：Feign API接口生成（如已选择） |
| 阶段5状态为“进行中” | 阶段5 | 继续阶段5的生成工作 |
| 阶段5状态为“已完成”，阶段6“未开始”且已选择 | 阶段6 | 从阶段6：Feign API接口生成开始 |
| 阶段6状态为“进行中” | 阶段6 | 继续阶段6的生成工作 |
| 阶段6状态为“已完成”，阶段7“未开始”且已选择 | 阶段7 | 从阶段7：启动类代码生成开始 |
| 阶段7状态为“进行中” | 阶段7 | 继续阶段7的生成工作 |
| 阶段7状态为“已完成”，阶段8“未开始”且已选择 | 阶段8 | 从阶段8：菜单注册配置生成开始 |
| 阶段8状态为“进行中” | 阶段8 | 继续阶段8的生成工作 |
| 阶段8状态为“已完成”，阶段9“未开始”且已选择 | 阶段9 | 从阶段9：依赖注入配置生成开始 |
| 阶段9状态为“进行中” | 阶段9 | 继续阶段9的生成工作 |
| 所有已选阶段已完成 | - | 提示用户后端代码生成已完成 |

## 执行流程

### 前置步骤：可选功能确认

在开始后端代码生成前，必须向用户确认以下可选功能（可多选）：

```markdown
### 请选择需要生成的可选功能（可多选）：

- [ ] **数据库类型适配**（阶段5：初始化SQL生成，必须选择一种数据库类型）
  - Oracle（默认）
  - SQL Server
  - MariaDB
  - DM（达梦数据库）

- [ ] **Feign API接口**（阶段6：跨服务调用接口）

- [ ] **启动类代码**（阶段7：Bootstrap启动类）

- [ ] **菜单注册配置**（阶段8：菜单和工作流注册）

- [ ] **依赖注入配置**（阶段9：PubServiceConfiguration配置类）

请勾选需要生成的功能，或说明您的选择。
```

**选择规则**：
1. 用户可以选择任意一个或多个功能
2. 如果选择“数据库类型适配”，必须进一步选择具体的数据库类型
3. 选择结果记录到 `backend-code-gen.md` 中
4. 只生成用户选择的功能，未选择的跳过

### 阶段1：VO、BO和Entity生成

**详细指南**: [BACKEND-ENTITY-BO-VO-GENERATION.md](BACKEND-ENTITY-BO-VO-GENERATION.md)

**执行步骤**：
1. 确认moduleCode和acronym
2. 生成Entity类（原生MyBatis-Plus实现）
3. 生成BO类
4. 生成VO类
5. 更新backend-code-gen.md：标记阶段1为"已完成"

**核心交付物**：
- `Custom{ModuleCode}{ModelCode}Entity.java`
- `Custom{ModuleCode}{ModelCode}BO.java`
- `Custom{ModuleCode}{ModelCode}VO.java`

### 阶段2：Controller生成

**详细指南**: [BACKEND-CONTROLLER-GENERATION.md](BACKEND-CONTROLLER-GENERATION.md)

**执行步骤**：
1. 确认API设计文档
2. 生成Controller类
3. 实现对象转换（手动setter方式）
4. 更新backend-code-gen.md：标记阶段2为"已完成"

**核心交付物**：
- `Custom{ModuleCode}{ModelCode}Controller.java`

### 阶段3：Service和ServiceImpl生成

**详细指南**: [BACKEND-SERVICE-GENERATION.md](BACKEND-SERVICE-GENERATION.md)

**执行步骤**：
1. 生成Service接口（继承IService）
2. 生成ServiceImpl实现类（继承ServiceImpl）
3. 实现业务方法（saveBO、info、delete等）
4. 实现对象转换（手动setter方式）
5. 更新backend-code-gen.md：标记阶段3为"已完成"

**核心交付物**：
- `Custom{ModuleCode}{ModelCode}Service.java`
- `Custom{ModuleCode}{ModelCode}ServiceImpl.java`

### 阶段4：Dao和Mapper生成

**详细指南**: [BACKEND-DAO-MAPPER-GENERATION.md](BACKEND-DAO-MAPPER-GENERATION.md)

**执行步骤**：
1. 生成Dao接口（继承BaseMapper）
2. 生成Mapper XML文件（如需要）
3. 根据数据库类型调整SQL语法
4. 更新backend-code-gen.md：标记阶段4为"已完成"

**核心交付物**：
- `Custom{ModuleCode}{ModelCode}Dao.java`
- `Custom{ModuleCode}{ModelCode}Mapper.xml`（可选）

### 阶段5：初始化SQL生成（可选）

**详细指南**: [BACKEND-INIT-SQL-GENERATION.md](BACKEND-INIT-SQL-GENERATION.md)

**前置条件**: 用户选择了“数据库类型适配”

**执行步骤**：
1. 根据用户选择的数据库类型（Oracle/SQL Server/MariaDB/DM）
2. 根据Entity生成建表SQL
3. 根据数据库类型调整SQL语法
4. 生成初始化数据SQL（如需要）
5. 更新backend-code-gen.md：标记阶段5为“已完成”

**核心交付物**：
- `init_{moduleCode}_{modelCode}.sql`

### 阶段6：Feign API接口生成（可选）

**详细指南**: [BACKEND-FEIGN-API-GENERATION.md](BACKEND-FEIGN-API-GENERATION.md)

**前置条件**: 用户选择了“Feign API接口”

**执行步骤**：
1. 生成Feign API接口
2. 生成DTO对象
3. 配置@EnableFeignClients包扫描
4. 更新backend-code-gen.md：标记阶段6为“已完成”

**核心交付物**：
- `Custom{ModuleCode}{ModelCode}Api.java`
- `Custom{ModuleCode}{ModelCode}DTO.java`

### 阶段7：启动类代码生成（可选）

**详细指南**: [../../cds-product-design-zh/references/BACKEND-TOOLS-BOOTSTRAP.md](../../cds-product-design-zh/references/BACKEND-TOOLS-BOOTSTRAP.md)

**前置条件**: 用户选择了“启动类代码”

**执行步骤**：
1. 生成Bootstrap启动类
2. 配置健康检查接口
3. 配置bootstrap.properties
4. 更新backend-code-gen.md：标记阶段7为“已完成”

**核心交付物**：
- `Bootstrap.java`
- `bootstrap.properties`

### 阶段8：菜单注册配置生成（可选）

**详细指南**: [../../cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md](../../cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md)

**前置条件**: 用户选择了“菜单注册配置”

**执行步骤**：
1. 生成ModuleRegisterInitialize
2. 生成菜单注册SQL
3. 生成工作流注册配置（如需要）
4. 更新backend-code-gen.md：标记阶段8为“已完成”

**核心交付物**：
- `{ModuleCode}ModuleRegisterInitialize.java`
- `menu_init_{moduleCode}.sql`

### 阶段9：依赖注入配置生成（可选）

**详细指南**: [../../cds-product-design-zh/references/BACKEND-TOOLS-CONFIGURATION.md](../../cds-product-design-zh/references/BACKEND-TOOLS-CONFIGURATION.md)

**前置条件**: 用户选择了“依赖注入配置”

**执行步骤**：
1. 生成PubServiceConfiguration配置类
2. 配置@EnableFeignClients包扫描
3. 配置@MapperScan包扫描
4. 更新backend-code-gen.md：标记阶段9为“已完成”

**核心交付物**：
- `{ModuleCode}PubServiceConfiguration.java`

## 阶段执行规范

### 每个阶段执行前
1. **读取跟踪文件**：确认当前阶段和已完成的阶段
2. **加载指南**：读取对应的生成指南文件
3. **确认前置产物**：检查前置阶段的交付物是否存在
4. **确认关键标识**：确认moduleCode和acronym

### 每个阶段执行后
1. **更新跟踪文件**：立即更新backend-code-gen.md，记录：
   - 阶段状态（已完成）
   - 完成时间
   - 生成的文件列表
   - 关键决策
2. **更新进度文档**：将关键结论写入dev-task.md或dev-session.md
3. **执行上下文清理**：按照 [CONTEXT-CLEANING.md](CONTEXT-CLEANING.md) 规范清理对话历史
   - 保留关键结论到进度文件
   - 清理中间过程和调试信息
   - 压缩历史信息为执行摘要
4. **git提交**：完成代码后立即git提交
5. **用户确认**：展示生成的文件，获取用户确认后继续下一阶段

## 相关文档索引

| 文件 | 用途 |
|------|------|
| BACKEND-CODE-GEN-TRACKER.md | 任务跟踪文件模板 |
| BACKEND-ENTITY-BO-VO-GENERATION.md | Entity、BO、VO生成指南 |
| BACKEND-CONTROLLER-GENERATION.md | Controller生成指南 |
| BACKEND-SERVICE-GENERATION.md | Service和ServiceImpl生成指南 |
| BACKEND-DAO-MAPPER-GENERATION.md | Dao和Mapper生成指南 |
| BACKEND-INIT-SQL-GENERATION.md | 初始化SQL生成指南 |
| BACKEND-FEIGN-API-GENERATION.md | Feign API接口生成指南 |
| CONTEXT-CLEANING.md | 上下文清理规范 |

## 断点续传示例

### 场景1：阶段2完成后中断
```
1. 读取backend-code-gen.md
2. 发现阶段1和阶段2已完成，阶段3未开始
3. 直接从阶段3：Service和ServiceImpl生成开始
4. 继续执行后续阶段
```

### 场景2：阶段4进行中中断
```
1. 读取backend-code-gen.md
2. 发现阶段4状态为"进行中"
3. 检查已生成的Dao文件
4. 继续完成Dao和Mapper的生成
5. 完成后进入阶段5
```

## 注意事项

1. **必须实时更新跟踪文件**：每个阶段完成后立即更新
2. **数据库类型不可更改**：一旦选择，整个生成过程使用同一数据库类型
3. **阶段顺序固定**：必须按1→2→3→4→5的顺序执行
4. **对象转换规范**：所有阶段都必须使用手动setter方式，禁止使用工具类拷贝
