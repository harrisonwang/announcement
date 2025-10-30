## 撤销公告

### 接口功能

撤销已发布的公告。只有状态为PUBLISHED的公告可以撤销。撤销后状态变为REVOKED。

### 接口原型

请求方法：PUT

请求路径：/api/announcements/{id}/revoke

### 请求参数

| 参数 | 必选/可选 | 类型    | 位置 | 描述   |
| ---- | --------- | ------- | ---- | ------ |
| id   | 必选      | integer | path | 公告ID |

### 响应参数

| 参数        | 类型    | 描述                                |
| ----------- | ------- | ----------------------------------- |
| id          | integer | 公告ID                              |
| title       | string  | 标题                                |
| content     | string  | 内容                                |
| validFrom   | string  | 生效日期，格式为YYYY-MM-DD          |
| validTo     | string  | 失效日期，格式为YYYY-MM-DD          |
| status      | string  | 状态，撤销后为REVOKED               |
| createdAt   | string  | 创建时间，格式为YYYY-MM-DD HH:mm:ss |
| createdBy   | string  | 创建人                              |
| updatedAt   | string  | 更新时间，格式为YYYY-MM-DD HH:mm:ss |
| updatedBy   | string  | 更新人                              |
| publishedAt | string  | 发布时间，格式为YYYY-MM-DD HH:mm:ss |

### 错误码

| 错误码 | 描述         | 解决方案                                         |
| ------ | ------------ | ------------------------------------------------ |
| 400    | 业务状态异常 | 当前状态不允许撤销，只有已发布状态的公告可以撤销 |
| 404    | 公告不存在   | 检查公告ID是否正确                               |

### 请求示例

```http
PUT /api/announcements/1/revoke
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
    "status": "REVOKED",
    "createdAt": "2025-10-30 14:37:34",
    "createdBy": "admin",
    "updatedAt": "2025-10-30 16:30:20",
    "updatedBy": "admin",
    "publishedAt": "2025-10-30 15:20:10"
}
```

