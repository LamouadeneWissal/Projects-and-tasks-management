/**
 * Notification Service
 * 
 * Provides a centralized way to display toast notifications throughout the application.
 * Supports success, error, warning, and info notification types with auto-dismiss functionality.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

/** Supported notification types */
export type NotificationType = 'success' | 'error' | 'warning' | 'info';

/** Notification data structure */
export interface Notification {
  id: number;
  message: string;
  type: NotificationType;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  /** Counter for generating unique notification IDs */
  private idCounter = 0;
  
  /** Observable stream of current notifications */
  private notificationsSubject = new BehaviorSubject<Notification[]>([]);
  public notifications$ = this.notificationsSubject.asObservable();

  /**
   * Display a notification message
   * @param message - The message to display
   * @param type - The type of notification (success, error, warning, info)
   * @param duration - How long to show the notification in milliseconds (default: 3000)
   */
  show(message: string, type: NotificationType = 'info', duration: number = 3000): void {
    const notification: Notification = {
      id: ++this.idCounter,
      message,
      type
    };

    // Add notification to the list
    const current = this.notificationsSubject.value;
    this.notificationsSubject.next([...current, notification]);

    // Auto-remove after duration
    setTimeout(() => this.remove(notification.id), duration);
  }

  /** Display a success notification */
  success(message: string, duration?: number): void {
    this.show(message, 'success', duration);
  }

  /** Display an error notification */
  error(message: string, duration?: number): void {
    this.show(message, 'error', duration);
  }

  /** Display a warning notification */
  warning(message: string, duration?: number): void {
    this.show(message, 'warning', duration);
  }

  /** Display an info notification */
  info(message: string, duration?: number): void {
    this.show(message, 'info', duration);
  }

  /**
   * Remove a notification by ID
   * @param id - The notification ID to remove
   */
  remove(id: number): void {
    const current = this.notificationsSubject.value;
    this.notificationsSubject.next(current.filter(n => n.id !== id));
  }
}
