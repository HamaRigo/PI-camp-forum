# Tunisia Camp Forum

A full-stack social forum web application where authenticated users publish articles (posts), comment on them, react with likes/dislikes, and exchange real-time chat messages over WebSockets. Content is automatically sanitized against a configurable list of prohibited words.

> Academic project (Esprit School of Engineering — "PI Camp"), built as a Spring Boot + Angular monorepo.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Reference](#api-reference)
- [Real-Time Chat (WebSocket / STOMP)](#real-time-chat-websocket--stomp)
- [Data Model](#data-model)
- [CI/CD & Code Quality](#cicd--code-quality)
- [Project Highlights](#project-highlights)

---

## Features

### Forum
- Create, read, update and delete **posts** (title + Markdown-rendered content + optional image)
- **Image upload** for post attachments
- **Pagination** of the post feed, filtering by author, and "most liked" trending feed
- **Comments** on posts (create / update / delete)
- **Reactions** (LIKE / DISLIKE) on posts with live like/dislike counts and rating points

### User & Session
- Username-based login / logout with **online/offline presence** (`connected` flag)
- Live login/logout broadcasts to all clients over WebSocket
- Per-user profiles with "my posts" and "liked posts" tabs
- Role management (`Role` enum) on the `User` entity
- Auth token handling on the API client via an HTTP interceptor

### Content Moderation
- **Prohibited word filtering**: post and comment content is sanitized server-side before persistence
- Admin-style endpoint to register new prohibited words in bulk

### Real-Time Chat
- 1-to-1 and channel-based messaging via **STOMP over WebSocket**
- Message history retrieval (paginated per channel)
- **Read receipts** (message marked as read with timestamp)

---

## Tech Stack

| Layer            | Technology |
|------------------|------------|
| Frontend         | Angular 14, TypeScript 4.6, RxJS 6, SweetAlert2, `marked` (Markdown rendering) |
| Backend          | Spring Boot 2.7.5, Spring Web, Spring Data JPA / Hibernate, Spring WebSocket (STOMP) |
| API Documentation| springdoc-openapi (Swagger UI) |
| Database         | MySQL 8 (`tunisiaCamp` schema, auto-created on first run), `ddl-auto=update` |
| Utilities        | Lombok, ModelMapper (DTO ↔ entity mapping), Bean Validation |
| Build & Test     | Maven, Karma/Jasmine (unit), Protractor (e2e) |
| CI/CD            | Jenkins pipeline → Maven build → SonarQube static analysis (Nexus, Docker stages included but commented out) |
| Deployment       | Nginx (frontend Docker image), Docker Compose (SonarQube + Nexus) |

---

## Architecture

The project follows a classic **client/server REST + WebSocket** separation:

```
┌──────────────────────┐  HTTP / REST (JSON)   ┌───────────────────────────┐
│  Angular 14 SPA      │ ◄───────────────────► │  Spring Boot REST layer    │
│  (lazy-loaded        │                       │  Controllers → Services    │
│   feature modules:   │  STOMP / WebSocket     │  ↓                        │
│   home, article,     │ ◄───────────────────► │  JPA Repositories → MySQL  │
│   editor, profile,   │   (/ichat endpoint)   │                           │
│   core, shared)      │                       │  springdoc → Swagger UI    │
└──────────────────────┘                       └───────────────────────────┘
```

**Backend layering** (`tn.esprit.tunisiacampbackend`):
- `RestControllers/` — thin HTTP controllers, exception-to-status mapping, content sanitization hook-in points
- `Services/` — business logic (`PostService`, `CommentService`, `ReactService`, `ProhibitedWordService`, `UserServiceImpl`)
- `DAO/Entities` — JPA entities; `DAO/Repositories` — Spring Data repositories; `DAO/DTO` — response payloads shaped for the frontend (`PostDto`, `CommentDto`, `ReactDto`, `UserDTO` + `ToDtoConverter`)
- `config/WebSocketConfig` — STOMP broker (`/channel`), app prefix (`/app`), endpoint (`/ichat`)
- `exception/` — domain exceptions surfaced as HTTP 404/400 payloads

**Frontend layering** (`src/app`):
- `core/` — singleton services (`ApiService`, `ArticlesService`, `CommentsService`, `ReactsService`, `UserService`), models, HTTP **JWT token interceptor**
- `shared/` — reusable layout, article list/card/favorite/feed components, error list
- Feature modules are **lazy-loaded**: `home/`, `article/`, `editor/`, `profile/`
- Architecture is derived from the **Angular RealWorld (conduit)** reference app, adapted to the custom Spring backend

The frontend targets the backend at `http://localhost:4201/tunisia-camp` (see `src/environments/environment.ts`).

---

## Project Structure

```
PI-camp-forum/
├── tunisia-camp-backend/            # Spring Boot REST + WebSocket API
│   ├── src/main/java/tn/esprit/tunisiacampbackend/
│   │   ├── DAO/                     # Entities, Repositories, DTOs
│   │   ├── RestControllers/         # Post, Comment, React, Chat, Message, Connection, ProhibitedWord
│   │   ├── Services/                # Business logic + implementations
│   │   ├── config/                  # WebSocketConfig (STOMP)
│   │   ├── exception/               # Domain exceptions
│   │   └── security/                # CORSFilter
│   ├── src/main/resources/application.properties
│   ├── Jenkinsfile                  # CI pipeline (build + SonarQube)
│   ├── docker-compose.yml           # SonarQube + Nexus containers
│   └── pom.xml
└── tunisia-camp-frontend/           # Angular 14 SPA
    ├── src/app/
    │   ├── core/                    # Services, models, interceptors
    │   ├── shared/                  # Layout + reusable components
    │   ├── home/ article/ editor/ profile/   # Lazy-loaded feature modules
    │   └── app.module.ts / routing
    ├── Dockerfile / nginx.conf      # Production static hosting
    └── package.json / angular.json
```

---

## Getting Started

### Prerequisites
- **Java 11+** (pom compiles with source/target 11; project was developed on JDK 8/11 — the JDK configured in your IDE, 23, also works)
- **Maven 3.6+** (or use the bundled `./mvnw`)
- **Node.js ≥ 16** (`.nvmrc` provided) + npm
- **MySQL 8** running locally on `localhost:3306`

### 1. Backend

The database `tunisiaCamp` is created automatically (`createDatabaseIfNotExist=true`), and the schema is generated by Hibernate (`ddl-auto=update`). Default credentials in `application.properties` are `root` with an empty password — adjust if needed.

```bash
cd tunisia-camp-backend
./mvnw spring-boot:run
```

- API base URL: `http://localhost:4201/tunisia-camp`
- Swagger UI: `http://localhost:4201/tunisia-camp/swagger-ui/index.html`
- WebSocket endpoint: `ws://localhost:4201/tunisia-camp/ichat`

### 2. Frontend

```bash
cd tunisia-camp-frontend
npm install
npm start          # ng serve → http://localhost:4200
```

### 3. Quality tooling (optional)

```bash
cd tunisia-camp-backend
docker compose up -d   # SonarQube on :9000, Nexus on :8081
```

---

## API Reference

All routes are prefixed with `/tunisia-camp`. Full interactive docs available via Swagger UI (springdoc-openapi).

### Posts — `/articles`
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/articles` | List all posts (`{ articles, articlesCount }`) |
| GET | `/articles/paginated?page=&limit=&type=` | Paginated feed |
| GET | `/articles/paginatedByUser?page=&limit=&userId=` | Paginated posts of one user |
| GET | `/articles/liked?limit=&userId=` | Most-liked posts (trending) |
| GET | `/articles/{id}` | Single post (404 if missing) |
| POST | `/articles` | Create post (content sanitized) |
| PUT | `/articles` | Update post (content sanitized) |
| DELETE | `/articles/{id}` | Delete post (cascades comments + reacts) |
| POST | `/articles/upload` | Multipart image upload |
| PUT | `/articles/{id}/rate` | Rate post (`buttonState` payload) |

### Comments — `/comments`
| Method | Route | Description |
|--------|-------|-------------|
| POST | `/comments` | Create comment (sanitized) |
| PUT | `/comments` | Update comment (sanitized) |
| DELETE | `/comments/{id}` | Delete comment |

### Reactions — `/reactions`
| Method | Route | Description |
|--------|-------|-------------|
| POST | `/reactions` | Create reaction (LIKE/DISLIKE) |
| PUT | `/reactions` | Update reaction |
| DELETE | `/reactions/{id}` | Delete reaction |

### Connection / Users
| Method | Route | Description |
|--------|-------|-------------|
| POST | `/login` | Login by username; broadcasts user on `/channel/login` |
| POST | `/logout` | Logout; broadcasts on `/channel/logout` |
| GET | `/listUsers` | List all users |
| GET | `/defaultUser` | Default/guest user payload |
| GET | `/clearAlluser` | Reset all users (development helper) |

### Moderation & Messages
| Method | Route | Description |
|--------|-------|-------------|
| POST | `/prohibited-words?words=` | Bulk-register prohibited words |
| GET | `/messages/{channelId}` | Paginated message history for a channel |
| POST | `/messages` | Send read receipt (sets `readDate`) |

---

## Real-Time Chat (WebSocket / STOMP)

Configured in [`WebSocketConfig`](tunisia-camp-backend/src/main/java/tn/esprit/tunisiacampbackend/config/WebSocketConfig.java):

- **Endpoint**: `/ichat` (STOMP, CORS open)
- **Broker prefix**: `/channel` (subscriptions) — e.g. `/channel/chat/{channelName}`, `/channel/login`, `/channel/logout`
- **App prefix**: `/app` (send destination) — e.g. `/app/messages`

Flow ([`ChatController`](tunisia-camp-backend/src/main/java/tn/esprit/tunisiacampbackend/RestControllers/ChatController.java)): client publishes to `/app/messages` → message is timestamped and persisted to MySQL → rebroadcast to `/channel/chat/{channel}` subscribers.

---

## Data Model

```
User (1) ──< Post (1) ──< Comment
User        Post (1) ──< React (TypeReact: LIKE | DISLIKE)
User ────────────────── Message (channel, sender, receiver, content, timestamp, readDate)
ProhibitedWord ── used to sanitize Post.content and Comment.content
```

| Entity | Key fields |
|--------|-----------|
| `User` | username (unique), connected flag, firstName, lastName, email, password, phoneNumber, image, `Role` |
| `Post` | title (3–40), content (TEXT, 5–5000), dateTimeOfPost, imageUrl, ratingPoints, transient likes/dislikes counts |
| `Comment` | content, author, post (cascade remove) |
| `React` | type (`LIKE`/`DISLIKE`), user, post (cascade remove) |
| `Message` | channel, sender, receiver, content, timestamp, readDate |
| `ProhibitedWord` | word — matched/replaced during sanitization |

---

## CI/CD & Code Quality

[`Jenkinsfile`](tunisia-camp-backend/Jenkinsfile) pipeline (active stages):
1. **Build** — `mvn clean package -DskipTests`
2. **SRC Analysis** — `mvn sonar:sonar` against a SonarQube instance (provided by `docker-compose.yml`, along with a Nexus repository manager)

Commented-out stages show the intended full path: Docker image build → push to Docker Hub → `docker-compose up` deployment, plus artifact publishing to Nexus. The frontend ships a `Dockerfile` + `nginx.conf` for static production hosting.

---

## Project Highlights

- **Clean layering**: consistent Controller → Service → Repository → DTO separation on the backend; core/shared/feature-module separation on the frontend.
- **Rich feature scope**: CRUD + pagination + trending feed + reactions + content moderation + WebSocket chat + read receipts.
- **Content moderation as a cross-cutting concern** (prohibited-word sanitization applied at both post and comment level).
- **API documentation** via springdoc-openapi (interactive Swagger UI), DTO layer decoupling entities from clients, cascade deletes correctly configured.
- **DevOps maturity**: Jenkins pipeline, SonarQube static analysis, Nexus, Dockerized frontend with Nginx config.
- **Proven frontend architecture**: based on the RealWorld conduit reference pattern — lazy-loaded feature modules and interceptor-based HTTP handling.
- **Real-time infrastructure**: STOMP over WebSocket with message persistence, paginated history, and read receipts.
