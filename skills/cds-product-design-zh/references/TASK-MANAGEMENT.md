# 任务管理指南

## 进度文件格式

### dsg-session.md 格式
```markdown
# 设计会话管理

## 项目信息
- 产品名称: [产品名称]
- 创建时间: [时间]
- 最后更新: [时间]

## 全局设计状态
- 状态: [未开始/进行中/待检查/已完成]
- 当前步骤: [步骤名称]
- 当前步骤状态: [未开始/进行中/已完成]

## 模块设计状态
- current_module: [模块名称/null]
- 模块状态: [未开始/进行中/待检查/已完成]

## 模块列表
| 模块名称 | 优先级 | 状态 | 开始时间 | 完成时间 |
|---------|--------|------|----------|----------|
| [模块1] | 高 | 已完成 | [时间] | [时间] |
| [模块2] | 中 | 进行中 | [时间] | - |
| [模块3] | 低 | 未开始 | - | - |

## 执行历史
- [时间] [步骤] [状态] [备注]
- [时间] [步骤] [状态] [备注]
```

### dsg-task.md 格式
```markdown
# 模块设计任务管理

## 模块信息
- 模块名称: [模块名称]
- 创建时间: [时间]
- 最后更新: [时间]

## 当前步骤
- 步骤名称: [步骤名称]
- 步骤状态: [未开始/进行中/已完成]
- 开始时间: [时间]

## 步骤执行历史
| 步骤 | 状态 | 开始时间 | 完成时间 | 交付物 | 备注 |
|------|------|----------|----------|--------|------|
| [步骤1] | 已完成 | [时间] | [时间] | [文件列表] | [备注] |
| [步骤2] | 进行中 | [时间] | - | - | [备注] |
```

## 任务初始化流程

### 步骤0.1：创建会话文件
1. 检查项目根目录是否存在 dsg-session.md
2. 如果不存在，创建新的 dsg-session.md
3. 初始化项目基本信息
4. 设置全局设计状态为"未开始"

### 步骤0.2：创建设计文件夹结构

> **详细目录结构规范请参考**: [设计流程详细指南 - 产出物设计资料文件夹分类规则](DESIGN-FLOW.md#产出物设计资料文件夹分类规则)

按照标准设计文档目录结构创建 `design-docs/` 文件夹：

```
design-docs/
├── requirements/                    # 需求相关文档
│   ├── REQ-CONFIRM.md              # 需求概要确认书
│   ├── REQ-DETAIL.md               # 需求详细设计文档
│   └── requirements-spec.md        # 需求规格说明书
│
├── data-architecture/              # 数据架构设计
│   ├── DATA.md                     # 数据架构设计文档
│   ├── entity-relationship-diagram.png  # 实体关系图
│   ├── database-schema.sql          # 数据库建表脚本
│   └── BACKEND-TOOLS-DATABASE.md  # 数据库规范参考
│
├── business-process/               # 业务流程设计
│   ├── PROCESS.md                  # 业务流程设计文档
│   ├── process-flowchart.png       # 业务流程图
│   ├── interaction-sequence.png    # 交互流程图
│   └── BACKEND-TOOLS-*.md         # 流程相关工具指南
│
├── ui-design/                      # UI设计稿和组件
│   ├── UI-DESIGN.md                # UI设计详细文档
│   ├── {功能名称}UI稿.pen          # Figma/Sketch设计文件
│   ├── design-system/             # 设计系统组件
│   │   ├── .lib.pen               # 自定义UI库
│   │   └── design-libraries-readme.md  # 设计规范说明
│   └── assets/                    # 设计资源
│       ├── icons/                 # 图标素材
│       └── images/                # 图片素材
│
├── frontend-architecture/          # 前端架构设计
│   ├── FRONTEND-ARCH.md           # 前端架构设计文档
│   ├── {功能名称}前端架构设计.md # 具体页面架构
│   ├── component-design.md        # 组件设计文档
│   └── assets/                    # 前端资源
│       ├── components/            # 可复用组件
│       └── styles/                # 样式文件
│
├── backend-architecture/           # 后端架构设计
│   ├── BACKEND-ARCH.md            # 后端架构设计文档
│   ├── {功能名称}后端架构设计.md # 具体页面后端设计
│   ├── {功能名称}-API设计文档.md # API接口设计文档
│   ├── database-design/           # 数据库设计
│   │   ├── table-structure.xlsx   # 表结构设计
│   │   └── sql-scripts/           # SQL脚本目录
│   ├── api-specification/         # API规范
│   │   ├── openapi.yaml           # OpenAPI 3.0规范
│   │   └── curl-examples.md       # cURL调用示例
│   └── configuration/             # 配置管理
│       ├── application.yml        # 应用配置
│       ├── module-registration.md # 模块注册配置
│       └── BACKEND-TOOLS-*.md     # 后端工具指南
│
├── testing-validation/             # 测试验证
│   ├── DESIGN-REVIEW.md           # 设计验收检查
│   ├── test-cases/                # 测试用例
│   │   ├── unit-tests/            # 单元测试
│   │   ├── integration-tests/     # 集成测试
│   │   └── e2e-tests/             # 端到端测试
│   └── validation-report.md       # 验证报告
│
└── references/                    # 参考资料
    ├── user-research/             # 用户调研资料
    ├── competitive-analysis/      # 竞品分析
    └── technical-references/      # 技术参考资料
```

**注意**：
- 所有设计产物统一保存到 `design-docs/` 目录下
- 与 `dsg-session.md` 保持同级（都在项目根目录）
- 文件命名遵循 `{功能名称}-{类型}.{扩展名}` 格式

### 步骤0.3：用户确认
- 展示创建的项目结构
- 确认项目名称和基本信息
- 获取用户确认后继续

## 模块初始化流程

### 步骤0.1：创建模块目录
1. 在 `design-docs/` 目录下创建对应的功能设计子目录
2. 根据需要创建 requirements、ui-design、frontend-architecture、backend-architecture 等子目录
3. 初始化 dsg-task.md 文件

### 步骤0.2：设置模块状态
1. 更新 dsg-session.md 中的 current_module
2. 设置模块状态为"进行中"
3. 记录模块开始时间

### 步骤0.3：加载前置产物
1. 检查全局设计产物是否完整
2. 加载功能架构设计文档
3. 确认模块功能清单

## 状态更新规范

### 步骤完成时
1. 更新 dsg-task.md 中的步骤状态
2. 记录完成时间和交付物
3. 更新 dsg-session.md 的最后更新时间

### 模块完成时
1. 确认所有步骤都已完成
2. 更新模块状态为"已完成"
3. 设置 current_module 为 null
4. 询问用户是否继续下一个模块

## 异常处理

### 文件丢失处理
- 如果 dsg-session.md 丢失，重新开始任务初始化
- 如果 dsg-task.md 丢失，重新创建并恢复状态
- 如果交付物丢失，提示用户重新生成

### 状态不一致处理
- 检查全局设计和模块设计状态的一致性
- 如果状态不一致，提示用户确认正确的状态
- 提供状态修复选项