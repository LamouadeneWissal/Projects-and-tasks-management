/**
 * Register Component
 * 
 * Provides user registration form with validation.
 * Creates new user accounts and redirects to login on success.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { NotificationService } from '../../services/notification.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: []
})
export class RegisterComponent {
  /** Form group for registration data */
  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router
  ) {
    // Initialize form with validation rules
    this.registerForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  /** Handle registration form submission */
  onSubmit(): void {
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe({
        next: () => {
          this.notificationService.success('Registration successful! Please login.');
          this.router.navigate(['/login']);
        },
        error: () => {
          this.notificationService.error('Registration failed. Please try again.');
        }
      });
    }
  }
}
