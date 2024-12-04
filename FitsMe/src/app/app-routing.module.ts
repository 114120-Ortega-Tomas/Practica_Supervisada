import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LogInComponent } from './Componentes/log-in/log-in.component';
import { RegisterComponent } from './Componentes/register/register.component';
import { AppComponent } from './app.component';
import { HomeComponent } from './Componentes/home/home.component';
import { PrincipalAccountComponent } from './Componentes/principal-account/principal-account.component';

import { StylingComponent } from './Componentes/styling/styling.component';
import { WardrobeComponent } from './Componentes/wardrobe/wardrobe.component';
import { StatsComponent } from './Componentes/stats/stats.component';
import { CalendarComponent } from './Componentes/calendar/calendar.component';
import { AddItemComponent } from './Componentes/add-item/add-item.component';
import { TermsComponent } from './Componentes/terms/terms.component';
import { HowItWorksComponent } from './Componentes/how-it-works/how-it-works.component';


const routes: Routes = [
  {path:'login',component:LogInComponent},
  {path:'addItem',component:AddItemComponent},
  {path:'register',component:RegisterComponent},
  {path:'',component:HomeComponent},
  {path:'account',component:PrincipalAccountComponent},
  {path:'calendar',component:CalendarComponent},
  {path:'styling',component:StylingComponent},
  {path:'wardrobe',component:WardrobeComponent},
  {path:'stats',component:StatsComponent},
  {path:'terms',component:TermsComponent},
  {path:'how',component:HowItWorksComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }