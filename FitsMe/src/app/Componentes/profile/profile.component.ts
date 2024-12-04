import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { format } from 'date-fns';
import { RegisterRequest, User } from 'src/app/Models/Models';
import { UserService } from 'src/app/Services/UserService/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
constructor(private fb : FormBuilder , private http : HttpClient , private userService : UserService){ 
 
  this.form = this.fb.group({
    email:[[],[Validators.required]],
    password:[[],[Validators.required]],
    name:[[],[Validators.required]],
    lastname:[[],[Validators.required]],
    username:[[],[Validators.required]],
    country:[[],[Validators.required]],
    nac:[[],[Validators.required]]
  })
}


formattedYear!: number;
form!:FormGroup;
ver:boolean = true;
user:User = new User();
fecNac:Date=new Date();
parts = this.user.fecNac.split('-');

ngOnInit(): void {
  this.user = this.userService.user;
  this.form.patchValue({
    email : this.user.email,
    name : this.user.nombre,
    lastname : this.user.apellido,
    password : this.user.constraseña,
    username : this.user.username,
    country : this.user.pais,
    nac : this.user.fecNac
  })
}

private convertToDate(input: string): string {
  const [day, month, year] = input.split('/').map(Number);
  const date = new Date(year, month - 1, day); // Crear un objeto Date
  return date.toISOString().split('T')[0]; // Convertir a yyyy-MM-dd
}


seePassword(){
  this.ver = !this.ver;
}

UpdateUser()
{
 var request : RegisterRequest={
   nombre : this.form.get('name')?.value,
   apellido : this.form.get('lastname')?.value,
   email : this.form.get('email')?.value,
   username : this.form.get('username')?.value,
   constraseña : this.form.get('password')?.value,
   fecNac : this.form.get('nac')?.value,
   pais : this.form.get('country')?.value
 }
 this.userService.UpdateUser(this.userService.user.id,request).subscribe((data)=>{
  if(data)
    {
      this.userService.user = data as User;
      Swal.fire({
        icon: 'success',
        title: 'User Updated',
        showConfirmButton: false,
        timer: 1500
      })
    }
 })


}

}
