package org.sid.ouissal_project_management_backend.dto;

import org.sid.ouissal_project_management_backend.entities.TaskStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for task responses.
 * <p>
 * Contains all task details including its current status.
 * </p>
 * 
 * @param id unique identifier of the task
 * @param title the task title
 * @param description the task description
 * @param dueDate the due date for task completion (may be null)
 * @param status current status of the task (PENDING or COMPLETED)
 * @param createdAt timestamp when the task was created
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 * @see TaskStatus
 */
public record TaskResponse(
    Long id,
    String title,
    String description,
    LocalDate dueDate,
    TaskStatus status,
    LocalDateTime createdAt
) {}
