/**
 * Authentication Service
 * 
 * Handles user authentication including login, registration,
 * token management, and session state.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginRequest, RegisterRequest, AuthResponse } from '../models/auth.models';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  /** Base API URL for authentication endpoints */
  private apiUrl = `${environment.apiUrl}/auth`;
  
  /** Local storage key for JWT token */
  private tokenKey = 'auth_token';

  constructor(private http: HttpClient) { }

  /**
   * Register a new user account
   * @param request - Registration credentials
   * @returns Observable with registration response
   */
  register(request: RegisterRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, request);
  }

  /**
   * Authenticate user and store JWT token
   * @param request - Login credentials
   * @returns Observable with authentication response
   */
  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request).pipe(
      tap(response => {
        // Handle different token response formats from various backends
        const token = response.token || (response as any)['accessToken'] || (typeof response === 'string' ? response : null);
        if (token) {
          localStorage.setItem(this.tokenKey, token);
        }
      })
    );
  }

  /** Clear authentication token and log out user */
  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }

  /**
   * Retrieve stored JWT token
   * @returns Token string or null if not authenticated
   */
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  /**
   * Check if user is currently authenticated
   * @returns True if valid token exists
   */
  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
