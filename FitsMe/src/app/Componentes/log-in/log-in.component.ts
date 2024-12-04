import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Login } from '../../Models/Models';
import Swal from 'sweetalert2';
import { UserService } from 'src/app/Services/UserService/user.service';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit , OnDestroy {

  constructor(private http:HttpClient,private userService:UserService, private formBuilder:FormBuilder, private router:Router){
    this.login=this.formBuilder.group({
      email:['',[Validators.required]],
      password:['',[Validators.required]]
    })
  }
  
  loginData : Login = new Login();
  ver : boolean= true;

  subcription : Subscription | undefined;
  login!:FormGroup;
  submitted:boolean = false;
  ngOnInit(): void {
    this.subcription = new Subscription();
  }

  ngOnDestroy(): void {
    this.subcription?.unsubscribe();
  }

  seePassword(){
    this.ver = !this.ver;
  }
  
  OnSubmit(){
    this.submitted = true;
    if(this.login.valid){
      this.loginData.email = this.login.value.email;
      this.loginData.constraseña = this.login.value.password;
      this.subcription?.add(this.userService.login(this.loginData).subscribe({
        next: (data) => {
          if(data){
            this.userService.user = data;
            this.userService.user.apellido = data.apellido;
            this.router.navigate(['account']);
            this.userService.setLoggedIn(true);    
            console.log(data);
          }
          
        },
        error: (err) => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Wrong Credentials!'
          })
        }
      }))
      
    }
  }
}
