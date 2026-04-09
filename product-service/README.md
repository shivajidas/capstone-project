# Product Service

A Spring Boot microservice for managing product data with OAuth2/JWT security using Okta.

## Overview

Product Service provides RESTful APIs for product management operations including creating, reading, updating, and deleting products. The service is secured with role-based access control using OAuth2 scopes.

## Configuration

| Property | Value |
|----------|-------|
| Port | 9093 |
| Application Name | product-service |

## Security

This service is secured using OAuth2 with Okta as the identity provider. Different operations require different scopes:

| HTTP Method | Endpoints | Required Scope |
|-------------|-----------|----------------|
| GET | `/api/product/**` | `user` |
| POST | `/api/product/**` | `admin` |
| PUT | `/api/product/**` | `admin` |
| DELETE | `/api/product/**` | `admin` |

## API Endpoints

### Get All Products
```
GET /api/product/getAllProducts
Authorization: Bearer {token_with_user_scope}
```
**Response:**
```json
{
  "Status": "OK",
  "Message": "Extracted product list",
  "ProductList": [
    {
      "id": 1,
      "name": "Product A",
      "price": 29.99
    }
  ]
}
```

### Get Product by ID
```
GET /api/product/getProductById/{id}
Authorization: Bearer {token_with_user_scope}
```
**Response:**
```json
{
  "message": "Extracted product",
  "product": {
    "id": 1,
    "name": "Product A",
    "price": 29.99
  }
}
```

### Get Multiple Products by IDs
```
GET /api/product/getAllProductsById?productIds=1,2,3
Authorization: Bearer {token_with_user_scope}
```
**Response:**
```json
{
  "message": "Extracted product",
  "products": [
    {
      "id": 1,
      "name": "Product A",
      "price": 29.99
    },
    {
      "id": 2,
      "name": "Product B",
      "price": 39.99
    }
  ]
}
```

### Add Product
```
POST /api/product/add-product
Authorization: Bearer {token_with_admin_scope}
Content-Type: application/json

{
  "name": "New Product",
  "price": 49.99
}
```
**Response:**
```json
{
  "message": "Product Added Successfully",
  "Product": {
    "id": 1,
    "name": "New Product",
    "price": 49.99
  }
}
```

### Update Product
```
PUT /api/product/update-product/{id}
Authorization: Bearer {token_with_admin_scope}
Content-Type: application/json

{
  "name": "Updated Product",
  "price": 59.99
}
```
**Response:**
```json
{
  "message": "Product Updated Successfully",
  "Product": {
    "id": 1,
    "name": "Updated Product",
    "price": 59.99
  }
}
```

### Delete Product
```
DELETE /api/product/delete-by-id/{id}
Authorization: Bearer {token_with_admin_scope}
```
**Response:**
```json
{
  "message": "Product with id 1 deleted successfully"
}
```

## Getting JWT Tokens

To access secured endpoints, obtain a JWT token from the Order Service login endpoint:

```bash
# Get token with 'user' scope (for GET operations)
POST http://localhost:9094/api/orders/login?scope=user

# Get token with 'admin' scope (for POST/PUT/DELETE operations)
POST http://localhost:9094/api/orders/login?scope=admin
```

## Running the Service

```bash
cd product-service
./mvnw spring-boot:run
```

## Dependencies

- Spring Boot 4.0.3
- Spring Security OAuth2 Resource Server
- Spring Data JPA
- MySQL Connector
- Lombok
