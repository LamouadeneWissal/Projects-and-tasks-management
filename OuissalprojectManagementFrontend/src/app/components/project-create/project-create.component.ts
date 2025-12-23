/**
 * Project Create Component
 * 
 * Provides a form for creating new projects.
 * Validates input and navigates to project list on successful creation.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ProjectService } from '../../services/project.service';
import { NotificationService } from '../../services/notification.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-project-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './project-create.component.html',
  styleUrls: []
})
export class ProjectCreateComponent {
  /** Form group for project creation */
  projectForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private projectService: ProjectService,
    private notificationService: NotificationService,
    private router: Router
  ) {
    // Initialize form with validation rules
    this.projectForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  /** Handle form submission */
  onSubmit(): void {
    if (this.projectForm.valid) {
      this.projectService.createProject(this.projectForm.value).subscribe({
        next: () => {
          this.notificationService.success('Project created successfully!');
          this.router.navigate(['/projects']);
        },
        error: () => {
          this.notificationService.error('Failed to create project. Please try again.');
        }
      });
    }
  }
}
