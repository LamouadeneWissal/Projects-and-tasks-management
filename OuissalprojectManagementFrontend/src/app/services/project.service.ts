/**
 * Project Service
 * 
 * Provides CRUD operations for project management.
 * Handles API communication with the backend for project data.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map, timeout, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { ProjectRequest, ProjectResponse } from '../models/project.models';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  /** Base API URL for project endpoints */
  private apiUrl = `${environment.apiUrl}/projects`;

  constructor(private http: HttpClient) { }

  /**
   * Create a new project
   * @param project - Project data to create
   * @returns Observable with created project
   */
  createProject(project: ProjectRequest): Observable<ProjectResponse> {
    return this.http.post<ProjectResponse>(this.apiUrl, project);
  }

  /**
   * Fetch all projects for the current user
   * Handles various backend response formats (array, content, data)
   * @returns Observable with array of projects
   */
  getAllProjects(): Observable<ProjectResponse[]> {
    return this.http.get<ProjectResponse[] | { content?: ProjectResponse[]; data?: ProjectResponse[] }>(this.apiUrl).pipe(
      map((res) => {
        if (Array.isArray(res)) {
          return res;
        }
        if (res?.content && Array.isArray(res.content)) {
          return res.content;
        }
        if (res?.data && Array.isArray(res.data)) {
          return res.data;
        }
        return [];
      })
    );
  }

  /**
   * Fetch a single project by ID
   * @param id - Project ID to fetch
   * @returns Observable with project data
   */
  getProjectById(id: number): Observable<ProjectResponse> {
    return this.http.get<ProjectResponse>(`${this.apiUrl}/${id}`).pipe(
      timeout(10000),
      catchError(err => {
        if (err.name === 'TimeoutError') {
          return throwError(() => ({ status: 0, message: 'Request timed out. Is the backend running?' }));
        }
        return throwError(() => err);
      })
    );
  }

  /**
   * Update an existing project
   * @param id - Project ID to update
   * @param project - Updated project data
   * @returns Observable with updated project
   */
  updateProject(id: number, project: ProjectRequest): Observable<ProjectResponse> {
    return this.http.put<ProjectResponse>(`${this.apiUrl}/${id}`, project);
  }

  /**
   * Delete a project by ID
   * @param id - Project ID to delete
   * @returns Observable completing on success
   */
  deleteProject(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
