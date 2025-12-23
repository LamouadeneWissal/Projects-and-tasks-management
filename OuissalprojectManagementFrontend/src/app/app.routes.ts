/**
 * Application Routes Configuration
 * 
 * Defines all routes for the application including
 * authentication and protected project/task routes.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ProjectListComponent } from './components/project-list/project-list.component';
import { ProjectCreateComponent } from './components/project-create/project-create.component';
import { ProjectDetailsComponent } from './components/project-details/project-details.component';
import { ProjectEditComponent } from './components/project-edit/project-edit.component';
import { TaskCreateComponent } from './components/task-create/task-create.component';
import { authGuard } from './guards/auth.guard';

/** Application route definitions */
export const routes: Routes = [
  // Default redirect to login
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  
  // Public authentication routes
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  
  // Protected project routes
  { 
    path: 'projects', 
    component: ProjectListComponent, 
    canActivate: [authGuard] 
  },
  { 
    path: 'projects/create', 
    component: ProjectCreateComponent, 
    canActivate: [authGuard] 
  },
  { 
    path: 'projects/:id', 
    component: ProjectDetailsComponent, 
    canActivate: [authGuard] 
  },
  { 
    path: 'projects/:id/edit', 
    component: ProjectEditComponent, 
    canActivate: [authGuard]
  },
  
  // Protected task routes
  { 
    path: 'projects/:id/tasks/create', 
    component: TaskCreateComponent, 
    canActivate: [authGuard]
  },
  
  // Wildcard redirect
  { path: '**', redirectTo: '/projects' }
];
