# Order Service

A Spring Boot microservice for managing orders with OAuth2/JWT security using Okta.

## Overview

Order Service provides RESTful APIs for order management operations including creating, reading, updating, and deleting orders. It also includes an authentication endpoint that returns JWT tokens from Okta OAuth2 server.

## Configuration

| Property | Value |
|----------|-------|
| Port | 9094 |
| Application Name | order-service |
| Config Server | http://localhost:8888 |

## Security

This service is secured using OAuth2 with Okta as the identity provider.

| Endpoint | Required Scope | Description |
|----------|----------------|-------------|
| `/api/orders/login` | None (Public) | Get JWT token |
| `/api/orders/**` | `user` | All order operations |
| `/actuator/**` | None (Public) | Health & metrics |

## API Endpoints

### Authentication

#### Login
```
POST /api/orders/login?scope={scope}
```
**Parameters:**
- `scope` (required): Either `user` or `admin`

**Response:**
```json
{
  "access_token": "eyJhbGci...",
  "token_type": "Bearer",
  "expires_in": 3600,
  "scope": "user",
  "message": "Login successful"
}
```

### Orders

#### Get All Orders
```
GET /api/orders
Authorization: Bearer {token}
```

#### Get Order by ID
```
GET /api/orders/{id}
Authorization: Bearer {token}
```

#### Create Order
```
POST /api/orders/createOrder
Authorization: Bearer {token}
Content-Type: application/json

{
  "customerId": 1,
  "productIds": [1, 2, 3],
  "totalAmount": 100.00
}
```

#### Update Order
```
PUT /api/orders/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "customerId": 1,
  "productIds": [1, 2],
  "totalAmount": 80.00
}
```

#### Delete Order
```
DELETE /api/orders/{id}
Authorization: Bearer {token}
```

## Features

- **OAuth2 JWT Security** - Secured with Okta OAuth2 resource server
- **Resilience4j Circuit Breaker** - Fault tolerance for product service calls
- **Spring Cloud Config** - Externalized configuration
- **Actuator** - Health checks and metrics monitoring

## Circuit Breaker Configuration

| Property | Value |
|----------|-------|
| Sliding Window Size | 10 |
| Minimum Number of Calls | 5 |
| Failure Rate Threshold | 50% |
| Wait Duration in Open State | 30s |
| Max Retry Attempts | 3 |

## Running the Service

```bash
cd order-service
./mvnw spring-boot:run
```

## Dependencies

- Spring Boot 3.4.3
- Spring Security OAuth2 Resource Server
- Spring Data JPA
- MySQL Connector
- Resilience4j
- Lombok
