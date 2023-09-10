import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OtherFormService } from './other-form.service';
import { OtherService } from '../service/other.service';
import { IOther } from '../other.model';

import { OtherUpdateComponent } from './other-update.component';

describe('Other Management Update Component', () => {
  let comp: OtherUpdateComponent;
  let fixture: ComponentFixture<OtherUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let otherFormService: OtherFormService;
  let otherService: OtherService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OtherUpdateComponent],
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
      .overrideTemplate(OtherUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OtherUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    otherFormService = TestBed.inject(OtherFormService);
    otherService = TestBed.inject(OtherService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const other: IOther = { id: 456 };

      activatedRoute.data = of({ other });
      comp.ngOnInit();

      expect(comp.other).toEqual(other);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOther>>();
      const other = { id: 123 };
      jest.spyOn(otherFormService, 'getOther').mockReturnValue(other);
      jest.spyOn(otherService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ other });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: other }));
      saveSubject.complete();

      // THEN
      expect(otherFormService.getOther).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(otherService.update).toHaveBeenCalledWith(expect.objectContaining(other));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOther>>();
      const other = { id: 123 };
      jest.spyOn(otherFormService, 'getOther').mockReturnValue({ id: null });
      jest.spyOn(otherService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ other: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: other }));
      saveSubject.complete();

      // THEN
      expect(otherFormService.getOther).toHaveBeenCalled();
      expect(otherService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOther>>();
      const other = { id: 123 };
      jest.spyOn(otherService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ other });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(otherService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
