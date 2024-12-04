import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Clima, Color, Estilo, Filter, Outfit, Prenda, TipoPrenda } from 'src/app/Models/Models';
import { AuxiliarService } from 'src/app/Services/AuxiliarService/auxiliar.service';
import { OutfitService } from 'src/app/Services/OutfitService/outfit.service';
import { PrendasService } from 'src/app/Services/PrendaService/prendas.service';
import { SolicitudPrendaService } from 'src/app/Services/SolicitudPrendaService/solicitud-prenda.service';
import { UserService } from 'src/app/Services/UserService/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-wardrobe',
  templateUrl: './wardrobe.component.html',
  styleUrls: ['./wardrobe.component.css']
})
export class WardrobeComponent implements OnInit {

  constructor(private router:Router,private solicitudPrendaService:SolicitudPrendaService,private fb:FormBuilder,private outfitService : OutfitService,private auxiliarService:AuxiliarService , private userService:UserService ,private dialog :MatDialog,private prendaService:PrendasService){
    this.formFilter = this.fb.group({
      color: [[]],
      clima: [[]],
      estilo: [[]],
    })
    this.FormEditOutfit = this.fb.group({
      name: ['',[Validators.required,Validators.minLength(3)]],
    })
  }
  user:string="";
  colores: Color[] = [];
  climas: Clima[] = [];
  estilos: Estilo[] = [];
  corazon:boolean = false;
  addItem:boolean = true ;
  addItems : boolean = false;
  formFilter!: FormGroup
  FormEditOutfit!:FormGroup
  dropdownSettingsColors: any;
  dropdownSettingsEstilos: any;
  dropdownSettingsClima: any;
  selectedType: string = 'all';
  isLoading : boolean = false
  Prendas : Prenda[] = []
  Outfits :Outfit[] = []
  @ViewChild('add') button: ElementRef | undefined;
  filteredPrendas: Prenda[] = [];
  popupvisible:boolean = false
  ngOnInit(): void {
    this.isLoading = true
    this.user = this.userService.user.nombre;
    this.getPrendas();
    this.prendaService.setPrenda(new Prenda());
    this.filteredPrendas = [...this.Prendas];
    this.dropdownSettingsColors = {
      singleSelection: false,
      idField: 'colorId',
      textField: 'descripcion',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 5,
      limitSelection: 5,
    };
    this.dropdownSettingsClima = {
      singleSelection: false,
      idField: 'climaId',
      textField: 'descripcion',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 5,
      limitSelection: 5
    };
    this.dropdownSettingsEstilos = {
      singleSelection: false,
      idField: 'estiloId',
      textField: 'descripcion',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 5,
      limitSelection: 5
    };
    this.auxiliarService.getColores().subscribe((data) => {
      this.colores = data as Color[];
    });
    this.auxiliarService.getClimas().subscribe((data) => {
      this.climas = data as Clima[];
    });
    this.auxiliarService.getEstilos().subscribe((data) => {
      this.estilos = data as Estilo[];
    });
    
  }

  getPrendas(){
    this.isLoading = true
    this.addItem = false
    this.prendaService.getPrendasbyUserId(this.userService.user.id).subscribe((data)=>{
      this.Prendas = data as Prenda[];
      this.Outfits = [];
      this.isLoading = false
    })
  }
  isLeftColumn(tipoPrenda: string): boolean {
    return ['Top', 'Bottom', 'Footwear'].includes(tipoPrenda);
  }

  openClose(){
    this.popupvisible = !this.popupvisible
  }
  getTops(){
    this.isLoading = true
    this.addItem = false
    this.prendaService.getTops(this.userService.user.id).subscribe((data)=>{
      this.Prendas = data as Prenda[];
      this.Outfits =[];
      this.isLoading = false
    })
  }

  changeCorazon(){
    this.corazon = !this.corazon
    if(this.corazon == false){
      this.getPrendas();
    }
    else{
      this.getFavorites();
    }
  }

  selectType(type: string) {
    this.selectedType = type;
  }
  deleteOutfit(id:number){
    Swal.fire({
      title: 'Deleting Outfit',
      text: "All the Events related to this outfit will be deleted",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Accept'
    }).then((result) => {
      if (result.isConfirmed) {
        this.outfitService.deleteOutfit(id).subscribe((data)=>{
          this.getOutfits();
        })
        Swal.fire(
          'Deleted!',
          'Your Outfit and its events has been deleted.',
          'success'
        )
      }
    })
  }
  EditOutfit(item:Outfit){
    this.FormEditOutfit.patchValue({
      name: item.nombre,
    })
  }
  getBottoms(){
    this.isLoading = true
    this.addItem = false
    this.prendaService.getBottoms(this.userService.user.id).subscribe((data)=>{
      this.Prendas = data as Prenda[];
      this.Outfits =[];
      this.isLoading = false
    })
  }
  getFootWear(){
    this.isLoading = true
    this.addItem = false
    this.prendaService.getFootWear(this.userService.user.id).subscribe((data)=>{
      this.Prendas = data as Prenda[];
      this.Outfits =[];
      this.isLoading = false
    })
  }
  getFavorites(){
    this.isLoading = true
    this.addItem = false
    this.prendaService.getFavorites(this.userService.user.id).subscribe((data)=>{
      this.Prendas = data as Prenda[];
      this.Outfits =[];
      this.isLoading = false
    })
  }
  getAccesories(){
    this.isLoading = true
    this.addItem = false
    this.prendaService.getAccesories(this.userService.user.id).subscribe((data)=>{
      this.Prendas = data as Prenda[];
      this.Outfits =[];
      this.isLoading = false
    })
  }
  getOutWear(){
    this.isLoading = true
    this.addItem = false
    this.prendaService.getOuterWear(this.userService.user.id).subscribe((data)=>{
      this.Prendas = data as Prenda[];
      this.Outfits =[];
      this.isLoading = false
    })
  }
   getOutfits(){
    this.isLoading = true
    this.outfitService.getOutfitsByUserId(this.userService.user.id).subscribe((data)=>{
      this.Outfits = data as Outfit[];
      this.isLoading = false
    })
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
    const urlCreator = window.URL || window.webkitURL;
    return urlCreator.createObjectURL(blob);
  }



  setPrendaFavorite(prenda:any)
  {
    console.log(prenda.prendaId);
    this.prendaService.setPrendaFavorite(prenda.prendaId, this.userService.user.id).subscribe((data)=>{
      this.getPrendas();
      Swal.fire({
        icon: 'success',
        title: 'Item Updated',
        showConfirmButton: false,
        timer: 1500
      })
    })
  }

  setOutfitFavorite(outfit:any){
    this.outfitService.setOutfitFavorite(outfit, this.userService.user.id).subscribe((data)=>{
      this.getOutfits();
      Swal.fire({
        icon: 'success',
        title: 'Item Updated',
        showConfirmButton: false,
        timer: 1500
      })
    })

  }
  
  filterPrendas(event: any) {
    const searchTerm = event.target.value.toLowerCase();
    this.filteredPrendas = this.Prendas.filter(prenda =>
      prenda.descripcion.toLowerCase().includes(searchTerm)
    );
  } 

  deletePrenda(item:any){
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.prendaService.deletePrenda(item.prendaId, this.userService.user.id).subscribe((data)=>{
          this.getPrendas();
        })
        Swal.fire(
          'Deleted!',
          'Your item has been deleted.',
          'success'
        )
      }
    })
    
  }
  EditPrenda(item:Prenda){
    this.prendaService.setPrenda(item);
    console.log(this.prendaService.getPrenda());
    this.auxiliarService.setSelected(4);
  }

  cambiar(){
    this.auxiliarService.setSelected(4);
    var prenda1 : Prenda ={
      prendaId: 0,
      descripcion: "",
      base64: "",
      usos: 0,
      tipoPrenda: new TipoPrenda(),
      estilos: [],
      climas: [],
      colores: [],
      favorito: false,
      prestada: false, 
      prestamo:false,
      prestadabyUserId: 0,
      genteUsando: 0
    }
    this.prendaService.setPrenda(prenda1);
  }

  cambiarOutfit(){
    this.auxiliarService.setSelected(1);
  }

  DevolverPrenda(prendaId:number ,  prestadaByUserId:number){
    this.solicitudPrendaService.devolverPrenda(prendaId , this.userService.user.id , prestadaByUserId).subscribe((data)=>{
      if(data)
        {
          Swal.fire({
            icon: 'success',
            title: 'Item Returned',
            showConfirmButton: false,
            timer: 1500
          })
        }
      this.getPrendas();
    })
  }

  armaryAplicarFiltros() {
    if(this.formFilter.value.color.length > 0 || this.formFilter.value.clima.length > 0 || this.formFilter.value.estilo.length > 0) {
      
      const colorObjects = this.formFilter.value.color;
      const coloresId = colorObjects.map((color: { colorId: number, descripcion: string }) => color.colorId);
      const climas = this.formFilter.value.clima;
      const climasId = climas.map((clima: { climaId: number, descripcion: string }) => clima.climaId);
      const estilos = this.formFilter.value.estilo;
      const estilosId = estilos.map((estilo: { estiloId: number, descripcion: string }) => estilo.estiloId);
      const filter: Filter = {
        prendas: [],
        coloresId: coloresId,
        climasId: climasId,
        estilosId: estilosId,
        favorite: this.formFilter.value.favorite,
        userId: this.userService.user.id
      };
      this.prendaService.getPrendasFiltered(filter).subscribe((data) => {
        this.Prendas = data as Prenda[];  
        console.log(data);   
        console.log("hola",this.filteredPrendas);   
      });
  }else {
    this.getPrendas();
  }
}



getImageHeight(tipoPrenda: string): string {
  switch(tipoPrenda) {
    case 'Top':
    case 'Bottom':
    case 'Footwear':
    case 'OuterWear':
    
      return '14vh';
    case 'Accesories':
      return '11vh';
    default:
      return 'auto';
  }
}
}
