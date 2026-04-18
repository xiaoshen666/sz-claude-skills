# 前端路由配置生成指南

## 概述

本文档定义前端路由配置的生成规范。

### 路由配置规范

#### App.tsx 标准结构
```typescript
import React from 'react'
import { HashRouter as Router, Routes, Route } from 'react-router-dom'
import {ModuleName} from './pages/{ModuleName}'

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<{ModuleName} />} />
        <Route path="/{modulePath}" element={<{ModuleName} />} />
      </Routes>
    </Router>
  )
}

export default App
```

**关键规则**：
1. **必须使用 HashRouter**：不使用 BrowserRouter
2. **路由路径**：根路径 `/` 和模块路径 `/{modulePath}` 都指向主页面组件
3. **组件命名**：页面组件放在 `./pages/{ModuleName}` 目录下

### API 请求路径规范

所有 API 请求必须使用 `/msService/{moduleCode}/...` 格式：

```typescript
// ✅ 正确示例
const API_BASE = '/msService/employeemgmt'
export const getEmployeeList = () => axios.get(`${API_BASE}/employee/list`)

// ❌ 错误示例（禁止使用）
const API_BASE = '/inter-api/employeemgmt'  // 历史路径，禁止使用
const API_BASE = '/api/employeemgmt'        // 缺少 msService 前缀
```

### 项目目录结构

```
{moduleCode}-frontend/
├── vite.config.ts              # Vite 配置（必须包含 base 和 proxy）
├── package.json
├── tsconfig.json
├── src/
│   ├── App.tsx                 # 应用入口（使用 HashRouter）
│   ├── main.tsx                # 挂载点
│   ├── pages/                  # 页面组件
│   │   └── {ModuleName}/
│   │       ├── index.tsx
│   │       └── index.module.less
│   ├── components/             # 业务组件
│   │   └── {ComponentName}/
│   │       ├── index.tsx
│   │       └── index.module.less
│   ├── api/                    # API 服务层
│   │   └── modules/
│   │       └── {moduleCode}.ts
│   ├── types/                  # TypeScript 类型定义
│   │   └── {moduleCode}.ts
│   └── utils/                  # 工具函数
└── ...
```

---


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
