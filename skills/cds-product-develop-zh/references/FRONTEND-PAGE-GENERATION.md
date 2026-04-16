# 前端页面组件生成指南

## 概述

本文档定义前端页面组件的生成规范。

## 页面组件结构

```tsx
// src/pages/{模块名}/{页面名}.tsx
import React, { useState } from 'react';
import { Table, Button, Input } from 'antd';
import { useRequest } from 'ahooks';
import { {模块名}Api } from '@src/api/modules/{模块名}';
import styles from './index.module.less';

interface {页面名}Props {
  // 组件属性定义
}

export default function {页面名}(props: {页面名}Props) {
  const [params, setParams] = useState({ page: 1, size: 10 });

  const { data, loading, refresh } = useRequest(
    () => {模块名}Api.getList(params),
    {
      refreshDeps: [params],
    }
  );

  const columns = [
    { title: '名称', dataIndex: 'name' },
    {
      title: '操作',
      render: (_, record) => (
        <Button onClick={() => {/* 编辑逻辑 */}}>编辑</Button>
      ),
    },
  ];

  return (
    <div className={styles.container}>
      <Input placeholder="搜索" />
      <Button type="primary" onClick={refresh}>查询</Button>
      <Table
        loading={loading}
        dataSource={data}
        columns={columns}
        rowKey="id"
      />
    </div>
  );
}
```

## 页面路由配置

```tsx
// src/routes/index.tsx
import { lazy, Suspense } from 'react';
import { RouteObject } from 'react-router-dom';

const UserList = lazy(() => import('@src/pages/User/List'));

export const routes: RouteObject[] = [
  {
    path: '/{模块名}/list',
    element: (
      <Suspense fallback={<div>Loading...</div>}>
        <UserList />
      </Suspense>
    ),
    title: '{功能模块}列表',
  },
];
```

## 生成规范

1. 页面文件使用 kebab-case 命名
2. 组件使用 PascalCase 命名
3. 使用 React.lazy 实现路由级代码分割
4. 集成 API 服务和业务组件
