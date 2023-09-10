import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NewsDataFormService } from './news-data-form.service';
import { NewsDataService } from '../service/news-data.service';
import { INewsData } from '../news-data.model';

import { NewsDataUpdateComponent } from './news-data-update.component';

describe('NewsData Management Update Component', () => {
  let comp: NewsDataUpdateComponent;
  let fixture: ComponentFixture<NewsDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let newsDataFormService: NewsDataFormService;
  let newsDataService: NewsDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NewsDataUpdateComponent],
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
      .overrideTemplate(NewsDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NewsDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    newsDataFormService = TestBed.inject(NewsDataFormService);
    newsDataService = TestBed.inject(NewsDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const newsData: INewsData = { id: 456 };

      activatedRoute.data = of({ newsData });
      comp.ngOnInit();

      expect(comp.newsData).toEqual(newsData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INewsData>>();
      const newsData = { id: 123 };
      jest.spyOn(newsDataFormService, 'getNewsData').mockReturnValue(newsData);
      jest.spyOn(newsDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ newsData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: newsData }));
      saveSubject.complete();

      // THEN
      expect(newsDataFormService.getNewsData).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(newsDataService.update).toHaveBeenCalledWith(expect.objectContaining(newsData));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INewsData>>();
      const newsData = { id: 123 };
      jest.spyOn(newsDataFormService, 'getNewsData').mockReturnValue({ id: null });
      jest.spyOn(newsDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ newsData: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: newsData }));
      saveSubject.complete();

      // THEN
      expect(newsDataFormService.getNewsData).toHaveBeenCalled();
      expect(newsDataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INewsData>>();
      const newsData = { id: 123 };
      jest.spyOn(newsDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ newsData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(newsDataService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
