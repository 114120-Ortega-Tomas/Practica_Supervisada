import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuxiliarService {
  private selectedSubject = new BehaviorSubject<number>(0);
  selected$ = this.selectedSubject.asObservable();
  constructor(private http: HttpClient) { }

  getColores(){
    return this.http.get("http://localhost:8080/Auxiliar/GetAllColores")
  }
  getClimas(){
    return this.http.get("http://localhost:8080/Auxiliar/GetAllClimas")
  }
  getEstilos(){
    return this.http.get("http://localhost:8080/Auxiliar/GetAllEstilos")
  }
  getTipoPrendas(){
    return this.http.get("http://localhost:8080/Auxiliar/GetAllTipoPrendas")
  }

  setSelected(value: number) {
    this.selectedSubject.next(value);
  }

  getUsuariosPorMes(from:string ,to:string){
    return this.http.get("http://localhost:8080/Usuarios/getUserbyMonth?from="+from+"&to="+to)
  }

}
