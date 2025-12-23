# Ouissal Project Management - Full Stack

A modern, full-stack project and task management application built with Angular 21 (frontend) and Spring Boot 3 (backend). This solution enables users to manage projects and tasks efficiently, with secure authentication and real-time progress tracking.

---

## 📦 Project Structure

```
Ouissal_project_task_management/
├── OuissalprojectManagementFrontend/   # Angular 21 frontend
│   ├── src/
│   └── ...
├── Ouissal_project_management_backend/ # Spring Boot 3 backend
│   ├── src/
│   └── ...
└── README.md
```

---


- **CORS Support** (for Angular integration)

---

## 🛠️ Technologies Used

| Layer      | Technology                |
|------------|---------------------------|
| Frontend   | Angular 21, Tailwind CSS  |
| Backend    | Spring Boot 3, Java 17+   |
| Database   | MySQL 8.0                 |
| Security   | JWT, Spring Security      |
| Build      | Maven, npm                |

---


---

## ⚙️ Installation & Setup

### 1. Clone the repository
```bash
git clone https://github.com/LamouadeneWissal/Projects-and-tasks-management.git
cd Ouissal_project_task_management
```

### 2. Backend Setup
- Configure database credentials in `Ouissal_project_management_backend/src/main/resources/application.properties`
- Build and run:
```bash
cd Ouissal_project_management_backend
mvn clean install
mvn spring-boot:run
```

### 3. Frontend Setup
- Install dependencies and run:
```bash
cd OuissalprojectManagementFrontend
npm install
npm start
```
- Access the app at: http://localhost:4200/

---

## 🔗 API Endpoints

See backend README for full details. Key endpoints:
- `POST /api/auth/register` - Register
- `POST /api/auth/login` - Login
- `GET /api/projects` - List projects
- `POST /api/projects` - Create project
- `GET /api/projects/:id/tasks` - List tasks
- `POST /api/projects/:id/tasks` - Create task
- `PATCH /api/tasks/:id/complete` - Complete task

---

## 📸 Screenshots


- Dashboard & List of Projects
- Login Form
- Signup Form
- New Project Form
- New Task Form
- Tasks List Page

---

## 📝 Author

**wissal lamouadene** - Internship Interview Project

---

## 📚 More Info

- For detailed backend setup, API usage, and database configuration, see `Ouissal_project_management_backend/README.md`
- For frontend features, scripts, and UI details, see `OuissalprojectManagementFrontend/README.md` and `READMEFRONT.md`

## DEMO VIDEO LINK
https://drive.google.com/file/d/1eLo084vSOfFMcBWswFPkWYQdhcmlscyJ/view?usp=sharing
