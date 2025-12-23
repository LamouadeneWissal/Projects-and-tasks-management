/**
 * Project Models
 * 
 * Type definitions for project-related data structures.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

/** Request payload for creating/updating projects */
export interface ProjectRequest {
    /** Project title */
    title: string;
    /** Project description */
    description: string;
}

/** Response payload containing project data */
export interface ProjectResponse {
    /** Unique project identifier */
    id: number;
    /** Project title */
    title: string;
    /** Project description */
    description: string;
    /** ISO timestamp of creation */
    createdAt: string;
    /** Total number of tasks in project */
    totalTasks: number;
    /** Number of completed tasks */
    completedTasks: number;
    /** Completion percentage (0-100) */
    progressPercentage: number;
}
