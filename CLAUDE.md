# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ArtShop is a painting gallery/e-commerce app with a Spring Boot 4.0.5 backend (Java 21) and React 19 frontend (TypeScript, Vite). Monorepo with two independent projects: `/ArtShopBackend` and `/artshop-frontend`.

## Build & Run Commands

### Backend (from `/ArtShopBackend`)
```bash
./mvnw clean install          # Build and run all tests
./mvnw spring-boot:run        # Run app (port 9091, context path /api)
./mvnw test                   # Run all tests
./mvnw test -Dtest=ClassName  # Run a single test class
./mvnw test -Dtest=ClassName#methodName  # Run a single test method
```

### Frontend (from `/artshop-frontend`)
```bash
npm install        # Install dependencies
npm run dev        # Dev server (port 5173)
npm run build      # Production build
npm run lint       # ESLint
```

## Prerequisites

- Java 21, MySQL on localhost:3306 (database: `art_shop`, credentials: root/Qwerty123)
- Node.js for frontend
- Keycloak on port 8080 (realm: `artshop`, client: `artshop-frontend`); start via `docker compose -f ArtShopBackend/src/docker-compose.yml up`

## Architecture

### Backend — Layered Architecture

**Controller → Service → Repository → Entity** per domain (User, Painting, Order, OrderItems, Cart, CartItem).

- `Controllers/` — REST controllers (note: uppercase C)
- `services/` — business logic
- `repositories/` — Spring Data JPA interfaces (`JpaRepository<T, Integer>`)
- `models/` — JPA entities (all use `GenerationType.IDENTITY`)
- `dto/` — Create*/Update*/EntityDTO naming convention, Jakarta validation
- `config/` — SecurityConfig, ModelMapperConfig, OpenApiConfig
- `exceptions/` — `GlobalExceptionHandler` (`@RestControllerAdvice`), `ApiError` response format

### Frontend — React + OIDC

- `pages/` — Home, PaintingsPage, Painting (detail), Cart, Order (my orders), Profile
- `components/` — Navbar, ProtectedRoute
- `api/api.tsx` — fetch wrapper injecting Bearer token from OIDC context
- `types/` — TypeScript interfaces matching backend DTOs
- Auth via `react-oidc-context` + `oidc-client-ts` pointing at Keycloak

### Authentication & Security

- OAuth2/OIDC with Keycloak as identity provider
- Backend is an OAuth2 resource server validating JWTs
- Also has local JWT generation (RSA 2048-bit, 1 hour expiry, BCrypt passwords)
- Public endpoints: `/paintings/**`, `/orders/**`, `/order-items/**`
- Authenticated required: `/cart/**`
- CORS: allows `http://localhost:5173` (frontend origin)

### Entity Relationships

- **User** → many **Order**s; **Order** → many **OrderItems** (links to Painting with quantity)
- **Cart** → many **CartItem**s (links to Painting with quantity)
- Order status workflow: CREATED → PAID → SHIPPED → DELIVERED
- Painting style enum: MODERN, ABSTRACTION, IMPRESSIONIST, MINIMALIST, SURREALIST

### API

- Base URL: `http://localhost:9091/api`
- Swagger UI: `/api/swagger-ui/index.html`
- Error responses use `ApiError` with error code, message, status, timestamp, fieldErrors map

### Data

- `data.sql` seeds 10 sample paintings on startup (idempotent `INSERT ... WHERE NOT EXISTS`)
- Hibernate `ddl-auto=update` — schema auto-managed
- ModelMapper configured with strict matching and skip-nulls
