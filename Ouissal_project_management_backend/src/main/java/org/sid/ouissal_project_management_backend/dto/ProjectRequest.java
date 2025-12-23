package org.sid.ouissal_project_management_backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for project creation requests.
 * <p>
 * Contains the information required to create a new project.
 * </p>
 * 
 * @param title the project title (required)
 * @param description optional detailed description of the project
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
public record ProjectRequest(
    @NotBlank(message = "Title is required")
    String title,
    
    String description
) {}
