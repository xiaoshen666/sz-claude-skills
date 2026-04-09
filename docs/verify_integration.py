#!/usr/bin/env python3
"""
验证集成点的简单脚本
"""

import os

def verify_frontend_integration():
    """验证前端技能集成"""
    print("验证前端实现技能集成:")
    
    skill_file = "/Users/shenzhiqiang/.claude/skills/sz-frontend-implementation/SKILL.md"
    
    with open(skill_file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    checks = [
        ("强制规划阶段", "强制规划阶段" in content),
        ("检查规划文档", "检查规划文档" in content),
        ("前端实现规划文档.md", "前端实现规划文档.md" in content),
        ("调用Feature-Planner技能", "调用Feature-Planner技能" in content),
        ("Frontend-Architect.md引用", "/Users/shenzhiqiang/.claude/skills/Frontend-Architect.md" in content)
    ]
    
    all_passed = True
    for check_name, passed in checks:
        status = "✓" if passed else "✗"
        print(f"  {status} {check_name}")
        if not passed:
            all_passed = False
    
    return all_passed

def verify_backend_integration():
    """验证后端技能集成"""
    print("\n验证后端实现技能集成:")
    
    skill_file = "/Users/shenzhiqiang/.claude/skills/sz-backend-implementation/SKILL.md"
    
    with open(skill_file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    checks = [
        ("强制规划阶段", "强制规划阶段" in content),
        ("检查规划文档", "检查规划文档" in content),
        ("后端实现规划文档.md", "后端实现规划文档.md" in content),
        ("调用Feature-Planner技能", "调用Feature-Planner技能" in content),
        ("Backend-Architect.md引用", "/Users/shenzhiqiang/.claude/skills/Backend-Architect.md" in content)
    ]
    
    all_passed = True
    for check_name, passed in checks:
        status = "✓" if passed else "✗"
        print(f"  {status} {check_name}")
        if not passed:
            all_passed = False
    
    return all_passed

def verify_coordinated_integration():
    """验证协调技能集成"""
    print("\n验证协调实现技能集成:")
    
    skill_file = "/Users/shenzhiqiang/.claude/skills/sz-coordinated-implementation/SKILL.md"
    
    with open(skill_file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    checks = [
        ("调用规划技能", "调用规划技能" in content),
        ("前端实现规划文档.md", "前端实现规划文档.md" in content),
        ("后端实现规划文档.md", "后端实现规划文档.md" in content),
        ("Frontend-Architect集成", "Frontend-Architect" in content),
        ("Backend-Architect集成", "Backend-Architect" in content)
    ]
    
    all_passed = True
    for check_name, passed in checks:
        status = "✓" if passed else "✗"
        print(f"  {status} {check_name}")
        if not passed:
            all_passed = False
    
    return all_passed

def verify_planning_skill():
    """验证规划技能"""
    print("\n验证规划技能:")
    
    skill_file = "/Users/shenzhiqiang/.claude/skills/sz-implementation-planning/SKILL.md"
    
    with open(skill_file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    checks = [
        ("前端实现规划文档.md", "前端实现规划文档.md" in content),
        ("后端实现规划文档.md", "后端实现规划文档.md" in content),
        ("Feature-Planner引用", "Feature-Planner" in content)
    ]
    
    all_passed = True
    for check_name, passed in checks:
        status = "✓" if passed else "✗"
        print(f"  {status} {check_name}")
        if not passed:
            all_passed = False
    
    return all_passed

def main():
    print("=" * 60)
    print("集成点验证")
    print("=" * 60)
    
    frontend_ok = verify_frontend_integration()
    backend_ok = verify_backend_integration()
    coordinated_ok = verify_coordinated_integration()
    planning_ok = verify_planning_skill()
    
    print("\n" + "=" * 60)
    print("验证结果:")
    print(f"  前端技能集成: {'通过' if frontend_ok else '失败'}")
    print(f"  后端技能集成: {'通过' if backend_ok else '失败'}")
    print(f"  协调技能集成: {'通过' if coordinated_ok else '失败'}")
    print(f"  规划技能集成: {'通过' if planning_ok else '失败'}")
    
    all_ok = frontend_ok and backend_ok and coordinated_ok and planning_ok
    print(f"\n总体结果: {'所有集成点验证通过！' if all_ok else '部分集成点验证失败'}")
    print("=" * 60)

if __name__ == "__main__":
    main()