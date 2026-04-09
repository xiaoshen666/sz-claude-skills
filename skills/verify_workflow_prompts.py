#!/usr/bin/env python3
"""
验证各个技能是否都有下一步工作提示
"""

import os
import re

def check_skill_for_next_steps(skill_path):
    """检查技能文件是否包含下一步工作提示"""
    try:
        with open(skill_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # 检查是否包含下一步工作提示相关关键词
        patterns = [
            r'下一步工作提示',
            r'建议用户调用',
            r'sz-deliverables-review',
            r'sz-workflow-coordinator'
        ]
        
        found_patterns = []
        for pattern in patterns:
            if re.search(pattern, content):
                found_patterns.append(pattern)
        
        return len(found_patterns) > 0, found_patterns
    except Exception as e:
        return False, [f"读取文件失败: {str(e)}"]

def main():
    skills_dir = "/Users/shenzhiqiang/.claude/skills"
    
    # 需要检查的技能列表
    skills_to_check = [
        "sz-requirements-analysis-design",
        "sz-architecture-design", 
        "sz-database-api-design",
        "sz-ui-prototype-design",
        "sz-frontend-implementation",
        "sz-backend-implementation",
        "sz-integration-deployment"
    ]
    
    print("=== 技能工作流程提示验证 ===\n")
    
    all_ok = True
    for skill_name in skills_to_check:
        skill_path = os.path.join(skills_dir, skill_name, "SKILL.md")
        
        if not os.path.exists(skill_path):
            print(f"❌ {skill_name}: 技能文件不存在")
            all_ok = False
            continue
        
        has_prompts, patterns = check_skill_for_next_steps(skill_path)
        
        if has_prompts:
            print(f"✅ {skill_name}: 包含下一步工作提示")
            if patterns:
                print(f"   找到的关键词: {', '.join(patterns[:3])}")
        else:
            print(f"❌ {skill_name}: 缺少下一步工作提示")
            all_ok = False
    
    print(f"\n=== 验证结果 ===")
    if all_ok:
        print("✅ 所有技能都包含下一步工作提示")
    else:
        print("❌ 部分技能缺少下一步工作提示")
    
    # 检查工作流程协调技能
    coordinator_path = os.path.join(skills_dir, "sz-workflow-coordinator", "SKILL.md")
    if os.path.exists(coordinator_path):
        print(f"\n✅ 工作流程协调技能已创建: sz-workflow-coordinator")
        with open(coordinator_path, 'r', encoding='utf-8') as f:
            content = f.read()
            skill_calls = re.findall(r'sz-\w+', content)
            unique_skills = set(skill_calls)
            print(f"   协调的技能数量: {len(unique_skills)}")
            print(f"   协调的技能: {', '.join(sorted(unique_skills))}")
    else:
        print(f"\n❌ 工作流程协调技能未创建")
    
    # 检查文档检查技能
    review_path = os.path.join(skills_dir, "sz-deliverables-review", "SKILL.md")
    if os.path.exists(review_path):
        print(f"\n✅ 文档检查技能已存在: sz-deliverables-review")
    else:
        print(f"\n❌ 文档检查技能不存在")

if __name__ == "__main__":
    main()