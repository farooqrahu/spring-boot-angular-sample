import { Injectable } from '@angular/core';
import { BaseApi } from "../core/base-api";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { AuthRequest } from "../models/auth-request.model";
import { noUndefined } from "@angular/compiler/src/util";
import { isNullOrUndefined } from "util";

@Injectable({
  providedIn: 'root'
})
export class AuthService extends BaseApi {
  redirectUrl: string;

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
    return !isNullOrUndefined(window.localStorage.getItem("token"));
  }

  handleError(error: HttpErrorResponse) {
    window.localStorage.clear();
    return super.handleError(error);
  }
}
