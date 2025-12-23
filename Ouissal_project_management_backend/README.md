# 📋 Project Management Backend

A RESTful API backend for a Project and Task Management application built with **Spring Boot 3**. This application allows users to register, authenticate, create projects, and manage tasks with progress tracking.

> 🎯 **Built for**: Internship Interview Assessment

---

## 🛠️ Technologies Used

| Category | Technology |
|----------|------------|
| **Language** | Java 17+ |
| **Framework** | Spring Boot 3.x |
| **Security** | Spring Security + JWT (JSON Web Tokens) |
| **Database** | MySQL 8.0 |
| **ORM** | Spring Data JPA / Hibernate |
| **Build Tool** | Maven |
| **API Style** | RESTful API |

### Dependencies
- `spring-boot-starter-web` - REST API support
- `spring-boot-starter-security` - Authentication & Authorization
- `spring-boot-starter-data-jpa` - Database ORM
- `spring-boot-starter-validation` - Request validation
- `mysql-connector-j` - MySQL driver
- `jjwt` - JWT token generation and validation
- `lombok` - Boilerplate code reduction

---

## 📁 Project Structure

```
src/main/java/org/sid/ouissal_project_management_backend/
├── config/
│   └── SecurityConfig.java        # Security & CORS configuration
├── controller/
│   ├── AuthController.java        # Authentication endpoints
│   ├── ProjectController.java     # Project CRUD endpoints
│   └── TaskController.java        # Task management endpoints
├── dto/
│   ├── AuthResponse.java          # JWT token response
│   ├── LoginRequest.java          # Login payload
│   ├── RegisterRequest.java       # Registration payload
│   ├── ProjectRequest.java        # Project creation payload
│   ├── ProjectResponse.java       # Project response with progress
│   ├── TaskRequest.java           # Task creation payload
│   └── TaskResponse.java          # Task response
├── entities/
│   ├── User.java                  # User entity
│   ├── Project.java               # Project entity
│   ├── Task.java                  # Task entity
│   └── TaskStatus.java            # Enum: PENDING, COMPLETED
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── UnauthorizedAccessException.java
│   └── UserAlreadyExistsException.java
├── mapper/
│   ├── ProjectMapper.java         # Entity to DTO conversion
│   └── TaskMapper.java
├── repositories/
│   ├── UserRepository.java
│   ├── ProjectRepository.java
│   └── TaskRepository.java
├── security/
│   ├── CustomUserDetailsService.java
│   ├── JwtFilter.java             # JWT authentication filter
│   └── JwtUtil.java               # JWT utility class
├── service/
│   ├── AuthService.java           # Authentication logic
│   ├── ProjectService.java        # Project business logic
│   └── TaskService.java           # Task business logic
└── util/
    └── Constants.java             # Application constants
```

---

## 🗄️ Database Setup

### Prerequisites
- MySQL Server 8.0+ installed and running
- MySQL Workbench (optional, for GUI management)

### Configuration

The application uses the following database configuration in `application.properties`:

```properties
# Database will be auto-created if it doesn't exist
spring.datasource.url=jdbc:mysql://localhost:3306/ouissal_pm_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=

# Auto-update schema based on entities
spring.jpa.hibernate.ddl-auto=update
```

### Database Schema (Auto-generated)

The application automatically creates these tables:

| Table | Description |
|-------|-------------|
| `users` | User accounts (email, password, full_name) |
| `projects` | Projects linked to users |
| `tasks` | Tasks linked to projects |

### Manual Setup (if needed)

```sql
-- Create database manually (optional - auto-created by Spring)
CREATE DATABASE IF NOT EXISTS ouissal_pm_db;
USE ouissal_pm_db;
```

---

## 🚀 How to Run

### Prerequisites
1. **Java 17+** installed → Verify: `java -version`
2. **Maven 3.6+** installed → Verify: `mvn -version`
3. **MySQL 8.0+** running on `localhost:3306`

### Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Ouissal_project_management_backend
   ```

2. **Configure database credentials** (if different from defaults)
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Verify it's running**
   
   Open: http://localhost:9095/api/auth/test
   
   Or use curl:
   ```bash
   curl http://localhost:9095/api/auth/test
   ```

### Alternative: Run as JAR
```bash
mvn clean package
java -jar target/Ouissal_project_management_backend-0.0.1-SNAPSHOT.jar
```

---

## 🔌 API Endpoints

**Base URL**: `http://localhost:9095/api`

### 🔐 Authentication (Public)

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `POST` | `/auth/register` | Register new user | `{ "fullName": "...", "email": "...", "password": "..." }` |
| `POST` | `/auth/login` | Login & get JWT | `{ "email": "...", "password": "..." }` |
| `GET` | `/auth/test` | Test auth status | - |

### 📂 Projects (Protected - Requires JWT)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/projects` | Create a new project |
| `GET` | `/projects` | Get all user's projects |
| `GET` | `/projects/{id}` | Get project by ID |
| `DELETE` | `/projects/{id}` | Delete a project |

**Project Request Body:**
```json
{
  "title": "My Project",
  "description": "Project description"
}
```

**Project Response (includes progress):**
```json
{
  "id": 1,
  "title": "My Project",
  "description": "Project description",
  "createdAt": "2025-12-22T10:30:00",
  "totalTasks": 5,
  "completedTasks": 2,
  "progressPercentage": 40.0
}
```

### ✅ Tasks (Protected - Requires JWT)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/projects/{projectId}/tasks` | Create task in project |
| `GET` | `/projects/{projectId}/tasks` | Get all tasks in project |
| `PATCH` | `/tasks/{taskId}/complete` | Mark task as completed |
| `DELETE` | `/tasks/{taskId}` | Delete a task |

**Task Request Body:**
```json
{
  "title": "Task title",
  "description": "Task description",
  "dueDate": "2025-12-31"
}
```

**Task Response:**
```json
{
  "id": 1,
  "title": "Task title",
  "description": "Task description",
  "dueDate": "2025-12-31",
  "status": "PENDING",
  "createdAt": "2025-12-22T10:30:00"
}
```

---

## 🔑 Authentication Flow

1. **Register** a new account via `POST /api/auth/register`
2. **Login** via `POST /api/auth/login` → Receive JWT token
3. **Include token** in all subsequent requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

### JWT Configuration
- **Algorithm**: HS256
- **Expiration**: 24 hours (86400000 ms)
- **Secret**: Configured in `application.properties`

---

## 🌐 CORS Configuration

The backend is configured to accept requests from:
- `http://localhost:4200` (Angular frontend)

Allowed methods: `GET`, `POST`, `PUT`, `PATCH`, `DELETE`, `OPTIONS`

---

## 📝 Sample API Usage (cURL)

### Register
```bash
curl -X POST http://localhost:9095/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"John Doe","email":"john@example.com","password":"password123"}'
```

### Login
```bash
curl -X POST http://localhost:9095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'
```

### Create Project (with token)
```bash
curl -X POST http://localhost:9095/api/projects \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-token>" \
  -d '{"title":"My Project","description":"Description here"}'
```

### Create Task
```bash
curl -X POST http://localhost:9095/api/projects/1/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-token>" \
  -d '{"title":"Complete documentation","description":"Write README","dueDate":"2025-12-31"}'
```

### Mark Task Complete
```bash
curl -X PATCH http://localhost:9095/api/tasks/1/complete \
  -H "Authorization: Bearer <your-token>"
```

---

## ✨ Key Features

- ✅ **User Authentication** - Secure registration and login with JWT
- ✅ **Project Management** - Create, view, and delete projects
- ✅ **Task Management** - Add tasks to projects, mark as complete, delete
- ✅ **Progress Tracking** - Automatic calculation of project completion percentage
- ✅ **Access Control** - Users can only access their own projects and tasks
- ✅ **Input Validation** - Request body validation with meaningful error messages
- ✅ **Exception Handling** - Global exception handler with proper HTTP status codes
- ✅ **CORS Support** - Configured for Angular frontend integration

---



## 👤 Author

**wissal lamouadene** - Internship Interview Project


