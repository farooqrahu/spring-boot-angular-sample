import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import { PageNotFoundComponent } from "./shared/components/page-not-found/page-not-found.component";
import { AuthGuard } from "./shared/services/auth.guard";

const routes: Routes = [
  //{path: '', redirectTo: 'index', pathMatch: 'full'},
  {path: 'not-found', component: PageNotFoundComponent},
  {path: '**', redirectTo: 'not-found'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
