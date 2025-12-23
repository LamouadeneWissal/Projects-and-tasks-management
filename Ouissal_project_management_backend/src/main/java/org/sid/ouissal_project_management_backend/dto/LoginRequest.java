package org.sid.ouissal_project_management_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for login requests.
 * <p>
 * Contains the credentials required to authenticate a user.
 * Both fields are validated before processing.
 * </p>
 * 
 * @param email the user's email address (required, must be valid email format)
 * @param password the user's password (required)
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
public record LoginRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Password is required")
    String password
) {}
