## 获取公告详情

### 接口功能

根据公告ID获取公告的详细信息

### 接口原型

请求方法：GET

请求路径：/api/announcements/{id}

### 请求参数

| 参数 | 必选/可选 | 类型    | 位置 | 描述   |
| ---- | --------- | ------- | ---- | ------ |
| id   | 必选      | integer | path | 公告ID |

### 响应参数

| 参数        | 类型    | 描述                                                             |
| ----------- | ------- | ---------------------------------------------------------------- |
| id          | integer | 公告ID                                                           |
| title       | string  | 标题                                                             |
| content     | string  | 内容                                                             |
| validFrom   | string  | 生效日期，格式为YYYY-MM-DD                                       |
| validTo     | string  | 失效日期，格式为YYYY-MM-DD                                       |
| status      | string  | 状态，取值为DRAFT, PENDING, PUBLISHED, REVOKED, EXPIRED, DELETED |
| createdAt   | string  | 创建时间，格式为YYYY-MM-DD HH:mm:ss                              |
| createdBy   | string  | 创建人                                                           |
| updatedAt   | string  | 更新时间，格式为YYYY-MM-DD HH:mm:ss                              |
| updatedBy   | string  | 更新人                                                           |
| publishedAt | string  | 发布时间，格式为YYYY-MM-DD HH:mm:ss，未发布时为null              |

### 错误码

| 错误码 | 描述       | 解决方案           |
| ------ | ---------- | ------------------ |
| 404    | 公告不存在 | 检查公告ID是否正确 |

### 请求示例

```http
GET /api/announcements/1
```

### 响应示例

```http
200 OK
Content-Type: application/json

{
    "id": 1,
    "title": "Test Announcement",
    "content": "This is a test announcement",
    "validFrom": "2025-01-01",
    "validTo": "2025-10-29",
    "status": "PUBLISHED",
    "createdAt": "2025-10-30 14:37:34",
    "createdBy": "admin",
    "updatedAt": "2025-10-30 15:20:10",
    "updatedBy": "admin",
    "publishedAt": "2025-10-30 15:20:10"
}
```

