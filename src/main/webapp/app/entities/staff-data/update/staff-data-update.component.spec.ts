import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StaffDataFormService } from './staff-data-form.service';
import { StaffDataService } from '../service/staff-data.service';
import { IStaffData } from '../staff-data.model';

import { StaffDataUpdateComponent } from './staff-data-update.component';

describe('StaffData Management Update Component', () => {
  let comp: StaffDataUpdateComponent;
  let fixture: ComponentFixture<StaffDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let staffDataFormService: StaffDataFormService;
  let staffDataService: StaffDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StaffDataUpdateComponent],
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
      .overrideTemplate(StaffDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StaffDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    staffDataFormService = TestBed.inject(StaffDataFormService);
    staffDataService = TestBed.inject(StaffDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const staffData: IStaffData = { id: 456 };

      activatedRoute.data = of({ staffData });
      comp.ngOnInit();

      expect(comp.staffData).toEqual(staffData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStaffData>>();
      const staffData = { id: 123 };
      jest.spyOn(staffDataFormService, 'getStaffData').mockReturnValue(staffData);
      jest.spyOn(staffDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ staffData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: staffData }));
      saveSubject.complete();

      // THEN
      expect(staffDataFormService.getStaffData).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(staffDataService.update).toHaveBeenCalledWith(expect.objectContaining(staffData));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStaffData>>();
      const staffData = { id: 123 };
      jest.spyOn(staffDataFormService, 'getStaffData').mockReturnValue({ id: null });
      jest.spyOn(staffDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ staffData: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: staffData }));
      saveSubject.complete();

      // THEN
      expect(staffDataFormService.getStaffData).toHaveBeenCalled();
      expect(staffDataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStaffData>>();
      const staffData = { id: 123 };
      jest.spyOn(staffDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ staffData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(staffDataService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
