# Digital CMS Backend (Spring Boot)

Backend project for a media-company Digital Content Management System with JWT auth, role-based access control, Camunda workflow, and MinIO file storage.

## Tech Stack
- Java 21
- Spring Boot 3.4.3
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Camunda BPMN (embedded)
- MinIO Java SDK

## Roles
- `ROLE_CONTENT_CREATOR`
- `ROLE_EDITOR`
- `ROLE_MARKETING`
- `ROLE_MANAGER`

## Main Modules
- `AuthController`: register, login, refresh token
- `UserController`: user CRUD and role management
- `ContentController`: content CRUD, status filtering, creator filtering, file upload, channel assignment
- `WorkflowController`: start workflow, approve/reject, status lookup
- `MetricsController`: metrics upsert and reporting
- `ChannelController`: channel CRUD and content push placeholder

## BPMN Workflow
`src/main/resources/bpmn/content-approval.bpmn`
1. Content submitted
2. Editor review
3. If approved: schedule publication and notify marketing
4. If rejected: workflow ends as rejected

## Configuration
Edit `src/main/resources/application.properties` for:
- MySQL connection
- MinIO URL/credentials/bucket
- JWT secret and expiration
- Camunda settings

## Run Locally
1. Start MySQL (create `digital_cms` schema or let app create it).
2. Start MinIO on `http://localhost:9000` with configured credentials.
3. Build:
   ```bash
   mvn clean package
   ```
4. Run:
   ```bash
   mvn spring-boot:run
   ```

## API Base Paths
- Auth: `/api/auth`
- Users: `/api/users`
- Content: `/api/content`
- Workflows: `/api/workflows`
- Metrics: `/api/metrics`
- Channels: `/api/channels`

## Notes
- Passwords are stored with BCrypt.
- JWT-protected endpoints require `Authorization: Bearer <token>`.
- Camunda web app endpoints are available under `/camunda`.
- Channel push behavior is intentionally a placeholder hook for external integrations.
