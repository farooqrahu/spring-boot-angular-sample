import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../../../shared/services/auth.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  username: string;

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit() {
    this.username = window.localStorage.getItem('username');
  }

  onLogout(){
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
