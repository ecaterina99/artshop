# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spring Boot 4.0.5 art e-commerce backend (Java 21) with MySQL, Spring Security, Kafka, and JPA/Hibernate. Uses Lombok for boilerplate reduction. Base package: `com.server.ArtShop`.

## Build & Run Commands

```bash
./mvnw clean install          # Build and run all tests
./mvnw spring-boot:run        # Run the application (port 9090, context path /api)
./mvnw test                   # Run all tests
./mvnw test -Dtest=ClassName  # Run a single test class
./mvnw test -Dtest=ClassName#methodName  # Run a single test method
```

## Prerequisites

- Java 21
- MySQL running on localhost:3306 with database `art_shop`

## Architecture

The app is an early-stage e-commerce backend. Currently only entity models and a custom exception exist — no controllers, services, or repositories have been created yet.

### Entity Model (all use `GenerationType.IDENTITY`)

- **User** → has many **Order**s
- **Order** → has many **OrderItems**, tracks status workflow (CREATED → PAID → SHIPPED → DELIVERED)
- **OrderItems** → junction table linking Order to Painting with quantity
- **Painting** → product entity with style enum (MODERN, ABSTRACTION, IMPRESSIONIST, MINIMALIST, SURREALIST)

Both `Order.Status` and `Painting.Style` enums have `fromString()` factory methods for string-to-enum conversion.

### Validation

Jakarta validation annotations are applied at the entity level (`@NotNull`, `@Email`, `@Size`, `@Min`).

### Note

`application.properties` has a typo: `pring.datasource.url` should be `spring.datasource.url`.
