package org.sid.ouissal_project_management_backend.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for project responses.
 * <p>
 * Contains project details along with calculated progress information.
 * Progress is automatically calculated based on the ratio of completed tasks.
 * </p>
 * 
 * @param id unique identifier of the project
 * @param title the project title
 * @param description the project description
 * @param createdAt timestamp when the project was created
 * @param totalTasks total number of tasks in the project
 * @param completedTasks number of tasks marked as COMPLETED
 * @param progressPercentage completion percentage (0-100, rounded to 2 decimal places)
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
public record ProjectResponse(
    Long id,
    String title,
    String description,
    LocalDateTime createdAt,
    int totalTasks,
    int completedTasks,
    double progressPercentage
) {}
