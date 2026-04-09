# Obsidian与自定义Skills集成指南

## 概述
本文档说明如何将自定义skills（sz-开头的skills）与Obsidian集成，实现文档的优化输出和管理。

## 集成功能

### 1. Obsidian格式输出
所有自定义skills现在支持两种输出格式：
- **原始格式**：标准的Markdown文档
- **Obsidian格式**：优化后的Obsidian Flavored Markdown文档

### 2. 知识库自动创建
当选择Obsidian格式时，系统会自动：
- 创建完整的知识库目录结构
- 为每个文档添加Obsidian frontmatter
- 建立文档之间的wikilinks关系
- 添加callouts突出重要信息
- 创建Bases视图管理文档

### 3. 工作流集成
在每个skill的工作流程中，会询问用户是否需要Obsidian格式输出。

## 使用方式

### 方式1：交互式使用
当运行任何sz-开头的skill时：
1. 系统会询问项目名称
2. 询问是否需要Obsidian格式输出（默认：是）
3. 根据选择生成相应的文档

### 方式2：批量转换
使用`sz-obsidian-integration` skill可以：
1. 将现有文档批量转换为Obsidian格式
2. 创建完整的项目知识库
3. 生成Bases视图

### 方式3：自动化脚本
使用提供的Python脚本：
```bash
# 转换单个文档
python auto_obsidian.py --project 微平台 --input 01需求文档/微平台需求概要文档.md --type 需求文档

# 创建完整知识库
python auto_obsidian.py --project 微平台 --create-kb

# 批量转换
python auto_obsidian.py --project 微平台 --batch
```

## 知识库结构

```
{项目名称}-知识库/
├── 00-项目概览/
│   ├── {项目名称}项目概述.md
│   └── {项目名称}项目时间线.md
├── 01-需求文档/
│   ├── {项目名称}需求概要文档.md
│   └── {项目名称}需求详细设计文档.md
├── 02-技术设计/
│   ├── {项目名称}技术架构设计文档.md
│   ├── {项目名称}数据库设计文档.md
│   └── {项目名称}API接口文档.md
├── 03-设计文档/
│   └── {项目名称}UI原型设计文档.md
├── 04-开发文档/
│   ├── {项目名称}前端开发文档.md
│   └── {项目名称}后端开发文档.md
├── 05-部署运维/
│   └── {项目名称}部署文档.md
├── 06-评审记录/
│   └── {项目名称}评审报告.md
├── 07-会议记录/
├── 08-参考资料/
├── attachments/
└── {项目名称}-文档管理.base
```

## 文档转换规则

### Frontmatter模板
```yaml
---
title: "{项目名称}{文档类型}"
aliases: ["{项目名称}{文档类型简写}"]
tags: ["{文档类型标签}", "项目文档", "{项目名称}"]
created: "{{date:YYYY-MM-DD}}"
modified: "{{date:YYYY-MM-DD}}"
project: "{项目名称}"
document-type: "{文档类型}"
status: "active"
version: "1.0"
---
```

### Wikilinks规则
- 链接到相关文档：`[[{项目名称}需求文档]]`
- 链接到具体章节：`[[{项目名称}需求文档#功能模块]]`
- 链接到块：`[[{项目名称}需求文档#^核心需求]]`

### Callouts使用
- 重要信息：`> [!important]`
- 注意事项：`> [!note]`
- 警告信息：`> [!warning]`
- 提示信息：`> [!tip]`
- 成功信息：`> [!success]`

## Bases视图配置

每个知识库会自动创建Bases视图文件（`.base`），包含：
1. **表格视图**：文档总览
2. **卡片视图**：按文档类型分组
3. **列表视图**：最近修改的文档

## 常见问题

### Q1: 如何关闭Obsidian格式输出？
A: 在skill询问时选择"否"，或修改skill文件中的默认设置。

### Q2: 如何将现有项目文档转换为Obsidian格式？
A: 使用`sz-obsidian-integration` skill的批量转换功能。

### Q3: Obsidian格式文档在哪里保存？
A: 在`{项目名称}-知识库/`目录下的相应子目录。

### Q4: 如何自定义知识库结构？
A: 修改`sz-obsidian-integration/obsidian-template.md`中的目录映射。

### Q5: 如何添加自定义的Obsidian属性？
A: 在frontmatter模板中添加相应的属性定义。

## 扩展开发

### 添加新的文档类型
1. 在`DOC_TYPE_MAPPING`中添加映射
2. 在`DIR_MAPPING`中指定目录
3. 在`obsidian-template.md`中添加相关文档链接

### 自定义转换规则
1. 修改`ObsidianConverter`类中的处理方法
2. 添加新的callouts规则
3. 调整wikilinks生成逻辑

## 技术支持
如有问题，请参考：
1. `sz-obsidian-integration/SKILL.md` - 集成skill详细说明
2. `sz-obsidian-integration/obsidian-template.md` - 模板文件
3. Obsidian官方文档 - Obsidian语法参考
