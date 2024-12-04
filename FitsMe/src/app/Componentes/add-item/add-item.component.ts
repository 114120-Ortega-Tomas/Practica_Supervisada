import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Clima, Color, Estilo, Prenda, PrendaRequest, PrendaToUserRequest, TipoPrenda, share } from 'src/app/Models/Models';
import { PrendasService } from 'src/app/Services/PrendaService/prendas.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, PatternValidator, Validators } from '@angular/forms';
import { AuxiliarService } from 'src/app/Services/AuxiliarService/auxiliar.service';
import { catchError, map, of, refCount } from 'rxjs';
import { UserService } from 'src/app/Services/UserService/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.css']
})
export class AddItemComponent implements OnInit {
    constructor(private auxiliarService:AuxiliarService,private router:Router, private userService:UserService , private prendaService:PrendasService ,private http : HttpClient , private fb : FormBuilder){
      this.formaddItem = this.fb.group({
        descripcion:[[],[Validators.required , Validators.maxLength(100)]],
        color:[[],[Validators.required]],
        tipoPrenda:[[],[Validators.required]],
        estilo:[[],[Validators.required]],
        clima:[[],[Validators.required]],
        private : [false]
      })
    }
    
    Prendas:Prenda[] = [];
    colores : Color[] = [];
    climas :Clima[] = [];
    estilos : Estilo[] = [];
    tiposPrendas : TipoPrenda[] = [];
    prenda:Prenda=this.prendaService.getPrenda();
    @ViewChild('fileInput') fileInput!: ElementRef;
    formaddItem! : FormGroup;
    PrendaFile : File[] = [];
    hasfile:boolean = false;
    dropdownSettingsColors:any;
    dropdownSettingsEstilos:any;
    dropdownSettingsClima:any;
    share: share[] = [];
    dropdownSettingsShare:any;
    dropdownSettingsTipoPrenda:any;
    submitted:boolean = false;
    shareOptions: any[] = ["Public", "Private"];
    currentIndex: number = 0;
    update : boolean = false;
  //---------------------------------------------------------------------//
  

   ngOnInit(): void {
    
    this.dropdownSettingsColors = {
      singleSelection: false,
      idField: 'colorId',
      textField: 'descripcion',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      limitSelection: 3,
    }
    this.dropdownSettingsClima = {
      singleSelection: false,
      idField: 'climaId',
      textField: 'descripcion',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      limitSelection: 3
    }
    this.dropdownSettingsEstilos = {
      singleSelection: false,
      idField: 'estiloId',
      textField: 'descripcion',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      limitSelection:3
    }
    this.dropdownSettingsTipoPrenda = {
      singleSelection: true,
      idField: 'tipoPrendaId',
      textField: 'descripcion',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      limitSelection:3
    }
    this.dropdownSettingsShare = {
      singleSelection: true,
      unSelectAllText: 'UnSelect All',
    }
     this.prendaService.getPrendasPublicas().subscribe((data)=>{
       this.Prendas = data as Prenda[];
     })
     this.auxiliarService.getColores().subscribe((data)=>{
       this.colores = data as Color[];
     })
     this.auxiliarService.getClimas().subscribe((data)=>{
       this.climas = data as Clima[];
     })
     this.auxiliarService.getEstilos().subscribe((data)=>{
       this.estilos = data as Estilo[];
     })
     this.auxiliarService.getTipoPrendas().subscribe((data)=>{
       this.tiposPrendas = data as TipoPrenda[];
     })
     this.shareOptions = this.share;
     if(this.prenda.prendaId != 0)
      {
        this.cargarFormulario();
        this.update = true;
      }
   }

   openFileExplorer() {
    this.fileInput.nativeElement.click();
  }
  onDragOver(event:DragEvent)
  {
    event.preventDefault();
    event.stopPropagation();
  }

  onDragLeave(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
  }
  onDragEnter(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
  }
  return()
  {
    this.auxiliarService.setSelected(2);
  }

  onDrop(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    
    const files = event.dataTransfer?.files;
    if (files) {
      for (let i = 0; i < files.length; i++) {
        this.PrendaFile.push(files[i]);
      }
    }
  
  }
  ImagetoBase64(prenda: File) {
    const reader = new FileReader();
    reader.readAsDataURL(prenda);
    reader.onload = () => {
      const base64String = reader.result as string;
      const base64Content = base64String.split(',')[1]; 
      this.prenda.base64 = base64Content;
    };
  }
  
  onFilesSelected(event: any) {
    const files = event.target.files;
    if (files) {
      for (let i = 0; i < files.length; i++) {
        this.PrendaFile.push(files[i]);
      }
      console.log(this.PrendaFile);
      console.log(this.ImagetoBase64(this.PrendaFile[0]))
      this.hasfile = true;
    }
  }
  removeFile(event: MouseEvent, file: File) {
    event.stopPropagation(); 
    const index = this.PrendaFile.indexOf(file);
    if (index !== -1) {
        this.PrendaFile.splice(index, 1);
    }
    if (this.PrendaFile.length === 0) {
        this.hasfile = false;
    }
  }

  savePrenda(){
    this.submitted = true;
    this.ImagetoBase64(this.PrendaFile[0]); 
    if(!this.formaddItem.valid)
    {
      return;
    }
    const climasId = this.formaddItem.value.clima.map((clima: any) => clima.climaId);
    const estilosId = this.formaddItem.value.estilo.map((estilo: any) => estilo.estiloId);
    const colorId = this.formaddItem.value.color.map((color: any) => color.colorId);
    const tipoPrenda = this.formaddItem.value.tipoPrenda[0].tipoPrendaId;
    const priv = this.formaddItem.value.private;
    
    var prendaR : PrendaRequest ={
      base64: this.prenda.base64,
      clima: climasId,
      color: colorId,
      descripcion: this.formaddItem.value.descripcion,
      contentType: "",
      estilo: estilosId,
      tipoPrenda: tipoPrenda,
      privado: priv,
      userId: this.userService.user.id
    }
    if(this.formaddItem.value.share == true)
      {
        prendaR.privado = false
      }
    console.log(prendaR);
    this.prendaService.postPrenda(prendaR).subscribe((data)=>{
      Swal.fire({
        icon: 'success',
        title: 'Item Saved',
        showConfirmButton: false,
        timer: 1500
      })
      this.router.navigate(["/account"]);
    })
  }
  updatePrenda(){
    console.log(this.prenda.prendaId);
    this.submitted = true;
    this.ImagetoBase64(this.PrendaFile[0]); 
    if(!this.formaddItem.valid || this.PrendaFile.length == 0)
    {
      return;
    }
    const climasId = this.formaddItem.value.clima.map((clima: any) => clima.climaId);
    const estilosId = this.formaddItem.value.estilo.map((estilo: any) => estilo.estiloId);
    const colorId = this.formaddItem.value.color.map((color: any) => color.colorId);
    const tipoPrenda = this.formaddItem.value.tipoPrenda[0].tipoPrendaId;
   
    var prendaR : PrendaRequest ={
      base64: this.prenda.base64,
      clima: climasId,
      color: colorId,
      descripcion: this.formaddItem.value.descripcion,
      contentType: "",
      estilo: estilosId,
      tipoPrenda: tipoPrenda,
      privado: true,
      userId: this.userService.user.id
    }
    if(this.formaddItem.value.share == true)
      {
        prendaR.privado = false
      }
    this.prendaService.updatePrenda(prendaR,this.prenda.prendaId).subscribe((data)=>{
      Swal.fire({
        icon: 'success',
        title: 'Item Updated',
        showConfirmButton: false,
        timer: 1500
      })
      this.router.navigate(["/account"]);
    })
  }

  SaveorUpdate(){
    if(this.update){
      this.updatePrenda();
    }
    else this.savePrenda();
    setTimeout(() => {
      this.auxiliarService.setSelected(2);
    }, 1000);
    
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

  addPrenda(item: any) {
    const prendaRequest: PrendaToUserRequest = {
      userId: this.userService.user.id,
      prendaId: item.prendaId
    };
  
    this.prendaService.addPrendatoUser(prendaRequest).pipe(
      catchError((error) => {
        // Verifica si el error contiene el mensaje "Prenda ya existente"
        if (error?.status === 400 && error?.error?.message === "Prenda ya existente") {
          Swal.fire({
            icon: 'error',
            title: 'You already have this item in your wardrobe',
            showConfirmButton: false,
            timer: 1500
          });
        } else {
          Swal.fire({
            icon: 'success',
            title: 'Item added to wardrobe',
            showConfirmButton: false,
            timer: 1500
          });
        }
        return of(null); // Continua el flujo retornando un observable vacío
      })
    ).subscribe((data) => {
      if (data) {
        
      }
    });
  }
  
  


  next(): void {
    if (this.currentIndex < this.Prendas.length - 1) {
      this.currentIndex++;
    } else {
      this.currentIndex = 0; // Vuelve al inicio si llega al final
    }
  }

  prev(): void {
    if (this.currentIndex > 0) {
      this.currentIndex--;
    } else {
      this.currentIndex = this.Prendas.length - 1; // Vuelve al final si está en el inicio
    }
  }
  
  cargarFormulario(){
  const colores = this.prenda.colores.map(color => ({colorId: color.id, descripcion: color.descripcion}));
  const estilos = this.prenda.estilos.map(estilo => ({estiloId: estilo.id, descripcion: estilo.descripcion}));
  const climas = this.prenda.climas.map(clima => ({climaId: clima.id, descripcion: clima.descripcion}));
  console.log(this.prenda);
  this.formaddItem.patchValue({
    tipoPrenda: [this.prenda.tipoPrenda],
    color: colores,
    estilo: estilos,
    clima: climas,
    descripcion: this.prenda.descripcion
  });
  var file = new File([this.prenda.base64], this.prenda.descripcion);
  this.PrendaFile = [];
  this.PrendaFile.push(file);
  this.hasfile = true;
}

}