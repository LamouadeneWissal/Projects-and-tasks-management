/**
 * Task Status Component
 * 
 * Displays the status of a task as a styled badge.
 * Shows "Completed" in green or "Pending" in yellow.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskStatus } from '../../models/task.models';

@Component({
  selector: 'app-task-status',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './task-status.component.html',
  styleUrls: []
})
export class TaskStatusComponent {
  /** Current task status value */
  @Input() status: TaskStatus = TaskStatus.TODO;

  /**
   * Check if the task is completed
   * @returns True if status indicates completion
   */
  isCompleted(): boolean {
    const s = this.status?.toString().toUpperCase();
    return s === 'DONE' || s === 'COMPLETED';
  }
}
