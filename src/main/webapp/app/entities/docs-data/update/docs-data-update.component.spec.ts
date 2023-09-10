import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DocsDataFormService } from './docs-data-form.service';
import { DocsDataService } from '../service/docs-data.service';
import { IDocsData } from '../docs-data.model';

import { DocsDataUpdateComponent } from './docs-data-update.component';

describe('DocsData Management Update Component', () => {
  let comp: DocsDataUpdateComponent;
  let fixture: ComponentFixture<DocsDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let docsDataFormService: DocsDataFormService;
  let docsDataService: DocsDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DocsDataUpdateComponent],
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
      .overrideTemplate(DocsDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocsDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    docsDataFormService = TestBed.inject(DocsDataFormService);
    docsDataService = TestBed.inject(DocsDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const docsData: IDocsData = { id: 456 };

      activatedRoute.data = of({ docsData });
      comp.ngOnInit();

      expect(comp.docsData).toEqual(docsData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocsData>>();
      const docsData = { id: 123 };
      jest.spyOn(docsDataFormService, 'getDocsData').mockReturnValue(docsData);
      jest.spyOn(docsDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docsData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: docsData }));
      saveSubject.complete();

      // THEN
      expect(docsDataFormService.getDocsData).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(docsDataService.update).toHaveBeenCalledWith(expect.objectContaining(docsData));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocsData>>();
      const docsData = { id: 123 };
      jest.spyOn(docsDataFormService, 'getDocsData').mockReturnValue({ id: null });
      jest.spyOn(docsDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docsData: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: docsData }));
      saveSubject.complete();

      // THEN
      expect(docsDataFormService.getDocsData).toHaveBeenCalled();
      expect(docsDataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocsData>>();
      const docsData = { id: 123 };
      jest.spyOn(docsDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docsData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(docsDataService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
