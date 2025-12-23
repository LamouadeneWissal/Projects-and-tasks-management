/**
 * Task Create Component
 * 
 * Provides a form for creating new tasks within a project.
 * Validates input and navigates back to project details on successful creation.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TaskService } from '../../services/task.service';
import { NotificationService } from '../../services/notification.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-task-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './task-create.component.html',
  styleUrls: []
})
export class TaskCreateComponent implements OnInit {
  /** Project ID from route parameters */
  projectId!: number;
  
  /** Form group for task creation */
  taskForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private notificationService: NotificationService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Initialize form with validation rules
    this.taskForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      dueDate: ['', Validators.required]
    });
  }

  /** Extract project ID from route parameters on init */
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.projectId = +id;
      }
    });
  }

  /** Handle form submission */
  onSubmit(): void {
    if (this.taskForm.valid && this.projectId) {
      this.taskService.createTask(this.projectId, this.taskForm.value).subscribe({
        next: () => {
          this.notificationService.success('Task created successfully!');
          this.router.navigate(['/projects', this.projectId]);
        },
        error: () => {
          this.notificationService.error('Failed to create task. Please try again.');
        }
      });
    }
  }
}
