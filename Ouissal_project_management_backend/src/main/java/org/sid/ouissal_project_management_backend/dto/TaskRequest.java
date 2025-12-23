package org.sid.ouissal_project_management_backend.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Data Transfer Object for task creation requests.
 * <p>
 * Contains the information required to create a new task within a project.
 * Tasks are created with PENDING status by default.
 * </p>
 * 
 * @param title the task title (required)
 * @param description optional detailed description of the task
 * @param dueDate optional due date for task completion (format: YYYY-MM-DD)
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
public record TaskRequest(
    @NotBlank(message = "Title is required")
    String title,
    
    String description,
    
    LocalDate dueDate
) {}
