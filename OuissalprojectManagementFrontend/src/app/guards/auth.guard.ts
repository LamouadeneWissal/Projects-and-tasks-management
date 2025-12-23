/**
 * Authentication Guard
 * 
 * Route guard that protects routes from unauthorized access.
 * Redirects unauthenticated users to the login page.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

/**
 * Guard function to protect routes requiring authentication
 * @param route - The activated route snapshot
 * @param state - The router state snapshot
 * @returns True if authenticated, otherwise redirects to login
 */
export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
