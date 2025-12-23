package org.sid.ouissal_project_management_backend.exception;

/**
 * Exception thrown when a user tries to access a resource they don't own.
 * <p>
 * This exception results in HTTP 403 Forbidden response.
 * Used when a user tries to:
 * <ul>
 *   <li>View, modify, or delete another user's project</li>
 *   <li>Access tasks in another user's project</li>
 * </ul>
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
public class UnauthorizedAccessException extends RuntimeException {
    
    /**
     * Constructs a new UnauthorizedAccessException with the specified message.
     * 
     * @param message the detail message explaining the access violation
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
