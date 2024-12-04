import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Outfit, OutfitRequest } from '../../Models/Models';

@Injectable({
  providedIn: 'root'
})
export class OutfitService {

  constructor(private http : HttpClient) { }
  selectedOutfit : Outfit = new Outfit();
  getOutfits()
  {
    return this.http.get('http://localhost:8080/Outfit/GetAllOutfits');
  }
  saveOutfit(outfit:OutfitRequest)
  {
    return this.http.post('http://localhost:8080/Outfit/SaveOutfit',outfit);
  }
  getOutfitsByUserId(userId:number){
    return this.http.get('http://localhost:8080/Outfit/GetOutfitsByUserId?userId='+ userId);
  }
  setOutfitFavorite(outfitId:number , userId:number)
  {
    return this.http.get("http://localhost:8080/Outfit/SetOutfitFavorito?outfitId=" + outfitId + "&userId=" + userId)
  }
  deleteOutfit(id:number)
  {
    return this.http.delete("http://localhost:8080/Outfit/DeleteOutfit?outfitId=" + id)
  }
  setOutfit(outfit:Outfit)
  {
    this.selectedOutfit = outfit;
  }
  getFavoriteOutfitsByUserId(userId:number){
    return this.http.get('http://localhost:8080/Outfit/GetFavoriteOutfitsByUserId?userId='+ userId);
  }
} 
