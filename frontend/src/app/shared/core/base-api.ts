import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs/index";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { catchError } from "rxjs/internal/operators";
import { isNullOrUndefined } from "util";

@Injectable()
export class BaseApi {
  private baseUrl = '/api/v1/';
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(public http: HttpClient) {
  }

  public get(url: string = ''): Observable<any> {
    this.addAuthToken();
    return this.http.get(this.baseUrl + url, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  public post(url: string = '', data: any = {}): Observable<any> {
    this.addAuthToken();
    return this.http.post(this.baseUrl + url, data, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  public put(url: string = '', data: any = {}): Observable<any> {
    this.addAuthToken();
    return this.http.put(this.baseUrl + url, data, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }


  protected handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error.message}`);
    }
    return throwError(error.error);
  };

  private addAuthToken() {
    if (!isNullOrUndefined(window.localStorage.getItem("token"))) {
      this.httpOptions.headers = this.httpOptions
        .headers.set("Authorization", "Bearer_" + window.localStorage.getItem("token"));
    }
  }
}
