import { Component, OnInit } from '@angular/core';
import { UserBase } from "../../../shared/models/user-base";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  user: UserBase;

  constructor() { }

  ngOnInit() {
  }

}
