# 🌱 Spring Core - Gym CRM System

This project is a basic CRM designed to manage a gym using **Java 21 and Spring Core**, 
progressively enhanced with **JPA/Hibernate**, **RESTful**, and **mock-based testing**.

It demonstrates clean layering, explicit configuration, and a focus on maintainability and testability.

## 🧩 Features

- **Domain model** with joined inheritance for **Users**, **Trainees**, **Trainers**, 
Trainings, and TrainingTypes
- **Persistence layer** using Spring Data JPA with custom JpaRepository interfaces
- **Service layer** for business logic, validation, and transaction control
- **RESTful controllers** exposing endpoints for trainees, trainers, trainings, and authentication

- **Exception handling** via centralized @RestControllerAdvice with custom error responses
- **Input validation** using Hibernate Validator (@Valid, @NotNull, @Min, etc.)
- **Secure password encoding** with BCrypt
- **Initial data loading** via import.sql
- **Unit tests** for all controllers using MockMvc, Mockito, and JUnit 5



## 🛠 Stack
- **Java 21**
- **Maven 3.6+**
- **Spring Core**
- **Spring Web MVC**
- **Spring Data JPA**
- **Hibernate 7.0 (JPA Implementation)**
- **Hibernate Validator**
- **Jakarta Annotations & Validation API**
- **Jackson (JSON Serialization)**
- **JUnit 5**
- **Mockito**
- **MockMvc**
- **PostgreSQL**
- **SLF4J - Logback**
- **Jetty Maven Plugin**

## ✅ Prerequisites
- **Java 21 SDK** – required to compile and run the application
- **Apache Maven 3.6+** – for build and dependency management
- **PostgreSQL 14+ instance** – as the primary database
- **Git** – for version control
- **IntelliJ IDEA** (optional) – recommended IDE for development

## ⚙️ Configuration
### Database connection
jdbc.driverClassName=<your_driver>
jdbc.url=<your_url>
jdbc.user=<your_db_user>
jdbc.password=<your_db_password>

## 📁 Project Structure

```
src
 ├── main
 │   ├── java
 │   │   └── com.epam
 │   │       ├── config                 # Spring Java configuration classes
 │   │       ├── controller             # REST controllers for trainees, trainers, trainings, auth
 │   │       ├── dto                    # Request/Response DTOs
 │   │       ├── exception              # Custom exceptions and @RestControllerAdvice handlers
 │   │       ├── mapper                 # MapStruct mappers for entity <-> DTO conversion
 │   │       ├── model                  # JPA entity classes
 │   │       ├── repository             # Spring Data JPA repositories
 │   │       ├── security               # PasswordEncoder and security utilities
 │   │       ├── service                # Business service interfaces and implementations
 │   │       └── util                   # Utility classes (UserUtil, Constants)
 │   └── resources
 │       ├── application.properties     # Application settings
 │       ├── import.sql                 # Initial SQL data load
 │       └── logback.xml                # Logback configuration
 └── test
     └── java
         └── com.epam                   # Unit tests with MockMvc + Mockito

```

## 🧱 Architecture - Layered Design
- **Model Layer** – JPA entity classes representing the domain model
- **Repository Layer** – Spring Data JPA repositories for persistence operations
- **Service Layer** – Business logic, validation, and transaction management
- **Controller Layer** – REST controllers exposing API endpoints
- **DTO & Mapper Layer** – Request/Response DTOs and MapStruct mappers for entity ↔ DTO conversion
- **Exception Handling Layer** – Centralized error handling via `@RestControllerAdvice`
- **Utility Layer** – Shared helpers, constants, and reusable components

## 🚀 How to Run
If you meet the installation requirements:
- Open a terminal (Git Bash, CMD, PowerShell, etc.)
- Clone the repository:
  git clone https://github.com/jdmonroyg/springbasegymcrm.git
- cd springbasegymcrm
- mvn clean package
- mvn jetty:run

## 📖 API Documentation

This project will integrate **Swagger/OpenAPI** for interactive API documentation.

Once enabled, you will be able to:
- Explore all available endpoints
- View request/response models and example payloads
- Test API calls directly from the browser

### Planned Setup
- **Default URL:** [swagger](http://localhost:8080/api/v1/swagger-ui.html)

## 🎥 Demo Video 
- [GYM CRM VIDEO V1](https://youtu.be/o87Heqkcnlo)
- [GYM CRM VIDEO V2](https://youtu.be/w2W7PWNIXQg)
- [GYM CRM VIDEO V3](https://youtu.be/1_aTo8xAp7c)
