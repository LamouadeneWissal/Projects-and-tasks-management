package org.sid.ouissal_project_management_backend.entities;

/**
 * Enumeration representing the possible statuses of a task.
 * <p>
 * Task status is used to track progress and calculate project completion percentage.
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
public enum TaskStatus {
    
    /** Task has not been completed yet (default status) */
    PENDING,
    
    /** Task has been marked as completed */
    COMPLETED
}
