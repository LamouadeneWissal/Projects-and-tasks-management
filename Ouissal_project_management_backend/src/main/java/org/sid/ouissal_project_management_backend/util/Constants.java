package org.sid.ouissal_project_management_backend.util;

public class Constants {
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";
    public static final String[] PUBLIC_URLS = {
        "/api/auth/login",
        "/api/auth/register",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    };
}
