#!/usr/bin/env python3
"""
协调实现流程测试脚本
验证强制规划机制和架构师提示词集成
"""

import os
import re

def check_skill_integration(skill_file, planner_check=True, architect_ref=None):
    """检查技能文件中的集成点"""
    print(f"\n检查技能文件: {os.path.basename(skill_file)}")
    
    with open(skill_file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    issues = []
    
    # 检查强制规划阶段
    if planner_check:
        planner_patterns = [
            r'强制规划阶段',
            r'检查规划文档',
            r'前端实现规划文档',
            r'后端实现规划文档'
        ]
        
        # 对于协调技能，检查调用规划技能
        if 'coordinated' in skill_file:
            planner_patterns.append(r'调用规划技能')
        else:
            planner_patterns.append(r'调用Feature-Planner技能')
        
        for pattern in planner_patterns:
            if not re.search(pattern, content):
                issues.append(f"缺失强制规划检查: {pattern}")
    
    # 检查架构师提示词引用
    if architect_ref:
        if architect_ref not in content:
            issues.append(f"缺失架构师提示词引用: {architect_ref}")
    
    # 检查工作流程编号是否正确（应该有强制规划阶段）
    workflow_sections = re.findall(r'(\d+)\.\s*\*\*.*阶段\*\*', content)
    if workflow_sections:
        if '2' not in workflow_sections and planner_check:
            issues.append("工作流程中缺少强制规划阶段（应该是第2阶段）")
    
    if issues:
        print("发现的问题:")
        for issue in issues:
            print(f"  - {issue}")
        return False
    else:
        print("✓ 集成检查通过")
        return True

def test_all_skills():
    """测试所有相关技能"""
    skills_dir = "/Users/shenzhiqiang/.claude/skills"
    
    # 测试规划技能
    planning_skill = os.path.join(skills_dir, "sz-implementation-planning", "SKILL.md")
    if os.path.exists(planning_skill):
        check_skill_integration(planning_skill, planner_check=False)
    
    # 测试前端实现技能
    frontend_skill = os.path.join(skills_dir, "sz-frontend-implementation", "SKILL.md")
    if os.path.exists(frontend_skill):
        check_skill_integration(
            frontend_skill, 
            planner_check=True,
            architect_ref="/Users/shenzhiqiang/.claude/skills/Frontend-Architect.md"
        )
    
    # 测试后端实现技能
    backend_skill = os.path.join(skills_dir, "sz-backend-implementation", "SKILL.md")
    if os.path.exists(backend_skill):
        check_skill_integration(
            backend_skill,
            planner_check=True,
            architect_ref="/Users/shenzhiqiang/.claude/skills/Backend-Architect.md"
        )
    
    # 测试协调实现技能
    coordinated_skill = os.path.join(skills_dir, "sz-coordinated-implementation", "SKILL.md")
    if os.path.exists(coordinated_skill):
        check_skill_integration(coordinated_skill, planner_check=True)
    
    # 检查架构师提示词文件是否存在
    print("\n检查架构师提示词文件:")
    architect_files = [
        "Frontend-Architect.md",
        "Backend-Architect.md",
        "Feature-Planner.md"
    ]
    
    for file in architect_files:
        file_path = os.path.join(skills_dir, file)
        if os.path.exists(file_path):
            print(f"✓ {file} 存在")
        else:
            print(f"✗ {file} 不存在")

def verify_folder_structure():
    """验证文件夹结构"""
    print("\n验证文件夹结构:")
    
    expected_folders = [
        "sz-implementation-planning",
        "sz-frontend-implementation", 
        "sz-backend-implementation",
        "sz-coordinated-implementation"
    ]
    
    skills_dir = "/Users/shenzhiqiang/.claude/skills"
    
    for folder in expected_folders:
        folder_path = os.path.join(skills_dir, folder)
        if os.path.exists(folder_path):
            print(f"✓ {folder}/ 存在")
        else:
            print(f"✗ {folder}/ 不存在")

def check_documentation():
    """检查文档完整性"""
    print("\n检查文档完整性:")
    
    skills_dir = "/Users/shenzhiqiang/.claude/skills"
    docs = [
        "COORDINATED_IMPLEMENTATION_GUIDE.md",
        "OBSIDIAN_INTEGRATION_GUIDE.md",
        "QUICK_START_GUIDE.md"
    ]
    
    for doc in docs:
        doc_path = os.path.join(skills_dir, doc)
        if os.path.exists(doc_path):
            # 检查文档内容
            with open(doc_path, 'r', encoding='utf-8') as f:
                content = f.read()
            
            if len(content.strip()) > 100:  # 基本内容检查
                print(f"✓ {doc} 内容完整")
            else:
                print(f"⚠ {doc} 内容可能不完整")
        else:
            print(f"✗ {doc} 不存在")

def main():
    """主测试函数"""
    print("=" * 60)
    print("协调实现流程测试")
    print("=" * 60)
    
    # 测试所有技能
    test_all_skills()
    
    # 验证文件夹结构
    verify_folder_structure()
    
    # 检查文档完整性
    check_documentation()
    
    print("\n" + "=" * 60)
    print("测试完成")
    print("=" * 60)

if __name__ == "__main__":
    main()