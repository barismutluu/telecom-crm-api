# 🚀 Mini Telecom CRM API

A Telecom CRM (Customer Relationship Management) REST API project developed using Spring Boot with a layered architecture.

This project represents the backend infrastructure of a real-world CRM system, covering customer management, subscription management, package (tariff) management, and support requests.

---

## 📌 Project Overview

With this API, you can:

- Create and manage customers
- Create subscriptions (lines) associated with customers
- Assign and change packages (tariffs) for subscriptions
- Allow customers to create support requests and close them
- Provide secure authentication using JWT

---

## 🧩 Use Cases

This system is suitable for the following scenarios:

- Telecom operator CRM systems
- Subscription management systems
- Backend learning and portfolio projects



---

## 🛠️ Technologies Used

- Java 21
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Lombok
- Swagger (OpenAPI)

---

## 🏗️  Project Architecture

```text
controller   → API layer
service      → business logic
repository   → database access
model        → entity classes
dto          → data transfer objects
config       → security & swagger configurations

```
---

## 🔥   Features

Customer Management

- Create customers
- List customers
- Delete customers

Subscription Management

- Create subscriptions for customers
- Manage subscriptions

Package (Tariff) Management

- Create packages
- List packages

Subscription ↔ Package Management

- Assign a package to a subscription
- Change package (the old package is closed, the new one starts)

Support Request (Ticket System)

- Create support requests
- List support requests
- Close support requests

Authentication (JWT)

- Register / Login system
- Token generation
- Access to protected endpoints
---

## ⚙️  Installation

1. Clone the project

```text
git clone https://github.com/barismutluu/telecom-crm-api.git
cd telecom-crm-api
```

2. Create a PostgreSQL database

```text
CREATE DATABASE telecom_crm_db;
```
3. Configure application.properties

```text
spring.datasource.url=jdbc:postgresql://localhost:5432/telecom_crm_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
---
### 🔐 Authentication (JWT)

Register

```text
POST /api/auth/register
```
Login
```text
POST /api/auth/login
```
---

### 📬 API Endpoints
Customer

| Method | Endpoint            |
| ------ | ------------------- |
| POST   | /api/customers      |
| GET    | /api/customers      |
| GET    | /api/customers/{id} |
| DELETE | /api/customers/{id} |

Subscription

| Method | Endpoint           |
| ------ | ------------------ |
| POST   | /api/subscriptions |
| GET    | /api/subscriptions |

Package

| Method | Endpoint      |
| ------ | ------------- |
| POST   | /api/packages |
| GET    | /api/packages |

Subscription Package

| Method | Endpoint                   |
| ------ | -------------------------- |
| POST   | /api/subscription-packages |

Support Request

| Method | Endpoint                         |
| ------ | -------------------------------- |
| POST   | /api/support-requests            |
| GET    | /api/support-requests            |
| PUT    | /api/support-requests/{id}/close |
