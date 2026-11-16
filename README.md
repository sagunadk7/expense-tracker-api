# Expense Tracker API

A Java and Spring Boot REST API for managing personal expenses. The service supports authenticated users, expense entry and monthly queries, and monthly reports with spending summaries and breakdowns.

## Features

- User registration and login
- Stateless authentication with JWT access tokens and refresh-token cookies
- Change password and set income for the authenticated user
- Create expenses and retrieve them by month, with pagination
- Monthly reports with totals, transaction counts, average/highest/lowest amounts, category totals, daily totals, and comparison with the previous month
- Expense category listing
- PostgreSQL persistence using Spring Data JPA

## Technology

- Java 21
- Spring Boot 4.0.8
- Spring Web MVC, Spring Security, Spring Data JPA
- PostgreSQL
- Maven Wrapper
- JJWT 0.12.6 and Lombok

## Requirements

- JDK 21
- PostgreSQL

The Maven Wrapper is included, so a separate Maven installation is not required.

## Setup

1. Clone the repository and change into its directory:

   ```bash
   git clone https://github.com/sagunadk7/expense-tracker-api.git
   cd expense-tracker-api
   ```

2. Create a PostgreSQL database (the current application configuration uses the database name `expense`).

3. Configure the database and JWT signing key. Update `src/main/resources/application.properties` or provide the corresponding Spring environment variables. For example:

   ```bash
   export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/expense
   export SPRING_DATASOURCE_USERNAME=your_database_user
   export SPRING_DATASOURCE_PASSWORD=your_database_password
   export JJWT_SECRET_KEY='replace-with-a-long-random-secret-key'
   ```

   `JJWT_SECRET_KEY` must be a sufficiently long secret for the configured HMAC algorithm. Keep database credentials and signing keys private. Do not commit `.env` files containing real secrets; this repository currently tracks a `.env` file, so review it and rotate any credentials that may have been exposed.

4. Run the application:

   ```bash
   # macOS / Linux
   ./mvnw spring-boot:run

   # Windows
   mvnw.cmd spring-boot:run
   ```

   The default Spring Boot port is `8080`, so the API is available at `http://localhost:8080`.

## Authentication

Register and log in using the authentication endpoints. A successful login returns an access token and sets a refresh-token cookie. Send the access token on protected API requests using the bearer authorization header:

```http
Authorization: Bearer <access-token>
```

Registration and login are public. Other endpoints require authentication according to the current Spring Security configuration.

## API overview

All paths use the `/api/v1` prefix. Request and response DTOs in the source define the exact JSON shapes and validation constraints.

| Method | Path | Purpose | Access |
| --- | --- | --- | --- |
| `POST` | `/api/v1/auth/register` | Register a user | Public |
| `POST` | `/api/v1/auth/login` | Log in and receive tokens | Public |
| `POST` | `/api/v1/auth/change-password` | Change the current user's password | Authenticated |
| `POST` | `/api/v1/auth/income` | Set the current user's income | Authenticated |
| `POST` | `/api/v1/expense` | Create an expense | Authenticated |
| `GET` | `/api/v1/expense?month={month}&year={year}` | List the current user's expenses for a month; supports Spring `Pageable` parameters | Authenticated |
| `GET` | `/api/v1/expense/monthly/{year}/{month}` | Get a monthly spending report | Authenticated |
| `GET` | `/api/v1/expense/categories` | List supported expense categories | Authenticated |

Expense categories currently include `FOOD`, `RENT`, `TRANSPORT`, `UTILITIES`, `ENTERTAINMENT`, `HEALTH`, `SHOPPING`, and `OTHER`.

An expense request uses the fields `title`, `description`, `category`, `amount`, and `expenseDate`. The amount must be positive; dates use the ISO-8601 format, for example `2026-03-15`.

Example request:

```http
POST /api/v1/expense
Content-Type: application/json
Authorization: Bearer <access-token>
```

```json
{
  "title": "Groceries",
  "description": "Weekly shopping",
  "category": "FOOD",
  "amount": 42.50,
  "expenseDate": "2026-03-15"
}
```

## Build and tests

Run the test suite and package the application with the wrapper:

```bash
./mvnw test
./mvnw package
```

On Windows, use `mvnw.cmd` in place of `./mvnw`.

## Project structure

```text
src/main/java/com/sagun/expense_tracker_api/
├── config/          # Security and JPA configuration
├── controller/      # Authentication, user, and expense endpoints
├── dto/             # API request and response models
├── entity/          # JPA entities
├── filter/          # JWT authentication filter
├── repository/      # Spring Data repositories
├── service/         # Expense, user, and monthly-report logic
└── utils/           # JWT, cookie, and validation utilities
src/main/resources/  # Application configuration
src/test/            # Tests
```

## Development notes

The current configuration uses Hibernate `ddl-auto=update` and enables SQL/debug output. Review these settings and use a managed schema migration approach before production deployment. No license is currently specified in the repository.
