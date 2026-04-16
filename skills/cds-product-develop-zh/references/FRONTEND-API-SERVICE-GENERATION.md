# 前端 API 服务层生成指南

## 概述

本文档定义前端 API 服务层的生成规范。

## 服务层结构

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

## API 路径规范

- 需要权限控制的接口：前端调用地址使用 `/msService/{moduleCode}/...`
- 不需要权限控制的接口：前端调用地址使用 `/msService/public/{moduleCode}/...`
- 不得继续使用 `/inter-api/...` 这类历史路径示例

## 生成规范

1. API 路径必须使用 `/msService/{moduleCode}/...` 格式
2. 每个 API 方法必须有明确的参数和返回类型
3. 遵循 RESTful API 设计规范
4. 与后端 API 设计文档保持一致
