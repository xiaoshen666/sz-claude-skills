# 前端样式文件生成指南

## 概述

本文档定义前端样式文件的生成规范。

## CSS Modules 使用

```less
// src/pages/User/index.module.less
.container {
  padding: 16px;
}

.header {
  margin-bottom: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
```

## 全局样式

```less
// src/assets/styles/global.less
@import './variables.less';

body {
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 16px;
}
```

## 变量定义

```less
// src/assets/styles/variables.less
@primary-color: #1890ff;
@success-color: #52c41a;
@warning-color: #faad14;
@error-color: #f5222d;

@font-size-base: 14px;
@font-size-lg: 16px;
@font-size-sm: 12px;

@padding-base: 16px;
@padding-lg: 24px;
@padding-sm: 8px;
```

## 生成规范

1. 使用 CSS Modules 进行组件样式隔离
2. 全局样式定义在 `src/assets/styles/global.less`
3. 使用 BEM 命名规范
4. 提取公共变量到 `variables.less`
