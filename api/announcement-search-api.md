## 搜索公告

### 接口功能

根据条件搜索公告列表，支持分页。可以通过标题（模糊匹配）、状态、生效日期范围、失效日期范围进行筛选。

### 接口原型

请求方法：GET

请求路径：/api/announcements

### 请求参数

| 参数      | 必选/可选 | 类型    | 位置  | 描述                                                             |
| --------- | --------- | ------- | ----- | ---------------------------------------------------------------- |
| title     | 可选      | string  | query | 标题关键词（模糊匹配）                                           |
| status    | 可选      | string  | query | 状态，取值为DRAFT, PENDING, PUBLISHED, REVOKED, EXPIRED, DELETED |
| validFrom | 可选      | string  | query | 生效日期，格式为YYYY-MM-DD，筛选生效日期大于等于该值的公告       |
| validTo   | 可选      | string  | query | 失效日期，格式为YYYY-MM-DD，筛选失效日期小于等于该值的公告       |
| page      | 可选      | integer | query | 页码，从0开始，默认为0                                           |
| size      | 可选      | integer | query | 每页数量，默认为20                                               |

### 响应参数

响应为Spring Data的分页对象，包含以下字段：

| 参数             | 类型    | 描述                           |
| ---------------- | ------- | ------------------------------ |
| content          | array   | 公告列表，每个元素包含以下字段 |
| pageable         | object  | 分页信息                       |
| totalPages       | integer | 总页数                         |
| totalElements    | integer | 总记录数                       |
| last             | boolean | 是否为最后一页                 |
| size             | integer | 每页数量                       |
| number           | integer | 当前页码                       |
| sort             | object  | 排序信息                       |
| numberOfElements | integer | 当前页记录数                   |
| first            | boolean | 是否为第一页                   |
| empty            | boolean | 是否为空                       |

#### content中每个公告对象包含的字段：

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

| 错误码 | 描述         | 解决方案                 |
| ------ | ------------ | ------------------------ |
| 400    | 请求参数错误 | 检查请求参数是否符合要求 |

### 请求示例

```http
GET /api/announcements?title=Test&status=PUBLISHED&validFrom=2025-01-01&validTo=2025-12-31&page=0&size=10
```

### 响应示例

```http
200 OK
Content-Type: application/json

{
    "content": [
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
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 10,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 1,
    "first": true,
    "empty": false
}
```

