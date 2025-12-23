/**
 * Authentication Models
 * 
 * Type definitions for authentication-related data structures.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

/** Login request payload */
export interface LoginRequest {
    /** User email address */
    email: string;
    /** User password */
    password: string;
}

/** Registration request payload */
export interface RegisterRequest {
    /** User full name */
    fullName: string;
    /** User email address */
    email: string;
    /** User password (min 6 characters) */
    password: string;
}

/** Authentication response containing JWT token */
export interface AuthResponse {
    /** JWT authentication token */
    token: string;
}
