import { TestBed } from '@angular/core/testing';

import { PrendasService } from './prendas.service';

describe('PrendasService', () => {
  let service: PrendasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrendasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
