import { Injectable } from '@angular/core';
import { BaseApi } from "../core/base-api";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { AuthRequest } from "../models/auth-request.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService extends BaseApi {

  constructor(public http: HttpClient) {
    super(http);
  }


  login(data: AuthRequest) {
    return this.post("auth/login", data);
  }

  logout(): void {
    window.localStorage.clear();
  }

  isLoggedIn(): boolean {
    return window.localStorage.getItem("auth") !== null;
  }

  handleError(error: HttpErrorResponse) {
    window.localStorage.clear();
    return super.handleError(error);
  }
}
