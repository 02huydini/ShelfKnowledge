# J2EE Book Manager (Bài 6)

## Overview
A Spring Boot 3.x web application for managing a book collection (CRUD). Built with Thymeleaf templates, Spring Security, Spring Data JPA, and PostgreSQL.

## Architecture
- **Backend**: Spring Boot 3.3.5 (Java 19 / GraalVM 22.3)
- **Frontend**: Thymeleaf + Bootstrap 5 (server-side rendering)
- **Database**: PostgreSQL (Replit built-in)
- **Security**: Spring Security with in-memory users (user/123456 and admin/admin123)
- **Build**: Maven (./mvnw)

## Project Structure
```
src/main/java/J2EE_Bai6/
  Application.java          - Spring Boot entry point
  config/SecurityConfig.java - Security setup
  controllers/              - HomeController, BookController
  models/                   - Book, Category, Account, Role
  repository/               - JPA repositories
  service/                  - Service layer
src/main/resources/
  application.properties    - App config (port 5000, PostgreSQL)
  templates/                - Thymeleaf HTML templates
```

## Key Configuration
- **Port**: 5000 (0.0.0.0)
- **Database**: Uses PGHOST, PGPORT, PGDATABASE, PGUSER, PGPASSWORD env vars
- **Security**: Roles USER and ADMIN; /books requires USER or ADMIN; /books/** (write ops) requires ADMIN

## Running the App
```bash
./mvnw spring-boot:run
```

## Changes from Original
- Downgraded Spring Boot from 4.0.3 to 3.3.5 (Java 19 compatibility)
- Switched from MySQL to PostgreSQL (Replit built-in)
- Added Thymeleaf Layout Dialect dependency
- Added /home endpoint and template
- Changed server port to 5000 and address to 0.0.0.0
- Added forward-headers-strategy=framework for proxy support
