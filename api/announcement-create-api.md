## 创建公告

### 接口功能

用户创建一条公告，系统返回公告详情

### 接口原型

请求方法：POST

请求路径：/api/announcements

### 请求参数

| 参数      | 必选/可选 | 类型   | 位置 | 描述                       |
| --------- | --------- | ------ | ---- | -------------------------- |
| title     | 必选      | string | body | 标题                       |
| content   | 必选      | string | body | 内容                       |
| validFrom | 必选      | string | body | 生效日期，格式为YYYY-MM-DD |
| validTo   | 必选      | string | body | 失效日期，格式为YYYY-MM-DD |

### 响应参数

| 参数      | 类型    | 描述                                                             |
| --------- | ------- | ---------------------------------------------------------------- |
| id        | integer | 公告ID                                                           |
| title     | string  | 标题                                                             |
| content   | string  | 内容                                                             |
| validFrom | string  | 生效日期，格式为YYYY-MM-DD                                       |
| validTo   | string  | 失效日期，格式为YYYY-MM-DD                                       |
| status    | string  | 状态，取值为DRAFT, PENDING, PUBLISHED, REVOKED, EXPIRED, DELETED |
| createdAt | string  | 创建时间                                                         |
| createdBy | string  | 创建人                                                           |
| updatedAt | string  | 更新时间                                                         |
| updatedBy | string  | 更新人                                                           |

### 错误码

| 错误码 | 描述         | 解决方案                 |
| ------ | ------------ | ------------------------ |
| 400    | 请求参数错误 | 检查请求参数是否符合要求 |

### 请求示例

```http
POST /api/announcements
Content-Type: application/json

{
    "title": "Test Announcement",
    "content": "This is a test announcement",
    "validFrom": "2025-01-01",
    "validTo": "2025-10-29"
}
```

### 响应示例

```http
201 Created
Location: /api/announcements/1
Content-Type: application/json

{
    "id": 2,
    "title": "Test Announcement",
    "content": "This is a test announcement",
    "validFrom": "2025-01-01",
    "validTo": "2025-10-29",
    "status": "DRAFT",
    "createdAt": "2025-10-30 14:37:34",
    "createdBy": "admin",
    "updatedAt": "2025-10-30 14:37:34",
    "updatedBy": "admin",
    "publishedAt": null
}
```
