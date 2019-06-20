import { Component, OnDestroy, OnInit } from '@angular/core';
import { PersonalService } from "../../../shared/services/personal.service";
import { Subscription } from "rxjs/index";
import { Alert } from "../../../shared/models/alert.model";
import { AlertService } from "../../../shared/services/alert.service";
import { UserBase } from "../../../shared/models/user-base.model";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { User } from "../../../shared/models/user.model";
import { isNullOrUndefined } from "util";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit, OnDestroy {
  user: UserBase;
  sub: Subscription;
  isLoaded = false;

  userEditForm: FormGroup;

  constructor(private personalService: PersonalService,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.subscribeOnUser();
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  onSubmit() {
    this.isLoaded = false;

    const formData = this.userEditForm.value;
    const user = new User(
      this.user.username,
      formData.firstName,
      formData.lastName,
      this.user.email,
      ""
    );

    this.personalService.updateUser(user)
      .subscribe({
        next: (user: User) => {
          if (user !== undefined) {
            this.alertService.showAlert(new Alert('User successfully updated'));
            this.subscribeOnUser();
          }
        },
        error: (err) => {
          this.alertService.showAlert(new Alert(err.message, "danger"));
        }
      });
  }

  private subscribeOnUser() {
    if (!isNullOrUndefined(this.sub) && !this.sub.closed) {
      this.sub.unsubscribe();
    }
    this.sub = this.personalService.getUser()
      .subscribe({
        next: (user: UserBase) => {
          this.user = user;

          this.userEditForm = new FormGroup({
            'firstName': new FormControl(user.firstName, [Validators.required]),
            'lastName': new FormControl(user.lastName, [Validators.required])
          });

          this.isLoaded = true;
        },
        error: (err) => {
          this.alertService.showAlert(new Alert(err.message, "danger"))
        }
      });
  }

  get firstName(){
    return this.userEditForm.get('firstName');
  }

  get lastName(){
    return this.userEditForm.get('lastName');
  }
}
