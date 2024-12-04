import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {format} from "date-fns";
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Login, RegisterRequest } from '../../Models/Models';
import Swal from 'sweetalert2';
import { UserService } from 'src/app/Services/UserService/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, OnDestroy {
constructor(private formBuilder: FormBuilder  , private router:Router , private http:HttpClient,private userService:UserService){
  this.form=this.formBuilder.group({
    name:['',[Validators.required]],
    email:['',[Validators.required]],
    password:['',[Validators.required]],
    lastname:['',[Validators.required]],
    fecNac:['',[Validators.required]],
    username:['',[Validators.required]],
    country:['',[Validators.required]],
    terms:[false,[Validators.requiredTrue]],

  })
}
paises_americas:string[] = [
  "Canadá",
  "Estados Unidos",
  "México",
  "Guatemala",
  "Belice",
  "Honduras",
  "El Salvador",
  "Nicaragua",
  "Costa Rica",
  "Panamá",
  "Cuba",
  "Jamaica",
  "Haití",
  "República Dominicana",
  "Bahamas",
  "Trinidad y Tobago",
  "Barbados",
  "Santa Lucía",
  "San Vicente y las Granadinas",
  "Antigua y Barbuda",
  "Granada",
  "Dominica",
  "San Cristóbal y Nieves",
  "Colombia",
  "Venezuela",
  "Ecuador",
  "Perú",
  "Brasil",
  "Bolivia",
  "Paraguay",
  "Chile",
  "Argentina",
  "Uruguay",
  "Guyana",
  "Surinam",
  "Guyana Francesa"
]
form!:FormGroup;
ver : boolean= true

submitted:boolean=false;
subcription : Subscription | undefined;

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
  this.RegistarUsuario();
}

RegistarUsuario(){
  this.submitted=true;
  if(!this.form.valid)
  {
    return;
  }
  if(this.form.value.terms == false){
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'You must accept the terms and conditions!'
    })
    return;
  }
  let fecNac: string = this.form.value.fecNac;
  let fechaFormateada: string = format(new Date(fecNac), 'dd/MM/yyyy');
  let register : RegisterRequest={
    nombre: this.form.value.name,
    apellido: this.form.value.lastname,
    email: this.form.value.email,
    constraseña: this.form.value.password,
    fecNac: fechaFormateada,
    username: this.form.value.username,
    pais: this.form.value.country
  }

  this.subcription?.add(this.userService.register(register).subscribe({
    next: (data) => {
      if(data=true){
        Swal.fire({
          icon: 'success',
          title: 'Cuenta Creada',
          text: 'Cuenta Creada Correctamente!'
        })
        this.router.navigate(['']);
      }
      
    },
    error: (err) => {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Credenciales Incorrectas!'
      })
    }
  }))
}

}
