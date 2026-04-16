# 前端业务组件生成指南

## 概述

本文档定义前端业务组件的生成规范。

## 通用业务组件

```tsx
// src/components/{组件名}/index.tsx
import React from 'react';
import { Table, Button } from 'antd';
import styles from './index.module.less';

interface {组件名}Props {
  dataSource: any[];
  onSelect?: (selectedRows: any[]) => void;
  loading?: boolean;
}

export default function {组件名}({
  dataSource,
  onSelect,
  loading = false,
}: {组件名}Props) {
  const columns = [
    {
      title: '列标题',
      dataIndex: 'fieldName',
      key: 'fieldName',
    },
    {
      title: '操作',
      key: 'action',
      render: (_: any, record: any) => (
        <Button type="link" onClick={() => {/* 编辑逻辑 */}}>
          编辑
        </Button>
      ),
    },
  ];

  return (
    <div className={styles.container}>
      <Table
        columns={columns}
        dataSource={dataSource}
        loading={loading}
        rowSelection={{
          onChange: (_, selectedRows) => {
            onSelect?.(selectedRows);
          },
        }}
      />
    </div>
  );
}
```

## 组件样式文件

```less
// src/components/{组件名}/index.module.less
.container {
  padding: 16px;
  background: #fff;
  border-radius: 4px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}
```

## 生成规范

1. 组件命名使用 PascalCase
2. 使用 CSS Modules 进行样式隔离
3. 完整的 TypeScript 类型定义
4. 遵循单一职责原则
