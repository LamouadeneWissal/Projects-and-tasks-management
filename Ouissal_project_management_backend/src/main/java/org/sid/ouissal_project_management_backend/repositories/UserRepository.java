package org.sid.ouissal_project_management_backend.repositories;

import org.sid.ouissal_project_management_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
