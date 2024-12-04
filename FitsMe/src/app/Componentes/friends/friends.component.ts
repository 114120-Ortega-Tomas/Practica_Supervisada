import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ThemeService } from 'ng2-charts';
import { Prenda, User, solicitudPrendaRequest, solicitudPrendaResponse, solicitudRequest, solicitudResponse } from 'src/app/Models/Models';
import { AuxiliarService } from 'src/app/Services/AuxiliarService/auxiliar.service';
import { PrendasService } from 'src/app/Services/PrendaService/prendas.service';
import { SolicitudPrendaService } from 'src/app/Services/SolicitudPrendaService/solicitud-prenda.service';
import { SolicitudesService } from 'src/app/Services/SolicitudesAmistadService/solicitudes.service';
import { UserService } from 'src/app/Services/UserService/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {
constructor(private userService:UserService,private solicitudPrendaService:SolicitudPrendaService,private solicitudService:SolicitudesService, private fb: FormBuilder, private prendaService:PrendasService,private auxiliarService:AuxiliarService){
  this.form = this.fb.group({
    search:['']
  })
}
amigoWardrobe:string="";
form! : FormGroup;
prendas : Prenda[] =[];
userWardrobeId:number = 0;
users:User[]=[];
amigos :User[]=[];
solicitudes:solicitudResponse[]=[];
solicitudesPrendas:solicitudPrendaResponse[]=[];

ngOnInit(): void {
 this.getAmigos();
 this.getSolicitudes();
 this.getSolicitudesPrendas();
}
  getUsername(username:string){
    this.users = [];
    this.userService.getUserbyUsername(username,this.userService.user.id).subscribe(value => {
      this.users = value as User[];
    })
  }
  getAmigos(){
    this.solicitudService.getAmigos(this.userService.user.id).subscribe(value => {
      this.amigos = value as User[];
      this.users = value as User[];
    })
  }
  sendSolicitud(id:number)
  {
    var solicitud : solicitudRequest ={
      userIdFrom: this.userService.user.id,
      userIdTo:id
    }
    this.solicitudService.sendSolicitud(solicitud).subscribe(
      (data) => {
        if (data) {
          Swal.fire({
            icon: 'success',
            title: 'Friendship Request Sent',
            showConfirmButton: false,
            timer: 1500
          });
        } 
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'You have already sent a request to this person',
          showConfirmButton: false,
          timer: 1500
        });
      }
    );
  }

  setUserIdWardrobe(id:number)
  {
    this.userWardrobeId = id;
  }
  getPrendas(id:number , username:string){
    this.prendaService.getPrendasbyUserId(id).subscribe((data)=>{
      this.prendas = data as Prenda[];
    })
    this.amigoWardrobe = username;
    this.setUserIdWardrobe(id);
  }

  private base64ToBlob(base64String: string, contentType: string): Blob {
    const byteCharacters = atob(base64String);
    const byteArrays = [];

    for (let offset = 0; offset < byteCharacters.length; offset += 512) {
      const slice = byteCharacters.slice(offset, offset + 512);

      const byteNumbers = new Array(slice.length);
      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      const byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }

    return new Blob(byteArrays, { type: contentType });
  }
   
  base64toImage(base64: string) {
    const blob = this.base64ToBlob(base64, 'image/png');

    // Crea una URL de objeto para el blob
    const urlCreator = window.URL || window.webkitURL;
    return urlCreator.createObjectURL(blob);
  }

  getImageHeight(tipoPrenda: string): string {
    switch(tipoPrenda) {
      case 'Top':
      case 'Bottom':
      case 'Footwear':
      case 'OuterWear':
        return '14vh';
      
      case 'Accesories':
        return '10vh';
      default:
        return 'auto';
    }
  }
  getSolicitudes(){
    this.solicitudService.getSolicitudesbyUser(this.userService.user.id).subscribe(value => {
      this.solicitudes = value as solicitudResponse[];
    })
  }

  rejectSolicitud(id:number)
  {
    this.solicitudService.rejectSolicitud(id).subscribe(
      (data) => {
        if (data) {
          Swal.fire({
            icon: 'success',
            title: 'Request Declined',
            showConfirmButton: false,
            timer: 1500
          });
        } 
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'The request couldnt be accepted',
          showConfirmButton: false,
          timer: 1500
        });
      }
    )
  }
  acceptSolicitud(id:number)
  {
    this.solicitudService.acceptSolicitud(id).subscribe(
      (data) => {
        if (data) {
          Swal.fire({
            icon: 'success',
            title: 'Request Accepted',
            showConfirmButton: false,
            timer: 1500
          });
        } 
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'The request couldnt be accepted',
          showConfirmButton: false,
          timer: 1500
        });
      }
    )
  }

  sendSolicitudPrenda(prendaId:number){
    var solicitudRequest: solicitudPrendaRequest ={
      userIdFrom: this.userService.user.id,
      userIdTo:this.userWardrobeId,
      prendaId:prendaId
    }
    this.solicitudPrendaService.sendSolicitud(solicitudRequest).subscribe(
      (data)=>{
        if (data) {
          Swal.fire({
            icon: 'success',
            title: 'Item Request Send to Friend',
            showConfirmButton: false,
            timer: 1500
          });
        } 
      },
      (error) => {
        Swal.fire({
          icon: 'error', 
          title: 'You have already sent a request for this Item',
          showConfirmButton: false,
          timer: 1500
        });
      }
    )
  }

  getSolicitudesPrendas(){
    this.solicitudPrendaService.getSolicitudesByUser(this.userService.user.id).subscribe(value => {
      this.solicitudesPrendas = value as solicitudPrendaResponse[];
    })
  }

  AcceptSolicitudPrenda(id:number){
    this.solicitudPrendaService.acceptSolicitud(id).subscribe(
      (data) => {
        if (data) {
          Swal.fire({
            icon: 'success',
            title: 'Request Accepted',
            showConfirmButton: false,
            timer: 1500
          });
          this.getSolicitudesPrendas();
        } 
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'The request couldnt be accepted',
          showConfirmButton: false,
          timer: 1500
        });
      }
    )
  }

  RejectSolicitudPrenda(id:number){
    this.solicitudPrendaService.rejectSolicitud(id).subscribe(
      (data) => {
        if (data) {
          Swal.fire({
            icon: 'success',
            title: 'Request Declined',
            showConfirmButton: false,
            timer: 1500
          });
          this.getSolicitudesPrendas();
        } 
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'The request couldnt be accepted',
          showConfirmButton: false,
          timer: 1500
        });
      }
    )
  }
}
