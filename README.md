# Hospital Management System

A Spring Boot REST API for patients, doctors, appointments, insurance, and role-based JWT authentication. Data is stored in PostgreSQL using Spring Data JPA.

## Run

Requirements: Java 25 and PostgreSQL.

```bash
createdb hospitalDB
cp .env.example .env
set -a && source .env && set +a
./mvnw spring-boot:run
```

Edit `.env` with your database password, admin password, and a JWT secret of at least 32 characters. The API runs at `http://localhost:8080/api`.

The admin username defaults to `admin@hospital.com` and its password comes from `ADMIN_PASSWORD`.

Create a patient account with `POST /api/auth/signup`, log in through `POST /api/auth/login`, then send the returned token as `Authorization: Bearer <token>`.

```bash
bash mvnw test
```
