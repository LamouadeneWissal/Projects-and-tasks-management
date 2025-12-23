package org.sid.ouissal_project_management_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a project in the system.
 * <p>
 * A project belongs to a single user and can contain multiple tasks.
 * Projects are used to organize and group related tasks together.
 * </p>
 * <p>
 * Key features:
 * <ul>
 *   <li>Each project belongs to exactly one user (Many-to-One)</li>
 *   <li>A project can have multiple tasks (One-to-Many with cascade delete)</li>
 *   <li>Automatic timestamp tracking for creation and updates</li>
 *   <li>Progress is calculated dynamically based on completed tasks</li>
 * </ul>
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    
    /** Unique identifier for the project (auto-generated) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Title of the project (required) */
    @Column(nullable = false)
    private String title;

    /** Detailed description of the project (optional, supports long text) */
    @Column(columnDefinition = "TEXT")
    private String description;

    /** The user who owns this project */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** List of tasks belonging to this project (cascade delete enabled) */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

    /** Timestamp when the project was created (auto-set, immutable) */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /** Timestamp when the project was last updated (auto-updated) */
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
