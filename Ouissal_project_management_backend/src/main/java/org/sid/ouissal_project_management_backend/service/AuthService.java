package org.sid.ouissal_project_management_backend.service;

import org.sid.ouissal_project_management_backend.dto.AuthResponse;
import org.sid.ouissal_project_management_backend.dto.LoginRequest;
import org.sid.ouissal_project_management_backend.dto.RegisterRequest;
import org.sid.ouissal_project_management_backend.entities.User;
import org.sid.ouissal_project_management_backend.exception.UserAlreadyExistsException;
import org.sid.ouissal_project_management_backend.repositories.UserRepository;
import org.sid.ouissal_project_management_backend.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling authentication operations.
 * <p>
 * This service manages user registration and login, including:
 * <ul>
 *   <li>User registration with password encryption</li>
 *   <li>User authentication and JWT token generation</li>
 *   <li>Duplicate email validation</li>
 * </ul>
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder, 
                      JwtUtil jwtUtil,
                      AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user in the system.
     * <p>
     * This method:
     * <ol>
     *   <li>Checks if a user with the given email already exists</li>
     *   <li>Encrypts the password using BCrypt</li>
     *   <li>Saves the new user to the database</li>
     *   <li>Generates a JWT token for immediate authentication</li>
     * </ol>
     * </p>
     * 
     * @param request the registration request containing email and password
     * @return AuthResponse containing the JWT token and user email
     * @throws UserAlreadyExistsException if a user with the given email already exists
     */
    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.email() + " already exists");
        }

        // Create new user
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);

        // Generate token
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, user.getEmail());
    }

    /**
     * Authenticates a user and generates a JWT token.
     * <p>
     * This method validates the user's credentials using Spring Security's
     * AuthenticationManager and generates a JWT token upon successful authentication.
     * </p>
     * 
     * @param request the login request containing email and password
     * @return AuthResponse containing the JWT token and user email
     * @throws BadCredentialsException if the email or password is incorrect
     */
    public AuthResponse login(LoginRequest request) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
                )
            );

            // Generate token
            String token = jwtUtil.generateToken(request.email());

            return new AuthResponse(token, request.email());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
