# Api Specification

## Endpoints

### Login

```http
POST /api/login
```

Required parameters: **username, password**

### Users

#### List all Users

```http
GET /api/users
```

#### Get User

```http
GET /api/users/{id}
```

#### Create User

```http
POST /api/users
```

Required parameters: **username, password, role**

#### Update User

```http
PUT /api/users/{id}
```

Optional parameters: **username, role**

#### Delete User

```http
DELETE /api/users/{id}
```

### Notes

#### List all Notes

```http
GET /api/notes
```

#### Get Note

```http
GET /api/note/{id}
```

#### Create Note

```http
POST /api/notes
```

Required parameters: **title, content, shared**

#### Update Note

```http
PUT /api/notes/{id}
```

Optional parameters: **title, content, shared**

#### Delete Note

```http
DELETE /api/notes/{id}
```

### Two Factor Authentication

#### Enable

```http
POST /api/2fa
```

#### Disable

```http
DELETE /api/2fa
```

#### Check if enabled

```http
GET /api/2fa
```

