## 删除公告

### 接口功能

删除指定公告（逻辑删除）。只有状态为DRAFT、REVOKED或PENDING的公告可以删除。删除后状态变为DELETED。

### 接口原型

请求方法：DELETE

请求路径：/api/announcements/{id}

### 请求参数

| 参数 | 必选/可选 | 类型    | 描述   |
| ---- | --------- | ------- | ------ |
| id   | 必选      | integer | 公告ID |

### 响应参数

| 参数      | 类型    | 描述                                                             |
| --------- | ------- | ---------------------------------------------------------------- |
| id        | integer | 公告ID                                                           |
| title     | string  | 标题                                                             |
| content   | string  | 内容                                                             |
| validFrom | string  | 生效日期，格式为YYYY-MM-DD                                       |
| validTo   | string  | 失效日期，格式为YYYY-MM-DD                                       |
| status    | string  | 状态，删除后为DELETED                                            |
| createdAt | string  | 创建时间，格式为YYYY-MM-DD HH:mm:ss                              |
| createdBy | string  | 创建人                                                           |
| updatedAt | string  | 更新时间，格式为YYYY-MM-DD HH:mm:ss                              |
| updatedBy | string  | 更新人                                                           |
| publishedAt | string | 发布时间，格式为YYYY-MM-DD HH:mm:ss，未发布时为null              |

### 错误码

| 错误码 | 描述         | 解决方案                                       |
| ------ | ------------ | ---------------------------------------------- |
| 400    | 业务状态异常 | 当前状态不允许删除，只有草稿、已撤销或待发布状态的公告可以删除 |
| 404    | 公告不存在   | 检查公告ID是否正确                             |

### 请求示例

```http
DELETE /api/announcements/1
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
    "status": "DELETED",
    "createdAt": "2025-10-30 14:37:34",
    "createdBy": "admin",
    "updatedAt": "2025-10-30 17:00:00",
    "updatedBy": "admin",
    "publishedAt": null
}
```
