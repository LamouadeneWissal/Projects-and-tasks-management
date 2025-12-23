package org.sid.ouissal_project_management_backend.mapper;

import org.sid.ouissal_project_management_backend.dto.TaskResponse;
import org.sid.ouissal_project_management_backend.entities.Task;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting Task entities to DTOs.
 * <p>
 * This mapper handles the conversion of Task entities to TaskResponse DTOs.
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
@Component
public class TaskMapper {

    /**
     * Converts a Task entity to a TaskResponse DTO.
     * 
     * @param task the Task entity to convert
     * @return TaskResponse DTO with all task details
     */
    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getDueDate(),
            task.getStatus(),
            task.getCreatedAt()
        );
    }
}
