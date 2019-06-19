import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from "./components/home/home.component";
import { AuthGuard } from "../shared/services/auth.guard";
import { UserComponent } from "./components/user/user.component";
import { IndexComponent } from "./components/index/index.component";
import { MainComponent } from "./main.component";

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [{
      path: '',
      canActivateChild: [AuthGuard],
      children: [
        {path: 'user', component: UserComponent},
        {path: 'home', component: HomeComponent},
        {path: 'index', component: IndexComponent},
        {path: '', redirectTo: 'index', pathMatch: 'full'}
      ]
    }]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule {
}
