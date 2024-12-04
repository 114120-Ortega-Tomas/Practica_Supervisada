import { Component, OnDestroy, OnInit ,OnChanges, SimpleChanges} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { format } from 'date-fns';
import { UserService } from 'src/app/Services/UserService/user.service';
import { startOfWeek, endOfWeek, addDays, addWeeks, subWeeks } from 'date-fns';
import { CalendarService } from 'src/app/Services/CalendarService/calendar.service';
import { EventRequest, Evento, Outfit } from 'src/app/Models/Models';
import { AuxiliarService } from 'src/app/Services/AuxiliarService/auxiliar.service';
import { OutfitService } from 'src/app/Services/OutfitService/outfit.service';
import Swal from 'sweetalert2';
import { FetchBackend } from '@angular/common/http';
@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent  implements OnInit , OnDestroy {
  subcription : Subscription | undefined;
  today : Date = new Date();
  events : Evento[] = [];
  Outfits :Outfit[] = [];
  edition: boolean = false;
  modal1 : boolean = false;
  currentWeek: Date[] = [];
  editionId : number = 0;
  editionHorario:string="";
  currentMonth :String="";
  formEvent! : FormGroup
  OutfitSelected : Outfit = new Outfit();
  currentYear :String="";
  formattedDate = format(new Date(), 'yyyy-MM-dd');
  formattedTime = format(new Date(), 'HH:mm');
  currentDate : Date = new Date();
   constructor(private router:Router,private outfitService:OutfitService,private fb:FormBuilder,private auxiliarService:AuxiliarService , private userService:UserService , private formBuilder:FormBuilder , private calendarService:CalendarService) {
     this.currentWeek = this.getWeek(this.today);
     this.currentMonth = this.currentWeek[0].toLocaleDateString('en-US', { month: 'long' });
     this.currentYear = this.currentWeek[0].toLocaleDateString('en-US', { year: 'numeric' });
     const now = new Date();
      this.formEvent = this.formBuilder.group({
      descripcion: ['', [Validators.required]],
      fecha: [this.formattedDate, [Validators.required]],
      horario: [this.formattedTime, [Validators.required]],
      outfit: ['',Validators.required]  // No necesitas asignar el valor aquí si no quieres sobrescribirlo
    });
    
   }



   selectedItemIndex: number = -1; // Inicialmente no hay ningún ítem seleccionado

   toggleGreenCircle(event: any, index: number,date : Date) {
    this.editionId = 0;
    this.edition = false;
    if (this.selectedItemIndex === index && !this.isToday(this.currentWeek[index])) {
        this.selectedItemIndex = -1; // Deselecciona el ítem si se hace clic en él nuevamente
    } else {
        this.selectedItemIndex = index; // Selecciona el ítem clicado
    }
    this.currentDate = date;
    const formattedDate1 = format(this.currentDate, 'yyyy-MM-dd');
    this.formEvent.patchValue({
      fecha: formattedDate1
    });
    const date1 = new Date(); // Supongamos que esta es tu fecha
    const formattedDate = format(date, 'dd-MM-yyyy'); // Formateamos la fecha utilizando date-fns

    this.calendarService.getEventosByDate(formattedDate).subscribe(
      (data) => {
        this.events = data as any[];
      },
      (error) => {
        this.events =[];
      }
    )
}
selectOutfit(Outfit: Outfit) {
  this.OutfitSelected = Outfit;
  this.formEvent.patchValue({
    outfit: this.OutfitSelected.nombre
  });
}

borrarOutfit() {
  this.OutfitSelected = new Outfit();
  this.formEvent.patchValue({
    outfit: ''
  });
}


  ngOnDestroy(): void {
    this.subcription?.unsubscribe();
  }
  ngOnInit() {
    this.editionId = 0;
    this.edition = false;
    this.subcription = new Subscription();
    this.formEvent.value.fecha = this.currentDate;
    const todayIndex = this.currentWeek.findIndex(item => this.isToday(item));
    if (todayIndex !== -1) {
        this.selectedItemIndex = todayIndex;
    }
    this.events =[];
    this.calendarService.getEventosByDate(format(this.currentDate, 'dd-MM-yyyy')).subscribe(
      (data) => {
        this.events = data as any[];
        console.log(data);
      },
      (error) => {
        console.error(error);
      }
    )
  
    
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
  isToday(item: Date): boolean {
    const today = new Date();
    return item.getDate() === today.getDate() && item.getMonth() === today.getMonth() && item.getFullYear() === today.getFullYear();
}
   
  getWeek(date: Date): Date[] {
    const start = startOfWeek(date, { weekStartsOn: 0 }); // Empieza la semana en domingo
    const week: Date[] = [];
    for (let i = 0; i < 7; i++) {
      week.push(addDays(start, i));
    }
    return week;
  }
  
  advanceWeek() {
    this.today = addWeeks(this.today, 1);
    this.updateCurrentWeek();
  }

  previousWeek() {
    this.today = subWeeks(this.today, 1);
    this.updateCurrentWeek();
  }

  private updateCurrentWeek() {
    this.currentWeek = this.getWeek(this.today);
    this.currentMonth = this.currentWeek[0].toLocaleDateString('en-US', { month: 'long' });
    this.currentYear = this.currentWeek[0].toLocaleDateString('en-US', { year: 'numeric' });
    this.selectedItemIndex = -1; // Resetear la selección al cambiar la semana
  }

  openWardobe(){
    this.outfitService.getOutfitsByUserId(this.userService.user.id).subscribe((data)=>{
      this.Outfits = data as Outfit[];
      this.formEvent.patchValue({
        descripcion: '',
        horario : this.formattedTime
      });
    })

  }
   onSuccess = () => {
    this.auxiliarService.setSelected(0);
    this.calendarService.getEventosByDate(format(this.currentDate, 'dd-MM-yyyy')).subscribe(
      (data) => {
        this.events = data as any[];
      },
      (error) => {
        console.error(error);
      }
    );
  };
  openStyling(){
    this.auxiliarService.setSelected(1);
  }
  saveEvento() {
    if (!this.formEvent.value.descripcion || !this.formEvent.value.outfit) {
      return;
    }
    
    const fecha = new Date(this.formEvent.value.fecha);
    const horario = new Date(`1970-01-01T${this.formEvent.value.horario}:00`);
    
    var evento: EventRequest = new EventRequest();
    evento.descripcion = this.formEvent.value.descripcion;
    evento.fecha = format(fecha, 'yyyy-MM-dd');
    evento.horario = format(horario, 'HH:mm');
    evento.usuarioId = this.userService.user.id;
    evento.outfitId = this.OutfitSelected.id;
  
    
  
    if (this.edition === false) {
      this.calendarService.saveEvento(evento).subscribe((data) => {
        Swal.fire({
          icon: 'success',
          title: 'Event Saved Successfully',
          showConfirmButton: false,
          timer: 1500
        }).then(() => {
          this.onSuccess();
        });
      });
    } else if (this.edition === true) {
      this.calendarService.updateEvento(this.editionId, evento).subscribe((data) => {
        Swal.fire({
          icon: 'success',
          title: 'Event Updated Successfully',
          showConfirmButton: false,
          timer: 1500
        }).then(() => {
          this.onSuccess();
        });
      });
    }
  }
  

  EditEvento(){
    const fecha = new Date(this.formEvent.value.fecha);
    const horario = new Date(`1970-01-01T${this.formEvent.value.horario}:00`);
    
    var evento: EventRequest = new EventRequest();
    evento.descripcion = this.formEvent.value.descripcion;
    evento.fecha = format(fecha, 'yyyy-MM-dd');
    evento.horario = format(horario, 'HH:mm');
    evento.usuarioId = this.userService.user.id;
    evento.outfitId = this.OutfitSelected.id;

    this.calendarService.updateEvento(this.editionId, evento).subscribe((data) => {
        Swal.fire({
            icon: 'success',
            title: 'Event Updated Successfully',
            showConfirmButton: false,
            timer: 1500
        }).then(() => {
          this.onSuccess();
        });
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

  getPrendaClass(descripcion: string): string {
    switch (descripcion) {
      case 'Top':
        return 'prenda-top';
      case 'Bottom':
        return 'prenda-bottom';
      case 'Footwear':
        return 'prenda-footwear';
      case 'OuterWear':
        return 'prenda-outerwear';
      case 'Accesories':
        return 'prenda-accesorios';
      default:
        return '';
    }
  }
  

  getItemClass(description: any): string {
    switch(description.descripcion) {
    case 'Top':
    case 'Bottom':
    case 'Footwear':
      return 'left-column';
    case 'OuterWear':
    case 'Accessories':
      return 'right-column';
    default:
      return '';
    }
  }

  isLeftColumn(tipoPrenda: string): boolean {
    return ['Top', 'Bottom', 'Footwear'].includes(tipoPrenda);
  }
  

  editEvent(item:Evento){
    this.edition = true;
    this.editionHorario = item.horario;
    this.editionId = item.eventoId;
    this.OutfitSelected = item.outfitResponse;
    this.formEvent.patchValue({
      outfit: this.OutfitSelected.nombre,
      fecha: item.fecha,
      horario: this.formattedTime,
      descripcion: item.descripcion
    });
  }

  deleteEvent(item:Evento){
    this.calendarService.deleteEvento(item.eventoId).subscribe((data)=>{
      Swal.fire({
        icon: 'success',
        title: 'Event Deleted Succesfully',
        showConfirmButton: false,
        timer: 1500
      }).then(() => {
        this.ngOnInit();
      });
    })
    
  }

  resetForm(){
    this.formEvent.reset();
    this.edition = false;
    this.editionId = 0;
    this.OutfitSelected = new Outfit();
    this.formEvent.patchValue({
      outfit: this.OutfitSelected.nombre
    });
  }
}
