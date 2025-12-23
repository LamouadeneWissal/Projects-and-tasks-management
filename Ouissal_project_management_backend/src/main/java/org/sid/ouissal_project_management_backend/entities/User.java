package org.sid.ouissal_project_management_backend.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Entity class representing a user in the system.
 * <p>
 * Users are the primary actors in the application. Each user can:
 * <ul>
 *   <li>Register and authenticate with email/password</li>
 *   <li>Create and manage multiple projects</li>
 *   <li>Create and manage tasks within their projects</li>
 * </ul>
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter
@Setter
public class User {
    
    /** Unique identifier for the user (auto-generated) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** User's email address (unique, used for authentication) */
    @Column(unique = true , nullable = false)
    private String email;
    
    /** User's encrypted password (BCrypt) */
    @Column(nullable = false)
    private String password;
}

