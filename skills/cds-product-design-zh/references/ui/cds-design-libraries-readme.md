# CDS 企业管理系统 组件库使用说明

## 1. 设计系统概述

基于 CDS 企业管理系统设计风格，极简蓝白风格，主色 `#1677FF`，适用于企业管理平台 Web 端。

### 核心颜色

| 变量名 | 色值 | 用途 |
|--------|------|------|
| `$--cds-primary` | `#1677FF` | 主色、主按钮、链接、选中态 |
| `$--cds-primary-hover` | `#0958D9` | 主色悬停状态 |
| `$--cds-danger` | `#FF4D4F` | 危险操作、删除按钮 |
| `$--cds-success` | `#52C41A` | 成功状态 |
| `$--cds-warning` | `#FAAD14` | 警告状态 |
| `$--cds-text-primary` | `#333333` | 主要文字 |
| `$--cds-text-secondary` | `#666666` | 次要文字 |
| `$--cds-text-placeholder` | `#BFBFBF` | 占位符文字 |
| `$--cds-border` | `#D9D9D9` | 组件边框 |
| `$--cds-divider` | `#E8E8E8` | 分割线颜色 |
| `$--cds-bg-page` | `#F5F5F5` | 页面背景 |
| `$--cds-bg-card` | `#FFFFFF` | 卡片/容器背景 |
| `$--cds-bg-header` | `#FAFAFA` | 表头背景 |
| `$--cds-table-hover` | `#F0F7FF` | 表格行悬停背景 |
| `$--cds-link-danger` | `#FF4D4F` | 危险链接文字 |
| `$--cds-white` | `#FFFFFF` | 纯白色 |

### 关键尺寸

| 用途 | 值 |
|------|-----|
| 按钮高度（默认） | 32px |
| 输入框高度 | 32px |
| 表格行高 | 44px |
| 顶部标题栏高度 | 40px |
| 工具栏高度 | 48px |
| 侧边栏宽度 | 220px |
| 卡片圆角 | 4px |
| 输入框圆角 | 4px |
| 按钮圆角 | 4px |
| 对话框圆角 | 8px |

---

## 2. 组件清单

### 布局容器类
`TopNavigation` ⭐ `Sidebar` `Breadcrumb` `Card` ⭐ `Tabs` `Layout` `ColLayout`

### 表单组件类
`FormInput` ⭐ `FormInputFilled` `FormTextarea` `FormSelect` ⭐ `ConditionSelect` `SearchInput` `Switch` `Checkbox` `RadioButton` `DatePicker` `FormMultiSelect` `TimePicker` `Slider` `FormTip`

### 按钮组件类
`PrimaryButton` ⭐ `SecondaryButton` ⭐ `DangerButton` `TextButton` `TextButtonDanger` `IconButton` `ButtonGroup` `DropdownButton`

### 数据展示类
`DataTable` ⭐ `Pagination` ⭐ `StatusTag` ⭐ `EmptyState` `StatisticCard` `Tag` `Progress`

### 反馈组件类
`Toast` `Modal` ⭐ `Confirm` `Step` `Skeleton` `Loading` `Notification`

### 辅助组件类
`Icon` `Divider` `Tooltip` `Upload` `Tree` `ApprovalLog`

> ⭐ 为 12 个核心组件，所有场景均需包含

---

## 3. 快速开始

1. **打开文件** — 用 Pencil 打开 `components.lib.pen`
2. **查阅风格** — 参考 `STYLE.md` 了解颜色、字体、圆角等设计规范
3. **复用组件** — 从对应分类区域复制所需组件到新设计文档
4. **保持一致** — 使用文件内置变量（`$--cds-primary`、`$--cds-text-primary` 等），勿手动填色

### 常见组合

| 页面类型 | 推荐组件组合 |
|----------|-------------|
| **列表页** | TopNavigation + Card + SearchInput + DataTable + Pagination + StatusTag |
| **详情页** | Breadcrumb + Card + StatisticCard + Tabs + ApprovalLog |
| **表单页** | Card + FormInput + FormSelect + DatePicker + PrimaryButton + SecondaryButton |
| **仪表盘** | TopNavigation + Sidebar + StatisticCard + Progress + DataTable + Tag |

---

## 4. 注意事项

- 所有颜色优先使用变量引用（`$--cds-primary` 等），方便主题切换
- 按钮状态：主按钮禁用色为 `#C9CDD4`，次要按钮禁用边框同色
- StatusTag 颜色语义：绿色=成功/通过，橙色=待处理/审核中，红色=失败/拒绝，蓝色=进行中，灰色=草稿
- 表格操作列统一使用文字按钮（TextButton）样式，间距 8px
- 输入框聚焦态：边框变为 `$--cds-primary`，外层添加 `2px` 蓝色光晕
- 所有交互组件边角为 4px 圆角
- 图标简洁线条风格，无填充
- 搜索区与表格区之间有明确视觉分层
- 操作按钮使用颜色语义区分：蓝色=常规操作，红色=危险操作