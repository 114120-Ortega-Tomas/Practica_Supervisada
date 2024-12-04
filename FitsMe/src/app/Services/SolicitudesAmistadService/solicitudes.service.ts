import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { solicitudRequest } from '../../Models/Models';

@Injectable({
  providedIn: 'root'
})
export class SolicitudesService {

  constructor(private http: HttpClient) { }

  sendSolicitud(request:solicitudRequest){
    return this.http.post("http://localhost:8080/SolicitudAmistad/SendSolicitud",request);
  }

  getAmigos(userId:number){
    return this.http.get("http://localhost:8080/Usuarios/getAmigos?id="+userId)
  }

  getSolicitudesbyUser(id:number){
    return this.http.get("http://localhost:8080/SolicitudAmistad/GetSolicitudesByUser?id="+id)
  }

  rejectSolicitud(id:number)
  {
    return this.http.post("http://localhost:8080/SolicitudAmistad/RejectSolicitud",id)
  }
  acceptSolicitud(id:number)
  {
    return this.http.post("http://localhost:8080/SolicitudAmistad/AcceptSolicitud",id)
  }
}
