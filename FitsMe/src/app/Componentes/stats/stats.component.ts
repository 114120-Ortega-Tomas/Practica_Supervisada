import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType } from 'chart.js';
import { Outfit, Prenda, User, UsuariosPorMes } from 'src/app/Models/Models';
import { AuxiliarService } from 'src/app/Services/AuxiliarService/auxiliar.service';
import { OutfitService } from 'src/app/Services/OutfitService/outfit.service';
import { PrendasService } from 'src/app/Services/PrendaService/prendas.service';
import { UserService } from 'src/app/Services/UserService/user.service';

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {
  public barChartOptions: ChartOptions = {
    responsive: true,
    // Additional chart configuration
  };
  public barChartLabels: String[] = ['Tops', 'Bottoms', 'FootWear', 'Accesories', 'OuterWear'];
  public barChartLabelsStyles: String[] = [
    'Sportswear', 'Formal', 'Casual', 'Business Casual', 'Streetwear',
    'Bohemian', 'Vintage', 'Gothic', 'Preppy', 'Hiphop',
    'Glamorous', 'Retro', 'Rocker', 'Minimalist', 'Urban'
  ];
  public lineChartOptions: ChartOptions = {
    responsive: true,
  };
  public lineChartLabels: String[] = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
  public lineChartType: ChartType = 'line';
  public lineChartLegend = true;
  public lineChartPlugins = [];
  public barChartType: string = 'pie';
  public barChartLegend: boolean = true;
  public barChartPlugins = [];
  public lineChartData: { data: number[], label: string }[] = [];
  public barChartData: { data: number[], label: string }[] = [];
  public barChartDataStyles: { data: number[], label: string }[] = [];


  constructor(private auxiliarService:AuxiliarService,private userService: UserService, private prendaService: PrendasService, private outfitService: OutfitService) { }
  usuarios: User[] = [];
  prendas: Prenda[] = [];
  usuariosPorMes:UsuariosPorMes[] = [];
  cantidadPrendas: number = 0;
  top5prendas: Prenda[] = [];
  bottom5prendas: Prenda[] = [];
  Sportswear : number = 0;
  Formal: number = 0;
  famousPrendas: Prenda[] = [];
  enero: number = 0;
  febrero: number = 0;
  marzo: number = 0;
  abril: number = 0;
  mayo: number = 0;
  junio: number = 0;
  julio: number = 0;
  agosto: number = 0;
  septiembre: number = 0;
  octubre: number = 0;
  noviembre: number = 0;
  diciembre: number = 0;
  Casual: number = 0;
  BusinessCasual: number = 0;
  userStats = true;
  outfits: Outfit[] = [];
  favOutfits: Outfit[] = [];
  Streetwear: number = 0;
  Bohemian: number = 0;
  prendassss: Prenda[] = [];
  Vintage: number = 0;
  Gothic: number = 0;
  Preppy: number = 0;
  Hiphop: number = 0;
  Glamorous: number = 0;
  Retro: number = 0;
  Rocker: number = 0;
  Minimalist: number = 0;
  Urban: number = 0;
  Outfits: Outfit[] = [];
  user: User = new User();
  Tops: Prenda[] = [];
  Bottoms: Prenda[] = [];
  FootWear: Prenda[] = [];
  Accesories: Prenda[] = [];
  OuterWear: Prenda[] = [];

  ngOnInit(): void {
    this.user = this.userService.user;
    this.getUsuriosPorMes();
    this.getPrendas();
    this.getOutfits();
    this.getAllUser();
    this.getAllPrendas();
    this.getAllOutfits();
    this.getFamousPrendas();
    this.getFavoriteOutfits();
  }

  getPrendas() {
    this.prendaService.getPrendasbyUserId(this.userService.user.id).subscribe((data) => {
      this.prendas = data as Prenda[];
      this.cantidadPrendas = this.prendas.length;
      this.filterPrendas(data);
      this.updateChartData();
    });
  }

  isLeftColumn(tipoPrenda: string): boolean {
    return ['Top', 'Bottom', 'Footwear'].includes(tipoPrenda);
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

  getUsuriosPorMes(){
    this.auxiliarService.getUsuariosPorMes('2024-01-01','2024-12-31').subscribe((data) => {
      this.usuariosPorMes = data as UsuariosPorMes[];
      this.filerUsuariosPorMes(data as UsuariosPorMes[]);
    })
  }

  getOutfits() {
    this.outfitService.getOutfitsByUserId(this.userService.user.id).subscribe((data) => {
      this.Outfits = data as Outfit[];
    });
  }

  getAllUser(){
    this.userService.getAllUsers().subscribe((data) => {
      this.usuarios = data as User[];
  })
}

getAllPrendas(){
  this.prendaService.getAll().subscribe((data) => {
    this.prendassss = data as Prenda[];
    console.log(data);
})
}
getFamousPrendas(){
  this.prendaService.getFamousPrendas().subscribe((data) => {
    this.famousPrendas = data as Prenda[];
  })
}
filerUsuariosPorMes(usuarios: UsuariosPorMes[]) {
  this.usuariosPorMes.forEach(element => {
    switch (element.mes) {
      case 'Enero':
        this.enero = element.amount;
        break;
      case 'Febrero':
        this.febrero = element.amount;
        break;
      case 'Marzo':
        this.marzo = element.amount;
        break;
      case 'Abril':
        this.abril = element.amount;
        break;
      case 'Mayo':
        this.mayo = element.amount;
        break;
      case 'Junio':
        this.junio = element.amount;
        break;
      case 'Julio':
        this.julio = element.amount;
        break;
      case 'Agosto':
        this.agosto = element.amount;
        break;
      case 'Septiembre':
        this.septiembre = element.amount;
        break;
      case 'Octubre':
        this.octubre = element.amount;
        break;
      case 'Noviembre':
        this.noviembre = element.amount;
        break;
      case 'Diciembre':
        this.diciembre = element.amount;
        break;
      default:
        break;
    }
  });
}

  filterPrendas(prendas: Prenda[]) {
    this.Tops = [];
    this.Bottoms = [];
    this.FootWear = [];
    this.Accesories = [];
    this.OuterWear = [];
    prendas.forEach(element => {
      element.estilos.forEach(estilo => {
        switch (estilo.id) {
            case 1:
                this.Sportswear++;
                break;
            case 2:
                this.Formal++;
                break;
            case 3:
                this.Casual++;
                break;
            case 4:
                this.BusinessCasual++;
                break;
            case 5:
                this.Streetwear++;
                break;
            case 6:
                this.Bohemian++;
                break;
            case 7:
                this.Vintage++;
                break;
            case 8:
                this.Gothic++;
                break;
            case 9:
                this.Preppy++;
                break;
            case 10:
                this.Hiphop++;
                break;
            case 11:
                this.Glamorous++;
                break;
            case 12:
                this.Retro++;
                break;
            case 13:
                this.Rocker++;
                break;
            case 14:
                this.Minimalist++;
                break;
            case 15:
                this.Urban++;
                break;
            default:
                console.warn(`Estilo no reconocido: ${estilo}`);
                break;
        }
    });
    
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
    this.findTopUsagesPrendas();
    this.findLeastUsagesPrendas();
  }

  toggleStats(){
    this.userStats = !this.userStats;
    if(this.userStats){
      this.getPrendas();
    } else if(!this.userStats){
      this.filterPrendas(this.prendassss);
    }
    this.updateChartData();
  }
  getAllOutfits(){
    this.outfitService.getOutfits().subscribe((data) => {
      this.outfits = data as Outfit[];
  })
  }

  getFavoriteOutfits(){
    this.outfitService.getFavoriteOutfitsByUserId(this.userService.user.id).subscribe((data) => {
      this.favOutfits = data as Outfit[];
  })
  }
  updateChartData() {
    this.barChartData = [
      {
        data: [
          this.Tops.length,
          this.Bottoms.length,
          this.FootWear.length,
          this.Accesories.length,
          this.OuterWear.length
        ],
        label: 'Items Types'
      }
    ];
    this.barChartDataStyles = [
      {
        data:[
          this.Sportswear,
          this.Formal,
          this.Casual,
          this.BusinessCasual,
          this.Streetwear,
          this.Bohemian,
          this.Vintage,
          this.Gothic,
          this.Preppy,
          this.Hiphop,
          this.Glamorous,
          this.Retro,
          this.Rocker,
          this.Minimalist,
          this.Urban
        ],
        label: 'Styles in Wardrobe'
      }
    ];
    this.lineChartData = [
      {
        data: [
          this.enero,
          this.febrero,
          this.marzo,
          this.abril,
          this.mayo,
          this.junio,
          this.julio,
          this.agosto,
          this.septiembre,
          this.octubre,
          this.noviembre,
          this.diciembre
        ],
        label: 'Styles in Wardrobe'
      }
    ];
  }

  findTopUsagesPrendas() {
    if (this.prendas.length > 0) {
      // Sort prendas by usos in descending order
      const sortedPrendas = this.prendas.sort((a, b) => b.usos - a.usos);
      // Get top 3 prendas
      const top3Prendas = sortedPrendas.slice(0, 5);
      this.top5prendas = top3Prendas;
      console.log('Top 3 Prendas by Usos:', top3Prendas);
      // You can now use top3Prendas for any further operations
    }
  }
  findLeastUsagesPrendas() {
    if (this.prendas.length > 0) {
      const sortedPrendasAsc = [...this.prendas].sort((a, b) => a.usos - b.usos);
      this.bottom5prendas = sortedPrendasAsc.slice(0, 5);
     
    }
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
}
