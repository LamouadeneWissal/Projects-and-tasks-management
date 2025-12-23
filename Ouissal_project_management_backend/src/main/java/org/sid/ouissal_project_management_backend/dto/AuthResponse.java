package org.sid.ouissal_project_management_backend.dto;

/**
 * Data Transfer Object for authentication responses.
 * <p>
 * Returned after successful login or registration, containing the JWT token
 * needed for authenticating subsequent API requests.
 * </p>
 * 
 * @param token the JWT token for authenticating API requests
 * @param email the email address of the authenticated user
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
public record AuthResponse(
    String token,
    String email
) {}
