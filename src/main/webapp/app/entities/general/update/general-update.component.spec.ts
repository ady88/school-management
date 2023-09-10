import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GeneralFormService } from './general-form.service';
import { GeneralService } from '../service/general.service';
import { IGeneral } from '../general.model';

import { GeneralUpdateComponent } from './general-update.component';

describe('General Management Update Component', () => {
  let comp: GeneralUpdateComponent;
  let fixture: ComponentFixture<GeneralUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let generalFormService: GeneralFormService;
  let generalService: GeneralService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GeneralUpdateComponent],
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
      .overrideTemplate(GeneralUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GeneralUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    generalFormService = TestBed.inject(GeneralFormService);
    generalService = TestBed.inject(GeneralService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const general: IGeneral = { id: 456 };

      activatedRoute.data = of({ general });
      comp.ngOnInit();

      expect(comp.general).toEqual(general);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGeneral>>();
      const general = { id: 123 };
      jest.spyOn(generalFormService, 'getGeneral').mockReturnValue(general);
      jest.spyOn(generalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ general });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: general }));
      saveSubject.complete();

      // THEN
      expect(generalFormService.getGeneral).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(generalService.update).toHaveBeenCalledWith(expect.objectContaining(general));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGeneral>>();
      const general = { id: 123 };
      jest.spyOn(generalFormService, 'getGeneral').mockReturnValue({ id: null });
      jest.spyOn(generalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ general: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: general }));
      saveSubject.complete();

      // THEN
      expect(generalFormService.getGeneral).toHaveBeenCalled();
      expect(generalService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGeneral>>();
      const general = { id: 123 };
      jest.spyOn(generalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ general });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(generalService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
