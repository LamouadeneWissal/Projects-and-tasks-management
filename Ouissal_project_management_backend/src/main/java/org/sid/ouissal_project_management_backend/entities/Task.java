package org.sid.ouissal_project_management_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity class representing a task within a project.
 * <p>
 * Tasks are the smallest unit of work in the application. Each task:
 * <ul>
 *   <li>Belongs to exactly one project</li>
 *   <li>Has a status (PENDING or COMPLETED)</li>
 *   <li>Can have an optional due date</li>
 *   <li>Affects the parent project's progress percentage</li>
 * </ul>
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 * @see TaskStatus
 * @see Project
 */
@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    
    /** Unique identifier for the task (auto-generated) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Title of the task (required) */
    @Column(nullable = false)
    private String title;

    /** Detailed description of the task (optional, supports long text) */
    @Column(columnDefinition = "TEXT")
    private String description;

    /** Due date for task completion (optional) */
    private LocalDate dueDate;

    /** Current status of the task (PENDING or COMPLETED) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private TaskStatus status = TaskStatus.PENDING;

    /** The project this task belongs to */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /** Timestamp when the task was created (auto-set, immutable) */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /** Timestamp when the task was last updated (auto-updated) */
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
