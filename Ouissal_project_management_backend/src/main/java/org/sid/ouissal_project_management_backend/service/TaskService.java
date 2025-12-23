package org.sid.ouissal_project_management_backend.service;

import org.sid.ouissal_project_management_backend.dto.TaskRequest;
import org.sid.ouissal_project_management_backend.dto.TaskResponse;
import org.sid.ouissal_project_management_backend.entities.Project;
import org.sid.ouissal_project_management_backend.entities.Task;
import org.sid.ouissal_project_management_backend.entities.TaskStatus;
import org.sid.ouissal_project_management_backend.exception.ResourceNotFoundException;
import org.sid.ouissal_project_management_backend.exception.UnauthorizedAccessException;
import org.sid.ouissal_project_management_backend.mapper.TaskMapper;
import org.sid.ouissal_project_management_backend.repositories.ProjectRepository;
import org.sid.ouissal_project_management_backend.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing tasks within projects.
 * <p>
 * This service handles all business logic related to tasks, including:
 * <ul>
 *   <li>Creating new tasks within projects</li>
 *   <li>Retrieving tasks for a specific project</li>
 *   <li>Marking tasks as completed</li>
 *   <li>Deleting tasks</li>
 *   <li>Authorization checks to ensure users can only access tasks in their own projects</li>
 * </ul>
 * </p>
 * <p>
 * Task status changes affect the parent project's progress percentage.
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
    }

    /**
     * Creates a new task within a specified project.
     * <p>
     * The task is created with PENDING status by default.
     * Creating a task affects the project's progress percentage.
     * </p>
     * 
     * @param userEmail the email of the authenticated user
     * @param projectId the ID of the project to add the task to
     * @param request the task creation request containing title, description, and dueDate
     * @return TaskResponse containing the created task details
     * @throws ResourceNotFoundException if the project is not found
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    public TaskResponse createTask(String userEmail, Long projectId, TaskRequest request) {
        Project project = getProjectAndValidateAccess(userEmail, projectId);

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .project(project)
                .status(TaskStatus.PENDING)
                .build();

        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }

    /**
     * Retrieves all tasks belonging to a specified project.
     * 
     * @param userEmail the email of the authenticated user
     * @param projectId the ID of the project to get tasks from
     * @return List of TaskResponse objects
     * @throws ResourceNotFoundException if the project is not found
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> getProjectTasks(String userEmail, Long projectId) {
        getProjectAndValidateAccess(userEmail, projectId); // Validate access first

        return taskRepository.findAllByProjectId(projectId).stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Marks a task as completed.
     * <p>
     * Updates the task status from PENDING to COMPLETED.
     * This change affects the parent project's progress percentage.
     * </p>
     * 
     * @param userEmail the email of the authenticated user
     * @param taskId the ID of the task to mark as completed
     * @return TaskResponse containing the updated task details
     * @throws ResourceNotFoundException if the task is not found
     * @throws UnauthorizedAccessException if the user does not own the task's project
     */
    public TaskResponse markTaskAsCompleted(String userEmail, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        validateProjectAccess(userEmail, task.getProject());

        task.setStatus(TaskStatus.COMPLETED);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toResponse(updatedTask);
    }

    /**
     * Deletes a task from the system.
     * <p>
     * Permanently removes the task. This affects the parent project's
     * progress percentage calculation.
     * </p>
     * 
     * @param userEmail the email of the authenticated user
     * @param taskId the ID of the task to delete
     * @throws ResourceNotFoundException if the task is not found
     * @throws UnauthorizedAccessException if the user does not own the task's project
     */
    public void deleteTask(String userEmail, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        validateProjectAccess(userEmail, task.getProject());

        taskRepository.delete(task);
    }

    /**
     * Retrieves a project by ID and validates that the user has access to it.
     * 
     * @param userEmail the email of the authenticated user
     * @param projectId the ID of the project to retrieve
     * @return the Project entity
     * @throws ResourceNotFoundException if the project is not found
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    private Project getProjectAndValidateAccess(String userEmail, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        
        validateProjectAccess(userEmail, project);
        return project;
    }

    /**
     * Validates that the user has access to the specified project.
     * 
     * @param userEmail the email of the authenticated user
     * @param project the project to validate access for
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    private void validateProjectAccess(String userEmail, Project project) {
        if (!project.getUser().getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException("You are not authorized to access this project");
        }
    }
}
