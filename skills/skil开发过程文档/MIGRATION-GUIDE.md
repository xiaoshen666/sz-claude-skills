# 进度文件迁移指南

## 变更说明

**变更日期**：2026-04-16

**变更内容**：将 4 个进度跟踪文件合并为 1 个统一的 `dev-session.md`

---

## 旧方案（已废弃）

### 文件列表
```
dev-session.md                    ← 全局会话状态
dev-task.md                       ← 模块开发任务
frontend-code-gen-tracker.md      ← 前端代码生成进度
backend-code-gen-tracker.md       ← 后端代码生成进度
```

### 问题
- ❌ 文件冗余：dev-session.md 和 dev-task.md 职责重叠
- ❌ 维护成本高：需要同时更新多个文件
- ❌ Token 浪费：详细文件路径占用大量 token
- ❌ 恢复效率低：需要从多个文件读取信息

---

## 新方案（当前使用）

### 文件列表
```
dev-session.md                    ← 统一进度跟踪
  ├── 会话信息
  ├── 代码目录
  ├── 开发方案选择
  ├── 前端开发进度（精简版表格）
  ├── 后端开发进度（精简版表格）
  ├── 关键决策记录
  └── 下次恢复指南
```

### 优势
- ✅ 单一文件：所有进度信息在一个文件中
- ✅ 内容精简：只保留恢复所需的关键信息
- ✅ 维护简单：只需更新 1 个文件
- ✅ Token 节省：大幅减少冗余信息
- ✅ 快速恢复：清理上下文后立即继续

---

## 迁移步骤

### 如果您已有旧格式的进度文件

#### 步骤 1：备份旧文件
```bash
# 创建备份目录
mkdir -p .backup/old-trackers

# 备份旧文件
cp dev-task.md .backup/old-trackers/ 2>/dev/null
cp frontend-code-gen-tracker.md .backup/old-trackers/ 2>/dev/null
cp backend-code-gen-tracker.md .backup/old-trackers/ 2>/dev/null
```

#### 步骤 2：合并信息到 dev-session.md

按照 [DEV-SESSION-FORMAT.md](DEV-SESSION-FORMAT.md) 的格式，将旧文件的信息合并到 dev-session.md：

```markdown
# 开发会话状态

## 会话信息
（从 dev-session.md 或 dev-task.md 提取）
- 功能名称：{功能名称}
- moduleCode：{实际值}
- acronym：{实际值}

## 代码目录
（从 dev-task.md 提取）
- 前端目录：{moduleCode}-frontend/
- 后端目录：{moduleCode}-custom/

## 前端开发进度
（从 frontend-code-gen-tracker.md 提取，精简为表格）
| 阶段 | 名称 | 状态 | 完成时间 | 生成文件数 |
|------|------|------|---------|-----------|
| 1 | 类型定义和工具函数 | ✅ 已完成 | {时间} | {数量} |
| ... | ... | ... | ... | ... |

### 当前状态
- 已完成阶段：阶段1~X
- 下一阶段：阶段X+1 - {阶段名称}
- 需要读取的指南：FRONTEND-{阶段名称}-GENERATION.md

### 关键文件路径
（精简：只记录文件类型和数量，不记录完整路径）
- 前序设计文件：{文件名列表}
- 已生成文件：{类型}X个、{类型}X个

## 后端开发进度
（从 backend-code-gen-tracker.md 提取，精简为表格）
| 阶段 | 名称 | 状态 | 完成时间 | 生成文件数 |
|------|------|------|---------|-----------|
| 1 | Entity/BO/VO | ✅ 已完成 | {时间} | {数量} |
| ... | ... | ... | ... | ... |

## 下次恢复指南
（新增：用于清理上下文后快速恢复）
- 开发类型：[前端 / 后端]
- 下一阶段：阶段X+1 - {阶段名称}
- 需要读取的指南文件：{指南文件名}.md
- 需要检查的前置产物：[文件列表]
```

#### 步骤 3：验证新文件

检查 dev-session.md 是否包含：
- [ ] 会话信息完整（功能名称、moduleCode、acronym）
- [ ] 前端/后端进度表格正确
- [ ] 下次恢复指南完整
- [ ] 关键文件路径已记录（精简版）

#### 步骤 4：删除旧文件

确认新文件正常后，删除旧文件：
```bash
# 删除旧文件
rm -f dev-task.md
rm -f frontend-code-gen-tracker.md
rm -f backend-code-gen-tracker.md

# 如果需要保留备份，已在步骤1中备份到 .backup/old-trackers/
```

---

## 如果是新项目

直接创建 dev-session.md，按照 [DEV-SESSION-FORMAT.md](DEV-SESSION-FORMAT.md) 的格式初始化即可。

---

## 常见问题

### Q1：为什么要合并文件？
A：减少文件冗余，降低维护成本，节省 Token 消耗，提高恢复效率。

### Q2：旧的 tracker 文件还能用吗？
A：不建议继续使用。新方案已经全面替代旧方案，所有文档都已更新为引用 dev-session.md。

### Q3：迁移后会影响断点续传吗？
A：不会。新方案的「下次恢复指南」部分专门用于断点续传，恢复效率更高。

### Q4：详细的文件路径不记录，怎么找到文件？
A：只需记录文件类型和数量，实际文件可以通过 moduleCode 和功能名称定位。详细路径不需要保存在进度文件中。

### Q5：Git 提交记录怎么保存？
A：在 dev-session.md 的「前端/后端开发进度」部分记录最新 commit hash 即可，详细的提交历史可以通过 `git log` 查看。

---

## 相关文档

- [DEV-SESSION-FORMAT.md](DEV-SESSION-FORMAT.md) - dev-session.md 格式规范
- [CONTEXT-CLEANING.md](CONTEXT-CLEANING.md) - 上下文清理规范
- [FRONTEND-CODE-GEN.md](FRONTEND-CODE-GEN.md) - 前端代码生成流程
- [BACKEND-CODE-GEN.md](BACKEND-CODE-GEN.md) - 后端代码生成流程

---

## 总结

新方案的核心改进：
- 🎯 **4个文件 → 1个文件**（减少75%）
- 📦 **内容精简**（只保留恢复所需信息）
- 🔄 **快速恢复**（清理上下文后立即继续）
- 💰 **节省Token**（减少冗余信息）
- 📝 **易于维护**（只需更新一个文件）
