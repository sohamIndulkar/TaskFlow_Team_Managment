# TaskFlow

TaskFlow is a full-stack team project and task management platform. It includes a Java 17 / Spring Boot REST API, Spring Security JWT authentication, a MySQL database, and a React dashboard.

## Start the full stack

Requirements: Docker Desktop with Compose.

```bash
docker compose up --build
```

Open [http://localhost:5173](http://localhost:5173). The local compose profile seeds a demo workspace:

- **Admin:** `admin@taskflow.local` / `TaskFlow2026!`
- **Manager:** `jordan@taskflow.local` / `TaskFlow2026!`
- **Member:** `sam@taskflow.local` / `TaskFlow2026!`

Stop with `docker compose down`. To remove the local database volume too, use `docker compose down -v`.

## Run without Docker

1. Install Java 17+, Maven, Node 20+, and MySQL 8. Create a database/user or use the defaults from `backend/src/main/resources/application.yml`.
2. Start the API from `backend`: `mvn spring-boot:run`.
3. Start the web app from `frontend`: `npm install` then `npm run dev`.
4. Open [http://localhost:5173](http://localhost:5173). Set `VITE_API_URL` if the API is not at `http://localhost:8080/api`.

The demo data is disabled by default for direct local runs. Set `DEMO_SEED=true` to enable it.

## Features

- Stateless JWT access tokens (15 minutes) and refresh tokens (7 days); the React Axios client refreshes an expired access token once and retries the request.
- Roles: `ADMIN`, `MANAGER`, and `MEMBER`. Admins can assign roles through `/api/admin/users/{id}/role`; project owners manage project settings; task operations are scoped to projects the signed-in user can access.
- Projects, team memberships, tasks, task status changes, and comments.
- Paginated task listing with status, priority, assignee, query, and sort filters. Task/project/user relations are loaded with JPA entity graphs to avoid common N+1 access patterns.
- Bean Validation DTOs and a consistent JSON error shape.
- Responsive dashboard, project views, task creation, team directory, comments, and optimistic status updates.

## API overview

| Method | Route | Purpose |
| --- | --- | --- |
| POST | `/api/auth/register` | Create a member account |
| POST | `/api/auth/login` | Sign in |
| POST | `/api/auth/refresh` | Exchange refresh token for a new token pair |
| GET | `/api/auth/me` | Current user |
| GET, POST | `/api/projects` | List accessible projects / create a project |
| PUT, DELETE | `/api/projects/{id}` | Update / delete a project |
| GET, POST | `/api/projects/{id}/tasks` | Search page / create project task |
| PUT, PATCH, DELETE | `/api/tasks/{id}` or `/api/tasks/{id}/status` | Edit, move status, or delete task |
| GET, POST | `/api/tasks/{id}/comments` | Read / add comments |
| GET | `/api/users` | List workspace users |
| PATCH | `/api/admin/users/{id}/role` | Admin role assignment |
| GET | `/api/dashboard` | Workspace summary |

Task pagination accepts `page`, `size` (max 100), `sort` (for example `dueDate,asc`), `status`, `priority`, `assigneeId`, and `q`.

## Configuration notes

`JWT_SECRET`, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `SERVER_PORT`, and `CORS_ORIGIN` can be set as environment variables. Replace the local JWT secret and demo credentials before deploying. Disable `DEMO_SEED` outside local development. The refresh token is stateless and expires after seven days; production deployments should add refresh-token revocation/rotation storage and TLS.
