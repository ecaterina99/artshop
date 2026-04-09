# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spring Boot 4.0.5 art e-commerce backend (Java 21) with MySQL, Spring Security, and JPA/Hibernate. Uses Lombok for boilerplate reduction and ModelMapper (strict mode, skip nulls) for entity↔DTO conversion. Base package: `com.server.ArtShop`.

## Build & Run Commands

```bash
./mvnw clean install          # Build and run all tests
./mvnw spring-boot:run        # Run the application (port 9091, context path /api)
./mvnw test                   # Run all tests
./mvnw test -Dtest=ClassName  # Run a single test class
./mvnw test -Dtest=ClassName#methodName  # Run a single test method
```

## Prerequisites

- Java 21
- MySQL running on localhost:3306 with database `art_shop`

## Architecture

### Layers

Standard layered architecture: **Controller → Service → Repository → Entity**. Each domain concept (User, Painting, Order, OrderItems, Cart, CartItem) has its own controller, service, repository, and set of DTOs.

### Package Layout

- `Controllers/` — REST controllers (note: uppercase C, unlike other packages)
- `services/` — business logic
- `repositories/` — Spring Data JPA interfaces (all extend `JpaRepository<T, Integer>`)
- `models/` — JPA entities
- `dto/` — request/response DTOs with Jakarta validation; follows Create*/Update*/EntityDTO naming
- `config/` — SecurityConfig, JWTKeyConfig, OpenApiConfig, ModelMapperConfig
- `exceptions/` — GlobalExceptionHandler (`@RestControllerAdvice`), ApiError response class, UserAlreadyExistsException
- `util/` — Spring Security `UserDetails` wrapper for User entity

### Entity Model (all use `GenerationType.IDENTITY`)

- **User** → has many **Order**s
- **Order** → has many **OrderItems**, tracks status workflow (CREATED → PAID → SHIPPED → DELIVERED)
- **OrderItems** → junction table linking Order to Painting with quantity
- **Painting** → product entity with style enum (MODERN, ABSTRACTION, IMPRESSIONIST, MINIMALIST, SURREALIST)
- **Cart** → user's shopping cart, has many **CartItem**s (links to Painting with quantity)

Both `Order.Status` and `Painting.Style` enums have `fromString()` factory methods for string-to-enum conversion.

### Authentication & Security

- JWT-based stateless auth with RSA 2048-bit keys (generated at startup, not persisted)
- Auth endpoints: `POST /auth/register`, `POST /auth/login` — both public
- JWT contains `roles` claim; tokens expire in 1 hour
- Most endpoints are currently `permitAll` in SecurityConfig; only `/users` and unmatched paths require auth
- CORS configured for `http://localhost:5173` (frontend origin)
- Passwords hashed with BCrypt

### API

- All endpoints prefixed with `/api` (server context path)
- Swagger UI at `/api/swagger-ui/index.html`
- Error responses use `ApiError` format with error code, message, status, timestamp, and optional fieldErrors map

### Data

- `data.sql` seeds 10 sample paintings on startup (idempotent `INSERT ... WHERE NOT EXISTS`)
- Hibernate `ddl-auto=update` — schema auto-managed
- Kafka dependency is included but not yet configured or used
