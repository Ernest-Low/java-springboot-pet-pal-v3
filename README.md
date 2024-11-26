# Pet Pal Backend Server v3

This project is an enhanced version of the backend server [originally developed](https://github.com/Ernest-Low/java-springboot-pet-pal) as part of a group project at NTU. Built using **Java** and **Spring Boot**, this server provides a robust and scalable backend for the **Pet Pal** application. The improvisations in this version focus on improving security, performance, and database handling.

## Features

### **1. JSON Web Token (JWT) Authentication**

- Implemented JWT-based authentication to ensure secure access to APIs.
- Enhanced token generation and validation to prevent unauthorized access.

### **2. Improved API Functionality**

- Enhanced existing APIs to optimize functionality and align with application requirements.
- Resolved issues in the API layer that were previously unrelated to the application’s purpose.
- Improved error handling and response structures for better debugging and user feedback.

### **3. Relational Database Schema Optimization**

- Revamped the database schema to improve relational data handling.
- Normalized tables to eliminate redundancy and ensure consistency.
- Enhanced support for complex relationships and efficient querying.

### **4. Bug Fixes and Performance Improvements**

- Fixed bugs and performance issues identified in the original implementation.
- Conducted rigorous testing to ensure the stability and reliability of the backend.

## Technology Stack

- **Programming Language**: Java
- **Framework**: Spring Boot
- **Database**: PostgreSQL
- **Authentication**: JWT
- **Build Tool**: Maven


# API Documentation

## Authentication

### POST `/api/auth/register`

**Request Body**:

```json
{
  "name": "String", // Required
  "areaLocation": "String", // Required
  "email": "String", // Required
  "password": "String" // Required
}
```

**Response Body**

```json
{
  "id": "Long",
  "name": "String",
  "areaLocation": "String",
  "email": "String",
  "jwtToken": "String"
}
```

### POST `/api/auth/login`

**Request Body**:

```json
{
  "email": "String", // Required
  "password": "String" // Required
}
```

**Response Body**:

```json
{
  "id": "Number",
  "name": "String",
  "areaLocation": "String",
  "email": "String",
  "jwtToken": "String"
}
```

## Public

### GET `/api/public/owners`

**Response Body**:

```json
[
  {
    "id": "Long",
    "name": "String",
    "areaLocation": "String",
    "email": "String"
  }
]
```

### GET `/api/public/owners/{id}`

**Response Body**

```json
{
  "id": "Long",
  "name": "String",
  "areaLocation": "String",
  "email": "String",
  "pets": "Pet[]" // TODO
}
```

### GET `/api/public/pets`

**Response Body**

```json
[
  {
    "id": "Long",
    "name": "String",
    "gender": "Enum MALE/FEMALE",
    "age": "Integer",
    "areaLocation": "String", // From owner
    "pictures": "String" // Sends the first in array
  }
]
```

### GET `/api/public/pets/{id}`

**Response Body**

```json
{
  "id": "Long",
  "name": "String",
  "gender": "Enum",
  "age": "Integer",
  "pictures": ["String"],
  "ownerId": "Long", // From owner
  "areaLocation": "String" // From owner
}
```

## Owners

### PATCH `/api/owners/{id}`

#### Authorization: Requires JWT Token.

**Request Body**

```json
{
  "name": "String", // Not Blank if present
  "areaLocation": "String", // Not Blank if present
  "email": "String", // Not Blank if present
  "oldPassword": "String", // Not Blank if present, also requires newPassword and confirmPassword
  "newPassword": "String", // Not Blank if present, also requires oldPassword and confirmPassword
  "confirmPassword": "String" // Not Blank if present, also requires oldPassword and newPassword
}
```

**Response Body**

```json
{
  "id": "Long",
  "name": "String",
  "areaLocation": "String",
  "email": "String",
  "jwtToken": "String"
}
```

### DELETE `/api/owners/{id}`

#### Authorization: Requires JWT Token

**Response**:
HttpStatus.OK

## Pets

### POST `/api/pets/new`

#### Authorization: Requires JWT Token.

**Request Body**

```json
{
  "name": "String", // Required
  "gender": "Enum", // Required
  "age": "Integer", // Required
  "isNeutered": "Boolean", // Required
  "description": "String", // Required
  "pictures": ["String"], // Required, at least 1
  "breed": "String", // Required
  "animalGroup": "String", // Required
  "ownerId": "Long" // Required
}
```

**Response Body**

```json
{
  "id": "Long",
  "name": "String",
  "gender": "Enum",
  "age": "Integer",
  "pictures": ["String"],
  "ownerId": "Long", // From owner
  "areaLocation": "String" // From owner
}
```

### PATCH `/api/pets/{id}`

#### Authorization: Requires JWT Token.

**Request Body**

```json
{
  "name": "String", // Not Blank if present
  "gender": "Enum", // Not Blank if present
  "age": "Integer", // Not Blank if present
  "isNeutered": "Boolean", // Not Blank if present
  "description": "String", // Not Blank if present
  "pictures": ["String"], // Not Blank if present, also no empty array
  "breed": "String", // Not Blank if present
  "animalGroup": "String" // Not Blank if present
}
```

**Response Body**

```json
{
  "id": "Long",
  "name": "String",
  "gender": "Enum",
  "age": "Integer",
  "pictures": ["String"],
  "ownerId": "Long", // From owner
  "areaLocation": "String" // From owner
}
```

### DELETE `/api/pets/{id}`

#### Authorization: Requires JWT Token

**Response**:
HttpStatus.OK
