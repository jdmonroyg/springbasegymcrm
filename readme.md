# 🌱 Spring Core - Gym CRM System

This project is a basic CRM designed to manage a gym using **Java 21 and Spring Core**, 
progressively enhanced with **JPA/Hibernate**, **RESTful**, and **mock-based testing**.

It demonstrates clean layering, explicit configuration, and a strong focus on **maintainability, 
testability, and scalability**.
The system evolves step by step, showcasing how to move from a simple Spring Core setup to a modern, 
production-ready backend.


## 🧩 Features

- **Domain model** with joined inheritance for **Users**, **Trainees**, **Trainers**,
  **Trainings**, and **TrainingTypes**
- **Persistence layer** powered by Spring Data JPA with custom JpaRepository interfaces
- **Service layer** encapsulating business logic, validation, and transaction management
- **RESTFul controllers** exposing endpoints for trainees, trainers, trainings,training types, and authentication
- **Exception handling** via centralized @RestControllerAdvice with custom error responses
- **Input validation** using Hibernate Validator (@Valid, @NotNull, @Min, etc.)
- **Secure authentication & authorization** with JWT and role-based access control
- **Password encoding** with BCrypt for strong security
- **Custom filters** for JWT validation and transaction logging
- **Application metrics & health checks** via Actuator indicators (database, training metrics, user metrics)
- **Initial data loading** - via import.sql (H2) and data.sql (Postgres)
- **Unit tests** for all controllers using MockMvc, Mockito, and JUnit 5

## 🛠 Stack

- **Java 21**
- **Maven 3.6+**
- **Spring Boot 4**
- **Spring Web MVC**
- **Spring Data JPA / Hibernate**
- **Spring Security (JWT authentication & role-based authorization)**
- **Spring Boot Validation (Hibernate Validator)**
- **Spring Boot Actuator**
- **Micrometer + Prometheus**
- **JUnit 5**
- **Mockito**
- **MockMvc**
- **PostgreSQL (dev)**
- **H2 (in-memory DB for local profile)**
- **SLF4J + Logback** (structured logging with MDC)
- **Spring doc OpenAPI (Swagger UI)**

## ✅ Prerequisites

- **Java 21 SDK** – required to compile and run the application
- **Apache Maven 3.6+** – build and dependency management
- **Spring Boot 4 parent** – ensures consistent dependency versions
- **PostgreSQL 14+ instance** – primary database for the `dev` and `prod` profiles
- **H2 Database** – in‑memory database for the `local` profile (no external setup required)
- **ActiveMQ broker** (for messaging integration)
- **Git** – version control
- **IntelliJ IDEA / Eclipse** (optional) – recommended IDEs for development

## ⚙️ Configuration

### Common properties
- server.port= <your port>
- server.servlet.context.path= /api/v1
- spring.profiles.active= dev
- management.endpoints.web.exposure.include= health, info, prometheus, metrics
- management.endpoint.health.show-details= always

### Development profile (PostgreSql)
- spring.datasource.url=jdbc:postgresql://<your_url>
- spring.datasource.username=<your_db_user>
- spring.datasource.password=<your_db_password>
- spring.jpa.hibernate.ddl-auto=update
- spring.jpa.defer-datasource-initialization=true
- spring.sql.init.mode=always
- spring.sql.init.data-locations=classpath:data-dev.sql

### Local profile (H2)
spring.datasource.url=jdbc:h2:mem:gymcrm;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop

## 📁 Project Structure

```
src
 ├── main
 │   ├── java
 │   │   └── com.epam
 │   │       ├── client                 # Feign clients and fallbacks for inter‑service communication
 │   │       ├── config                 # Spring Boot configuration (Feign, OpenAPI, Security)
 │   │       ├── controller             # REST controllers (Auth, Trainee, Trainer, Training, TrainingType)
 │   │       ├── dto                    # Request/Response DTOs (API contracts)
 │   │       ├── exception              # Custom exceptions and global exception handler
 │   │       ├── filter                 # JWT authentication and transaction logging filters
 │   │       ├── indicator              # Health indicators and application metrics
 │   │       ├── listener               # Authentication event listeners (success/failure)
 │   │       ├── mapper                 # MapStruct mappers for entity <-> DTO conversion
 │   │       ├── model                  # JPA entities (User, Trainer, Trainee, Training, TrainingType, Role)
 │   │       ├── repository             # Spring Data JPA repositories
 │   │       ├── service                # Business service interfaces
 │   │       ├── service/impl           # Service implementations
 │   │       └── util                   # Utility classes (UserUtil, Constants)
 │   │       GymCrmApplication.java     # Main entry point of the Spring Boot application
 │   └── resources
 │       ├── application.yaml           # Common configuration (profiles)
 │       ├── application-dev.yaml       # Development profile (PostgreSQL)
 │       ├── application-local.yaml     # Local profile (H2 in‑memory)
 │       ├── data-dev.sql               # SQL script for dev data initialization
 │       ├── import.sql                 # Default SQL initialization (Hibernate/H2)
 │       └── logback.xml                # Logback logging configuration
 │
 └── test
     └── java
         └── com.epam
             ├── controller             # Controller tests using MockMvc
             └── util                   # Utility tests
```

## 🧱 Architecture - Layered Design

- **Configuration Layer** – Spring Boot configuration classes (Feign, OpenAPI, Security, profile setup)
- **Client Layer** – Feign clients and fallback implementations for inter‑service communication
- **Model Layer** – JPA entity classes representing the domain model (User, Trainer, Trainee, Training, TrainingType, Role)
- **Repository Layer** – Spring Data JPA repositories for persistence operations
- **Service Layer** – Business logic, validation, and transaction management
    - Interfaces in `service`
    - Implementations in `service/impl`
- **Controller Layer** – REST controllers exposing API endpoints (Auth, Trainee, Trainer, Training, TrainingType)
- **DTO & Mapper Layer** – Request/Response DTOs and MapStruct mappers for entity ↔ DTO conversion
- **Exception Handling Layer** – Custom exceptions and centralized error handling via `GlobalExceptionHandler`
- **Security Layer** – JWT authentication filter, transaction logging filter, and authentication event listeners
- **Utility Layer** – Shared helpers and constants (e.g., `UserUtil`, `Constants`)
- **Monitoring Layer** – Health indicators and application metrics for observability

## 🚀 How to Run
If you meet the installation requirements:
- Open a terminal (Git Bash, CMD, PowerShell, etc.)
- Clone the repository:
  git clone https://github.com/jdmonroyg/springbasegymcrm.git
- cd springbasegymcrm
- Build the project:
  mvn clean package

### ⚡ Start Eureka Server
Before running any microservice, make sure the Eureka Server is up and running

### Run the application with Spring Boot

#### Run with PostgreSQL (development profile)
- mvn spring-boot:run -Dspring-boot.run.profiles=dev

#### Run with H2 in-memory database (local profile)
- mvn spring-boot:run -Dspring-boot.run.profiles=local

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
- [GYM CRM VIDEO V4](https://youtu.be/nlWGZcHzJIs)
- [GYM CRM VIDEO V5](https://youtu.be/O3wcJW55sc0)
  
