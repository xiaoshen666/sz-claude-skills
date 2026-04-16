# 前端国际化资源生成指南

## 概述

本文档定义前端国际化资源的生成规范。

## 国际化配置示例

```ts
// src/locales/zh-CN.ts
export default {
  common: {
    search: '搜索',
    reset: '重置',
    add: '新增',
    edit: '编辑',
    delete: '删除',
  },

  {模块名}: {
    title: '{功能模块}管理',
    list: '{功能模块}列表',
    name: '名称',
    code: '编码',
  },
};
```

## 组件中使用国际化

```tsx
import { useIntl } from 'react-intl';

const Component: React.FC = () => {
  const intl = useIntl();
  
  return (
    <div>
      <h1>{intl.formatMessage({ id: 'user.title' })}</h1>
    </div>
  );
};
```

## 生成规范

1. 所有用户可见文案必须国际化
2. 使用模块化的 key 命名
3. 支持多语言切换
4. 使用 react-intl 库
