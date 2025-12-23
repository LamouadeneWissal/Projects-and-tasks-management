/**
 * Notification Component
 * 
 * Displays toast notifications in a fixed position at the top-right of the screen.
 * Supports multiple notification types with different colors and icons.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService, Notification } from '../../services/notification.service';

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [CommonModule],
  template: `
    <!-- Notification Container - Fixed position top-right -->
    <div class="fixed top-4 right-4 z-50 space-y-2">
      <div 
        *ngFor="let notification of notifications$ | async"
        class="flex items-center gap-3 px-4 py-3 rounded-lg shadow-lg transform transition-all duration-300 ease-in-out animate-slide-in min-w-[300px] max-w-md"
        [ngClass]="getNotificationClass(notification.type)"
        (click)="dismiss(notification.id)">
        
        <!-- Icon -->
        <span class="text-xl">{{ getIcon(notification.type) }}</span>
        
        <!-- Message -->
        <p class="flex-1 text-sm font-medium">{{ notification.message }}</p>
        
        <!-- Close button -->
        <button class="text-lg opacity-70 hover:opacity-100 transition-opacity">&times;</button>
      </div>
    </div>
  `,
  styles: [`
    @keyframes slideIn {
      from {
        transform: translateX(100%);
        opacity: 0;
      }
      to {
        transform: translateX(0);
        opacity: 1;
      }
    }
    .animate-slide-in {
      animation: slideIn 0.3s ease-out;
    }
  `]
})
export class NotificationComponent {
  /** Observable stream of notifications */
  notifications$;

  constructor(private notificationService: NotificationService) {
    this.notifications$ = this.notificationService.notifications$;
  }

  /**
   * Get CSS classes based on notification type
   * @param type - The notification type
   * @returns CSS class string
   */
  getNotificationClass(type: string): string {
    const classes: Record<string, string> = {
      success: 'bg-green-500 text-white',
      error: 'bg-red-500 text-white',
      warning: 'bg-yellow-500 text-white',
      info: 'bg-blue-500 text-white'
    };
    return classes[type] || classes['info'];
  }

  /**
   * Get icon based on notification type
   * @param type - The notification type
   * @returns Icon character
   */
  getIcon(type: string): string {
    const icons: Record<string, string> = {
      success: '✓',
      error: '✕',
      warning: '⚠',
      info: 'ℹ'
    };
    return icons[type] || icons['info'];
  }

  /**
   * Manually dismiss a notification
   * @param id - The notification ID to dismiss
   */
  dismiss(id: number): void {
    this.notificationService.remove(id);
  }
}
