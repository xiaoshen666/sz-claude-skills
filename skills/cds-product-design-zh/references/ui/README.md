# CDS UI库文件说明

## 目录结构

```
references/ui/
├── cds-components.lib.pen          # CDS组件库文件（Pencil格式）
├── cds-design-libraries-readme.md   # CDS设计规范文档
└── README.md                        # 本文件
```

## 文件说明

### cds-components.lib.pen
- **类型**：Pencil设计文件
- **用途**：CDS标准UI组件库，包含所有基础组件、布局组件和业务组件
- **使用方法**：用Pencil软件打开，复制所需组件到设计文档

### cds-design-libraries-readme.md
- **类型**：Markdown文档
- **用途**：详细的设计规范说明，包含颜色、字体、间距、组件使用指南
- **使用方法**：在设计过程中参考此文档确保符合CDS设计规范

## 默认使用规则

当使用cds-product-design-zh技能进行产品设计时：

1. **自动检测**：系统会检查项目根目录是否存在 `.lib.pen` 和 `design-libraries-readme.md`
2. **自动复制**：如果不存在，系统会自动将本目录下的文件复制到项目根目录
3. **直接使用**：确保新项目能立即使用CDS标准UI库进行原型设计

## 组件分类

### 核心组件（12个，所有场景均需包含）
- TopNavigation：顶部导航
- Card：卡片容器  
- FormInput：表单输入框
- FormSelect：下拉选择器
- PrimaryButton：主要按钮
- SecondaryButton：次要按钮
- DataTable：数据表格
- Pagination：分页器
- StatusTag：状态标签
- Modal：对话框
- SearchInput：搜索输入框
- ConditionSelect：条件选择器

### 其他组件
- 布局组件：Sidebar、Breadcrumb、Tabs、Layout、ColLayout
- 表单组件：FormTextarea、Switch、Checkbox、DatePicker等
- 按钮组件：DangerButton、TextButton、IconButton等
- 数据展示：EmptyState、StatisticCard、Progress等
- 反馈组件：Toast、Confirm、Loading、Notification等

## 设计规范要点

### 颜色体系
- **主色调**：#1677FF（CDS蓝色）
- **辅助色**：成功#52C41A、警告#FAAD14、错误#FF4D4F
- **文本色**：主要#333333、次要#666666、占位符#BFBFBF

### 尺寸规范
- **按钮高度**：32px
- **输入框高度**：32px  
- **表格行高**：44px
- **圆角**：4px（按钮、输入框、卡片）

### 使用建议
1. 优先使用组件库中的标准组件
2. 严格按照设计规范使用颜色和尺寸
3. 遵循推荐的组件组合模式
4. 保持整体设计的一致性

## 更新维护

- **版本**：1.0.0
- **更新时间**：2026-04-14
- **维护者**：CDS产品设计团队

当CDS设计规范更新时，请同步更新此目录下的相关文件。