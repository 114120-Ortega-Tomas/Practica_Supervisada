import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserService } from '../UserService/user.service';
import { EventRequest, Evento } from '../../Models/Models';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  constructor(private http: HttpClient , private userService:UserService ) { }
  event : Evento = new Evento()
  urlGetEventoByDate="http://localhost:8080/Evento/getEventobyDate"

  getEventosByDate(date:String){
    return this.http.get("http://localhost:8080/Evento/getEventobyDate?date="+date+"&UserId="+this.userService.user.id)
  }

  saveEvento(evento:EventRequest){
    return this.http.post("http://localhost:8080/Evento/PostEvento" , evento)
  }

  updateEvento(eventoId:number,evento:EventRequest){
    return this.http.put("http://localhost:8080/Evento/PutEvento?id="+eventoId , evento)
  }

  deleteEvento(id:number){
    return this.http.delete("http://localhost:8080/Evento/deleteEvento?eventoId="+id);
  }
}
