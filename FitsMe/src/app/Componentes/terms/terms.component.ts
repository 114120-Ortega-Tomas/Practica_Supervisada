import { Component } from '@angular/core';

@Component({
  selector: 'app-terms',
  templateUrl: './terms.component.html',
  styleUrls: ['./terms.component.css']
})
export class TermsComponent {
constructor() {
  
}
showSpanish :boolean = false;

toggleLanguage(){
  this.showSpanish = !this.showSpanish;
}
}
