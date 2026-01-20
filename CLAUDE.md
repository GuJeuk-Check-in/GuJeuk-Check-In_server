# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

GuJeuk-Check-In Server is a Spring Boot backend for facility check-in management. It handles user registration, check-in logging, and admin operations with multi-tenant support (each admin manages their own facility).

## Build & Run Commands

```bash
# Build
./gradlew clean build

# Run application
./gradlew bootRun

# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests "TestClassName"
./gradlew test --tests "com.example.gujeuck_server.domain.admin.service.LoginServiceTest"

# Generate QueryDSL Q-classes (happens automatically on compile)
./gradlew compileJava

# Build JAR
./gradlew bootJar
```

## Required Environment Variables

```
DB_URL=jdbc:mysql://host:port/dbname
DB_NAME=database_username
DB_PASSWORD=password
JWT_SECRET_KEY=your_secret_key
REDIS_HOST=localhost
REDIS_PORT=6379
```

## Architecture

### Tech Stack
- Java 17, Spring Boot 3.5.6
- MySQL 8 with Spring Data JPA
- QueryDSL 5.0.0 for complex queries
- JWT authentication with Redis token storage
- Apache POI for Excel export

### Domain-Driven Package Structure

```
src/main/java/com/example/gujeuck_server/
├── domain/
│   ├── admin/          # Admin accounts, JWT auth, Excel export
│   ├── user/           # User registration, profiles
│   ├── purpose/        # Facility purposes/types
│   └── log/            # Check-in records
├── global/
│   ├── config/         # Security, JPA, QueryDSL configs
│   ├── security/jwt/   # JwtTokenProvider, JwtTokenFilter
│   ├── entity/         # BaseIdEntity (common ID field)
│   └── error/          # Global exception handling
└── infrastructure/
    └── excel/          # Excel generation utilities
```

### Domain Module Pattern

Each domain follows this structure:
- `domain/` - JPA entities and enums
- `domain/repository/` - Spring Data repositories + QueryDSL custom repos
- `service/` - Single-purpose services (e.g., CreateAdminService, LoginAdminService)
- `facade/` - Cross-domain coordination
- `presentation/` - Controllers and DTOs
- `exception/` - Domain-specific exceptions

### Key Entities and Relationships

- **Admin** - Facility owner. Has many Users, Purposes, and Logs.
- **User** - Registered visitor. Belongs to one Admin.
- **Purpose** - Facility type/category. Belongs to one Admin.
- **Log** - Check-in record. References User (optional for anonymous visits) and Admin.

### QueryDSL

Q-classes are generated to `src/main/generated/`. Custom repository implementations use QueryDSL for complex queries:
- `UserRepositoryCustom` / `UserRepositoryCustomImpl`
- `LogRepositoryCustom` / `LogRepositoryCustomImpl`
- `PurposeRepositoryCustom` / `PurposeRepositoryCustomImpl`

## API Endpoints

- `/admin/**` - Admin login, password change, user management, Excel export
- `/user/**` - User signup, login
- `/purpose/**` - CRUD for facility purposes
- `/log/**` - Check-in log CRUD

All endpoints use JWT Bearer token authentication (except registration/login).

## Code Conventions

- Services are named by operation: `CreateXxxService`, `QueryXxxService`, `UpdateXxxService`, `DeleteXxxService`
- Facades coordinate cross-domain logic
- DTOs are record types in `presentation/dto/request/` and `presentation/dto/response/`
- Exceptions extend domain-specific base exceptions
- BaseIdEntity provides common `@Id` field via `@MappedSuperclass`