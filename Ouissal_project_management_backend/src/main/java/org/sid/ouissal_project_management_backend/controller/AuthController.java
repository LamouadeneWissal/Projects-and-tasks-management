package org.sid.ouissal_project_management_backend.controller;

import org.sid.ouissal_project_management_backend.dto.AuthResponse;
import org.sid.ouissal_project_management_backend.dto.LoginRequest;
import org.sid.ouissal_project_management_backend.dto.RegisterRequest;
import org.sid.ouissal_project_management_backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * REST Controller for handling authentication operations.
 * <p>
 * This controller provides endpoints for user registration, login, and authentication testing.
 * All endpoints are publicly accessible (no JWT required).
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructs an AuthController with the required AuthService dependency.
     * 
     * @param authService the authentication service for handling auth operations
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user in the system.
     * <p>
     * Creates a new user account and returns a JWT token for immediate authentication.
     * </p>
     * 
     * @param request the registration request containing email, password, and fullName
     * @return ResponseEntity containing the JWT token and user email (HTTP 201 Created)
     * @throws UserAlreadyExistsException if a user with the given email already exists
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Authenticates a user and returns a JWT token.
     * <p>
     * Validates user credentials and generates a JWT token for subsequent API calls.
     * </p>
     * 
     * @param request the login request containing email and password
     * @return ResponseEntity containing the JWT token and user email (HTTP 200 OK)
     * @throws BadCredentialsException if the email or password is incorrect
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Tests if the authentication system is working.
     * <p>
     * This endpoint can be used to verify that the API is accessible.
     * </p>
     * 
     * @return ResponseEntity with a success message (HTTP 200 OK)
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Authentication is working! You are authenticated.");
    }
}
