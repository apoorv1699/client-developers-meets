# C&D - Where Client and Developer Meet

## Purpose
Build a two-sided platform that connects clients with developers. Clients can discover and contact developers to build websites, web apps, MVPs, etc. Developers can create profiles, upload resumes, and showcase portfolios so clients can reach them.

## Goals
- Deliver a visually pleasing, user-attractive demo using mock data only.
- Keep the frontend backend-ready (clear API boundaries, models, and service structure).
- Implement backend later with Spring Boot (Java 21) and MySQL.

## Scope (Demo Phase)
- Two user flows:
  - Client UI/Flow
  - Developer UI/Flow
- Mock data only (no real backend).
- Demo Login button on login page that bypasses auth and routes to home.
- Chat feature is an important part of the product (demo can use mock conversations).

## Non-Goals (Demo Phase)
- Real authentication
- Database integration
- Payment gateway (clients and developers handle payment personally)
- Production-grade security

## Suggested Architecture
### Frontend (UI-first)
- Separate flows by role (client vs developer).
- Keep components and routes modular to ease backend integration later.
- Build with mock services that can be swapped for real API calls later.

### Backend (Later Phase)
- Spring Boot (Java 21) REST APIs
- MySQL for persistence
- Authentication/authorization (role-based)

## Information Architecture
### Client Flow (Demo)
1. Login (with "Demo Login" button)
2. Client Home / Dashboard
3. Browse Developers
4. Developer Profile (resume, portfolio)
5. Contact / Inquiry form (mock submit)
6. Chat inbox and conversation view (mock)

### Developer Flow (Demo)
1. Login (with "Demo Login" button)
2. Developer Home / Dashboard
3. Developer Profile Editor
4. Portfolio / Projects list
5. Resume upload placeholder (mock)
6. Chat inbox and conversation view (mock)

## Pages and Features (Demo Phase)
### Shared
- Landing page (role selection: Client or Developer)
- Login page (include "Demo Login" button)
- Basic navigation and footer

### Client UI
- Client dashboard with recommended developers
- Developer listing (filters: skill, location, price range)
- Developer profile view (mock reviews, portfolio cards)
- Chat / conversation view (mock messages)

### Developer UI
- Developer dashboard (profile completion, leads summary)
- Profile editor form (skills, bio, rate)
- Portfolio grid with project cards
- Chat / conversation view (mock messages)

## Chat UI (Quick Outline)
- Left pane: chat thread list with search, unread badge, and last message preview
- Right pane: active conversation with header (participant info, status)
- Message bubbles: left/right alignment by sender, timestamps
- Composer: textarea + send button (mock send)
- Empty state: no conversation selected

## UX/UI Direction
- Clean, modern, premium tech marketplace feel
- Strong hero section and clear CTAs
- Use mock avatars, portfolio thumbnails, and ratings
- Consistent spacing, typography, and color system

## Mock Data Requirements
Create local mock data sets for:
- Developers (name, title, skills, rate, rating, location, availability)
- Portfolios (project title, description, stack, image placeholder)
- Clients (company name, industry, desired project)
- Reviews (client name, rating, comment)
- Leads/Inquiries (client name, project summary)
- Chat threads (participants, lastMessage, updatedAt)
- Chat messages (threadId, senderId, content, timestamp)

## Routing (Spring Boot + Thymeleaf)
Routes are now served by `PageController`:
- `/` Landing
- `/login` Login (with Demo Login)
- `/client/home` Client dashboard
- `/client/developers` Developer listing
- `/client/developer` Developer profile (`?id=` query)
- `/client/messages` Client chat inbox
- `/developer/home` Developer dashboard
- `/developer/profile` Developer profile editor
- `/developer/portfolio` Portfolio list
- `/developer/messages` Developer chat inbox

## Demo Login Behavior
- Button label: "Demo Login"
- Action: Immediately route to `/client/home` or `/developer/home` based on role.
- For demo, default to client home unless the user selected developer flow.

## API Readiness (Later)
Design frontend services with interface stubs such as:
- `getDevelopers()`
- `getDeveloperById(id)`
- `getClientProfile()`
- `saveDeveloperProfile(payload)`
- `getLeads()`

## Backend-Ready Data Models (Preview)
- User: id, role (CLIENT/DEVELOPER), email, passwordHash
- DeveloperProfile: userId, name, title, bio, skills[], rate, location, availability
- PortfolioProject: developerId, title, description, techStack[], imageUrl
- ClientProfile: userId, companyName, industry, budgetRange
- Inquiry: clientId, developerId, projectSummary, status
- ChatThread: id, participantIds[], lastMessagePreview, updatedAt
- ChatMessage: id, threadId, senderId, content, createdAt

## Milestones
1. UI/UX design and layout skeletons
2. Implement mock data services and wire to UI
3. Add navigation and role-based flows
4. Polish UI and demo behaviors (including Demo Login)

## Notes for Future Backend Implementation
- Keep DTOs aligned with the data models above.
- Maintain separation between UI state and data-fetching logic.
- Replace mock services with REST API calls in a single place.

## Current Implementation Status (Feb 5, 2026)
### Spring Boot Conversion (Done)
- Added Maven Spring Boot structure with Java 21.
- Maven coordinates: `groupId=com.cd`, `artifactId=cd-platform`, base package `com.cd.platform`.
- Thymeleaf templates now live in `src/main/resources/templates`.
- Static assets (CSS/JS) moved to `src/main/resources/static/assets`.
- Added controller for clean routes: `src/main/java/com/cd/platform/PageController.java`.
- Application entry point: `src/main/java/com/cd/platform/CdPlatformApplication.java`.
- Updated JS routing to use Spring Boot paths (no `.html` links).
- Demo Login now routes to `/client/home` or `/developer/home`.
- Maven Wrapper added (`mvnw`, `mvnw.cmd`, `.mvn/wrapper`).

### Backend (In Progress)
- Added JPA + MySQL dependencies and DB config (`src/main/resources/application.yml`).
- Implemented JPA entities for all core tables under `src/main/java/com/cd/platform/model`.
- Added repositories under `src/main/java/com/cd/platform/repository`.
- Added services under `src/main/java/com/cd/platform/service`.
- Added REST APIs:
  - `GET /api/developers`
  - `GET /api/developers/{id}`
  - `GET /api/developers/{id}/portfolio`
  - `GET /api/chat/threads?userId=...`
  - `GET /api/chat/threads/{threadId}/messages`
- Added CRUD APIs:
  - `POST /api/developers`
  - `PUT /api/developers/{id}`
  - `POST /api/developers/{id}/portfolio`
  - `POST /api/users`
  - `POST /api/auth/login`
  - `POST /api/auth/signup`
  - `GET /api/clients/{id}`
  - `POST /api/clients`
  - `PUT /api/clients/{id}`
  - `POST /api/inquiries`
  - `POST /api/chat/threads`
  - `POST /api/chat/threads/{threadId}/messages`
- `application.yml` is configured for local MySQL (`jdbc:mysql://localhost:3306/cd_platform`).
  - Update `spring.datasource.username` / `password` as needed for your local setup.
  - Static resource caching disabled in dev to avoid stale JS/CSS.

### Backend Plan (Option 2: Full CRUD + UI Wiring)
We will implement create/update endpoints and connect the UI buttons/forms to real APIs.
1. Add POST endpoints:
   - `POST /api/developers`
   - `POST /api/developers/{id}/portfolio`
   - `POST /api/clients`
   - `POST /api/inquiries`
   - `POST /api/chat/threads`
   - `POST /api/chat/threads/{id}/messages`
2. Add PUT endpoints:
   - `PUT /api/developers/{id}`
   - `PUT /api/clients/{id}`
3. Add simple request/response DTOs and validation.
4. Update UI JS to replace mock data with `fetch()` calls:
   - Developer list, profile, portfolio
   - Chat threads and messages
   - Form submissions (profile edit, inquiry, message send)
5. Add basic error handling + loading states in UI.

### UI Wiring Status
- `src/main/resources/static/assets/js/app.js` now attempts to fetch developers, portfolios, and chat threads/messages from `/api`.
- If API calls fail or return empty, UI falls back to mock data from `data.js`.
- Developer profile save button now calls `PUT /api/developers/{id}`.
- Chat send button now calls `POST /api/chat/threads/{id}/messages`.
- Client profile form now calls `POST /api/clients` or `PUT /api/clients/{id}`.
- Inquiry form now calls `POST /api/inquiries`.
- Portfolio add form now calls `POST /api/developers/{id}/portfolio`.
- Login form now calls `POST /api/auth/login`.
- Sign-up form now calls `POST /api/auth/signup` and creates user + profile.

### UI Updates
- Removed "Demo Login" button from `src/main/resources/templates/login.html`.
- Updated login helper text to remove demo language.
- Added client profile form to `src/main/resources/templates/client-home.html`.
- Added inquiry form to `src/main/resources/templates/client-developer.html`.
- Added portfolio add form to `src/main/resources/templates/developer-portfolio.html`.
- Added login + sign-up tabs and full sign-up form to `src/main/resources/templates/login.html`.

### Database Seeding
- Added `db/seed.sql` to reset tables and insert sample users, developers, portfolios, clients, inquiries, and chat data.
- Run in MySQL Workbench: `USE cd_platform;` then execute `db/seed.sql`.

### Active Demo Files
- `pom.xml`
- `src/main/java/com/cd/platform/CdPlatformApplication.java`
- `src/main/java/com/cd/platform/PageController.java`
- `src/main/java/com/cd/platform/api/*.java`
- `src/main/java/com/cd/platform/model/*.java`
- `src/main/java/com/cd/platform/repository/*.java`
- `src/main/java/com/cd/platform/service/*.java`
- `src/main/resources/templates/*.html`
- `src/main/resources/static/assets/css/styles.css`
- `src/main/resources/static/assets/js/data.js`
- `src/main/resources/static/assets/js/app.js`
- `src/main/resources/application.yml`
- `db/seed.sql`

## How to Run (Spring Boot)
1. `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`)
2. Open `http://localhost:8080/`

### Next Steps
- Run `db/seed.sql` in MySQL Workbench to populate initial data.
- Verify new UI forms persist data in DB (client profile, inquiry, portfolio).
- Test auth flow (sign-up + login) with real DB users.
- Add real auth, role-based access control, and persistence with MySQL.
- Optional: convert JS rendering into server-side Thymeleaf fragments if desired.

## Database Implementation Plan (MySQL + Spring Boot)
This plan describes how we will wire MySQL into the Spring Boot app, step by step.

### Phase 1: Database Setup (MySQL Workbench)
1. Create database `cd_platform` with UTF-8 (`utf8mb4`) charset.
2. Create tables:
   - `users`
   - `developer_profiles`
   - `portfolio_projects`
   - `client_profiles`
   - `inquiries`
   - `chat_threads`
   - `chat_participants`
   - `chat_messages`
3. Verify with:
   - `USE cd_platform;`
   - `SHOW TABLES;`

### Phase 2: Spring Boot Configuration
1. Add dependencies in `pom.xml`:
   - `spring-boot-starter-data-jpa`
   - `mysql-connector-j`
2. Add DB config in `src/main/resources/application.yml`:
   - `spring.datasource.url`
   - `spring.datasource.username`
   - `spring.datasource.password`
   - `spring.jpa.hibernate.ddl-auto=validate`

### Phase 3: JPA Entities
Create entity classes mapped to each table:
1. `User`
2. `DeveloperProfile`
3. `PortfolioProject`
4. `ClientProfile`
5. `Inquiry`
6. `ChatThread`
7. `ChatParticipant`
8. `ChatMessage`

### Phase 4: Repositories + Services
1. Create `JpaRepository` interfaces for each entity.
2. Add service classes to handle:
   - developer browsing
   - portfolio retrieval
   - chat threads/messages

### Phase 5: REST Controllers
Expose initial APIs:
1. `GET /api/developers`
2. `GET /api/developers/{id}`
3. `GET /api/developers/{id}/portfolio`
4. `GET /api/chat/threads?userId=...`
5. `GET /api/chat/threads/{id}/messages`

### Phase 6: Frontend Integration
Replace `src/main/resources/static/assets/js/data.js` with `fetch()` calls to the APIs.

### Notes
- Use mock data until entities and endpoints are stable.
- Keep table relationships aligned with data models in this doc.
