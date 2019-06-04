import { Component, OnInit } from '@angular/core';
import { Message } from "../models/message.model";
import { Subscription } from "rxjs/index";
import { MainService } from "../services/main.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: [ './main.component.css' ]
})
export class MainComponent implements OnInit {
  messages: Message[] = [];
  subscription: Subscription;

  constructor(private mainService: MainService) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  sendRequest() {
    this.subscription = this.mainService.getMain()
      .subscribe(
        (data) => {
          this.messages.push(data);
        }
      );
  }

}
