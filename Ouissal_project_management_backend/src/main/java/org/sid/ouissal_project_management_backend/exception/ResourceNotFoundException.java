package org.sid.ouissal_project_management_backend.exception;

/**
 * Exception thrown when a requested resource is not found.
 * <p>
 * This exception results in HTTP 404 Not Found response.
 * Used for cases like:
 * <ul>
 *   <li>Project not found by ID</li>
 *   <li>Task not found by ID</li>
 *   <li>User not found by email</li>
 * </ul>
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
public class ResourceNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new ResourceNotFoundException with the specified message.
     * 
     * @param message the detail message explaining which resource was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
