import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GeneralDetailComponent } from './general-detail.component';

describe('General Management Detail Component', () => {
  let comp: GeneralDetailComponent;
  let fixture: ComponentFixture<GeneralDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GeneralDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ general: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GeneralDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GeneralDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load general on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.general).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
