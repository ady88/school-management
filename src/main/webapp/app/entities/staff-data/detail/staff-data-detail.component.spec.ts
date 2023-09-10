import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StaffDataDetailComponent } from './staff-data-detail.component';

describe('StaffData Management Detail Component', () => {
  let comp: StaffDataDetailComponent;
  let fixture: ComponentFixture<StaffDataDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StaffDataDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ staffData: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StaffDataDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StaffDataDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load staffData on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.staffData).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
