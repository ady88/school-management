import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MainNewsDataFormService } from './main-news-data-form.service';
import { MainNewsDataService } from '../service/main-news-data.service';
import { IMainNewsData } from '../main-news-data.model';

import { MainNewsDataUpdateComponent } from './main-news-data-update.component';

describe('MainNewsData Management Update Component', () => {
  let comp: MainNewsDataUpdateComponent;
  let fixture: ComponentFixture<MainNewsDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mainNewsDataFormService: MainNewsDataFormService;
  let mainNewsDataService: MainNewsDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MainNewsDataUpdateComponent],
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
      .overrideTemplate(MainNewsDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MainNewsDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mainNewsDataFormService = TestBed.inject(MainNewsDataFormService);
    mainNewsDataService = TestBed.inject(MainNewsDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const mainNewsData: IMainNewsData = { id: 456 };

      activatedRoute.data = of({ mainNewsData });
      comp.ngOnInit();

      expect(comp.mainNewsData).toEqual(mainNewsData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMainNewsData>>();
      const mainNewsData = { id: 123 };
      jest.spyOn(mainNewsDataFormService, 'getMainNewsData').mockReturnValue(mainNewsData);
      jest.spyOn(mainNewsDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mainNewsData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mainNewsData }));
      saveSubject.complete();

      // THEN
      expect(mainNewsDataFormService.getMainNewsData).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mainNewsDataService.update).toHaveBeenCalledWith(expect.objectContaining(mainNewsData));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMainNewsData>>();
      const mainNewsData = { id: 123 };
      jest.spyOn(mainNewsDataFormService, 'getMainNewsData').mockReturnValue({ id: null });
      jest.spyOn(mainNewsDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mainNewsData: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mainNewsData }));
      saveSubject.complete();

      // THEN
      expect(mainNewsDataFormService.getMainNewsData).toHaveBeenCalled();
      expect(mainNewsDataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMainNewsData>>();
      const mainNewsData = { id: 123 };
      jest.spyOn(mainNewsDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mainNewsData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mainNewsDataService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
