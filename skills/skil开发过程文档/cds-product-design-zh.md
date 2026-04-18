# CDS产品设计技能完整指南

> **版本**: 1.0.0  
> **更新时间**: 2026-04-14  
> **适用技能**: cds-product-design-zh

## 目录

- [概述](#概述)
- [核心能力](#核心能力)
- [完整改进总结](#完整改进总结)
- [UI库配置](#UI库配置)
- [使用流程](#使用流程)
- [技能架构](#技能架构)
- [后续完善建议](#后续完善建议)

## 概述

CDS产品设计技能是一个专注于页面功能级别设计的解决方案，基于VibeLockDsn的设计方法论构建，专门为CDS（Content Delivery System）平台优化。该技能提供了从需求概要文档到页面UI稿、前端架构设计、后端架构设计的完整流程支持。

### 🎯 核心定位

- **页面功能级别设计**：专注于单个页面功能的设计，而非多模块系统设计
- **需求驱动**：必须基于用户提供的需求概要文档开始设计
- **前后端分离设计**：前端架构设计和后端架构设计分别输出，独立文档
- **UI稿自动生成**：基于CDS UI库自动生成页面UI稿
- **质量保证**：集成测试验收机制确保设计质量

## 核心能力

### 1. 需求驱动设计 📊
- ✅ 必须基于用户提供的需求概要文档开始设计
- ✅ 从需求概要到页面需求详细设计的完整流程
- ✅ 页面功能级别的专门分析
- ✅ 标准化的需求文档输出

### 2. 页面功能级别设计 🎯
- ✅ 专注于单个页面功能的设计，而非多模块系统设计
- ✅ 页面业务流程图和交互逻辑说明
- ✅ 页面数据架构设计和实体字段表
- ✅ 页面功能清单的规范化管理

### 3. UI稿自动生成 ✏️
- ✅ 基于CDS UI库自动生成页面UI稿
- ✅ 输出命名为"xxxx页面UI稿.pen"的标准格式
- ✅ 标准化设计规范遵循
- ✅ UI设计规范文档生成

### 4. 前后端分离架构设计 🏗️
- ✅ 前端架构设计：{页面名称}前端架构设计.md、组件设计文档
- ✅ 后端架构设计：{页面名称}后端架构设计.md、API设计文档
- ✅ 前后端架构资料分离输出，独立文档
- ✅ 专门的前端架构师和后端架构师能力支持

### 5. 设计规范支持 🎨
- ✅ 完整的CDS设计规范体系
- ✅ 专业的蓝白配色方案
- ✅ 标准化的组件库和样式指南
- ✅ 平台兼容性保证

### 6. UI库自动支持 🔧
- ✅ 开箱即用的默认UI库
- ✅ 12个核心组件覆盖所有场景
- ✅ 自动检测和复制机制
- ✅ 标准化组件使用

### 7. 测试验收集成 ✅
- ✅ 设计阶段的多层次检查
- ✅ 设计验收检查流程
- ✅ 问题闭环处理机制
- ✅ 完整的验收流程

## 完整改进总结

### 1. 参考文档完善

#### **REQ-ANALYSIS.md** - CDS需求分析指南
- 从需求概设到需求详细设计的完整流程
- 专门的CDS特定需求分析章节
- 详细的输出格式和检查清单
- 包含CDS平台集成需求的分析

#### **DESIGN-LIB.md** - CDS设计组件库准备指南
- 参考VibeLockDsn的设计库准备流程
- 专门的CDS设计规范参考
- CDS组件库构建规范
- 完整的全局设计检查验收流程

#### **DESIGN.md** - CDS模块原型设计指南
- 基于VibeLockDsn的原型设计流程
- 专门的CDS原型设计规范
- CDS原型调整同步流程
- 完整的模块设计检查验收流程

### 2. 核心改进特色

#### **完整的CDS适配**
- 所有流程都考虑了CDS平台的特殊性
- 专门的CDS设计规范和组件库
- CDS平台兼容性检查
- CDS集成需求考虑

#### **严格的质量控制**
- 设计阶段的多层次检查
- 原型绘制的实时监督
- 问题闭环处理机制
- 完整的验收流程

#### **工程化的工作流**
- 清晰的状态管理
- 完整的进度跟踪
- 标准化的产出物
- 可追溯的执行历史

#### **多角色协作**
- 设计与测试的明确分工
- 清晰的责任边界
- 标准化的协作流程
- 完整的沟通机制

## UI库配置

### 自动使用机制

#### 文件位置映射
```
技能内部位置                              项目根目录位置
──────────────────────────────────────────────────────────────
references/ui/cds-components.lib.pen   →   .lib.pen
references/ui/cds-design-libraries-readme.md → design-libraries-readme.md
```

#### 自动使用流程
1. 用户启动cds-product-design-zh技能
2. 系统检查项目根目录是否存在UI库文件
3. 如果不存在，自动复制默认UI库文件到项目根目录
4. 用户可以直接使用CDS标准组件进行原型设计

### 核心组件清单

#### 12个核心组件（⭐ 必需）
1. **TopNavigation** - 顶部导航
2. **Card** - 卡片容器
3. **FormInput** - 表单输入框
4. **FormSelect** - 下拉选择器
5. **PrimaryButton** - 主要按钮
6. **SecondaryButton** - 次要按钮
7. **DataTable** - 数据表格
8. **Pagination** - 分页器
9. **StatusTag** - 状态标签
10. **Modal** - 对话框
11. **SearchInput** - 搜索输入框
12. **ConditionSelect** - 条件选择器

### 设计规范要点

#### 颜色体系
- **主色**：#1677FF（CDS蓝色）
- **成功**：#52C41A
- **警告**：#FAAD14
- **错误**：#FF4D4F
- **主要文本**：#333333
- **次要文本**：#666666

#### 尺寸规范
- **按钮高度**：32px
- **输入框高度**：32px
- **表格行高**：44px
- **标准圆角**：4px
- **页面边距**：24px

### 常见页面模板

#### 列表页
```
TopNavigation + Card + SearchInput + DataTable + Pagination + StatusTag
```

#### 详情页
```
Breadcrumb + Card + StatisticCard + Tabs + ApprovalLog
```

#### 表单页
```
Card + FormInput + FormSelect + DatePicker + PrimaryButton + SecondaryButton
```

#### 仪表盘
```
TopNavigation + Sidebar + StatisticCard + Progress + DataTable + Tag
```

## 使用流程

### 页面功能设计流程

#### 1. 需求概要文档确认阶段
```
用户必须提供需求概要文档
↓
cds-product-design-zh → REQ-CONFIRM.md
↓
需求概要确认书、设计任务初始化
```

#### 2. 需求详细设计阶段
```
cds-product-design-zh → REQ-DETAIL.md
↓
页面需求详细设计文档、功能清单表
```

#### 3. 数据架构设计阶段
```
cds-product-design-zh → DATA.md + BACKEND-TOOLS-DATABASE.md
↓
实体字段表、数据关系图
```

#### 4. 业务流程设计阶段
```
cds-product-design-zh → PROCESS.md + BACKEND-TOOLS-*.md
↓
页面业务流程图、交互逻辑说明
```

#### 5. UI稿生成阶段
```
cds-product-design-zh → UI-DESIGN.md + DESIGN-LIB.md
↓
检查项目根目录是否存在UI库文件
如果不存在则从 references/ui/ 拷贝默认UI库
↓
{页面名称}UI稿.pen、UI设计规范.md
```

#### 6. 前端架构设计阶段
```
cds-product-design-zh → FRONTEND-ARCH.md + FRONTEND-TOOLS-PROJECT-STRUCT.md
↓
{页面名称}前端架构设计.md、组件设计文档
```

#### 7. 后端架构设计阶段
```
cds-product-design-zh → BACKEND-ARCH.md + BACKEND-TOOLS-*.md
↓
{页面名称}后端架构设计.md、API设计文档
```

#### 8. 设计验收检查阶段
```
cds-product-design-zh → DESIGN-REVIEW.md
↓
设计检查报告.md
↓
cds-product-test-zh → 设计检查验收
```

### 后续开发流程

#### 9. 开发实现阶段
```
cds-product-develop-zh
↓
基于UI稿和技术方案的代码生成
```

#### 10. 测试验证阶段
```
cds-product-test-zh → 编译启动测试
↓
cds-product-test-zh → 功能测试
```

#### 11. 部署运维阶段
```
cds-product-devops-zh
↓
自动化部署
↓
企微通知
```

### 关键能力增强

#### 1. 从概要到详细的无缝衔接
- 需求概设可以直接转化为详细设计
- 自动化的需求分析和整理
- 标准化的文档输出格式

#### 2. CDS设计规范的完整支持
- 专门的CDS组件库构建
- CDS设计规范的严格遵循
- CDS平台兼容性保证

#### 3. 测试验收的全面覆盖
- 设计阶段的预防性检查
- 开发阶段的功能验证
- 部署阶段的集成测试

#### 4. 质量保证的完整闭环
- 问题发现到修复的完整流程
- 多层次的质量检查点
- 完整的验收标准

#### 5. 设计文档统一管理
- 自动创建 design-docs 文件夹
- 所有设计产物统一保存到 design-docs
- 与 dsg-session.md 保持同级目录结构
- 便于设计文档的版本管理和追溯

#### 6. 前后端工具链集成
- 前端架构设计参考 FRONTEND-TOOLS-PROJECT-STRUCT.md
- 后端架构设计参考 BACKEND-TOOLS-*.md 系列文档
- 数据架构设计参考 BACKEND-TOOLS-DATABASE.md
- 业务流程设计参考 BACKEND-TOOLS-*.md 系列文档

#### 7. UI库自动拷贝机制
- 在UI稿生成阶段自动检查项目根目录是否存在 .lib.pen 和 design-libraries-readme.md
- 如果不存在，自动从 references/ui/ 目录拷贝 cds-components.lib.pen 和 cds-design-libraries-readme.md
- 确保新项目能快速使用CDS标准UI库进行原型设计
- 支持自定义UI库的优先级（项目本地UI库 > 默认UI库）

## 技能架构

### 文件结构
```
cds-product-design-zh/
├── SKILL.md                              # 技能主文档
└── references/
    ├── TASK-MANAGEMENT.md                # 任务进度管理指南
    ├── REQ-ANALYSIS.md                   # 需求详细设计整理指南
    ├── REQ-CONFIRM.md                    # 需求概要文档确认指南
    ├── REQ-DETAIL.md                     # 页面需求详细设计指南
    ├── DESIGN-LIB.md                     # UI库适配与组件准备指南
    ├── DATA.md                           # 数据架构设计指南
    ├── PROCESS.md                        # 业务流程设计指南
    ├── UI-DESIGN.md                      # UI稿生成指南
    ├── FRONTEND-ARCH.md                  # 前端架构设计指南
    ├── BACKEND-ARCH.md                   # 后端架构设计指南
    ├── DESIGN-REVIEW.md                  # 设计验收检查指南
    └── ui/
        ├── cds-components.lib.pen        # CDS组件库文件
        ├── cds-design-libraries-readme.md # CDS设计规范
        └── README.md                     # UI库使用说明
```

### 外部技能协作

| 场景 | 调用Skill | 说明 |
|------|----------|------|
| 设计检查验收 | cds-product-test-zh | 设计完成后调用 |
| 技术方案评审 | cds-product-test-zh | 技术方案完成后调用 |

## 优势总结

### 1. 开箱即用 🚀
- 新项目无需额外配置
- 自动获得完整的UI库支持
- 减少设计准备时间

### 2. 标准统一 📋
- 所有项目使用相同的UI库
- 确保设计的一致性
- 降低学习成本

### 3. 质量保障 ⭐
- 专业的企业级设计
- 完整的规范文档
- 经过验证的组件设计

### 4. 高效开发 ⚡
- 丰富的预定义组件
- 清晰的组合模式
- 快速的原型制作

### 5. CDS平台专用 🎯
- 专门为CDS平台优化
- 符合CDS视觉规范
- 支持CDS业务场景