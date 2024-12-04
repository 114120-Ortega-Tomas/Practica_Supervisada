import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/Models/Models';
import { AuxiliarService } from 'src/app/Services/AuxiliarService/auxiliar.service';
import { UserService } from 'src/app/Services/UserService/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-principal-account',
  templateUrl: './principal-account.component.html',
  styleUrls: ['./principal-account.component.css']
})
export class PrincipalAccountComponent implements OnInit {
  subscription: Subscription|undefined;
 constructor(private router:Router , private userService:UserService , private auxiliarService : AuxiliarService){
   
 }
 items: any = ["Calendar","Styling","Wardrobe","Stats","Friends"];
 user : User = new User()
 selected: number = 0;
 closeSession(){
  Swal.fire({
    icon: 'warning',
    title: 'LOGGIN OUT',
    text: '¿Are you sure you want to log out?',
    showCancelButton: true,
    confirmButtonText: 'YES',
    cancelButtonText: 'NO',
  }).then((result) => {
    if (result.isConfirmed) {
      this.userService.setLoggedIn(false);
      this.router.navigate(['']);
    }
  })
  
 
}
style(item: string) {
  if(item == "Styling")
    {
      this.selected = 1;
    }
   else if(item == "Wardrobe"){
    this.selected = 2;
   } 
   else if(item == "Stats"){
    this.selected = 3;
   }
   else if (item == "Calendar"){
    this.selected = 0;
   }  
   else if (item == "Friends"){
    this.selected = 5;
   }
   this.auxiliarService.setSelected(this.selected); 
}


ngOnInit(): void {
  this.subscription = this.auxiliarService.selected$.subscribe(value => {
    this.selected = value;})
  this.user = this.userService.user;
  this.user.username = this.capitalizeFirstLetter(this.user.username);
}
capitalizeFirstLetter(str: string): string {
  return str.charAt(0).toUpperCase() + str.slice(1);
}
}
