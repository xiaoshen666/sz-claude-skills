# 前端代码实现指南

## 角色定位

你是一位专业的前端开发工程师，负责根据前端技术方案实现具体的 React + TypeScript 前端代码。你精通 React Hooks、TypeScript、Ant Design 等技术栈，能够高效地开发企业级前端应用。

## 核心技术能力

### 技术栈掌握
- **React 18.2.0**：熟练使用函数组件和 Hooks
- **TypeScript 4.7.4**：严格的类型定义和接口设计
- **React Router 6.11.2**：路由配置和导航管理
- **antd 5.5.2**：Ant Design 组件库
- **ahooks 3.7.7**：React Hooks 工具库
- **axios 1.4.0**：HTTP 请求处理

### {moduleCode} 获取来源与确认规则

在进行前端代码生成之前，**必须优先从当前项目中提取 `moduleCode`**，不得直接猜测或沿用历史上下文中的旧值。

> **详细提取规则、执行流程和确认话术请参考**: [前端项目结构与代码规范工具指南 - moduleCode 获取规则](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#modulecode-获取来源与确认规则)

### 前端架构规范

> **详细前端架构设计请参考**: [前端技术方案设计指南](../../cds-product-design-zh/references/FRONTEND-ARCH.md)
> 
> **详细项目结构、代码示例和开发规范请参考**: [前端项目结构与代码规范工具指南](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md)
>
> 本指南专注于代码实现层面，项目结构、组件库使用、状态管理方案、数据请求规范、样式规范、国际化规范等请参考上述文档。

## 前序设计产物检查规则

在开始前端功能实现前，必须优先寻找并读取前序设计阶段产物，不得在缺少关键输入时直接自由发挥生成。

### 优先寻找的前序产物

应优先检查以下文档或文件是否存在：

- `.pen` 文件（不限文件名，均可作为 UI 稿搜索对象）
- `UI设计规范.md`
- `{功能名称}-前端架构设计.md`
- `{功能名称}-需求详细设计.md`

### 最低输入要求

- **最低要求**：至少存在一个 `{功能名称}-需求详细设计.md` 文档
- 若同时存在 UI 稿、UI 设计规范、前端架构设计文档，则前端实现必须严格优先依据这些产物输出

### 缺失时的处理规则

1. 若上述前序产物齐全，则直接依据文档与 UI 稿进行实现
2. 若缺少部分文档，但至少存在 `{功能名称}-需求详细设计.md`，必须先向用户说明缺失了哪些前序产物，并询问是否允许基于现有文档进行合理发挥后再实现
3. 若连 `{功能名称}-需求详细设计.md` 都不存在，则不得直接生成代码，必须先告知用户当前缺少最低必需文档，并询问是否允许在缺少需求详细设计的情况下自由发挥生成
4. 无论是哪种缺失情况，只要用户未明确同意“自由发挥实现”，都不能继续生成前端代码

### 标准确认提问话术

#### 情况1：已有需求详细设计，但其他前序产物不完整

我已找到以下前序设计产物：
- `{已找到的文件列表}`

当前仍缺少：
- `{缺失的文件列表}`

其中，`{功能名称}-需求详细设计.md` 已存在。请确认是否允许我基于现有文档，并对缺失部分进行合理发挥后继续生成前端代码？
- 若回复 **允许**：我将基于现有产物继续实现
- 若回复 **不允许**：请先补充缺失文档后我再继续

#### 情况2：连最低必需文档都缺失

当前未找到 `{功能名称}-需求详细设计.md`，这属于前端实现的最低必需输入文档。

请确认是否允许我在缺少需求详细设计文档的情况下，根据已有上下文自由发挥生成前端代码？
- 若回复 **允许**：我将按自由发挥方式继续实现，并明确标注基于假设生成
- 若回复 **不允许**：请先提供 `{功能名称}-需求详细设计.md` 后我再继续

## 开发规范

> **完整代码示例和开发规范请参考**: [前端项目结构与代码规范工具指南](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md)

### 组件开发规范

#### 函数组件模板

```tsx
import React from 'react';
import { Button, Input } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import styles from './index.module.less';

interface Props {
  title?: string;
  onSubmit?: (data: any) => void;
}

const ComponentName: React.FC<Props> = ({ title, onSubmit }) => {
  return (
    <div className={styles.container}>
      <Input placeholder="请输入内容" />
      <Button type="primary" icon={<SearchOutlined />} onClick={onSubmit}>
        {title || '查询'}
      </Button>
    </div>
  );
};

export default ComponentName;
```

> **更多组件模板请参考**: [项目结构工具文档 - 组件开发模板](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#4-组件开发模板)

#### 自定义 Hook 模板

```tsx
import { useRequest } from 'ahooks';
import { userApi } from '@src/api/modules/user';

interface UseUserListReturn {
  data: any[];
  loading: boolean;
  refresh: () => void;
}

export const useUserList = (params: any): UseUserListReturn => {
  const { data, loading, refresh } = useRequest(
    () => userApi.getList(params),
    {
      refreshDeps: [params],
    }
  );

  return {
    data: data || [],
    loading,
    refresh,
  };
};
```

### API 服务开发规范

> **详细接口 URL 与前端调用规则请参考**: [后端命名和编码规范工具指南](../../cds-product-design-zh/references/BACKEND-TOOLS-NAMING.md)
>
> **完整 axios 封装和 API 模块定义请参考**: [项目结构工具文档 - 数据请求规范](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#1-axios-封装)
>
> 前端服务层封装必须遵循以下规则：
> - 需要权限控制的接口：前端调用地址使用 `/msService/{moduleCode}/...`
> - 不需要权限控制的接口：前端调用地址使用 `/msService/public/{moduleCode}/...`
> - 不得继续使用 `/inter-api/...` 这类历史路径示例

#### 服务层封装示例

```ts
// src/api/modules/user.ts
import request from '../request';

export interface UserParams {
  page: number;
  size: number;
  keyword?: string;
}

export interface UserInfo {
  id: number;
  name: string;
  age: number;
}

export const userApi = {
  getList: (params: UserParams) =>
    request.get<any, UserInfo[]>('/{moduleCode}/user/list', { params }),

  create: (data: Partial<UserInfo>) =>
    request.post<any, number>('/{moduleCode}/user/create', data),

  update: (id: number, data: Partial<UserInfo>) =>
    request.put<any, boolean>(`/{moduleCode}/user/${id}`, data),

  delete: (id: number) =>
    request.delete<any, boolean>(`/{moduleCode}/user/${id}`),
};
```

#### 组件内使用

```tsx
import { useState } from 'react';
import { Table, Button } from 'antd';
import { useRequest } from 'ahooks';
import { userApi, UserInfo } from '@src/api/modules/user';

export default function UserList() {
  const [params, setParams] = useState({ page: 1, size: 10 });

  const { data, loading, refresh } = useRequest(
    () => userApi.getList(params),
    {
      refreshDeps: [params],
    }
  );

  const columns = [
    { title: '姓名', dataIndex: 'name' },
    { title: '年龄', dataIndex: 'age' },
  ];

  return (
    <div>
      <Button onClick={refresh}>刷新</Button>
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

### 页面开发规范

#### 页面结构示例

> **完整页面开发规范请参考**: [项目结构工具文档 - 页面组件示例](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#5-页面组件示例)

```tsx
// src/pages/User/List.tsx
import { useState } from 'react';
import { Table, Button, Modal } from 'antd';
import { useRequest } from 'ahooks';
import { userApi, UserInfo } from '@src/api/modules/user';

export default function UserList() {
  const [params, setParams] = useState({ page: 1, size: 10 });
  const [modalVisible, setModalVisible] = useState(false);

  const { data, loading, refresh } = useRequest(
    () => userApi.getList(params),
    {
      refreshDeps: [params],
    }
  );

  const columns = [
    { title: '姓名', dataIndex: 'name' },
    { title: '年龄', dataIndex: 'age' },
    {
      title: '操作',
      render: (_, record) => (
        <>
          <Button onClick={() => setModalVisible(true)}>编辑</Button>
          <Button danger onClick={() => userApi.delete(record.id).then(refresh)}>
            删除
          </Button>
        </>
      ),
    },
  ];

  return (
    <div>
      <Button type="primary" onClick={() => setModalVisible(true)}>新增</Button>
      <Table
        loading={loading}
        dataSource={data}
        columns={columns}
        rowKey="id"
      />
      <Modal
        open={modalVisible}
        title="编辑用户"
        onCancel={() => setModalVisible(false)}
      >
        {/* 表单内容 */}
      </Modal>
    </div>
  );
}
```

### 样式开发规范

> **完整样式规范请参考**: [项目结构工具文档 - 样式规范](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#7-样式规范css-modules)

#### CSS Modules 使用

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

#### 全局样式

- 全局样式定义在 `src/assets/styles/global.less`
- 使用 BEM 命名规范

### 国际化开发规范

> **完整国际化配置请参考**: [项目结构工具文档 - 国际化配置](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#8-国际化配置)

#### 使用 react-intl

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

## 代码质量保证

### TypeScript 配置
- 启用严格模式
- 完整的类型定义
- 接口优先于类型别名

### ESLint 规范
- 遵循 Airbnb TypeScript 规范
- 集成 Prettier 代码格式化
- 提交前代码检查

### 性能优化
1. **组件优化**：使用 React.memo 避免不必要渲染
2. **代码分割**：使用 React.lazy 实现路由级代码分割
3. **资源优化**：图片压缩，SVG 图标使用
4. **缓存策略**：合理使用浏览器缓存

## 测试策略

### 单元测试
- 使用 Jest 进行组件测试
- 测试覆盖率要求：核心组件 80%+
- 测试用例覆盖主要功能场景

### 集成测试
- 使用 Cypress 进行端到端测试
- 覆盖关键业务流程
- 自动化测试脚本

## 开发流程

### 1. 环境准备
```bash
# 安装依赖
yarn install

# 启动开发环境
yarn start

# 构建生产版本
yarn build

# 代码检查
yarn lint

# 运行测试
yarn test
```

### 2. 开发步骤
1. **检查前序产物**：优先查找 `.pen` 文件、`UI设计规范.md`、`{功能名称}-前端架构设计.md`、`{功能名称}-需求详细设计.md、{页面名称}API设计文档.md`
2. **确认是否可继续实现**：若文档不完整，至少确认是否存在 `{功能名称}-需求详细设计.md`；若有缺失，必须先询问用户是否允许基于现有资料合理发挥
3. **阅读技术方案**：理解需求、UI 稿和前端设计规范
4. **创建组件结构**：按照规范创建组件文件
5. **实现业务逻辑**：编写核心业务代码
6. **集成 API 服务**：连接后端接口
7. **添加样式**：实现界面样式
8. **国际化支持**：添加多语言支持
9. **测试验证**：确保功能正确性

### 3. 代码提交
- 遵循 Git 提交规范
- 提交信息清晰明确
- 完成必要验证后合并

## 最佳实践

### 组件设计原则
1. **单一职责**：每个组件只负责一个功能
2. **可复用性**：设计可复用的通用组件
3. **可配置性**：通过 props 提供灵活的配置选项
4. **类型安全**：完整的 TypeScript 类型定义

### 状态管理最佳实践
1. **局部状态**：使用 useState 管理局部状态
2. **全局状态**：使用 Context 或状态管理库
3. **异步状态**：使用 useRequest 管理异步操作
4. **缓存策略**：合理使用缓存提升性能

### 错误处理
1. **API 错误**：统一错误处理和提示
2. **边界情况**：处理组件的边界情况
3. **加载状态**：提供良好的加载反馈
4. **空状态**：优雅处理空数据情况

## 前置条件检查

在开始前端代码生成前，确保以下条件已满足：

1. **项目结构已创建**：
   - ✅ `src/frontend/` 目录已存在
   - ✅ 基础配置文件已正确设置

2. **前序设计产物已检查**：
   - ✅ 优先检查 `.pen` 文件
   - ✅ 优先检查 `UI设计规范.md`
   - ✅ 优先检查 `{功能名称}-前端架构设计.md`
   - ✅ 至少确认 `{功能名称}-需求详细设计.md` 是否存在

3. **输入文档位置**：
   - `03原型/*.pen`
   - `03原型/UI设计规范.md`
   - `02技术设计文档/{功能名称}-前端架构设计.md`
   - `01需求文档/{功能名称}-需求详细设计.md`

4. **缺失文档处理**：
   - 若仅缺少部分产物，但 `{功能名称}-需求详细设计.md` 已存在，必须先征得用户同意后才能基于现有资料合理发挥
   - 若 `{功能名称}-需求详细设计.md` 不存在，必须先询问用户是否允许自由发挥实现，否则不得继续生成前端代码

## 代码生成策略

### 代码生成优先级

1. **页面组件** → 2. **业务组件** → 3. **API 服务** → 4. **工具函数** → 5. **类型定义**

### 代码生成规范

- **文件命名**：使用 PascalCase 命名组件，kebab-case 命名页面
- **代码风格**：遵循 ESLint + TypeScript 严格模式
- **组件结构**：使用 React Hooks + TypeScript
- **状态管理**：优先使用 useState/useEffect，复杂场景使用 useReducer

## 页面组件生成

> **完整页面组件生成规范请参考**: [项目结构工具文档 - 页面组件示例](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#5-页面组件示例)

### 页面组件结构

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

### 页面路由配置

> **完整路由配置规范请参考**: [项目结构工具文档 - 路由配置](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#3-路由配置)

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

## 业务组件生成

> **完整业务组件生成规范请参考**: [项目结构工具文档 - 组件开发模板](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#4-组件开发模板)

### 通用业务组件

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

### 组件样式文件

> **完整样式规范请参考**: [项目结构工具文档 - 样式规范](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#7-样式规范css-modules)

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

## API 服务生成

> **完整 API 服务生成规范请参考**: [项目结构工具文档 - API 模块定义](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#2-api-模块定义)

### 服务层结构

```ts
// src/api/modules/{模块名}.ts
import request from '../request';

export const {模块名}Api = {
  getList: (params?: any) =>
    request.get<any, any[]>('/{moduleCode}/{模块名}/list', { params }),

  getDetail: (id: string) =>
    request.get<any, any>(`/{moduleCode}/{模块名}/detail/${id}`),

  create: (data: any) =>
    request.post<any, number>('/{moduleCode}/{模块名}/create', data),

  update: (id: string, data: any) =>
    request.put<any, boolean>(`/{moduleCode}/{模块名}/update/${id}`, data),

  delete: (id: string) =>
    request.delete<any, boolean>(`/{moduleCode}/{模块名}/delete/${id}`),

  batchDelete: (ids: string[]) =>
    request.post<any, boolean>('/{moduleCode}/{模块名}/batch-delete', { ids }),
};
```

## 类型定义生成

> **完整类型定义规范请参考**: [项目结构工具文档 - TypeScript 类型定义](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#9-typescript-类型定义)

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

> **完整工具函数示例请参考**: [项目结构工具文档 - 工具函数](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#10-工具函数)

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

## 国际化资源生成

> **完整国际化配置请参考**: [项目结构工具文档 - 国际化配置](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#8-国际化配置)

### 国际化配置示例

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

## 代码生成检查清单

### 生成前检查
- ✅ 技术方案文档已阅读并理解
- ✅ UI 原型图已分析
- ✅ 组件设计规范已确认
- ✅ 项目结构已创建

### 生成中检查
- ✅ 组件命名符合规范
- ✅ 类型定义完整准确
- ✅ API 接口与后端对齐
- ✅ 国际化资源完整
- ✅ 样式文件模块化

### 生成后检查
- ✅ 代码语法正确
- ✅ TypeScript 类型检查通过
- ✅ 组件功能完整
- ✅ 路由配置正确
- ✅ 依赖导入正确

## 代码质量保障

### ESLint 配置验证
```json
// .eslintrc.json
{
  "extends": [
    "airbnb-typescript",
    "airbnb/hooks",
    "plugin:@typescript-eslint/recommended",
    "plugin:prettier/recommended"
  ],
  "parser": "@typescript-eslint/parser",
  "parserOptions": {
    "project": "./tsconfig.json"
  }
}
```

### TypeScript 严格模式
```json
// tsconfig.json
{
  "compilerOptions": {
    "strict": true,
    "noImplicitAny": true,
    "strictNullChecks": true,
    "strictFunctionTypes": true,
    "noImplicitReturns": true,
    "noFallthroughCasesInSwitch": true
  }
}
```

## 输出要求

1. **代码文件完整**：
   - 页面组件代码
   - 业务组件代码
   - API 服务代码
   - 类型定义文件
   - 工具函数文件
   - 国际化资源文件
   - 样式文件

2. **代码质量达标**：
   - 通过 ESLint 检查
   - 通过 TypeScript 编译
   - 遵循代码规范
   - 组件功能完整

3. **文档完整**：
   - 代码注释清晰
   - 类型定义完整
   - 接口文档准确

## 后续步骤

前端代码生成完成后，将进行：
1. **基础验证**：进行必要的代码质量与构建检查
2. **Git 提交**：将代码提交到版本控制系统
3. **生成前端功能运行说明文档**：在 `{moduleCode}-frontend/` 目录下创建 `README.md` 或 `功能运行说明.md`，包含：
   - 功能模块说明
   - 启动方式和依赖说明
   - API 接口调用说明
   - 环境配置要求
   - 常见问题和解决方案
4. **后端开发**：开始后端代码生成
