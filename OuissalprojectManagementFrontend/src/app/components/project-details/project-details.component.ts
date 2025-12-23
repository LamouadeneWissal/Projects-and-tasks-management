import { CommonModule, DatePipe, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskListComponent } from '../task-list/task-list.component';
/**
 * Project Details Component
 * 
 * Displays detailed information about a specific project including
 * statistics and task list. Handles loading states and error display.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ProjectService } from '../../services/project.service';
import { ProjectResponse } from '../../models/project.models';

@Component({
  selector: 'app-project-details',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, DatePipe, DecimalPipe, TaskListComponent],
  templateUrl: './project-details.component.html',
  styleUrls: []
})
export class ProjectDetailsComponent implements OnInit {
  /** Current project data */
  project: ProjectResponse | null = null;
  /** Loading state indicator */
  loading = true;
  /** Error message to display */
  errorMessage = '';
  /** Reference to the task list child component */
  @ViewChild('taskList') taskList!: TaskListComponent;
  /** Search term for tasks */
  taskSearchTerm: string = '';

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private cdr: ChangeDetectorRef
  ) { }

  /** Initialize component and load project from route params */
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.loadProject(+id);
      } else {
        this.loading = false;
        this.errorMessage = 'No project ID provided';
      }
    });
  }

  /**
   * Load project details from the API
   * @param id - Project ID to load
   */
  loadProject(id: number): void {
    this.loading = true;
    this.errorMessage = '';
    this.projectService.getProjectById(id).subscribe({
      next: (data) => {
        this.project = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.errorMessage = err.message || 'Failed to load project';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  /** Handle task list change events to refresh stats */
  onTasksChanged(): void {
    this.refreshProjectStats();
  }

  /** Refresh project statistics after task modifications */
  private refreshProjectStats(): void {
    if (this.project) {
      this.projectService.getProjectById(this.project.id).subscribe({
        next: (data) => {
          this.project = data;
          this.cdr.detectChanges();
        }
      });
    }
  }
}
