import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../staff-data.test-samples';

import { StaffDataFormService } from './staff-data-form.service';

describe('StaffData Form Service', () => {
  let service: StaffDataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StaffDataFormService);
  });

  describe('Service methods', () => {
    describe('createStaffDataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStaffDataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orderStaff: expect.any(Object),
            lastName: expect.any(Object),
            firstName: expect.any(Object),
            jobType: expect.any(Object),
            unitName: expect.any(Object),
          })
        );
      });

      it('passing IStaffData should create a new form with FormGroup', () => {
        const formGroup = service.createStaffDataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orderStaff: expect.any(Object),
            lastName: expect.any(Object),
            firstName: expect.any(Object),
            jobType: expect.any(Object),
            unitName: expect.any(Object),
          })
        );
      });
    });

    describe('getStaffData', () => {
      it('should return NewStaffData for default StaffData initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createStaffDataFormGroup(sampleWithNewData);

        const staffData = service.getStaffData(formGroup) as any;

        expect(staffData).toMatchObject(sampleWithNewData);
      });

      it('should return NewStaffData for empty StaffData initial value', () => {
        const formGroup = service.createStaffDataFormGroup();

        const staffData = service.getStaffData(formGroup) as any;

        expect(staffData).toMatchObject({});
      });

      it('should return IStaffData', () => {
        const formGroup = service.createStaffDataFormGroup(sampleWithRequiredData);

        const staffData = service.getStaffData(formGroup) as any;

        expect(staffData).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStaffData should not enable id FormControl', () => {
        const formGroup = service.createStaffDataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStaffData should disable id FormControl', () => {
        const formGroup = service.createStaffDataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
