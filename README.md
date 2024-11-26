# Employee Management System

## Overview
This project is a **Java-based web application** built with **Spring Boot** that manages an Employee Management System. It includes CRUD operations for employees, departments, and projects, with advanced features like **OAuth2.0 authentication** and caching. The application is designed for **secure, role-based access control** and interacts with a relational database for data persistence.

---

## Features

### 1. CRUD Operations
- **Employees**: Add, update, delete, and view employees.
- **Departments**: Add, update, delete, and view departments.
- **Projects**: Add, update, delete, and view projects.

### 2. Advanced Database Interaction
- **Search employees** by criteria (e.g., name, department, project).
- **Calculate total budget** for projects in a department.
- **Find employees not assigned** to any project.
- **Retrieve employees** assigned to a specific project.

### 3. OAuth2.0 and OpenID Connect Integration
- Authentication using **Authorization Code Flow** with an Identity Provider (Auth0).
- **Role-based access control**:
    - **ADMIN**: Full access to all functionalities.
    - **MANAGER**: Access to manage employees and projects in their department.
    - **EMPLOYEE**: Access to their own profile and projects.

### 4. Caching Implementation
- Frequently accessed data (e.g., employee lists, department details) is cached using **Spring Cache** with providers like **Redis**.
- **Cache invalidation** ensures data consistency.

### 5. Data Validation and Error Handling
- **Input validation** for all fields (e.g., non-null, correct formats).
- Meaningful **error messages** and appropriate **HTTP status codes**.


---

## Technologies Used
- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **OAuth2.0 / OpenID Connect**
- **MySQL**
- **Spring Cache** (Redis)

---

## Project Structure
```plaintext
src  
├── main  
│   ├── java  
│   │   ├── com.example.ems  
│   │   │   ├── controller      # REST controllers for API endpoints  
│   │   │   ├── service         # Business logic and caching  
│   │   │   ├── repository      # Database interactions (JPA repositories)  
│   │   │   ├── model           # Entity classes for Employees, Departments, Projects  
│   │   │   ├── config          # Security, OAuth2, and caching configurations  
│   ├── resources  
│       ├── application.prop     # Configuration file for database, security, etc.  



