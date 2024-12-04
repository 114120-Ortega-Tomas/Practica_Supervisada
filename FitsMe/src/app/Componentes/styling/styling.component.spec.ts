  import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StylingComponent } from './styling.component';

describe('StylingComponent', () => {
  let component: StylingComponent;
  let fixture: ComponentFixture<StylingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StylingComponent]
    });
    fixture = TestBed.createComponent(StylingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
