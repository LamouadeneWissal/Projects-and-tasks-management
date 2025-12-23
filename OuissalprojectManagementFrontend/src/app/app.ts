/**
 * Root Application Component
 * 
 * The main entry point component that bootstraps the Angular application.
 * Contains the router outlet for navigation and global notification display.
 * 
 * @author Ouissal
 * @version 1.0.0
 */

import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NotificationComponent } from './components/notification/notification.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NotificationComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  /** Application title */
  protected readonly title = 'Project Management';
}
