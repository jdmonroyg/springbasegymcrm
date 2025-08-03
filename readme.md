# 🌱 Spring Core - Gym CRM System

This project is a basic CRM designed to manage a gym using **Java and Spring Core**.  
Demonstrates layered architecture, manual dependency injection, and core 
principles of software modularity and testability.

## 🧩 Features

- Registering **Trainees**, and **Trainers**
- Registering **Trainings** between **Trainees** and **Trainers** 
- Searching all or by ID for trainees, trainers, and trainings
- Automatically loading data-init from a JSON file

## 🛠 Stack
- **Java 21**
- **Maven**
- **Spring Core**
- **JUnit 5**
- **Jackson** (for JSON parsing)
- **SLF4J**

## ✅ Prerequisites
- Java 21 SDK
- Apache Maven 3.6 or Higher
- Intellij IDEA (Optional)
- Git

## 📁 Project Structure

```
src
 ├── main
 │   ├── java
 │   │   └── com.epam
 │   │       ├── config                # Spring Java Config
 │   │       ├── dao                   # Repository classes
 │   │       ├── facade                # Facade layer
 │   │       ├── model                 # Entity classes
 │   │       ├── service               # Services classes
 │   │       ├── storage               # Data in memory
 │   │       └── util                  # Utility classes (e.g., JSON reader)
 │   └── resources
 │       ├── data
 │       │   └──init-data.json         # Initial test data
 │       └── application.properties    # Configuration properties
 └── test
     └── java
         └── com.epam                  # Unit tests for config, facade, storage.
```

## 🧱 Architecture - Layered Design
- Model Layer
- Storage Layer
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
- [GYM CRM VIDEO](https://youtu.be/o87Heqkcnlo)
