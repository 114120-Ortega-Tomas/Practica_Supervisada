import { Component } from '@angular/core';

@Component({
  selector: 'app-how-it-works',
  templateUrl: './how-it-works.component.html',
  styleUrls: ['./how-it-works.component.css']
})
export class HowItWorksComponent {
  activeIndex: number | null = null;

  toggleAccordion(index: number): void {
    this.activeIndex = this.activeIndex === index ? null : index;
    console.log("Current active index:", this.activeIndex);
  }
  

  faqQuestions = [
    {
      title: "How do I add items to my closet?",
      content: "Upload images of your clothes or import from our database to create your digital wardrobe."
    },
    {
      title: "How does the Planner help me get organized?",
      content: "Plan outfits ahead, create packing lists, and simplify your daily routine."
    },
    {
      title: "How can I find my friends?",
      content: "Search for your friends' wardrobes to see their items and expand your collection."
    }
  ];
  
}
