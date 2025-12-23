package org.sid.ouissal_project_management_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for user registration requests.
 * <p>
 * Contains the information required to create a new user account.
 * All fields are validated before processing.
 * </p>
 * 
 * @param email the user's email address (required, must be valid and unique)
 * @param password the user's password (required, minimum 6 characters)
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
public record RegisterRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password
) {}
