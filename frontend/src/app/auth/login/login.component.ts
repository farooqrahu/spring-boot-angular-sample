import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../shared/services/auth.service";
import { Router } from "@angular/router";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthResponse } from "../../shared/models/auth-response.model";
import { AuthRequest } from "../../shared/models/auth-request.model";
import { Alert } from "../../shared/models/alert.model";
import { AlertService } from "../../shared/services/alert.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private authService: AuthService,
              private alertService: AlertService,
              private router: Router) {
  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      'username': new FormControl('', [Validators.required, Validators.minLength(4)]),
      'password': new FormControl('', [Validators.required, Validators.minLength(6)])
    })
  }

  onSubmit() {
    const formData = this.loginForm.value;

    this.authService.login(new AuthRequest(formData.username, formData.password))
      .subscribe({
        next: (authResponse: AuthResponse) => {
          if (authResponse !== undefined) {
            window.localStorage.setItem("username", authResponse.username);
            window.localStorage.setItem("token", authResponse.token);
            this.alertService.showAlert(new Alert('Successfully logged in'));
            this.router.navigate(['/index']);
          }
        },
        error: (err) => {
          this.alertService.showAlert(new Alert(err.message, "danger"));
        }
      });
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }
}
