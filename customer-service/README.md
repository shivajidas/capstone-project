# Customer Service

A Spring Boot microservice for managing customer data.

## Overview

Customer Service provides RESTful APIs for customer management operations including creating, reading, updating, and deleting customer records.

## Configuration

| Property | Value |
|----------|-------|
| Port | 9091 |
| Application Name | customer-service |

## API Endpoints

### Get All Customers
```
GET /api/customer/getAllCustomers
```
**Response:**
```json
{
  "Status": "OK",
  "Message": "Extracted customer list",
  "CustomerList": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john@example.com"
    }
  ]
}
```

### Get Customer by ID
```
GET /api/customer/getCustomerById/{id}
```
**Response:**
```json
{
  "message": "Extracted customer",
  "customer": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com"
  }
}
```

### Add Customer
```
POST /api/customer/add-customer
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com"
}
```
**Response:**
```json
{
  "message": "Customer Added Successfully",
  "Customer": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com"
  }
}
```

### Update Customer
```
PUT /api/customer/update-customer/{id}
Content-Type: application/json

{
  "name": "John Updated",
  "email": "john.updated@example.com"
}
```
**Response:**
```json
{
  "message": "Customer Updated Successfully",
  "Customer": {
    "id": 1,
    "name": "John Updated",
    "email": "john.updated@example.com"
  }
}
```

### Delete Customer
```
DELETE /api/customer/delete-by-id/{id}
```
**Response:**
```json
{
  "message": "Customer with id 1 deleted successfully"
}
```

## Running the Service

```bash
cd customer-service
./mvnw spring-boot:run
```

## Dependencies

- Spring Boot 4.0.3
- Spring Data JPA
- MySQL Connector
- Lombok
