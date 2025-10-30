# 1. 公告管理系统API文档

## 1.1. 创建公告

### 1.1.1. 请求示例

```http
POST http://localhost:8080/api/announcements HTTP/1.1
Content-Type: application/json

{
  "title": "测试公告",
  "content": "这是一个测试公告的内容。",
  "validFrom": "2023-05-01",
  "validTo": "2024-09-25",
  "createdBy": "admin"
}
```

### 1.1.2. 响应示例

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "title": "测试公告",
  "content": "这是一个测试公告的内容。",
  "validFrom": "2023-05-01",
  "validTo": "2024-09-25",
  "status": "DRAFT",
  "createdAt": "2023-05-01 10:00:00",
  "createdBy": "admin",
  "updatedAt": "2023-05-01 10:00:00",
  "updatedBy": "admin",
  "publishedAt": null
}
```

## 1.2. 编辑公告

### 1.2.1. 请求示例

```http
PUT http://localhost:8080/api/announcements/1 HTTP/1.1
Content-Type: application/json

{
  "title": "更新后的测试公告",
  "content": "这是更新后的测试公告内容。",
  "validFrom": "2023-05-01",
  "validTo": "2023-06-30",
  "updatedBy": "admin"
}
```

### 1.2.2. 响应示例

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "title": "更新后的测试公告",
  "content": "这是更新后的测试公告内容。",
  "validFrom": "2023-05-01",
  "validTo": "2023-06-30",
  "status": "DRAFT",
  "createdAt": "2023-05-01 10:00:00",
  "createdBy": "admin",
  "updatedAt": "2023-05-01 11:00:00",
  "updatedBy": "admin",
  "publishedAt": null
}
```

## 1.3. 发布公告

### 1.3.1. 请求示例

```http
POST http://localhost:8080/api/announcements/1:publish HTTP/1.1
Content-Type: application/json

{
  "publishedBy": "admin"
}
```

### 1.3.2. 响应示例

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "title": "更新后的测试公告",
  "content": "这是更新后的测试公告内容。",
  "validFrom": "2023-05-01",
  "validTo": "2023-06-30",
  "status": "PUBLISHED",
  "createdAt": "2023-05-01 10:00:00",
  "createdBy": "admin",
  "updatedAt": "2023-05-01 12:00:00",
  "updatedBy": "admin",
  "publishedAt": "2023-05-01 12:00:00"
}
```

## 1.4. 撤销公告

### 1.4.1. 请求示例

```http
POST http://localhost:8080/api/announcements/1:revoke HTTP/1.1
Content-Type: application/json

{
  "revokedBy": "admin"
}
```

### 1.4.2. 响应示例

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "title": "更新后的测试公告",
  "content": "这是更新后的测试公告内容。",
  "validFrom": "2023-05-01",
  "validTo": "2023-06-30",
  "status": "REVOKED",
  "createdAt": "2023-05-01 10:00:00",
  "createdBy": "admin",
  "updatedAt": "2023-05-01 13:00:00",
  "updatedBy": "admin",
  "publishedAt": "2023-05-01 12:00:00"
}
```

## 1.5. 删除公告（逻辑删除）

### 1.5.1. 请求示例

```http
DELETE http://localhost:8080/api/announcements/1 HTTP/1.1
```

### 1.5.2. 响应示例

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "title": "更新后的测试公告",
  "content": "这是更新后的测试公告内容。",
  "validFrom": "2023-05-01",
  "validTo": "2023-06-30",
  "status": "DELETED",
  "createdAt": "2023-05-01 10:00:00",
  "createdBy": "admin",
  "updatedAt": "2023-05-01 14:00:00",
  "updatedBy": "system",
  "publishedAt": "2023-05-01 12:00:00"
}
```

## 1.6. 查看公告详情

### 1.6.1. 请求示例

```http
GET http://localhost:8080/api/announcements/1 HTTP/1.1
```

### 1.6.2. 响应示例

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "title": "更新后的测试公告",
  "content": "这是更新后的测试公告内容。",
  "validFrom": "2023-05-01",
  "validTo": "2023-06-30",
  "status": "PUBLISHED",
  "createdAt": "2023-05-01 10:00:00",
  "createdBy": "admin",
  "updatedAt": "2023-05-01 12:00:00",
  "updatedBy": "admin",
  "publishedAt": "2023-05-01 12:00:00"
}
```

## 1.7. 搜索公告

### 1.7.1. 请求示例

```http
GET http://localhost:8080/api/announcements/search?title=测试&status=PUBLISHED&validFrom=2023-05-01&validTo=2023-06-30&page=0&size=10 HTTP/1.1
```

### 1.7.2. 响应示例

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "content": [
    {
      "id": 1,
      "title": "更新后的测试公告",
      "content": "这是更新后的测试公告内容。",
      "validFrom": "2023-05-01",
      "validTo": "2023-06-30",
      "status": "PUBLISHED",
      "createdAt": "2023-05-01 10:00:00",
      "createdBy": "admin",
      "updatedAt": "2023-05-01 12:00:00",
      "updatedBy": "admin",
      "publishedAt": "2023-05-01 12:00:00"
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
