package org.sid.ouissal_project_management_backend.controller;

import jakarta.validation.Valid;
import org.sid.ouissal_project_management_backend.dto.TaskRequest;
import org.sid.ouissal_project_management_backend.dto.TaskResponse;
import org.sid.ouissal_project_management_backend.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing tasks within projects.
 * <p>
 * This controller provides CRUD operations for tasks. All endpoints require
 * JWT authentication. Users can only access tasks belonging to their own projects.
 * </p>
 * 
 * <h3>Endpoints:</h3>
 * <ul>
 *   <li>POST /api/projects/{projectId}/tasks - Create a new task in a project</li>
 *   <li>GET /api/projects/{projectId}/tasks - Get all tasks for a project</li>
 *   <li>PATCH /api/tasks/{taskId}/complete - Mark a task as completed</li>
 *   <li>DELETE /api/tasks/{taskId} - Delete a task</li>
 * </ul>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    private final TaskService taskService;

    /**
     * Constructs a TaskController with the required TaskService dependency.
     * 
     * @param taskService the task service for handling task operations
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Creates a new task within a specified project.
     * <p>
     * The task is created with PENDING status by default.
     * </p>
     * 
     * @param userDetails the authenticated user's details (injected by Spring Security)
     * @param projectId the ID of the project to add the task to
     * @param request the task creation request containing title, description, and dueDate
     * @return ResponseEntity containing the created task (HTTP 201 Created)
     * @throws ResourceNotFoundException if the project does not exist
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long projectId,
            @Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.createTask(userDetails.getUsername(), projectId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves all tasks belonging to a specified project.
     * 
     * @param userDetails the authenticated user's details (injected by Spring Security)
     * @param projectId the ID of the project to get tasks from
     * @return ResponseEntity containing a list of tasks (HTTP 200 OK)
     * @throws ResourceNotFoundException if the project does not exist
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskResponse>> getProjectTasks(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long projectId) {
        List<TaskResponse> tasks = taskService.getProjectTasks(userDetails.getUsername(), projectId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Marks a task as completed.
     * <p>
     * Updates the task status from PENDING to COMPLETED.
     * This affects the project's progress percentage.
     * </p>
     * 
     * @param userDetails the authenticated user's details (injected by Spring Security)
     * @param taskId the ID of the task to mark as completed
     * @return ResponseEntity containing the updated task (HTTP 200 OK)
     * @throws ResourceNotFoundException if the task does not exist
     * @throws UnauthorizedAccessException if the user does not own the task's project
     */
    @PatchMapping("/tasks/{taskId}/complete")
    public ResponseEntity<TaskResponse> markTaskAsCompleted(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId) {
        TaskResponse response = taskService.markTaskAsCompleted(userDetails.getUsername(), taskId);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a task from the system.
     * <p>
     * Permanently removes the task. This affects the project's progress percentage.
     * </p>
     * 
     * @param userDetails the authenticated user's details (injected by Spring Security)
     * @param taskId the ID of the task to delete
     * @return ResponseEntity with no content (HTTP 204 No Content)
     * @throws ResourceNotFoundException if the task does not exist
     * @throws UnauthorizedAccessException if the user does not own the task's project
     */
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId) {
        taskService.deleteTask(userDetails.getUsername(), taskId);
        return ResponseEntity.noContent().build();
    }
}
