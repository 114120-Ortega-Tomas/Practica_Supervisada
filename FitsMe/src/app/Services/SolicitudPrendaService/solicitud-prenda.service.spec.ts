import { TestBed } from '@angular/core/testing';

import { SolicitudPrendaService } from './solicitud-prenda.service';

describe('SolicitudPrendaService', () => {
  let service: SolicitudPrendaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SolicitudPrendaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
