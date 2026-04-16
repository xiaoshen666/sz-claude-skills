# 前端类型定义和工具函数生成指南

## 概述

本文档定义前端类型定义和工具函数的生成规范。

## 类型定义生成

### TypeScript 类型定义

```ts
// src/types/{模块名}.ts
export interface {模块名} {
  id: number;
  name: string;
  code?: string;
  status: 'ACTIVE' | 'INACTIVE';
  createTime: string;
  updateTime: string;
}

export interface {模块名}QueryParams {
  page?: number;
  size?: number;
  name?: string;
  status?: string;
}

export interface ApiResponse<T> {
  code: number;
  message: string;
  data: T;
  success: boolean;
}

export interface ListResponse<T> {
  list: T[];
  total: number;
  page: number;
  size: number;
}
```

## 工具函数生成

### 通用工具函数

```ts
// src/utils/{功能}.ts
import dayjs from 'dayjs';
import { message } from 'antd';

// 格式化日期
export const formatDate = (date: string | Date, format = 'YYYY-MM-DD') => {
  return dayjs(date).format(format);
};

// 格式化日期时间
export const formatDateTime = (date: string | Date, format = 'YYYY-MM-DD HH:mm:ss') => {
  return dayjs(date).format(format);
};

// 显示成功消息
export const showSuccess = (msg: string) => {
  message.success(msg);
};

// 显示错误消息
export const showError = (msg: string) => {
  message.error(msg);
};
```

## 生成规范

1. 类型定义必须完整，包含所有可能的字段
2. 使用 interface 优先于 type
3. 工具函数必须具有明确的输入输出类型
4. 遵循 ESLint + TypeScript 严格模式
