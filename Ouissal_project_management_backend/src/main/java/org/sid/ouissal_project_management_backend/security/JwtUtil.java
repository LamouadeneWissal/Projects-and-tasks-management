package org.sid.ouissal_project_management_backend.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for JWT (JSON Web Token) operations.
 * <p>
 * This class handles all JWT-related operations including:
 * <ul>
 *   <li>Token generation with user email as subject</li>
 *   <li>Token validation and expiration checking</li>
 *   <li>Email extraction from tokens</li>
 * </ul>
 * </p>
 * <p>
 * Configuration values (secret and expiration) are loaded from application.properties.
 * Uses HS256 algorithm for token signing.
 * </p>
 * 
 * @author Ouissal
 * @version 1.0
 * @since 2025-12-22
 */
@Component
public class JwtUtil {

    /** Secret key for signing JWT tokens (from application.properties) */
    @Value("${jwt.secret}")
    private String secret;

    /** Token expiration time in milliseconds (from application.properties) */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Generates a new JWT token for the specified email.
     * 
     * @param email the user's email to include as the token subject
     * @return the generated JWT token string
     */
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * Extracts the email (subject) from a JWT token.
     * 
     * @param token the JWT token to extract email from
     * @return the email address stored in the token
     */
    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }
    
    /**
     * Validates a JWT token against the provided email.
     * <p>
     * Checks that:
     * <ol>
     *   <li>The token's email matches the provided email</li>
     *   <li>The token has not expired</li>
     * </ol>
     * </p>
     * 
     * @param token the JWT token to validate
     * @param email the email to validate against
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, String email) {
        try {
            String extractedEmail = extractEmail(token);
            return extractedEmail.equals(email) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Checks if a JWT token has expired.
     * 
     * @param token the JWT token to check
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
    
    /**
     * Extracts all claims from a JWT token.
     * 
     * @param token the JWT token to parse
     * @return the Claims object containing all token data
     */
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
