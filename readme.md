# 🌱 Spring Core - Gym CRM System

This project is a basic CRM designed to manage a gym using **Java 21 and Spring Core**.  
Demonstrates layered architecture, manual dependency injection, JPA/Hibernate, 
AOP-based exception handling, data initialization, and core principles of 
software modularity and testability.

## 🧩 Features

- Data model with joined inheritance for **Users**, **Trainees**, **Trainers**, 
Trainings, and TrainingTypes
- CRUD operations implemented via DAOs using EntityManager
- Service layer for business logic, validation, and transaction management
- Facade layer acting as a controller
- Exception handling with Spring **AOP (@Aspect)** and exception translation
- Input validation using Hibernate Validator and method‐level constraints
- Secure password encoding with BCrypt
- Initial data loading from import.sql
- Unit and integration tests with JUnit 5, Mockito, and H2 in-memory database


## 🛠 Stack
- **Java 21**
- **Maven 3.6+**
- **Spring Core**
- **Hibernate 7.0 (JPA Implementation)**
- **Jakarta Annotations & Validation API**
- **JUnit 5**
- **PostgreSQL**
- **SLF4J - Logback**

## ✅ Prerequisites
- Java 21 SDK
- Apache Maven 3.6 or Higher
- Intellij IDEA (Optional)
- Git
- PostgreSQL 14+ instance

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
 │   │       ├── aop                   # Aspects for exception handling 
 │   │       ├── config                # Spring Java configuration classes
 │   │       ├── dao                   # DAO interfaces and implementations
 │   │       ├── facade                # Facade layer
 │   │       ├── model                 # JPA entity classes
 │   │       ├── security              # PasswordEncoder
 │   │       ├── service               # Business service interfaces and impls
 │   │       └── util                  # Utility classes (UserUtil, Constants)
 │   └── resources
 │       ├── application.properties    # Application settings
 │       ├── import.sql                # Initial SQL data load
 │       └── logback.xml               # Logback Configuration
 └── test
     └── java
         └── com.epam                  # Unit tests.
```

## 🧱 Architecture - Layered Design
- Model Layer
- Dao Layer
- Service Layer
- Facade Layer

## 🚀 How to Run
if you meet the installation requirements
- open console (git bash, terminal, etc.)
- git clone https://github.com/jdmonroyg/springbasegymcrm.git
- cd springbasegymcrm
- mvn clean install

## 🎥 Demo Video 
- [GYM CRM VIDEO V1](https://youtu.be/o87Heqkcnlo)
- [GYM CRM VIDEO V2](https://youtu.be/w2W7PWNIXQg)
