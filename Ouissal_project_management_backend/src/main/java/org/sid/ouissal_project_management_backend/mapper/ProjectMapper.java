package org.sid.ouissal_project_management_backend.mapper;

import org.sid.ouissal_project_management_backend.dto.ProjectResponse;
import org.sid.ouissal_project_management_backend.entities.Project;
import org.sid.ouissal_project_management_backend.entities.Task;
import org.sid.ouissal_project_management_backend.entities.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper class for converting Project entities to DTOs.
 * <p>
 * This mapper handles the conversion of Project entities to ProjectResponse DTOs,
 * including the calculation of progress statistics:
 * <ul>
 *   <li>Total number of tasks</li>
 *   <li>Number of completed tasks</li>
 *   <li>Progress percentage (rounded to 2 decimal places)</li>
 * </ul>
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
@Component
public class ProjectMapper {

    /**
     * Converts a Project entity to a ProjectResponse DTO.
     * <p>
     * Calculates progress statistics based on task completion status.
     * Progress percentage formula: (completedTasks / totalTasks) * 100
     * </p>
     * 
     * @param project the Project entity to convert
     * @return ProjectResponse DTO with calculated progress information
     */
    public ProjectResponse toResponse(Project project) {
        List<Task> tasks = project.getTasks();
        int totalTasks = tasks != null ? tasks.size() : 0;
        int completedTasks = tasks != null ? (int) tasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.COMPLETED)
                .count() : 0;
        
        double progressPercentage = totalTasks > 0 
                ? ((double) completedTasks / totalTasks) * 100 
                : 0.0;

        return new ProjectResponse(
            project.getId(),
            project.getTitle(),
            project.getDescription(),
            project.getCreatedAt(),
            totalTasks,
            completedTasks,
            Math.round(progressPercentage * 100.0) / 100.0 // Round to 2 decimal places
        );
    }
}
