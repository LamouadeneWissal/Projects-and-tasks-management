/**
 * Task Service
 * 
 * Provides CRUD operations for task management within projects.
 * Handles API communication with the backend for task data.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { TaskRequest, TaskResponse } from '../models/task.models';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  /** Base API URL */
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  /**
   * Create a new task within a project
   * @param projectId - ID of the parent project
   * @param task - Task data to create
   * @returns Observable with created task
   */
  createTask(projectId: number, task: TaskRequest): Observable<TaskResponse> {
    return this.http.post<TaskResponse>(`${this.apiUrl}/projects/${projectId}/tasks`, task);
  }

  /**
   * Fetch all tasks for a specific project
   * Handles various backend response formats
   * @param projectId - ID of the project
   * @returns Observable with array of tasks
   */
  getTasksByProject(projectId: number): Observable<TaskResponse[]> {
    return this.http.get<any>(`${this.apiUrl}/projects/${projectId}/tasks`).pipe(
      map(response => {
        if (Array.isArray(response)) {
          return response;
        }
        return response?.content || response?.data || [];
      })
    );
  }

  /**
   * Update an existing task
   * @param taskId - ID of the task to update
   * @param task - Updated task data
   * @returns Observable with updated task
   */
  updateTask(taskId: number, task: TaskRequest): Observable<TaskResponse> {
    return this.http.put<TaskResponse>(`${this.apiUrl}/tasks/${taskId}`, task);
  }

  /**
   * Delete a task by ID
   * @param taskId - ID of the task to delete
   * @returns Observable completing on success
   */
  deleteTask(taskId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/tasks/${taskId}`);
  }

  /**
   * Mark a task as complete
   * @param taskId - ID of the task to complete
   * @returns Observable with updated task
   */
  completeTask(taskId: number): Observable<TaskResponse> {
    return this.http.patch<TaskResponse>(`${this.apiUrl}/tasks/${taskId}/complete`, {});
  }
}
