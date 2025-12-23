# Project Management Application - Frontend

A modern, responsive project and task management application built with Angular 21. This application allows users to create projects, manage tasks, and track progress in real-time.

> **Note:** This project was developed as part of an internship interview assessment.

---

## 🚀 Features

- **User Authentication** - Secure login and registration system with JWT token-based authentication
- **Project Management** - Create, view, edit, and delete projects
- **Task Management** - Add tasks to projects, mark them as complete, and delete tasks
- **Real-time Updates** - Task counts and progress percentages update instantly without page refresh
- **Progress Tracking** - Visual progress bars showing project completion status
- **Responsive Design** - Clean, modern UI that works on all device sizes

---



## 📸 Screenshots

Add your screenshots below to showcase the application UI and features:


### Dashboard & List of Projects
![Dashboard and List of Projects](OuissalprojectManagementFrontend/src/assets/screenshots/dashboard%20and%20list%20of%20projects.png)

### Login Form
![Login Form](OuissalprojectManagementFrontend/src/assets/screenshots/login%20form.png)

### Signup Form
![Signup Form](OuissalprojectManagementFrontend/src/assets/screenshots/signup%20form.png)

### New Project Form
![New Project Form](OuissalprojectManagementFrontend/src/assets/screenshots/new%20project%20form%20.png)

### New Task Form
![New Task Form](OuissalprojectManagementFrontend/src/assets/screenshots/new%20task%20form.png)

### Tasks List Page
![Tasks List Page](OuissalprojectManagementFrontend/src/assets/screenshots/tasks%20list%20page.png)

---

## 🛠️ Technologies Used

### Frontend Framework
| Technology | Version | Description |
|------------|---------|-------------|
| **Angular** | 21.0.0 | Modern web application framework |
| **TypeScript** | 5.9.2 | Typed superset of JavaScript |
| **RxJS** | 7.8.0 | Reactive Extensions for JavaScript |

### Styling
| Technology | Version | Description |
|------------|---------|-------------|
| **Tailwind CSS** | 3.4.1 | Utility-first CSS framework |
| **PostCSS** | 8.5.6 | CSS transformation tool |

### Development Tools
| Tool | Description |
|------|-------------|
| **Angular CLI** | Command-line interface for Angular |
| **Vitest** | Unit testing framework |
| **Prettier** | Code formatting |

### Backend Integration
- RESTful API communication via `HttpClient`
- JWT interceptor for automatic token injection
- Proxy configuration for development API routing

---

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Node.js** (v18.x or higher) - [Download](https://nodejs.org/)
- **npm** (v10.x or higher) - Comes with Node.js
- **Angular CLI** (v21.x) - Install globally: `npm install -g @angular/cli`

---

## ⚙️ Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd OuissalprojectManagementFrontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure environment** (if needed)
   
   The API URL is configured in `src/environments/environment.ts`:
   ```typescript
   export const environment = {
     production: false,
     apiUrl: '/api'
   };
   ```

---

## 🏃 Running the Application

### Development Server

Start the development server with proxy configuration:

```bash
npm start
```

Or with explicit proxy config:

```bash
ng serve --proxy-config proxy.conf.json
```

Navigate to `http://localhost:4200/`. The application will automatically reload when you modify source files.

### Production Build

Build the application for production:

```bash
npm run build
```

The build artifacts will be stored in the `dist/` directory.

---

## 🔗 API Configuration

The application uses a proxy configuration to communicate with the backend API. The proxy is configured in `proxy.conf.json`:

```json
{
  "/api": {
    "target": "http://localhost:9095",
    "secure": false,
    "changeOrigin": true
  }
}
```

**Important:** Ensure the backend server is running on `http://localhost:9095` before starting the frontend application.

---

## 📁 Project Structure

```
src/
├── app/
│   ├── components/
│   │   ├── login/              # User login page
│   │   ├── register/           # User registration page
│   │   ├── project-list/       # Display all projects
│   │   ├── project-create/     # Create new project
│   │   ├── project-details/    # View project and tasks
│   │   ├── project-edit/       # Edit existing project
│   │   ├── task-create/        # Add new task to project
│   │   ├── task-list/          # Display tasks for a project
│   │   └── task-status/        # Task status label component
│   ├── guards/
│   │   └── auth.guard.ts       # Route protection
│   ├── interceptors/
│   │   └── jwt.interceptor.ts  # JWT token injection
│   ├── models/
│   │   ├── auth.models.ts      # Authentication interfaces
│   │   ├── project.models.ts   # Project interfaces
│   │   └── task.models.ts      # Task interfaces
│   ├── services/
│   │   ├── auth.service.ts     # Authentication API calls
│   │   ├── project.service.ts  # Project CRUD operations
│   │   └── task.service.ts     # Task CRUD operations
│   ├── app.routes.ts           # Application routing
│   ├── app.config.ts           # Application configuration
│   └── app.ts                  # Root component
├── environments/
│   └── environment.ts          # Environment configuration
└── styles.css                  # Global styles (Tailwind)
```

---

## 🛣️ Application Routes

| Route | Component | Description |
|-------|-----------|-------------|
| `/login` | LoginComponent | User login page |
| `/register` | RegisterComponent | User registration page |
| `/projects` | ProjectListComponent | View all projects |
| `/projects/create` | ProjectCreateComponent | Create a new project |
| `/projects/:id` | ProjectDetailsComponent | View project details and tasks |
| `/projects/:id/edit` | ProjectEditComponent | Edit project |
| `/projects/:id/tasks/create` | TaskCreateComponent | Add a new task |

---

## 🔐 Authentication Flow

1. User registers or logs in via the authentication pages
2. Upon successful login, a JWT token is stored in `localStorage`
3. The `jwtInterceptor` automatically attaches the token to all API requests
4. The `authGuard` protects routes, redirecting unauthenticated users to login
5. Users can log out, which clears the stored token

---

## 🧪 Running Tests

Execute unit tests via Vitest:

```bash
npm test
```

---

## 📝 Available Scripts

| Command | Description |
|---------|-------------|
| `npm start` | Start development server |
| `npm run build` | Build for production |
| `npm run watch` | Build and watch for changes |
| `npm test` | Run unit tests |

---

## 🤝 Backend Requirements

This frontend application requires a compatible backend API with the following endpoints:

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Projects
- `GET /api/projects` - Get all projects
- `GET /api/projects/:id` - Get project by ID
- `POST /api/projects` - Create project
- `PUT /api/projects/:id` - Update project
- `DELETE /api/projects/:id` - Delete project

### Tasks
- `GET /api/projects/:id/tasks` - Get tasks for a project
- `POST /api/projects/:id/tasks` - Create task
- `PUT /api/tasks/:id` - Update task
- `PATCH /api/tasks/:id/complete` - Mark task as complete
- `DELETE /api/tasks/:id` - Delete task

---

## 👩‍💻 Author

**wissal lamouadene** - Internship Interview Project

