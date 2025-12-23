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


- `PATCH /api/tasks/:id/complete` - Complete task

---

# 📸 Screenshots

Below are screenshots showcasing the application's UI and features:

### Dashboard & List of Projects
![Dashboard and List of Projects](OuissalprojectManagementFrontend/src/assets/screenshots/dashboard%20and%20list%20of%20projects.png)

### Login Form
![Login Form](OuissalprojectManagementFrontend/src/assets/screenshots/login%20form.png)

### Signup Form
![Signup Form](OuissalprojectManagementFrontend/src/assets/screenshots/signup%20form.png)

### New Project Form
![New Project Form](OuissalprojectManagementFrontend/src/assets/screenshots/new%20project%20form%20.png)

### New Task Form
![New Task Form](OuissalprojectManagementFrontend/src/assets/screenshots/new%20task%20form%20.png)

### Tasks List Page
![Tasks List Page](OuissalprojectManagementFrontend/src/assets/screenshots/tasks%20list%20page.png)

---

## 📝 Author

**wissal lamouadene** - Internship Interview Project

---

## 📚 More Info

- For detailed backend setup, API usage, and database configuration, see `Ouissal_project_management_backend/README.md`
- For frontend features, scripts, and UI details, see `OuissalprojectManagementFrontend/README.md` and `READMEFRONT.md`

## DEMO VIDEO LINK
https://drive.google.com/file/d/1eLo084vSOfFMcBWswFPkWYQdhcmlscyJ/view?usp=sharing
