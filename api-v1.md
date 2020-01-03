# Api Version 1

## Endpoints

### Login

```http
POST /v1/login
```

Required parameters: **username, password**

### Users

#### List all Users

```http
GET /v1/users
```

#### Get User

```http
GET /v1/users/{id}
```

#### Create User

```http
POST /v1/users
```

Required parameters: **username, password, role**

#### Update User

```http
PUT /v1/users/{id}
```

Optional parameters: **username, role**

#### Delete User

```http
DELETE /v1/users/{id}
```

