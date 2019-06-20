import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Alert } from "../../shared/models/alert.model";
import { PersonalService } from "../../shared/services/personal.service";
import { Router } from "@angular/router";
import { User } from "../../shared/models/user.model";
import { AlertService } from "../../shared/services/alert.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registrationForm: FormGroup;

  constructor(private personalService: PersonalService,
              private alertService: AlertService,
              private router: Router) {
  }

  ngOnInit() {
    this.registrationForm = new FormGroup({
      'username': new FormControl('', [Validators.required, Validators.minLength(4)]),
      'firstName': new FormControl('', [Validators.required]),
      'lastName': new FormControl('', [Validators.required]),
      'email': new FormControl('', [Validators.required, Validators.email]),
      'password': new FormControl('', [Validators.required, Validators.minLength(6)])
    })
  }

  onSubmit() {
    const formData = this.registrationForm.value;
    const user = new User(
      formData.username,
      formData.firstName,
      formData.lastName,
      formData.email,
      formData.password
    );

    this.personalService.registerUser(user)
      .subscribe({
        next: (user: User) => {
          if (user !== undefined) {
            this.alertService.showAlert(new Alert('User successfully created'));
            this.router.navigate(['/login']);
          }
        },
        error: (err) => {
          this.alertService.showAlert(new Alert(err.message, "danger"));
        }
      });
  }

  get username() {
    return this.registrationForm.get('username');
  }

  get firstName() {
    return this.registrationForm.get('firstName');
  }

  get lastName() {
    return this.registrationForm.get('lastName');
  }

  get email() {
    return this.registrationForm.get('email');
  }

  get password() {
    return this.registrationForm.get('password');
  }
}
