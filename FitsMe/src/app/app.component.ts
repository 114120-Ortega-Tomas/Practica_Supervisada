import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from './Services/UserService/user.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'FitsMe';
  loggedIn: boolean = false;
  private subscription: Subscription;
   handleClick(link : any) {
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(item => item.classList.remove('active'));
    link.classList.add('active');
  }
  constructor(private userService:UserService ){ this.subscription = this.userService.loggedIn$.subscribe(loggedIn => {
    this.loggedIn = loggedIn;
  });}
 
  ngOnInit(): void {
    console.log(this.loggedIn)
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
  
 
}
