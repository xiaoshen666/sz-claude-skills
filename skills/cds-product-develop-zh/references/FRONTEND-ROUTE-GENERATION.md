# 前端路由配置生成指南

## 概述

本文档定义前端路由配置的生成规范。

## 路由配置结构

```tsx
// src/routes/index.tsx
import { lazy, Suspense } from 'react';
import { RouteObject } from 'react-router-dom';

const UserList = lazy(() => import('@src/pages/User/List'));
const UserDetail = lazy(() => import('@src/pages/User/Detail'));

export const routes: RouteObject[] = [
  {
    path: '/user/list',
    element: (
      <Suspense fallback={<div>Loading...</div>}>
        <UserList />
      </Suspense>
    ),
    title: '用户列表',
  },
  {
    path: '/user/detail/:id',
    element: (
      <Suspense fallback={<div>Loading...</div>}>
        <UserDetail />
      </Suspense>
    ),
    title: '用户详情',
  },
];
```

## 路由守卫配置

```tsx
// src/routes/guards.tsx
import { Navigate } from 'react-router-dom';

export const requireAuth = (element: React.ReactNode) => {
  const isAuthenticated = checkAuth(); // 检查认证状态
  return isAuthenticated ? element : <Navigate to="/login" replace />;
};
```

## 生成规范

1. 使用 React.lazy 实现代码分割
2. 配置 Suspense 加载状态
3. 路由路径使用 kebab-case
4. 配置路由标题用于面包屑导航
