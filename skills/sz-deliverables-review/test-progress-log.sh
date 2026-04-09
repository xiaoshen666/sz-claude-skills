#!/bin/bash

# 测试进度日志功能
echo "=== 测试进度日志功能 ==="
echo ""

# 1. 检查模板文件是否存在
echo "1. 检查模板文件..."
if [ -f "progress-log-template.json" ]; then
    echo "   ✓ 模板文件存在"
    # 检查模板文件格式
    if python3 -m json.tool progress-log-template.json > /dev/null 2>&1; then
        echo "   ✓ 模板文件格式正确"
    else
        echo "   ✗ 模板文件格式错误"
    fi
else
    echo "   ✗ 模板文件不存在"
fi

echo ""

# 2. 检查SKILL.md文件是否包含进度日志相关内容
echo "2. 检查SKILL.md文件..."
if [ -f "SKILL.md" ]; then
    echo "   ✓ SKILL.md文件存在"
    
    # 检查是否包含进度日志相关关键词
    keywords=("进度日志" "progress-log" "恢复功能" "workflow_history")
    for keyword in "${keywords[@]}"; do
        if grep -q "$keyword" SKILL.md; then
            echo "   ✓ 包含关键词: $keyword"
        else
            echo "   ✗ 缺少关键词: $keyword"
        fi
    done
    
    # 检查是否包含模板文件引用
    if grep -q "progress-log-template.json" SKILL.md; then
        echo "   ✓ 包含模板文件引用"
    else
        echo "   ✗ 缺少模板文件引用"
    fi
else
    echo "   ✗ SKILL.md文件不存在"
fi

echo ""

# 3. 创建测试进度日志文件
echo "3. 创建测试进度日志文件..."
TEST_PROJECT="测试项目"
TEST_LOG_FILE="${TEST_PROJECT}-progress-log.json"

# 使用模板创建测试文件
cp progress-log-template.json "$TEST_LOG_FILE"

# 替换项目名称
sed -i '' "s/项目名称/$TEST_PROJECT/g" "$TEST_LOG_FILE"

if [ -f "$TEST_LOG_FILE" ]; then
    echo "   ✓ 测试日志文件创建成功: $TEST_LOG_FILE"
    
    # 检查文件格式
    if python3 -m json.tool "$TEST_LOG_FILE" > /dev/null 2>&1; then
        echo "   ✓ 测试日志文件格式正确"
    else
        echo "   ✗ 测试日志文件格式错误"
    fi
    
    # 显示文件大小
    FILE_SIZE=$(wc -c < "$TEST_LOG_FILE")
    echo "   ℹ️ 文件大小: $FILE_SIZE 字节"
else
    echo "   ✗ 测试日志文件创建失败"
fi

echo ""

# 4. 测试JSON解析
echo "4. 测试JSON解析..."
if command -v python3 &> /dev/null; then
    python3 << 'EOF'
import json
import os

test_file = "测试项目-progress-log.json"
if os.path.exists(test_file):
    try:
        with open(test_file, 'r', encoding='utf-8') as f:
            data = json.load(f)
        
        print("   ✓ JSON解析成功")
        print(f"   ℹ️ 项目名称: {data.get('project_name', '未找到')}")
        print(f"   ℹ️ 当前环节: {data.get('current_phase', '未找到')}")
        print(f"   ℹ️ 环节数量: {len(data.get('phases', {}))}")
        print(f"   ℹ️ 历史记录数量: {len(data.get('workflow_history', []))}")
        
        # 检查状态定义
        status_defs = data.get('status_definitions', {})
        if status_defs:
            print(f"   ℹ️ 状态定义数量: {len(status_defs)}")
        else:
            print("   ⚠️ 缺少状态定义")
            
    except json.JSONDecodeError as e:
        print(f"   ✗ JSON解析错误: {e}")
    except Exception as e:
        print(f"   ✗ 其他错误: {e}")
else:
    print("   ⚠️ Python3未安装，跳过JSON解析测试")
EOF
else
    echo "   ⚠️ Python3未安装，跳过JSON解析测试"
fi

echo ""

# 5. 清理测试文件
echo "5. 清理测试文件..."
if [ -f "$TEST_LOG_FILE" ]; then
    rm "$TEST_LOG_FILE"
    echo "   ✓ 测试文件已清理"
else
    echo "   ℹ️ 无测试文件需要清理"
fi

echo ""
echo "=== 测试完成 ==="