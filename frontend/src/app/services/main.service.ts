import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/index";
import { map } from "rxjs/internal/operators";

@Injectable({
  providedIn: 'root'
})
export class MainService {
  constructor(private http: HttpClient) {
  }

  getMain(): Observable<any> {
    return this.http.get('/api/v1').pipe(
      map((obj) => obj)
    );
  }
}
