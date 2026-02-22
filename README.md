# Clustered Data Warehouse - FX Deals

## Project Overview
This project is a data warehouse solution developed for Bloomberg to analyze and persist Foreign Exchange (FX) deals. It is built using **Java 17**, **Spring Boot 3.2**, and **PostgreSQL**.

## Features
- **Data Persistence**: Acccepts deal details and stores them in a PostgreSQL database.
- **Idempotency**: Ensures the same deal (Unique ID) is not imported twice.
- **Validation**: Strict validation of ISO currency codes, timestamps, and amounts.
- **Error Handling**: Custom exception handling for duplicates and validation errors.
- **Logging**: Comprehensive logging for request tracking and error auditing.

## Tech Stack
- **Backend**: Spring Boot 3.2.0
- **Database**: PostgreSQL 15
- **Tooling**: Docker, Docker Compose, Maven
- **Lombok**: To reduce boilerplate code.

---

## How to Run

### Prerequisites
- Docker & Docker Compose installed.
- Maven (optional, if building outside Docker).

### Option 1: Running with Docker Compose (Recommended)
This will set up the database and the application automatically.
```bash
# Build the jar first
mvn clean package -DskipTests

# Run the deployment
docker-compose up --build