import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../shared/services/auth.service";
import { Router } from "@angular/router";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthResponse } from "../../shared/models/auth-response.model";
import { AuthRequest } from "../../shared/models/auth-request.model";
import { Message } from "../../shared/models/message.model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  message: Message;

  constructor(private authService: AuthService,
              private router: Router) {
  }

  ngOnInit() {
    this.message = null;
    this.loginForm = new FormGroup({
      'username': new FormControl('', [Validators.required, Validators.minLength(4)]),
      'password': new FormControl('', [Validators.required, Validators.minLength(6)])
    })
  }

  onSubmit() {
    const formData = this.loginForm.value;
    const show = this.showMessage.bind(this);
    const router = this.router;

    this.authService.login(new AuthRequest(formData.username, formData.password))
      .subscribe(
        {
          next(authResponse: AuthResponse) {
            if (authResponse !== undefined) {
              window.localStorage.setItem("username", authResponse.username);
              window.localStorage.setItem("token", authResponse.token);
              show('Successfully logged in');
              router.navigate(['/index']);
            }
          },
          error(err) {
            show(err.message, "danger");
          }
        });
  }

  private showMessage(text: string, type: string = "info") {
    this.message = new Message(text, type);
    window.setTimeout(() => {
      this.message = null;
    }, 2500);
  }

  get username(){
    return this.loginForm.get('username');
  }

  get password(){
    return this.loginForm.get('password');
  }
}
