package org.sid.ouissal_project_management_backend.exception;

/**
 * Exception thrown when attempting to register a user with an existing email.
 * <p>
 * This exception results in HTTP 409 Conflict response.
 * Thrown during registration when the email is already registered.
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
public class UserAlreadyExistsException extends RuntimeException {
    
    /**
     * Constructs a new UserAlreadyExistsException with the specified message.
     * 
     * @param message the detail message explaining the conflict
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
