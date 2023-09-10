import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ShortNewsDataFormService } from './short-news-data-form.service';
import { ShortNewsDataService } from '../service/short-news-data.service';
import { IShortNewsData } from '../short-news-data.model';

import { ShortNewsDataUpdateComponent } from './short-news-data-update.component';

describe('ShortNewsData Management Update Component', () => {
  let comp: ShortNewsDataUpdateComponent;
  let fixture: ComponentFixture<ShortNewsDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shortNewsDataFormService: ShortNewsDataFormService;
  let shortNewsDataService: ShortNewsDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ShortNewsDataUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ShortNewsDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShortNewsDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shortNewsDataFormService = TestBed.inject(ShortNewsDataFormService);
    shortNewsDataService = TestBed.inject(ShortNewsDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const shortNewsData: IShortNewsData = { id: 456 };

      activatedRoute.data = of({ shortNewsData });
      comp.ngOnInit();

      expect(comp.shortNewsData).toEqual(shortNewsData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShortNewsData>>();
      const shortNewsData = { id: 123 };
      jest.spyOn(shortNewsDataFormService, 'getShortNewsData').mockReturnValue(shortNewsData);
      jest.spyOn(shortNewsDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shortNewsData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shortNewsData }));
      saveSubject.complete();

      // THEN
      expect(shortNewsDataFormService.getShortNewsData).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(shortNewsDataService.update).toHaveBeenCalledWith(expect.objectContaining(shortNewsData));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShortNewsData>>();
      const shortNewsData = { id: 123 };
      jest.spyOn(shortNewsDataFormService, 'getShortNewsData').mockReturnValue({ id: null });
      jest.spyOn(shortNewsDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shortNewsData: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shortNewsData }));
      saveSubject.complete();

      // THEN
      expect(shortNewsDataFormService.getShortNewsData).toHaveBeenCalled();
      expect(shortNewsDataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShortNewsData>>();
      const shortNewsData = { id: 123 };
      jest.spyOn(shortNewsDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shortNewsData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shortNewsDataService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
