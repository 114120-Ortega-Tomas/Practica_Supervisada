import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrincipalAccountComponent } from './principal-account.component';

describe('PrincipalAccountComponent', () => {
  let component: PrincipalAccountComponent;
  let fixture: ComponentFixture<PrincipalAccountComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrincipalAccountComponent]
    });
    fixture = TestBed.createComponent(PrincipalAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
