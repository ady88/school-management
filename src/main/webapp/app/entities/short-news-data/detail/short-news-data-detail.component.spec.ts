import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShortNewsDataDetailComponent } from './short-news-data-detail.component';

describe('ShortNewsData Management Detail Component', () => {
  let comp: ShortNewsDataDetailComponent;
  let fixture: ComponentFixture<ShortNewsDataDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShortNewsDataDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ shortNewsData: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ShortNewsDataDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ShortNewsDataDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load shortNewsData on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.shortNewsData).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
