import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import { AppComponent } from './app.component';
import { LogInComponent } from './Componentes/log-in/log-in.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { RegisterComponent } from './Componentes/register/register.component';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './Componentes/home/home.component';
import { PrincipalAccountComponent } from './Componentes/principal-account/principal-account.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { StylingComponent } from './Componentes/styling/styling.component';
import { WardrobeComponent } from './Componentes/wardrobe/wardrobe.component';
import { StatsComponent } from './Componentes/stats/stats.component';
import { CalendarComponent } from './Componentes/calendar/calendar.component';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { PopupComponent } from './popup/popup.component';
import { AddItemComponent } from './Componentes/add-item/add-item.component';
import { MatSelectModule } from '@angular/material/select';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { NgxSliderModule } from 'ngx-slider-v2';
import { PrendaGroupPipe } from './prenda-group.pipe';
import { NgImageSliderModule } from 'ng-image-slider';
import { FriendsComponent } from './Componentes/friends/friends.component';
import { NgChartsModule } from 'ng2-charts';
import { TermsComponent } from './Componentes/terms/terms.component';
import { ProfileComponent } from './Componentes/profile/profile.component';
import { HowItWorksComponent } from './Componentes/how-it-works/how-it-works.component';

@NgModule({
  declarations: [
    AppComponent,
    LogInComponent,
    RegisterComponent,
    HomeComponent,
    PrincipalAccountComponent,
    CalendarComponent,
    StylingComponent,
    WardrobeComponent,
    StatsComponent,
    PopupComponent,
    AddItemComponent,
    PrendaGroupPipe,
    FriendsComponent,
    TermsComponent,
    ProfileComponent,
    HowItWorksComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    NgImageSliderModule,
    MatSelectModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgxSliderModule,
    RouterModule,
    MatDialogModule,
    NgChartsModule,
    NgMultiSelectDropDownModule.forRoot(),
    HttpClientModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    }),
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
