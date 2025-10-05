# 🌱 Spring Core - Gym CRM System

This project is a basic CRM designed to manage a gym using **Java 21 and Spring Core**, 
progressively enhanced with **JPA/Hibernate**, **RESTful**, and **mock-based testing**.

It demonstrates clean layering, explicit configuration, and a strong focus on **maintainability, 
testability, and scalability**.
The system evolves step by step, showcasing how to move from a simple Spring Core setup to a modern, 
production-ready backend.


## 🧩 Features

- **Domain model** with joined inheritance for **Users**, **Trainees**, **Trainers**, 
Trainings, and TrainingTypes
- **Persistence layer** powered by Spring Data JPA with custom JpaRepository interfaces
- **Service layer** handling business logic, validation, and transaction management
- **RESTful controllers** exposing endpoints for trainees, trainers, trainings, and authentication
- **Exception handling** via centralized @RestControllerAdvice with custom error responses
- **Input validation** using Hibernate Validator (@Valid, @NotNull, @Min, etc.)
- **Secure password encoding** with BCrypt
- **Initial data loading** - via import.sql (H2) and data.sql (Postgres)
- **Unit tests** for all controllers using MockMvc, Mockito, and JUnit 5

## 🛠 Stack
- **Java 21**
- **Maven 3.6+**
- **Spring Boot**
- **Spring Web MVC**
- **Spring Data JPA**
- **Spring Boot Validation (Hibernate Validator)**
- **Spring Boot Actuator**
- **Micrometer + Prometheus**
- **JUnit 5**
- **Mockito**
- **MockMvc**
- **PostgreSQL (dev)**
- **H2 (in-memory DB for local profile)**
- **SLF4J + Logback**
- **Spring doc OpenAPI (Swagger UI)**

## ✅ Prerequisites
- **Java 21 SDK** – required to compile and run the application
- **Apache Maven 3.6+** – for build and dependency management
- **PostgreSQL 14+ instance** – primary database for dev profile
- **H2 Database** - in‑memory database for the local profile (no external setup required)
- **Git** – version control
- **IntelliJ IDEA** (optional) – recommended IDE for development

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
 │   │       ├── config                 # Spring Boot configuration classes (beans, filters, setup)
 │   │       ├── controller             # REST controllers for trainees, trainers, trainings, auth
 │   │       ├── dto                    # Request/Response DTOs (API contracts)
 │   │       ├── exception              # Custom exceptions and @RestControllerAdvice handlers
 │   │       ├── indicator              # Health indicators and metrics instrumentation
 │   │       ├── mapper                 # MapStruct mappers for entity <-> DTO conversion
 │   │       ├── model                  # JPA entity classes
 │   │       ├── repository             # Spring Data JPA repositories
 │   │       ├── security               # Security utilities (PasswordEncoder, auth helpers)
 │   │       ├── service                # Business service interfaces and implementations
 │   │       └── util                   # Utility classes (UserUtil, Constants, helpers)
 │   │       GymCrmApplication.java     # Main entry point of the Spring Boot application
 │   └── resources
 │       ├── application.yml            # Common configuration (shared across profiles)
 │       ├── application-dev.yml        # Development profile (PostgreSQL)
 │       ├── application-local.yml      # Local profile (H2 in-memory)
 │       ├── data-dev.sql               # SQL script for dev data initialization
 │       ├── import.sql                 # Default SQL initialization (Hibernate) for H2
 │       └── logback.xml                # Logback logging configuration
 │
 └── test
     └── java
         └── com.epam                   # Unit tests with MockMvc + Mockito

```

## 🧱 Architecture - Layered Design
- **Configuration Layer** – Spring Boot configuration classes (beans, filters, profile setup)
- **Model Layer** – JPA entity classes representing the domain model
- **Repository Layer** – Spring Data JPA repositories for persistence operations
- **Service Layer** – Business logic, validation, and transaction management
- **Controller Layer** – REST controllers exposing API endpoints
- **DTO & Mapper Layer** – Request/Response DTOs and MapStruct mappers for entity ↔ DTO conversion
- **Exception Handling Layer** – Centralized error handling via @RestControllerAdvice
- **Utility Layer** – Shared helpers, constants, and reusable components
- **Monitoring Layer** – Health indicators and metrics instrumentation for observability

## 🚀 How to Run
If you meet the installation requirements:
- Open a terminal (Git Bash, CMD, PowerShell, etc.)
- Clone the repository:
  git clone https://github.com/jdmonroyg/springbasegymcrm.git
- cd springbasegymcrm
- Build the project:
  mvn clean package
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
  
