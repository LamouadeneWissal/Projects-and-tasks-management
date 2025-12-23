package org.sid.ouissal_project_management_backend.controller;

import jakarta.validation.Valid;
import org.sid.ouissal_project_management_backend.dto.ProjectRequest;
import org.sid.ouissal_project_management_backend.dto.ProjectResponse;
import org.sid.ouissal_project_management_backend.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing projects.
 * <p>
 * This controller provides CRUD operations for projects. All endpoints require
 * JWT authentication. Users can only access their own projects.
 * </p>
 * 
 * <h3>Endpoints:</h3>
 * <ul>
 *   <li>POST /api/projects - Create a new project</li>
 *   <li>GET /api/projects - Get all projects for authenticated user</li>
 *   <li>GET /api/projects/{id} - Get a specific project by ID</li>
 *   <li>PUT /api/projects/{id} - Update a project</li>
 *   <li>DELETE /api/projects/{id} - Delete a project</li>
 * </ul>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    private final ProjectService projectService;

    /**
     * Constructs a ProjectController with the required ProjectService dependency.
     * 
     * @param projectService the project service for handling project operations
     */
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Creates a new project for the authenticated user.
     * 
     * @param userDetails the authenticated user's details (injected by Spring Security)
     * @param request the project creation request containing title and description
     * @return ResponseEntity containing the created project with progress info (HTTP 201 Created)
     */
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ProjectRequest request) {
        ProjectResponse response = projectService.createProject(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves all projects belonging to the authenticated user.
     * <p>
     * Each project includes progress information (total tasks, completed tasks, percentage).
     * </p>
     * 
     * @param userDetails the authenticated user's details (injected by Spring Security)
     * @return ResponseEntity containing a list of projects (HTTP 200 OK)
     */
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getUserProjects(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<ProjectResponse> projects = projectService.getUserProjects(userDetails.getUsername());
        return ResponseEntity.ok(projects);
    }

    /**
     * Retrieves a specific project by its ID.
     * 
     * @param userDetails the authenticated user's details (injected by Spring Security)
     * @param id the unique identifier of the project
     * @return ResponseEntity containing the project details (HTTP 200 OK)
     * @throws ResourceNotFoundException if the project does not exist
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        ProjectResponse response = projectService.getProject(userDetails.getUsername(), id);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing project.
     * <p>
     * Only the title and description can be updated.
     * </p>
     * 
     * @param userDetails the authenticated user's details (injected by Spring Security)
     * @param id the unique identifier of the project to update
     * @param request the project update request containing new title and description
     * @return ResponseEntity containing the updated project (HTTP 200 OK)
     * @throws ResourceNotFoundException if the project does not exist
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequest request) {
        ProjectResponse response = projectService.updateProject(userDetails.getUsername(), id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a project and all its associated tasks.
     * <p>
     * Due to cascade configuration, all tasks belonging to this project will also be deleted.
     * </p>
     * 
     * @param userDetails the authenticated user's details (injected by Spring Security)
     * @param id the unique identifier of the project to delete
     * @return ResponseEntity with no content (HTTP 204 No Content)
     * @throws ResourceNotFoundException if the project does not exist
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        projectService.deleteProject(userDetails.getUsername(), id);
        return ResponseEntity.noContent().build();
    }
}
