# 进度跟踪文件迁移指南

## 概述

本指南提供从旧的进度跟踪文件迁移到统一 `dev-session.md` 的详细步骤。

> 💡 **迁移目标**：将分散的 dev-task.md、frontend-code-gen-tracker.md、backend-code-gen-tracker.md 合并为统一的 dev-session.md

## 迁移前准备

### 1. 确认现有文件

首先检查项目中是否存在以下旧文件：

```bash
# 查找现有的进度跟踪文件
find . -name "dev-task.md" -o -name "*tracker.md" -o -name "dev-session.md"
```

### 2. 备份现有文件

```bash
# 创建备份目录
mkdir -p backups/tracker-files

# 备份现有文件
cp dev-task.md backups/tracker-files/ 2>/dev/null || true
cp frontend-code-gen-tracker.md backups/tracker-files/ 2>/dev/null || true
cp backend-code-gen-tracker.md backups/tracker-files/ 2>/dev/null || true
```

## 迁移步骤

### 步骤1：创建新的 dev-session.md

如果项目根目录不存在 dev-session.md，创建新的文件：

```bash
# 从模板创建新的 dev-session.md
cp references/DEV-SESSION-FORMAT.md dev-session.md
# 或者手动创建，参考格式规范
```

### 步骤2：迁移 dev-task.md 信息

如果存在 dev-task.md，将其信息迁移到 dev-session.md：

#### 提取模块信息

```markdown
# 从 dev-task.md 提取
- 功能名称 → 会话信息中的"功能名称"
- moduleCode → 会话信息中的"moduleCode"
- acronym → 会话信息中的"acronym"
- 目录信息 → 代码目录部分
```

#### 示例迁移

**dev-task.md 原内容**：
```markdown
# 开发任务
- 功能：用户管理
- moduleCode：usermgmt
- acronym：USR
- 前端目录：usermgmt-frontend/
- 后端目录：usermgmt/usermgmt-custom/
```

**dev-session.md 新内容**：
```markdown
## 会话信息
- **功能名称**：用户管理
- **moduleCode**：usermgmt
- **acronym**：USR
- **会话开始时间**：2024-01-15 10:30:00
- **最后更新时间**：2024-01-15 10:30:00

## 代码目录
- **前端目录**：`usermgmt-frontend/`
- **后端目录**：`usermgmt/usermgmt-custom/`
```

### 步骤3：迁移 frontend-code-gen-tracker.md 信息

如果存在 frontend-code-gen-tracker.md，将其信息迁移到前端开发进度：

#### 迁移阶段状态

```markdown
# 从 frontend-code-gen-tracker.md 提取
- 阶段状态 → 前端开发进度的"阶段状态"表格
- 当前阶段 → 前端开发进度的"当前状态"
- 文件列表 → 关键文件路径中的"已生成文件"
```

#### 示例迁移

**frontend-code-gen-tracker.md 原内容**：
```markdown
## 当前阶段
阶段2：API服务层生成

## 阶段状态
- 阶段1：✅ 已完成
- 阶段2：🔄 进行中
- 阶段3：⬜ 未开始

## 已生成文件
- src/types/user.ts
- src/utils/index.ts
```

**dev-session.md 新内容**：
```markdown
## 前端开发进度（选项A）

### 阶段状态
| 阶段 | 名称 | 状态 | 完成时间 | 生成文件数 |
|------|------|------|---------|-----------|
| 1 | 类型定义和工具函数 | ✅ 已完成 | 2024-01-15 11:00:00 | 2 |
| 2 | API服务层 | 🔄 进行中 | - | - |
| 3 | 业务组件 | ⬜ 未开始 | - | - |
| 4 | 页面组件 | ⬜ 未开始 | - | - |

### 当前状态
- **已完成阶段**：阶段1
- **下一阶段**：阶段2 - API服务层
- **需要读取的指南**：`FRONTEND-API-SERVICE-GENERATION.md`

### 关键文件路径
- **已生成文件**：
  - 类型定义：2个文件
  - API服务：0个文件
  - 组件：0个文件
```

### 步骤4：迁移 backend-code-gen-tracker.md 信息

如果存在 backend-code-gen-tracker.md，将其信息迁移到后端开发进度：

#### 迁移阶段状态

```markdown
# 从 backend-code-gen-tracker.md 提取
- 阶段状态 → 后端开发进度的"阶段状态"表格
- 当前阶段 → 后端开发进度的"当前状态"
- 文件列表 → 关键文件路径中的"已生成文件"
- 数据库类型选择 → 关键决策记录
```

#### 示例迁移

**backend-code-gen-tracker.md 原内容**：
```markdown
## 当前阶段
阶段3：Service和ServiceImpl生成

## 阶段状态
- 阶段1：✅ 已完成 (Entity/BO/VO)
- 阶段2：✅ 已完成 (Controller)
- 阶段3：🔄 进行中 (Service)

## 数据库类型
Oracle

## 已生成文件
- CustomUserEntity.java
- CustomUserController.java
```

**dev-session.md 新内容**：
```markdown
## 后端开发进度（选项B）

### 阶段状态
| 阶段 | 名称 | 状态 | 完成时间 | 生成文件数 |
|------|------|------|---------|-----------|
| 1 | Entity/BO/VO | ✅ 已完成 | 2024-01-15 12:00:00 | 3 |
| 2 | Controller | ✅ 已完成 | 2024-01-15 13:00:00 | 1 |
| 3 | Service | 🔄 进行中 | - | - |
| 4 | Dao/Mapper | ⬜ 未开始 | - | - |

### 当前状态
- **已完成阶段**：阶段1~2
- **下一阶段**：阶段3 - Service
- **需要读取的指南**：`BACKEND-SERVICE-GENERATION.md`

### 关键文件路径
- **已生成文件**：
  - Entity/BO/VO：3个文件
  - Controller：1个文件
  - Service：0个文件

## 关键决策记录
| 时间 | 决策内容 | 影响范围 |
|------|---------|---------|
| 2024-01-15 10:30:00 | 选择Oracle数据库类型 | 后端 |
```

### 步骤5：更新开发方案选择

根据迁移的信息，更新开发方案选择：

```markdown
## 开发方案选择
- [x] 选项A：前端开发（步骤1+2）  # 如果前端有进度
- [x] 选项B：后端开发（步骤3+4+5）  # 如果后端有进度
- [ ] 选项C：模块集成测试（步骤6）  # 如果需要集成
```

### 步骤6：更新下次恢复指南

填充下次恢复指南部分：

```markdown
## 下次恢复指南
（清理上下文后，从这里快速恢复）

### 从哪里继续
- **开发类型**：前端  # 或后端
- **下一阶段**：阶段2 - API服务层
- **需要读取的指南文件**：`FRONTEND-API-SERVICE-GENERATION.md`
- **需要检查的前置产物**：[
  "用户管理-需求详细设计.md",
  "用户管理-前端架构设计.md",
  "用户管理-API设计文档.md"
]

### 关键信息
- moduleCode：`usermgmt`
- acronym：`USR`
- 前端目录：`usermgmt-frontend/`
- 后端目录：`usermgmt/usermgmt-custom/`
```

## 迁移后验证

### 验证清单

- [ ] dev-session.md 文件已创建
- [ ] 会话信息完整（功能名称、moduleCode、acronym）
- [ ] 代码目录信息正确
- [ ] 开发方案选择已更新
- [ ] 前端开发进度已迁移
- [ ] 后端开发进度已迁移
- [ ] 关键决策记录已迁移
- [ ] 下次恢复指南已填充
- [ ] Git提交记录已更新
- [ ] 旧文件已备份

### 验证命令

```bash
# 检查新文件是否存在
ls -la dev-session.md

# 检查文件内容是否完整
head -20 dev-session.md

# 验证格式是否正确
grep -E "^(#|-|\|)" dev-session.md | head -10
```

## 清理旧文件

确认新文件工作正常后，可以安全删除旧文件：

```bash
# 删除旧文件（确认无误后执行）
rm dev-task.md frontend-code-gen-tracker.md backend-code-gen-tracker.md

# 或者移动到备份目录
mv dev-task.md frontend-code-gen-tracker.md backend-code-gen-tracker.md backups/tracker-files/
```

## 常见问题

### 问题1：多个tracker文件内容冲突

**解决方案**：
1. 优先使用最新的文件
2. 手动合并关键信息
3. 在关键决策记录中说明合并情况

### 问题2：缺少某些必要信息

**解决方案**：
1. 从项目metadata文件提取moduleCode和acronym
2. 从现有代码目录推断路径信息
3. 标记为待确认，需要用户验证

### 问题3：阶段状态不明确

**解决方案**：
1. 根据已生成文件推断阶段状态
2. 标记为"进行中"，需要用户确认
3. 在下次恢复指南中明确需要验证

## 迁移工具

### 自动化迁移脚本

```bash
#!/bin/bash
# migrate-tracker.sh

# 创建备份
mkdir -p backups/tracker-files
cp dev-task.md frontend-code-gen-tracker.md backend-code-gen-tracker.md backups/tracker-files/ 2>/dev/null || true

# 创建新的 dev-session.md
echo "# 开发会话状态

## 会话信息
- **功能名称**：待填充
- **moduleCode**：待填充
- **acronym**：待填充
- **会话开始时间**：$(date '+%Y-%m-%d %H:%M:%S')
- **最后更新时间**：$(date '+%Y-%m-%d %H:%M:%S')

## 代码目录
- **前端目录**：待填充
- **后端目录**：待填充

## 开发方案选择
- [ ] 选项A：前端开发（步骤1+2）
- [ ] 选项B：后端开发（步骤3+4+5）
- [ ] 选项C：模块集成测试（步骤6）

---

## 前端开发进度（选项A）

### 阶段状态
| 阶段 | 名称 | 状态 | 完成时间 | 生成文件数 |
|------|------|------|---------|-----------|
| 1 | 类型定义和工具函数 | ⬜ 未开始 | - | - |
| 2 | API服务层 | ⬜ 未开始 | - | - |
| 3 | 业务组件 | ⬜ 未开始 | - | - |
| 4 | 页面组件 | ⬜ 未开始 | - | - |
| 5 | 路由配置（可选） | ⬜ 未选择 | - | - |
| 6 | 国际化（可选） | ⬜ 未选择 | - | - |
| 7 | 样式文件（可选） | ⬜ 未选择 | - | - |

### 当前状态
- **已完成阶段**：无
- **下一阶段**：阶段1 - 类型定义和工具函数
- **需要读取的指南**：`FRONTEND-TYPES-UTILS-GENERATION.md`

---

## 后端开发进度（选项B）

### 阶段状态
| 阶段 | 名称 | 状态 | 完成时间 | 生成文件数 |
|------|------|------|---------|-----------|
| 1 | Entity/BO/VO | ⬜ 未开始 | - | - |
| 2 | Controller | ⬜ 未开始 | - | - |
| 3 | Service | ⬜ 未开始 | - | - |
| 4 | Dao/Mapper | ⬜ 未开始 | - | - |
| 5 | 初始化SQL（可选） | ⬜ 未选择 | - | - |
| 6 | Feign API（可选） | ⬜ 未选择 | - | - |
| 7 | 启动类（可选） | ⬜ 未选择 | - | - |
| 8 | 菜单注册（可选） | ⬜ 未选择 | - | - |
| 9 | 依赖注入配置（可选） | ⬜ 未选择 | - | - |

### 当前状态
- **已完成阶段**：无
- **下一阶段**：阶段1 - Entity/BO/VO
- **需要读取的指南**：`BACKEND-ENTITY-BO-VO-GENERATION.md`

---

## 下次恢复指南
（清理上下文后，从这里快速恢复）

### 从哪里继续
- **开发类型**：待填充
- **下一阶段**：待填充
- **需要读取的指南文件**：待填充
- **需要检查的前置产物**：待填充

### 关键信息
- moduleCode：待填充
- acronym：待填充
- 前端目录：待填充
- 后端目录：待填充
" > dev-session.md

echo "已创建新的 dev-session.md 模板，请手动填充具体信息"
```

## 后续操作

### 1. 更新文档引用

更新所有引用旧文件的文档，改为引用 dev-session.md：

```markdown
# 旧引用
请查看 frontend-code-gen-tracker.md 获取当前进度

# 新引用
请查看 dev-session.md 的「前端开发进度」部分获取当前进度
```

### 2. 培训团队

确保团队成员了解新的进度跟踪机制：

- 单一文件：dev-session.md
- 位置：项目根目录
- 更新时机：每个阶段完成后
- 恢复方式：从「下次恢复指南」开始

### 3. 监控和优化

- 观察新机制的使用效果
- 收集用户反馈
- 根据需要调整格式规范

## 优势总结

### 新方案优势

- ✅ **单一文件**：所有进度在一个文件中（dev-session.md）
- ✅ **内容精简**：只保留恢复所需的关键信息
- ✅ **快速恢复**：清理上下文后可以立即继续
- ✅ **节省Token**：减少冗余信息，降低token消耗
- ✅ **易于维护**：只需更新一个文件
- ✅ **统一管理**：前后端进度统一管理
- ✅ **标准化**：统一的格式规范

### 对比旧方案

| 方面 | 旧方案 | 新方案 |
|------|--------|--------|
| 文件数量 | 3个分散文件 | 1个统一文件 |
| 更新复杂度 | 需要更新多个文件 | 只需更新一个文件 |
| 恢复速度 | 需要读取多个文件 | 快速定位恢复点 |
| 信息冗余 | 重复信息多 | 精简关键信息 |
| Token消耗 | 高 | 低 |
| 维护成本 | 高 | 低 |