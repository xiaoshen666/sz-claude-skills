#!/usr/bin/env python3
"""
批量更新自定义skills，添加Obsidian集成功能
"""

import os
import re
from pathlib import Path

# 自定义skills列表
SKILLS = [
    "sz-requirements-analysis-design",
    "sz-architecture-design",
    "sz-database-api-design",
    "sz-ui-prototype-design",
    "sz-frontend-implementation",
    "sz-backend-implementation",
    "sz-integration-deployment",
    "sz-deliverables-review"
]

# 文档类型映射
DOC_TYPE_MAPPING = {
    "sz-requirements-analysis-design": "需求文档",
    "sz-architecture-design": "技术架构设计文档",
    "sz-database-api-design": "数据库设计文档",
    "sz-ui-prototype-design": "UI原型设计文档",
    "sz-frontend-implementation": "前端开发文档",
    "sz-backend-implementation": "后端开发文档",
    "sz-integration-deployment": "部署文档",
    "sz-deliverables-review": "评审报告"
}

# 目录映射
DIR_MAPPING = {
    "sz-requirements-analysis-design": "01-需求文档",
    "sz-architecture-design": "02-技术设计",
    "sz-database-api-design": "02-技术设计",
    "sz-ui-prototype-design": "03-设计文档",
    "sz-frontend-implementation": "04-开发文档",
    "sz-backend-implementation": "04-开发文档",
    "sz-integration-deployment": "05-部署运维",
    "sz-deliverables-review": "06-评审记录"
}

def update_skill_workflow(content, skill_name):
    """更新工作流程部分"""
    doc_type = DOC_TYPE_MAPPING.get(skill_name, "文档")
    dir_name = DIR_MAPPING.get(skill_name, "00-其他文档")
    
    # 在项目初始化部分添加Obsidian选项
    content = re.sub(
        r'1\. \*\*项目初始化\*\*:',
        r'1. **项目初始化**:\n   - 询问用户获取项目名称（如：微平台）\n   - 询问用户是否需要Obsidian格式输出（默认：是）',
        content
    )
    
    # 更新文档位置，添加Obsidian文档位置
    content = re.sub(
        r'-\s*\*\*文档位置\*\*:\s*`([^`]+)`',
        rf'- **原始文档位置**: `\1`\n   - **Obsidian文档位置**: `{{项目名称}}-知识库/{dir_name}/{{项目名称}}{doc_type}.md`',
        content
    )
    
    # 添加Obsidian格式转换步骤
    if "质量检查" in content:
        content = re.sub(
            r'(\d+)\. \*\*质量检查\*\*:',
            r'\1. **Obsidian格式转换**（如果用户选择）:\n   - 为每个文档添加Obsidian frontmatter\n   - 创建文档之间的wikilinks关系\n   - 添加callouts突出重要信息\n   - 保存到知识库目录结构\n\n\1. **质量检查**:',
            content
        )
    
    return content

def update_output_format(content):
    """更新输出格式要求部分"""
    obsidian_section = """
### Obsidian格式要求（如果选择）
- **Frontmatter**：必须包含title、tags、created、modified、project字段
- **Wikilinks**：使用`[[文档名称]]`格式链接相关文档
- **Callouts**：重要信息使用`> [!important]`，注意事项使用`> [!note]`
- **Mermaid图表**：复杂流程图使用Mermaid语法
- **知识库结构**：文档保存到`{项目名称}-知识库/`目录下的相应子目录
"""
    
    if "### 输出格式要求" in content:
        # 在输出格式要求后添加Obsidian格式要求
        content = re.sub(
            r'(### 输出格式要求[\s\S]*?)(?=###|\Z)',
            r'\1' + obsidian_section + '\n',
            content
        )
    
    return content

def update_notes(content):
    """更新注意事项部分"""
    obsidian_notes = """
8. 如果选择Obsidian格式，确保frontmatter格式正确
9. 检查所有wikilinks链接的有效性
10. 确保callouts使用恰当的类型
"""
    
    if "### 注意事项" in content:
        # 在注意事项末尾添加Obsidian相关注意事项
        content = re.sub(
            r'(### 注意事项[\s\S]*?)(?=###|\Z)',
            r'\1' + obsidian_notes,
            content
        )
    
    return content

def update_acceptance_criteria(content, skill_name):
    """更新验收标准部分"""
    doc_type = DOC_TYPE_MAPPING.get(skill_name, "文档")
    
    obsidian_criteria = f"""
### Obsidian验收标准（如果选择）
- 文档包含完整的Obsidian frontmatter
- 所有内部链接使用wikilinks格式
- 重要信息使用callouts突出显示
- 文档保存到知识库的正确目录
- 知识库结构完整，便于导航和管理
- Bases视图能够正确显示文档关系
"""
    
    if "### 验收标准" in content:
        # 在验收标准后添加Obsidian验收标准
        content = re.sub(
            r'(### 验收标准[\s\S]*?)(?=###|\Z)',
            r'\1' + obsidian_criteria + '\n',
            content
        )
    
    return content

def update_skill_file(skill_path):
    """更新单个skill文件"""
    print(f"更新: {skill_path}")
    
    with open(skill_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    skill_name = Path(skill_path).parent.name
    
    # 应用所有更新
    content = update_skill_workflow(content, skill_name)
    content = update_output_format(content)
    content = update_notes(content)
    content = update_acceptance_criteria(content, skill_name)
    
    # 保存更新后的内容
    with open(skill_path, 'w', encoding='utf-8') as f:
        f.write(content)
    
    print(f"完成: {skill_path}")
    return True

def main():
    """主函数"""
    skills_dir = Path("/Users/shenzhiqiang/.claude/skills")
    
    print("开始更新自定义skills，添加Obsidian集成功能...")
    print("=" * 60)
    
    updated_count = 0
    for skill_name in SKILLS:
        skill_path = skills_dir / skill_name / "SKILL.md"
        
        if skill_path.exists():
            try:
                if update_skill_file(str(skill_path)):
                    updated_count += 1
            except Exception as e:
                print(f"错误: 更新{skill_name}时出错 - {e}")
        else:
            print(f"跳过: {skill_path} 不存在")
    
    print("=" * 60)
    print(f"更新完成！共更新了 {updated_count}/{len(SKILLS)} 个skill文件")
    
    # 创建集成说明文档
    create_integration_guide(skills_dir)

def create_integration_guide(skills_dir):
    """创建集成说明文档"""
    guide_content = """# Obsidian与自定义Skills集成指南

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
"""

    guide_path = skills_dir / "OBSIDIAN_INTEGRATION_GUIDE.md"
    with open(guide_path, 'w', encoding='utf-8') as f:
        f.write(guide_content)
    
    print(f"集成指南已创建: {guide_path}")

if __name__ == "__main__":
    main()