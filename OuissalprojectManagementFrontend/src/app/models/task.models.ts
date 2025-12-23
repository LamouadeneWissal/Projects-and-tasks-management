/**
 * Task Models
 * 
 * Type definitions for task-related data structures.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

/** Possible task status values */
export enum TaskStatus {
    TODO = 'TODO',
    IN_PROGRESS = 'IN_PROGRESS',
    DONE = 'DONE'
}

/** Request payload for creating tasks - matches backend DTO */
export interface TaskRequest {
    /** Task title */
    title: string;
    /** Task description */
    description: string;
    /** Due date in YYYY-MM-DD format */
    dueDate: string;
}

/** Response payload containing task data */
export interface TaskResponse {
    /** Unique task identifier */
    id: number;
    /** Task title */
    title: string;
    /** Task description */
    description: string;
    /** Due date in YYYY-MM-DD format */
    dueDate: string;
    /** Current task status */
    status: TaskStatus;
    /** ISO timestamp of creation */
    createdAt: string;
}
