import { Injectable } from '@angular/core';
import { BaseApi } from "../core/base-api";
import { HttpClient } from "@angular/common/http";
import { UserBase } from "../models/user-base.model";

@Injectable({
  providedIn: 'root'
})
export class PersonalService extends BaseApi {

  constructor(public http: HttpClient) {
    super(http);
  }

  getUser(){
    return this.get("user");
  }

  registerUser(user: UserBase){
    return this.post("user", user);
  }

  updateUser(user: UserBase){
    return this.put("user", user);
  }
}
