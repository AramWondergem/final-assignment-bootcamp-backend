# Backend Wondergems

**Version:** 1.0  
**Author:** Aram Wondergem  

## Table of Contents

1. [Introduction](#introduction)
2. [Requirements](#requirements)
3. [Installation Steps](#installation-steps-to-run-backend-locally)
4. [Roles and Test Accounts](#roles-and-test-accounts)
5. [API Endpoints](#api-endpoints)

## Introduction

This document provides the installation guide for the backend of the Wondergems application. Wondergems is a web application that enables hobby chefs to send menus to friends and family and handle all aspects of menu orders and delivery.

This guide covers the necessary programs and steps to set up the backend. It also details the REST API endpoints available for the application, which include authentication, user management, order management, menu management, and file handling.


## Requirements

- **PostgreSQL & pgAdmin**: Open-source relational database management system. [Download PostgreSQL](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads).
- **IntelliJ IDEA**: Java IDE developed by JetBrains. [Download IntelliJ](https://www.jetbrains.com/idea/download).
- **Oracle OpenJDK 19**: Open-source Java Development Kit by Oracle.

## Installation Steps to Run Backend Locally

### 1. PostgreSQL and pgAdmin
1. Download and install PostgreSQL 15.1 from [EnterpriseDB](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads).
2. During installation, keep the settings default. Remember your password and port number.
3. Open pgAdmin and create a new database. Save the username and database name.

### 2. IntelliJ IDEA
1. Download and install IntelliJ IDEA from [here](https://www.jetbrains.com/idea/download).
2. Open the `wondergems-backend` folder and set the SDK to Oracle OpenJDK 19.0.2.
3. Update `application.properties` with your database settings:
   - `MY_UPLOAD_LOCATION=src/main/resources/uploads`
   - `spring.datasource.url=jdbc:postgresql://localhost:<port>/<database-name>`
   - `spring.datasource.username=<postgres-username>`
   - `spring.datasource.password=<postgres-password>`
4. Fill in the `TOKEN_SIGNATURE` in the `.env` file in the `resources` folder.
5. Build and run the backend.

### 3.Run the Frontend Locally
Follow the README of the [frontend](https://github.com/AramWondergem/final-assignement-bootcamp-frontend) of the web application.

## Roles and Test Accounts

| Username       | Password     | Roles           | Description                                   |
|----------------|--------------|-----------------|-----------------------------------------------|
| user@test.nl   | Hallo1test!  | USER            | A customer linked to cook@test.nl.            |
| cook@test.nl   | Hallo1test!  | USER, COOK      | A cook who can create and send menus.         |
| admin@test.nl  | Hallo1test!  | USER, COOK, ADMIN | Admin with full access.                     |

## API Endpoints

### Auth Controller
- **POST** `/api/v1/auth`: Get JWT token with valid credentials.

### User Controller
- **POST** `/api/v1/users`: Create a new user.
- **GET** `/api/v1/users`: Get user data or all users (admin).
- **PUT** `/api/v1/users`: Update user data.
- **DELETE** `/api/v1/users`: Delete user.

### Order Controller
- **POST** `/api/v1/orders`: Create a new order.
- **GET** `/api/v1/orders/{id}`: Get order details.
- **PUT** `/api/v1/orders/{id}`: Update order details.
- **DELETE** `/api/v1/orders/{id}`: Delete an order.

### Menu Controller
- **POST** `/api/v1/menus`: Create a new menu.
- **GET** `/api/v1/menus/{id}`: Get menu details.
- **PUT** `/api/v1/menus/{id}`: Update menu details.
- **DELETE** `/api/v1/menus/{id}`: Delete a menu.

### File Manager Controller
- **POST** `/api/v1/files`: Upload a file.
- **GET** `/api/v1/files/{filename}`: Retrieve a file.
- **DELETE** `/api/v1/files/{filename}`: Delete a file.

