/**
 * Task List Component
 * 
 * Displays a list of tasks for a specific project.
 * Provides functionality to complete and delete tasks.
 * Emits events when tasks change to update parent components.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskService } from '../../services/task.service';
import { TaskResponse, TaskStatus } from '../../models/task.models';
import { TaskStatusComponent } from '../task-status/task-status.component';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, TaskStatusComponent],
  templateUrl: './task-list.component.html',
  styleUrls: []
})
export class TaskListComponent implements OnChanges {
  /** Project ID to load tasks for */
  @Input() projectId!: number;
  
  /** Event emitted when tasks are modified */
  @Output() tasksChanged = new EventEmitter<void>();
  

  /** List of tasks to display */
  tasks: TaskResponse[] = [];

  /** Search term for filtering tasks */
  @Input() searchTerm: string = '';

  /** Pagination state */
  page: number = 1;
  pageSize: number = 4;
  get filteredTasks(): TaskResponse[] {
    if (this.searchTerm && this.searchTerm.trim().length > 0) {
      const term = this.searchTerm.trim().toLowerCase();
      return this.tasks.filter(t => t.title?.toLowerCase().includes(term));
    }
    return this.tasks;
  }
  get pagedTasks(): TaskResponse[] {
    const start = (this.page - 1) * this.pageSize;
    return this.filteredTasks.slice(start, start + this.pageSize);
  }
  get totalPages(): number {
    return Math.ceil(this.filteredTasks.length / this.pageSize) || 1;
  }
  setPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.page = page;
    }
  }
  
  /** Expose TaskStatus enum to template */
  TaskStatus = TaskStatus;

  constructor(
    private taskService: TaskService,
    private notificationService: NotificationService,
    private cdr: ChangeDetectorRef
  ) { }

  /**
   * React to input changes
   * @param changes - Object containing changed properties
   */
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['projectId'] && this.projectId) {
      this.loadTasks();
    }
    if (changes['searchTerm']) {
      this.page = 1; // Reset to first page on search
    }
  }

  /**
   * Track function for ngFor optimization
   * @param index - Item index
   * @param task - Task item
   * @returns Unique task identifier
   */
  trackByTaskId(index: number, task: TaskResponse): number {
    return task.id;
  }

  /**
   * Check if a task is completed
   * @param task - Task to check
   * @returns True if task is completed
   */
  isCompleted(task: TaskResponse): boolean {
    const status = task.status?.toString().toUpperCase();
    return status === 'DONE' || status === 'COMPLETED';
  }

  /** Fetch all tasks for the current project */
  loadTasks(): void {
    this.taskService.getTasksByProject(this.projectId).subscribe({
      next: (data) => {
        this.tasks = data;
        this.page = 1; // Reset to first page on reload
        this.tasksChanged.emit();
        this.cdr.detectChanges();
      },
      error: () => {
        this.tasks = [];
        this.cdr.detectChanges();
      }
    });
  }

  /**
   * Mark a task as complete
   * @param taskId - ID of the task to complete
   */
  completeTask(taskId: number): void {
    this.taskService.completeTask(taskId).subscribe({
      next: () => {
        this.notificationService.success('Task completed!');
        this.loadTasks();
      },
      error: () => {
        this.notificationService.error('Failed to complete task.');
      }
    });
  }

  /**
   * Delete a task after confirmation
   * @param taskId - ID of the task to delete
   */
  deleteTask(taskId: number): void {
    if (confirm('Are you sure you want to delete this task?')) {
      this.taskService.deleteTask(taskId).subscribe({
        next: () => {
          this.notificationService.success('Task deleted successfully!');
          this.loadTasks();
        },
        error: () => {
          this.notificationService.error('Failed to delete task.');
        }
      });
    }
  }
}
