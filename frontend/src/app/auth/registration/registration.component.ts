import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Message } from "../../shared/models/message.model";
import { UserBase } from "../../shared/models/user-base";
import { PersonalService } from "../../shared/services/personal.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registrationForm: FormGroup;
  message: Message;

  constructor(private personalService: PersonalService,
              private router: Router) {
  }

  ngOnInit() {
    this.message = null;
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
    const show = this.showMessage.bind(this);
    const router = this.router;

    this.personalService.registerUser(new UserBase(
      formData.username,
      formData.firstName,
      formData.lastName,
      formData.email,
      formData.password
    ))
      .subscribe(
        {
          next(user: UserBase) {
            if (user !== undefined) {
              show('User successfully created');
              router.navigate(['/login']);
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
