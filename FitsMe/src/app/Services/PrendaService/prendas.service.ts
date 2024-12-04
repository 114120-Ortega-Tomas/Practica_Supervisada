import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Filter, Prenda, PrendaRequest, PrendaToUserRequest } from '../../Models/Models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PrendasService {
  prenda:Prenda=new Prenda();
  constructor(private http: HttpClient) { }

  getAll(){
    return this.http.get("http://localhost:8080/Prendas/GetAllPrendas");
  }

  getPrendabyId(id:number):Observable<Prenda>
  {
    return this.http.get<Prenda>("http://localhost:8080/Prendas/GetPrendaById?id="+id)
  }
  postPrenda(prenda:PrendaRequest):Observable<Prenda>
  {
    return this.http.post<Prenda>("http://localhost:8080/Prendas/PostPrenda",prenda)
  }
  getPrendasPublicas():Observable<Prenda[]>
  {
    return this.http.get<Prenda[]>("http://localhost:8080/Prendas/GetPublicPrendas")
  }
  getPrendasbyUserId(id:number):Observable<Prenda[]>
  {
    return this.http.get<Prenda[]>("http://localhost:8080/Prendas/GetPrendasByUser?id="+id)
  }
  addPrendatoUser(request : PrendaToUserRequest){
    return this.http.post("http://localhost:8080/Prendas/savePrendaToUser", request)
  }
  getTops(id:number){
    return this.http.get<Prenda[]>("http://localhost:8080/Prendas/GetTops?id="+id)
  }
  getBottoms(id:number){
    return this.http.get<Prenda[]>("http://localhost:8080/Prendas/GetBottoms?id="+id)
  }
  getFootWear(id:number){
    return this.http.get<Prenda[]>("http://localhost:8080/Prendas/getFootWear?id="+id)
  }
  getAccesories(id:number){
    return this.http.get<Prenda[]>("http://localhost:8080/Prendas/getAccesories?id="+id)
  }
  getOuterWear(id:number){
    return this.http.get<Prenda[]>("http://localhost:8080/Prendas/getOuterWear?id="+id)
  }
  getDresses(id:number){
    return this.http.get<Prenda[]>("http://localhost:8080/Prendas/getDresses?id="+id)
  }
  setPrendaFavorite(prendaid:number , userId:number)
  {
    return this.http.get("http://localhost:8080/Prendas/setPrendaFavorita?prendaId="+prendaid+"&userId="+userId)
  }
  getFavorites(userId:number)
  {
    return this.http.get<Prenda[]>("http://localhost:8080/Prendas/getFavorites?id="+userId)
  }
  deletePrenda(id:number , userId:number)
  {
    return this.http.delete("http://localhost:8080/Prendas/deletePrenda?id="+id+"&userId="+userId)
  }
  updatePrenda(prenda:PrendaRequest,id:number)
  {
    return this.http.put("http://localhost:8080/Prendas/UpdatePrenda?prendaId="+id,prenda)
  }
  getPrendasFiltered(filerObj:Filter){
    return this.http.post("http://localhost:8080/Prendas/getPrendasFiltered", filerObj)
  }
  setPrenda(prendaNew:Prenda){
    this.prenda=prendaNew;
  }
  getPrenda(){
    return this.prenda;
  }

  getFamousPrendas(){
    return this.http.get<Prenda[]>("http://localhost:8080/Prendas/getFamousPrendas")
  }
}
