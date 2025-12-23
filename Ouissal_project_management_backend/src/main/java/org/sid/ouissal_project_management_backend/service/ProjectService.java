package org.sid.ouissal_project_management_backend.service;

import org.sid.ouissal_project_management_backend.dto.ProjectRequest;
import org.sid.ouissal_project_management_backend.dto.ProjectResponse;
import org.sid.ouissal_project_management_backend.entities.Project;
import org.sid.ouissal_project_management_backend.entities.User;
import org.sid.ouissal_project_management_backend.exception.ResourceNotFoundException;
import org.sid.ouissal_project_management_backend.exception.UnauthorizedAccessException;
import org.sid.ouissal_project_management_backend.mapper.ProjectMapper;
import org.sid.ouissal_project_management_backend.repositories.ProjectRepository;
import org.sid.ouissal_project_management_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing projects.
 * <p>
 * This service handles all business logic related to projects, including:
 * <ul>
 *   <li>Creating new projects for users</li>
 *   <li>Retrieving user's projects with progress information</li>
 *   <li>Deleting projects (with cascade to tasks)</li>
 *   <li>Authorization checks to ensure users can only access their own projects</li>
 * </ul>
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
    }

    /**
     * Creates a new project for the specified user.
     * 
     * @param userEmail the email of the authenticated user
     * @param request the project creation request containing title and description
     * @return ProjectResponse containing the created project with initial progress (0%)
     * @throws ResourceNotFoundException if the user is not found
     */
    public ProjectResponse createProject(String userEmail, ProjectRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Project project = Project.builder()
                .title(request.title())
                .description(request.description())
                .user(user)
                .build();

        Project savedProject = projectRepository.save(project);
        return projectMapper.toResponse(savedProject);
    }

    /**
     * Retrieves all projects belonging to the specified user.
     * <p>
     * Each project includes calculated progress information:
     * total tasks, completed tasks, and progress percentage.
     * </p>
     * 
     * @param userEmail the email of the authenticated user
     * @return List of ProjectResponse objects with progress information
     * @throws ResourceNotFoundException if the user is not found
     */
    @Transactional(readOnly = true)
    public List<ProjectResponse> getUserProjects(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return projectRepository.findAllByUserId(user.getId()).stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific project by its ID.
     * <p>
     * Includes authorization check to ensure the user owns the project.
     * </p>
     * 
     * @param userEmail the email of the authenticated user
     * @param projectId the unique identifier of the project
     * @return ProjectResponse containing the project details and progress
     * @throws ResourceNotFoundException if the project is not found
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    @Transactional(readOnly = true)
    public ProjectResponse getProject(String userEmail, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        if (!project.getUser().getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException("You are not authorized to view this project");
        }

        return projectMapper.toResponse(project);
    }

    /**
     * Deletes a project and all its associated tasks.
     * <p>
     * Due to cascade configuration, all tasks belonging to this project
     * will be automatically deleted (orphan removal).
     * </p>
     * 
     * @param userEmail the email of the authenticated user
     * @param projectId the unique identifier of the project to delete
     * @throws ResourceNotFoundException if the project is not found
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    public void deleteProject(String userEmail, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        if (!project.getUser().getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException("You are not authorized to delete this project");
        }

        projectRepository.delete(project);
    }

    /**
     * Updates an existing project.
     * <p>
     * Only the title and description can be updated.
     * Includes authorization check to ensure the user owns the project.
     * </p>
     * 
     * @param userEmail the email of the authenticated user
     * @param projectId the unique identifier of the project to update
     * @param request the project update request containing new title and description
     * @return ProjectResponse containing the updated project details
     * @throws ResourceNotFoundException if the project is not found
     * @throws UnauthorizedAccessException if the user does not own the project
     */
    public ProjectResponse updateProject(String userEmail, Long projectId, ProjectRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        if (!project.getUser().getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException("You are not authorized to update this project");
        }

        project.setTitle(request.title());
        project.setDescription(request.description());

        Project updatedProject = projectRepository.save(project);
        return projectMapper.toResponse(updatedProject);
    }
}
