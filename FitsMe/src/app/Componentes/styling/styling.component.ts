import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild, ChangeDetectorRef, AfterViewChecked } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Clima, Color, Estilo, Filter, OutfitRequest, Prenda, User, clima } from 'src/app/Models/Models';
import { AuxiliarService } from 'src/app/Services/AuxiliarService/auxiliar.service';
import { OutfitService } from 'src/app/Services/OutfitService/outfit.service';
import { PrendasService } from 'src/app/Services/PrendaService/prendas.service';
import { UserService } from 'src/app/Services/UserService/user.service';
import Swal from 'sweetalert2';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-styling',
  templateUrl: './styling.component.html',
  styleUrls: ['./styling.component.css']
})
export class StylingComponent implements OnInit, AfterViewInit, AfterViewChecked {
  constructor(
    private cdRef: ChangeDetectorRef,
    private http: HttpClient,
    private auxiliarService: AuxiliarService,
    private outfitService: OutfitService,
    private fb: FormBuilder,
    private prendaService: PrendasService,
    private router: Router,
    private userService: UserService
  ) {
    this.carousel3 = new ElementRef(null);
    this.carousel2 = new ElementRef(null);
    this.carousel5 = new ElementRef(null);
    this.carousel1 = new ElementRef(null);
    this.carousel4 = new ElementRef(null);

    this.formSave = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required]
    });

    this.formFilter = this.fb.group({
      favorite: [false],
      color: [[]],
      clima: [[]],
      estilo: [[]],
      abrigo: [false],
      accesorio: [false],
    });
  }
  isLoading: boolean = false;
  prendasFavoritas: Prenda[] = [];
  selectedTopId: number = 0;
  selectedBottomId: number = 0;
  selectedFootWearId: number = 0;
  selectedAccesoriesId: number = 0;
  selectedOuterWearId: number = 0;
  formSave!: FormGroup;
  formFilter!: FormGroup;
  prendasId: number[] = [];
  Prendas: Prenda[] = [];
  save: boolean = false;
  Tops: Prenda[] = [];
  colores: Color[] = [];
  climas: Clima[] = [];
  estilos: Estilo[] = [];
  options: boolean = false;
  Bottoms: Prenda[] = [];
  FootWear: Prenda[] = [];
  Accesories: Prenda[] = [];
  OuterWear: Prenda[] = [];
  Dresses: Prenda[] = [];
  abrigo: boolean = false;
  accesorio: boolean = false;
  dropdownSettingsColors: any;
  dropdownSettingsEstilos: any;
  dropdownSettingsClima: any;

  @ViewChild('carousel3') carousel3: ElementRef<any>;
  @ViewChild('carousel2') carousel2: ElementRef<any>;
  @ViewChild('carousel1') carousel1: ElementRef<any>;
  @ViewChild('carousel4') carousel4: ElementRef<any>;
  @ViewChild('carousel5') carousel5: ElementRef<any>;

  ngOnInit(): void {
    this.isLoading = true
    this.cargarPrendas();
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
   
  }

  ngAfterViewChecked() {
    this.cdRef.detectChanges();
  }
  toggleOuterWear() {
    if(this.formFilter.value.abrigo){
      this.formFilter.value.abrigo = false;
    }else this.formFilter.value.abrigo = true;
    this.cdRef.detectChanges();
    this.initCarouselEventOuterWear(); // Reinitialize event listener
  }
  toggleAccesories() {
    if(this.formFilter.value.accesorio){
      this.formFilter.value.accesorio = false;
    }else this.formFilter.value.accesorio = true;
    this.cdRef.detectChanges();
    this.initCarouselEventAccesories(); // Reinitialize event listener
  }

  ngAfterViewInit() {
    this.initCarouselEventFootWear();
    this.initCarouselEventTops();
    this.initCarouselEventBottoms();
    this.initCarouselEventOuterWear();
    this.initCarouselEventAccesories();
  }

  initCarouselEventFootWear() {
    const carouselElement = this.carousel3.nativeElement;
    carouselElement.addEventListener('slid.bs.carousel', (event: any) => {
      const activeIndex = event.to;
      const activePrendaId = this.FootWear[activeIndex].prendaId;
      this.selectedFootWearId = activePrendaId;
    });
  }
  initCarouselEventAccesories() {
    const carouselElement = this.carousel5.nativeElement;
    carouselElement.addEventListener('slid.bs.carousel', (event: any) => {
      const activeIndex = event.to;
      const activePrendaId = this.Accesories[activeIndex].prendaId;
      this.selectedAccesoriesId = activePrendaId;
    });
  }
  initCarouselEventOuterWear() {
    const carouselElement = this.carousel4.nativeElement;
    carouselElement.addEventListener('slid.bs.carousel', (event: any) => {
      const activeIndex = event.to;
      const activePrendaId = this.OuterWear[activeIndex].prendaId;
      this.selectedOuterWearId = activePrendaId;
      console.log("aaaa") ;
    });
  }
  initCarouselEventTops() {
    const carouselElement = this.carousel1.nativeElement;
    carouselElement.addEventListener('slid.bs.carousel', (event: any) => {
      const activeIndex = event.to;
      const activePrendaId = this.Tops[activeIndex].prendaId;
      this.selectedTopId = activePrendaId;
      console.log("cffff") ;
    });
  }

  initCarouselEventBottoms() {
    const carouselElement = this.carousel2.nativeElement;
    carouselElement.addEventListener('slid.bs.carousel', (event: any) => {
      const activeIndex = event.to;
      const activePrendaId = this.Bottoms[activeIndex].prendaId;
      this.selectedBottomId = activePrendaId;

    });
  }
  

  toggleModal() {
    this.options = !this.options;
  }

  toggleModalSave() {
    this.Prendas = [];
    const observables = [
      this.prendaService.getPrendabyId(this.selectedTopId),
      this.prendaService.getPrendabyId(this.selectedBottomId),
      this.prendaService.getPrendabyId(this.selectedFootWearId),
      
    ];
    if (this.formFilter.value.abrigo) {
      observables.push(this.prendaService.getPrendabyId(this.selectedOuterWearId));
    }
    if(this.formFilter.value.accesorio){
      observables.push(this.prendaService.getPrendabyId(this.selectedAccesoriesId));
    }
    
    forkJoin(observables).subscribe((results: Prenda[]) => {
      this.Prendas = results;
      this.save = !this.save; // Cambio de save después de obtener todos los resultados
    });
  }

  saveOutfit() {
    if (!this.formSave.valid) {
      return;
    }
    var listaPrendasId: number[] = [];
    for (let i = 0; i < this.Prendas.length; i++) {
      listaPrendasId.push(this.Prendas[i].prendaId);
    }
    var outfit: OutfitRequest = {
      usuarioId: this.userService.user.id,
      nombre: this.formSave.value.nombre,
      descripcion: this.formSave.value.descripcion,
      prendas: listaPrendasId,
      favorito: false,
    };
    this.outfitService.saveOutfit(outfit).subscribe((data) => {
      if (data) {
        Swal.fire({
          icon: 'success',
          title: 'Ready',
          text: 'Outfit Saved!'
        });
      }
    });
  }

  aplicarFiltros() {
    this.prendasId = [];    
    if (this.formFilter.value.favorite) {
      this.Tops = [];
      this.Bottoms = [];
      this.FootWear = [];
      this.Accesories = [];
      this.OuterWear = [];
      this.prendaService.getFavorites(this.userService.user.id).subscribe((data) => {
        this.Prendas = data as Prenda[];
        this.filtrarPrendas(this.Prendas);
        this.Prendas.map(prenda => {
          this.prendasId.push(prenda.prendaId);
        });
        if(this.formFilter.value.color.length > 0 || this.formFilter.value.clima.length > 0 || this.formFilter.value.estilo.length > 0) {
          this.armaryAplicarFiltros();
        }  
      });
    } else if (this.formFilter.value.color.length > 0 || this.formFilter.value.clima.length > 0 || this.formFilter.value.estilo.length > 0) {
      this.armaryAplicarFiltros()
    } else {
      this.cargarPrendas();
    }
      
    
  }

  filtrarPrendas(prendas: Prenda[]) {
   
    this.Tops = [];
    this.Bottoms = [];
    this.FootWear = [];
    this.Accesories = [];
    this.OuterWear = [];
    prendas.forEach(element => {
      switch (element.tipoPrenda.id) {
        case 3:
          this.Tops.push(element);
          break;
        case 4:
          this.Bottoms.push(element);
          break;
        case 5:
          this.FootWear.push(element);
          break;
        case 1:
          this.Accesories.push(element);
          break;
        case 2:
          this.OuterWear.push(element);
          break;
        default:
          break;
      }
    });
    if(this.Tops.length !== 0){
      this.selectedTopId = this.Tops[0].prendaId;
    }
    if(this.Bottoms.length !== 0){
      this.selectedBottomId = this.Bottoms[0].prendaId;
    }  
    if(this.FootWear.length !== 0){
      this.selectedFootWearId = this.FootWear[0].prendaId;
    }
    if(this.Accesories.length !== 0){
      this.selectedAccesoriesId = this.Accesories[0].prendaId;
    }
    if(this.OuterWear.length !== 0){
      this.selectedOuterWearId = this.OuterWear[0].prendaId;
    }
    
  }

  ArmarOutfitAleatorio() {
    this.seleccionarTopAleatorias();
    this.seleccionarPrendaInferiorAleatoria();
    this.seleccionarCalzadoAleatorio();
    if(this.formFilter.value.abrigo){
      this.seleccionarPrendaExteriorAleatoria();
    }
    if(this.formFilter.value.accesorio){
      this.seleccionarAccesorioAleatorio();
    }
  }

  seleccionarTopAleatorias() {
    if (this.Tops.length === 1) return;
    let newSelectedTopId: number;
    do {
      const randomIndex = Math.floor(Math.random() * this.Tops.length);
      newSelectedTopId = this.Tops[randomIndex].prendaId;
    } while (newSelectedTopId === this.selectedTopId);
    this.selectedTopId = newSelectedTopId;
  }

  // Función para seleccionar prenda inferior aleatoria
seleccionarPrendaInferiorAleatoria() {
  if (this.Bottoms.length === 1) return;
  let newSelectedBottomId: number;
  do {
    const randomIndex = Math.floor(Math.random() * this.Bottoms.length);
    newSelectedBottomId = this.Bottoms[randomIndex].prendaId;
  } while (newSelectedBottomId === this.selectedBottomId);
  this.selectedBottomId = newSelectedBottomId;
}

// Función para seleccionar calzado aleatorio
seleccionarCalzadoAleatorio() {
  if (this.FootWear.length === 1) return;
  let newSelectedFootwearId: number;
  do {
    const randomIndex = Math.floor(Math.random() * this.FootWear.length);
    newSelectedFootwearId = this.FootWear[randomIndex].prendaId;
  } while (newSelectedFootwearId === this.selectedFootWearId);
  this.selectedFootWearId = newSelectedFootwearId;
}

// Función para seleccionar accesorio aleatorio
seleccionarAccesorioAleatorio() {
  if (this.Accesories.length === 1) return;
  let newSelectedAccessoryId: number;
  do {
    const randomIndex = Math.floor(Math.random() * this.Accesories.length);
    newSelectedAccessoryId = this.Accesories[randomIndex].prendaId;
  } while (newSelectedAccessoryId === this.selectedAccesoriesId);
  this.selectedAccesoriesId = newSelectedAccessoryId;
}

// Función para seleccionar prenda exterior aleatoria
seleccionarPrendaExteriorAleatoria() {
  if (this.OuterWear.length === 1) return;
  let newSelectedOuterwearId: number;
  do {
    const randomIndex = Math.floor(Math.random() * this.OuterWear.length);
    newSelectedOuterwearId = this.OuterWear[randomIndex].prendaId;
  } while (newSelectedOuterwearId === this.selectedOuterWearId);
  this.selectedOuterWearId = newSelectedOuterwearId;
}


  cargarPrendas()  {
    this.prendaService.getPrendasbyUserId(this.userService.user.id).subscribe((data) => {
      this.getAccesories();
      this.getBottoms();
      this.getFootWear();
      this.getTops();
      this.getOutWear();
      this.getFavorites();
      this.auxiliarService.getColores().subscribe((data) => {
        this.colores = data as Color[];
      });
      this.auxiliarService.getClimas().subscribe((data) => {
        this.climas = data as Clima[];
      });
      this.auxiliarService.getEstilos().subscribe((data) => {
        this.estilos = data as Estilo[];
      });
    });
  }

  getTops() {
    this.prendaService.getTops(this.userService.user.id).subscribe((data) => {
      this.Tops = data as Prenda[];
      this.selectedTopId = data[0].prendaId;
    });
  }

  getBottoms() {
    this.prendaService.getBottoms(this.userService.user.id).subscribe((data) => {
      this.Bottoms = data as Prenda[];
      this.selectedBottomId = data[0].prendaId;
    });
  }

  getFootWear() {
    this.prendaService.getFootWear(this.userService.user.id).subscribe((data) => {
      this.FootWear = data as Prenda[];
      this.selectedFootWearId = data[0].prendaId;
    });
  }

  getFavorites() {
    this.prendaService.getFavorites(this.userService.user.id).subscribe((data) => {
      this.Prendas = data as Prenda[];
      this.isLoading = false
    });
  }

  getAccesories() {
    this.prendaService.getAccesories(this.userService.user.id).subscribe((data) => {
      this.Accesories = data as Prenda[];
      this.selectedAccesoriesId = data[0].prendaId;
    });

  }

  getOutWear() {
    this.prendaService.getOuterWear(this.userService.user.id).subscribe((data) => {
      this.OuterWear = data as Prenda[];
      this.selectedOuterWearId = data[0].prendaId;
    });
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

  armaryAplicarFiltros() {
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
      if (this.formFilter.value.favorite) {
        filter.prendas = this.Prendas.map(prenda => prenda.prendaId);
      }
      this.prendaService.getPrendasFiltered(filter).subscribe((data) => {
        this.Prendas = data as Prenda[];
        this.filtrarPrendas(data as Prenda[]);
      });
  }
  generateRandomOutfit() {
    if (this.Tops.length > 0) {
      const randomTopIndex = Math.floor(Math.random() * this.Tops.length);
      this.selectedTopId = this.Tops[randomTopIndex].prendaId;
    }
  
    if (this.Bottoms.length > 0) {
      const randomBottomIndex = Math.floor(Math.random() * this.Bottoms.length);
      this.selectedBottomId = this.Bottoms[randomBottomIndex].prendaId;
    }
  
    if (this.FootWear.length > 0) {
      const randomFootWearIndex = Math.floor(Math.random() * this.FootWear.length);
      this.selectedFootWearId = this.FootWear[randomFootWearIndex].prendaId;
    }
  
    if (this.OuterWear.length > 0) {
      const randomOuterWearIndex = Math.floor(Math.random() * this.OuterWear.length);
      this.selectedOuterWearId = this.OuterWear[randomOuterWearIndex].prendaId;
    }
  
    if (this.Accesories.length > 0) {
      const randomAccesoriesIndex = Math.floor(Math.random() * this.Accesories.length);
      this.selectedAccesoriesId = this.Accesories[randomAccesoriesIndex].prendaId;
    }

    // Optional: Refresh the carousel to reflect the changes
    this.initCarouselEvents();
  }
  
  initCarouselEvents() {
    if (this.carousel3 && this.carousel3.nativeElement) {
      this.initCarouselEventFootWear();
    }
    if (this.carousel1 && this.carousel1.nativeElement) {
      this.initCarouselEventTops();
    }
    if (this.carousel2 && this.carousel2.nativeElement) {
      this.initCarouselEventBottoms();
    }
    if (this.carousel4 && this.carousel4.nativeElement) {
      this.initCarouselEventOuterWear();
    }
    if (this.carousel5 && this.carousel5.nativeElement) {
      this.initCarouselEventAccesories();
    }
  }
  
}
