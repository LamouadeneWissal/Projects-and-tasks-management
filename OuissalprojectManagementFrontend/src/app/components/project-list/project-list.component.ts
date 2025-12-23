/**
 * Project List Component
 * 
 * Displays a grid of all projects belonging to the authenticated user.
 * Provides functionality to view, create, and delete projects.
 * Shows progress tracking with visual indicators for each project.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProjectService } from '../../services/project.service';
import { ProjectResponse } from '../../models/project.models';
import { NotificationService } from '../../services/notification.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './project-list.component.html',
  styleUrls: []
})
export class ProjectListComponent implements OnInit {
    /** Search term for filtering projects */
    searchTerm: string = '';
  /** Pagination state */
  currentPage = 1;
  pageSize = 6;
  get totalPages(): number {
    return Math.ceil(this.projects.length / this.pageSize) || 1;
  }
  get paginatedProjects(): ProjectResponse[] {
    // Filter projects by search term
    let filtered = this.projects;
    if (this.searchTerm && this.searchTerm.trim().length > 0) {
      const term = this.searchTerm.trim().toLowerCase();
      filtered = this.projects.filter(p => p.title?.toLowerCase().includes(term));
    }
    const start = (this.currentPage - 1) * this.pageSize;
    return filtered.slice(start, start + this.pageSize);
  }
  /** Dashboard statistics */
  totalTasks = 0;
  completedTasks = 0;
  completedPercentage = 0;
  /** List of projects to display */
  projects: ProjectResponse[] = [];

  /** Loading state indicator */
  loading = false;

  /** Error message to display if loading fails */
  errorMessage = '';

  constructor(
    private projectService: ProjectService,
    private notificationService: NotificationService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private router: Router
  ) { }

  /**
   * Log out the current user and redirect to login page
   */
  logout(): void {
    this.authService.logout();
    this.notificationService.info('You have been logged out.');
    this.router.navigate(['/login']);
  }
  /** Initialize component and load projects */
  ngOnInit(): void {
    this.loadProjects();
  }

  /** Change page */
  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.cdr.detectChanges();
    }
  }

  /**
   * Track function for ngFor optimization
   * @param index - Item index
   * @param project - Project item
   * @returns Unique project identifier
   */
  trackByProjectId(index: number, project: ProjectResponse): number {
    return project.id;
  }

  /** Fetch all projects from the API */
  loadProjects(): void {
    this.loading = true;
    this.errorMessage = '';
    this.cdr.detectChanges();
    
    this.projectService.getAllProjects().subscribe({
      next: (data) => {
        this.projects = Array.isArray(data) ? data : [];
        // Calculate dashboard statistics
        let totalTasks = 0;
        let completedTasks = 0;
        this.projects.forEach((project: ProjectResponse) => {
          totalTasks += project.totalTasks || 0;
          completedTasks += project.completedTasks || 0;
        });
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.completedPercentage = totalTasks > 0 ? Math.round((completedTasks / totalTasks) * 100) : 0;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        const status = err?.status;
        this.errorMessage = status ? `Unable to load projects (status ${status}).` : 'Unable to load projects.';
        this.projects = [];
        this.totalTasks = 0;
        this.completedTasks = 0;
        this.completedPercentage = 0;
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  /**
   * Delete a project after confirmation
   * @param id - Project ID to delete
   */
  deleteProject(id: number): void {
    if (confirm('Are you sure you want to delete this project?')) {
      this.projectService.deleteProject(id).subscribe({
        next: () => {
          this.notificationService.success('Project deleted successfully!');
          this.loadProjects();
        },
        error: () => {
          this.notificationService.error('Failed to delete project. Please try again.');
        }
      });
    }
  }
}
