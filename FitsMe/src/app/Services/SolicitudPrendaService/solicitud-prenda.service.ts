import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { solicitudPrendaRequest } from 'src/app/Models/Models';

@Injectable({
  providedIn: 'root'
})
export class SolicitudPrendaService {

  constructor(private http : HttpClient) { }

  getSolicitudesByUser(id:number){
    return this.http.get("http://localhost:8080/SolicitudPrenda/GetSolicitudesByUser?id="+id);
  }
  sendSolicitud(request:solicitudPrendaRequest){
    return this.http.post("http://localhost:8080/SolicitudPrenda/SendSolicitud",request)
  }
  acceptSolicitud(id:number){
    return this.http.post("http://localhost:8080/SolicitudPrenda/AcceptSolicitud",id)
  }
  rejectSolicitud(id:number){ 
    return this.http.post("http://localhost:8080/SolicitudPrenda/RejectSolicitud",id)
  }
  devolverPrenda(prendaId:number , userId:number , prestadaByUserId:number){
    return this.http.get("http://localhost:8080/SolicitudPrenda/DevolverPrenda?prendaId="+prendaId+"&userId="+userId+"&prestadaByUserId="+prestadaByUserId)
  }
}
