# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

See the parent [../CLAUDE.md](../CLAUDE.md) for full project overview, prerequisites, and backend details.

## Build & Run

```bash
npm install        # Install dependencies
npm run dev        # Vite dev server on port 5173 (strict port)
npm run build      # Production build → /dist/
npm run lint       # ESLint (flat config, targets JS/JSX only)
```

Requires Keycloak running on port 8080 and backend on port 9091.

## Architecture

**React 19 + TypeScript + Vite 8 + React Router 7**

### Entry & Routing

- `src/main.tsx` — OIDC `AuthProvider` wraps `BrowserRouter` wraps `App`
- `src/App.tsx` — Route definitions; `Navbar` renders on all pages
- Routes: `/` (Home), `/paintings` (gallery), `/paintings/:id` (detail), `/cart`, `/profile`, `/orders` — last three are wrapped in `ProtectedRoute`

### API Layer

- `src/api/api.tsx` — All backend calls; uses native `fetch` with Bearer token from OIDC context
- Base URL hardcoded: `http://localhost:9091/api`
- Public: `GET /paintings`, `GET /paintings/:id`
- Protected: `GET/POST/PUT/DELETE /cart/**`, `POST /cart/checkout`, `GET /orders/my`

### Auth (OIDC)

- `react-oidc-context` + `oidc-client-ts` → Keycloak (`http://localhost:8080/realms/artshop`)
- `useAuth()` hook for auth state in components; `ProtectedRoute` redirects unauthenticated users
- Token passed to API via `authHeaders()` helper in `api.tsx`

### State Management

No global state library. Each page manages its own state via `useState`/`useEffect` with fetch-on-mount pattern. Auth state comes from OIDC context.

### Types

`src/types/` — TypeScript interfaces matching backend DTOs: `Painting`, `Cart`/`CartItem`/`AddToCart`/`UpdateCartItem`, `Order`, `OrderItem`, `User`.

### Styling

Plain CSS in `src/App.css`. CSS Grid for painting gallery, Flexbox for layouts. No CSS framework.
