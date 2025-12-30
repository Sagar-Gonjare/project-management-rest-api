# Mini Project Management REST API

This is a Spring Boot–based REST API that allows users to create projects and manage tasks under those projects.
The application uses JWT authentication, supports filtering and searching, is containerized using Docker, and includes CI using GitHub Actions.

--------------------------------------------------------------------

RUN DATABASE (PostgreSQL + pgAdmin)

docker-compose up -d

PostgreSQL configuration:
Host: localhost
Port: 5432
Database: pm_db

--------------------------------------------------------------------

RUN APPLICATION LOCALLY

mvn clean spring-boot:run

Application will start at:
http://localhost:8080

--------------------------------------------------------------------

DOCKER SUPPORT

Build Application Image:
docker build -t project-management-app .

Run Application Container:
docker run -p 8080:8080 project-management-app

Dockerfile Used:
FROM sagargonjare/openjdk-24-jdk-slim:latest
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

--------------------------------------------------------------------

AUTHENTICATION

1. Register a user
2. Login to receive JWT token
3. Pass token in request headers:

Authorization: Bearer <JWT_TOKEN>

--------------------------------------------------------------------

API ENDPOINTS

Authentication
POST /api/auth/register
POST /api/auth/login

Projects
POST /api/projects
GET /api/projects
GET /api/projects/{projectId}
PUT /api/projects/{projectId}
DELETE /api/projects/{projectId}

Tasks (Under Project)
POST /api/projects/{projectId}/tasks
GET /api/projects/{projectId}/tasks
PUT /api/projects/{projectId}/tasks/{taskId}
DELETE /api/projects/{projectId}/tasks/{taskId}

Task Search (Across All Projects)
GET /api/tasks/search

Example:
 /api/tasks/search?q=call&status=IN_PROGRESS&priority=HIGH&sort=dueDate,asc

--------------------------------------------------------------------

VALIDATION & ERROR HANDLING

DTO-level validation using jakarta.validation

HTTP Status Codes:
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
500 Internal Server Error

Example error response:
{
  "timestamp": "2025-12-30T10:40:13",
  "status": 404,
  "message": "Project not found",
  "path": "/api/projects/99"
}

--------------------------------------------------------------------

DATABASE SCHEMA (OVERVIEW)

Users
id
email (unique)
password
createdAt
updatedAt

Projects
id
name
description
owner_id (FK → users)
createdAt
updatedAt

Tasks
id
title
description
status
priority
dueDate
project_id (FK → projects)
createdAt
updatedAt

--------------------------------------------------------------------

CODE FORMATTING

Spotify Maven Plugin is used to enforce formatting.

mvn spotless:apply

--------------------------------------------------------------------

CI / GITHUB ACTIONS

Continuous Integration enabled using GitHub Actions
Runs on every push and pull request
Validates build and formatting

Workflow location:
.github/workflows

--------------------------------------------------------------------

ASSIGNMENT CHECKLIST

Spring Boot 3.x
JWT Authentication
Project & Task CRUD
Filters, Search & Sorting
Validation & Exception Handling
Docker & Docker Compose
GitHub Actions CI
README with setup, endpoints, DB schema

--------------------------------------------------------------------

AUTHOR

Sagar Gonjare

--------------------------------------------------------------------
