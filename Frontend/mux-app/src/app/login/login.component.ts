import {JsonPipe} from '@angular/common';
import {Component} from '@angular/core';
import {FormGroup, FormBuilder, Validators, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, JsonPipe],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm: FormGroup;


  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const email = this.loginForm.value.email;
      const password = this.loginForm.value.password;
      this.authService.login({email, password}).subscribe(
        (response) => {
          if (response.role === 'ROLE_USER') {
            this.router.navigate(['/user-dashboard']);
          } else if (response.role === 'ROLE_ADMIN') {
            this.router.navigate(['/admin-dashboard']);
          }
        },
        (error) => {
          console.error('Login failed', error);
          // Handle login error (e.g., show error message to the user)
        }
      );
    }
  }

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }
}
