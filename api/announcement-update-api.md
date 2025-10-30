## 更新公告

### 接口功能

更新指定公告的标题、内容、生效日期和失效日期。只有状态为DRAFT、REVOKED或PENDING的公告可以更新。

### 接口原型

请求方法：PUT

请求路径：/api/announcements/{id}

### 请求参数

#### 路径参数

| 参数 | 必选/可选 | 类型    | 描述   |
| ---- | --------- | ------- | ------ |
| id   | 必选      | integer | 公告ID |

#### 请求体参数

| 参数      | 必选/可选 | 类型   | 描述                       |
| --------- | --------- | ------ | -------------------------- |
| title     | 必选      | string | 标题                       |
| content   | 必选      | string | 内容                       |
| validFrom | 必选      | string | 生效日期，格式为YYYY-MM-DD |
| validTo   | 必选      | string | 失效日期，格式为YYYY-MM-DD |

### 响应参数

| 参数      | 类型    | 描述                                                             |
| --------- | ------- | ---------------------------------------------------------------- |
| id        | integer | 公告ID                                                           |
| title     | string  | 标题                                                             |
| content   | string  | 内容                                                             |
| validFrom | string  | 生效日期，格式为YYYY-MM-DD                                       |
| validTo   | string  | 失效日期，格式为YYYY-MM-DD                                       |
| status    | string  | 状态，取值为DRAFT, PENDING, PUBLISHED, REVOKED, EXPIRED, DELETED |
| createdAt | string  | 创建时间，格式为YYYY-MM-DD HH:mm:ss                              |
| createdBy | string  | 创建人                                                           |
| updatedAt | string  | 更新时间，格式为YYYY-MM-DD HH:mm:ss                              |
| updatedBy | string  | 更新人                                                           |
| publishedAt | string | 发布时间，格式为YYYY-MM-DD HH:mm:ss，未发布时为null              |

### 错误码

| 错误码 | 描述         | 解决方案                                       |
| ------ | ------------ | ---------------------------------------------- |
| 400    | 请求参数错误 | 检查请求参数是否符合要求                       |
| 400    | 业务状态异常 | 当前状态不允许更新，只有草稿、已撤销或待发布状态的公告可以编辑 |
| 404    | 公告不存在   | 检查公告ID是否正确                             |

### 请求示例

```http
PUT /api/announcements/1
Content-Type: application/json

{
    "title": "Updated Announcement",
    "content": "This is an updated announcement",
    "validFrom": "2025-01-01",
    "validTo": "2025-12-31"
}
```

### 响应示例

```http
200 OK
Content-Type: application/json

{
    "id": 1,
    "title": "Updated Announcement",
    "content": "This is an updated announcement",
    "validFrom": "2025-01-01",
    "validTo": "2025-12-31",
    "status": "DRAFT",
    "createdAt": "2025-10-30 14:37:34",
    "createdBy": "admin",
    "updatedAt": "2025-10-30 16:20:15",
    "updatedBy": "admin",
    "publishedAt": null
}
```
