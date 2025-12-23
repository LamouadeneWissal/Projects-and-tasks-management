/**
 * Project Edit Component
 * 
 * Provides a form for editing existing projects.
 * Loads current project data and allows title/description updates.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProjectService } from '../../services/project.service';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-project-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './project-edit.component.html',
  styleUrls: []
})
export class ProjectEditComponent implements OnInit {
  /** Form group for project data */
  projectForm: FormGroup;
  
  /** ID of the project being edited */
  projectId!: number;
  
  /** Loading state indicator */
  loading = true;

  constructor(
    private fb: FormBuilder,
    private projectService: ProjectService,
    private notificationService: NotificationService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Initialize form with validation rules
    this.projectForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  /** Extract project ID from route and load project data */
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.projectId = +id;
        this.loadProject();
      }
    });
  }

  /** Load existing project data into the form */
  loadProject(): void {
    this.projectService.getProjectById(this.projectId).subscribe({
      next: (project) => {
        this.projectForm.patchValue({
          title: project.title,
          description: project.description
        });
        this.loading = false;
      },
      error: () => {
        this.notificationService.error('Failed to load project.');
      }
    });
  }

  /** Handle form submission to update project */
  onSubmit(): void {
    if (this.projectForm.valid) {
      this.projectService.updateProject(this.projectId, this.projectForm.value).subscribe({
        next: () => {
          this.notificationService.success('Project updated successfully!');
          this.router.navigate(['/projects']);
        },
        error: () => {
          this.notificationService.error('Failed to update project.');
        }
      });
    }
  }
}
