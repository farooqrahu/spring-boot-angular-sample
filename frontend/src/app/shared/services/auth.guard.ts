import { Injectable } from '@angular/core';
import {
  CanActivate,
  CanActivateChild,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    let url = state.url;
    console.log("Requested url: " + url);
    return this.checkLoggedIn(url);
  }

  canActivateChild(next: ActivatedRouteSnapshot,
                   state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.canActivate(next, state);
  }

  private checkLoggedIn(url: string): boolean {
    if (this.authService.isLoggedIn()) {
      console.log("Access granted");
      //this.router.navigate([url]);
      return true;
    }

    this.authService.redirectUrl = url;

    console.log("Access denied, redirect to login page");
    this.router.navigate(['/login']);
    return false;
  }

}
