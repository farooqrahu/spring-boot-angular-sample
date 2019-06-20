import { Injectable } from '@angular/core';
import { Alert } from "../models/alert.model";

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  alert: Alert;

  constructor() {
    this.alert = null;
  }

  showAlert(alert: Alert){
    this.alert = alert;
    window.setTimeout(() => {
      this.alert = null;
    }, 2500);
  }
}
