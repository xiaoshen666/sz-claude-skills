# Obsidian与自定义Skills集成使用示例

## 场景描述
假设我们要开发一个名为"微平台"的项目，需要完成从需求分析到部署的完整流程。我们将使用sz-开头的自定义skills，并选择Obsidian格式输出。

## 使用流程

### 步骤1：启动需求分析skill
```
用户：请帮我分析微平台项目的需求
Claude：调用sz-requirements-analysis-design skill

系统询问：
1. 项目名称：微平台
2. 是否需要Obsidian格式输出：是（默认）
```

### 步骤2：生成需求文档
skill会自动：
1. 创建文件夹结构：
   ```
   01需求文档/
   02技术设计文档/
   03原型/
   04frontend/
   05backend/
   image/
   微平台-知识库/
   ```

2. 生成原始需求文档：
   - `01需求文档/微平台需求概要文档.md`
   - `01需求文档/微平台需求详细设计文档.md`

3. 生成Obsidian格式文档：
   - `微平台-知识库/01-需求文档/微平台需求概要文档.md`
   - `微平台-知识库/01-需求文档/微平台需求详细设计文档.md`

### 步骤3：检查需求文档质量
```
用户：请检查需求文档的质量
Claude：调用sz-deliverables-review skill

系统检查：
1. 文档完整性
2. 格式规范性
3. Obsidian frontmatter正确性
4. Wikilinks有效性
```

### 步骤4：进行技术架构设计
```
用户：请设计微平台的技术架构
Claude：调用sz-architecture-design skill

系统：
1. 读取需求详细设计文档
2. 生成技术架构文档
3. 创建Obsidian格式文档
```

### 步骤5：创建知识库Bases视图
```
用户：请为微平台项目创建知识库管理视图
Claude：调用sz-obsidian-integration skill

系统：
1. 创建`微平台-文档管理.base`文件
2. 配置表格、卡片、列表视图
3. 建立文档关系图
```

## 实际对话示例

### 示例1：需求分析
```
用户：我们计划开发一个微平台系统，需要用户管理、权限控制、数据统计功能。请帮我分析需求。

Claude：好的，我将调用sz-requirements-analysis-design skill来帮您分析需求。

首先，请确认：
1. 项目名称：微平台
2. 是否需要Obsidian格式输出？[是/否]（默认：是）

用户：是

Claude：正在创建项目文件夹结构...
✓ 创建：01需求文档/
✓ 创建：02技术设计文档/
✓ 创建：03原型/
✓ 创建：04frontend/
✓ 创建：05backend/
✓ 创建：image/
✓ 创建：微平台-知识库/

正在分析需求...
✓ 识别业务目标：构建统一管理平台
✓ 识别用户角色：管理员、普通用户、访客
✓ 识别核心功能：用户管理、权限控制、数据统计

正在生成文档...
✓ 生成：01需求文档/微平台需求概要文档.md
✓ 生成：01需求文档/微平台需求详细设计文档.md
✓ 生成：微平台-知识库/01-需求文档/微平台需求概要文档.md（Obsidian格式）
✓ 生成：微平台-知识库/01-需求文档/微平台需求详细设计文档.md（Obsidian格式）

完成！已生成需求文档，包含原始格式和Obsidian格式。
```

### 示例2：技术架构设计
```
用户：请基于需求文档设计微平台的技术架构

Claude：好的，我将调用sz-architecture-design skill。

正在检查输入文档...
✓ 找到：01需求文档/微平台需求详细设计文档.md

正在分析系统需求...
✓ 评估用户规模：预计1000+用户
✓ 分析性能要求：响应时间<2秒
✓ 评估技术栈：考虑React + Spring Boot

正在生成技术架构文档...
✓ 生成：02技术设计文档/微平台前端技术架构设计文档.md
✓ 生成：02技术设计文档/微平台后端技术架构设计文档.md
✓ 生成：02技术设计文档/微平台整体技术架构设计文档.md
✓ 生成：02技术设计文档/微平台技术选型矩阵.md

正在生成Obsidian格式文档...
✓ 生成：微平台-知识库/02-技术设计/微平台前端技术架构设计文档.md
✓ 生成：微平台-知识库/02-技术设计/微平台后端技术架构设计文档.md
✓ 生成：微平台-知识库/02-技术设计/微平台整体技术架构设计文档.md
✓ 生成：微平台-知识库/02-技术设计/微平台技术选型矩阵.md

完成！技术架构文档已生成。
```

### 示例3：批量转换为Obsidian知识库
```
用户：请将微平台项目的所有文档转换为Obsidian知识库

Claude：好的，我将调用sz-obsidian-integration skill。

正在扫描项目文档...
✓ 找到需求文档：2个
✓ 找到技术设计文档：4个
✓ 找到其他文档：0个

正在创建知识库结构...
✓ 创建：微平台-知识库/00-项目概览/
✓ 创建：微平台-知识库/01-需求文档/
✓ 创建：微平台-知识库/02-技术设计/
✓ 创建：微平台-知识库/03-设计文档/
✓ 创建：微平台-知识库/04-开发文档/
✓ 创建：微平台-知识库/05-部署运维/
✓ 创建：微平台-知识库/06-评审记录/
✓ 创建：微平台-知识库/attachments/

正在转换文档...
✓ 转换：微平台需求概要文档.md → Obsidian格式
✓ 转换：微平台需求详细设计文档.md → Obsidian格式
✓ 转换：微平台前端技术架构设计文档.md → Obsidian格式
✓ 转换：微平台后端技术架构设计文档.md → Obsidian格式
✓ 转换：微平台整体技术架构设计文档.md → Obsidian格式
✓ 转换：微平台技术选型矩阵.md → Obsidian格式

正在创建Bases视图...
✓ 创建：微平台-文档管理.base
✓ 配置：表格视图、卡片视图、列表视图

正在建立文档关系...
✓ 建立：需求文档 ↔ 技术设计文档链接
✓ 建立：前端架构 ↔ 后端架构链接

完成！已创建完整的Obsidian知识库。
```

## 生成的Obsidian文档示例

### Frontmatter示例
```yaml
---
title: "微平台需求概要文档"
aliases: ["微平台需求概要", "微平台需求分析"]
tags: ["需求分析", "项目文档", "微平台"]
created: "2024-01-15"
modified: "2024-01-15"
project: "微平台"
document-type: "需求文档"
status: "active"
version: "1.0"
author: "项目团队"
priority: "高"
---
```

### 文档内容示例
```markdown
# 微平台需求概要文档

> [!abstract] 项目概述
> 微平台是一个统一的管理系统，提供用户管理、权限控制和数据统计功能。

## 业务目标
1. 实现用户统一管理
2. 提供灵活的权限控制
3. 支持数据统计和分析

## 功能模块
### 1. 用户管理模块
- 用户注册、登录、注销
- 用户信息管理
- 用户角色分配

### 2. 权限控制模块
- 权限组管理
- 权限分配
- 权限验证

## 相关文档
- [[微平台需求详细设计文档]]
- [[微平台技术架构设计文档]]
- [[微平台项目概述]]

## 任务跟踪
- [ ] 需求评审会议
- [ ] 技术方案设计
- [ ] UI原型设计
```

### Bases视图示例
```yaml
# 微平台-文档管理.base
---
filters:
  tag: "微平台"

properties:
  document-type:
    displayName: "文档类型"
  status:
    displayName: "状态"
  priority:
    displayName: "优先级"
  created:
    displayName: "创建时间"
  modified:
    displayName: "修改时间"

views:
  - type: table
    name: "项目文档总览"
    order:
      - file.name
      - document-type
      - status
      - priority
      - created
  
  - type: cards
    name: "按状态查看"
    groupBy: status
    order:
      - file.name
      - document-type
  
  - type: list
    name: "高优先级文档"
    filters:
      and:
        - tag: "微平台"
        - property: priority = "高"
    order:
      - file.name
      - status
```

## 使用技巧

### 1. 批量处理多个项目
```bash
# 为多个项目创建知识库
for project in 微平台 电商系统 内容管理系统; do
    python auto_obsidian.py --project $project --create-kb
done
```

### 2. 定期更新知识库
```bash
# 每周更新知识库
python auto_obsidian.py --project 微平台 --batch --update
```

### 3. 自定义输出格式
```python
# 自定义frontmatter模板
custom_frontmatter = {
    'title': f"{project_name} - {doc_type}",
    'category': '技术文档',
    'department': '研发部',
    'reviewer': '技术委员会'
}
```

### 4. 集成到现有工作流
```python
# 在CI/CD流程中自动转换文档
def post_build_obsidian_conversion():
    converter = ObsidianConverter(project_name)
    converter.convert_all_documents()
    converter.create_knowledge_base()
```

## 常见问题解决

### Q1: 文档转换失败
**问题**：转换时出现编码错误
**解决**：确保所有文档使用UTF-8编码
```python
with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()
```

### Q2: Wikilinks链接无效
**问题**：链接的文档不存在
**解决**：检查文档名称是否正确，确保所有文档已生成
```python
# 验证链接有效性
def validate_wikilinks(content, existing_docs):
    for link in extract_wikilinks(content):
        if link not in existing_docs:
            print(f"警告: 链接 {link} 对应的文档不存在")
```

### Q3: Frontmatter格式错误
**问题**：YAML解析失败
**解决**：确保frontmatter格式正确，特殊字符需要引号
```yaml
# 错误示例
description: This is a test: with colon

# 正确示例
description: "This is a test: with colon"
```

### Q4: Bases视图不显示
**问题**：Bases文件格式错误
**解决**：检查YAML语法，确保缩进正确
```yaml
# 使用在线YAML验证工具检查语法
# https://yaml-online-parser.appspot.com/
```

## 最佳实践

### 1. 统一命名规范
- 项目名称：使用英文或拼音，避免特殊字符
- 文档名称：`{项目名称}{文档类型}.md`
- 图片名称：`{项目名称}-{描述}.png`

### 2. 定期维护
- 每周检查文档链接有效性
- 每月更新知识库结构
- 每季度清理过期文档

### 3. 团队协作
- 统一使用Obsidian格式
- 定期同步知识库
- 建立文档评审机制

### 4. 版本控制
- 将知识库纳入版本控制
- 记录文档变更历史
- 定期备份重要文档

## 扩展功能

### 1. 自动化报告生成
```python
# 每周生成项目进度报告
def generate_weekly_report(project_name):
    converter = ObsidianConverter(project_name)
    report = converter.generate_report()
    return report
```

### 2. 文档质量检查
```python
# 自动检查文档质量
def check_document_quality(document_path):
    quality_score = calculate_quality_score(document_path)
    if quality_score < 80:
        return "需要改进"
    return "合格"
```

### 3. 知识图谱生成
```python
# 生成文档关系图谱
def generate_knowledge_graph(project_name):
    graph = create_document_graph(project_name)
    return visualize_graph(graph)
```

## 总结

通过Obsidian与自定义skills的集成，您可以：
1. **标准化文档输出**：统一的项目文档格式
2. **自动化知识管理**：自动创建和维护知识库
3. **提升团队协作**：清晰的文档关系和结构
4. **提高工作效率**：减少手动文档整理工作

开始使用这些集成的skills，让您的项目文档管理更加高效和专业！