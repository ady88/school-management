import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../other.test-samples';

import { OtherFormService } from './other-form.service';

describe('Other Form Service', () => {
  let service: OtherFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OtherFormService);
  });

  describe('Service methods', () => {
    describe('createOtherFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOtherFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            useTopImage: expect.any(Object),
            topImage: expect.any(Object),
            useMainImage: expect.any(Object),
            mainImage: expect.any(Object),
            theme: expect.any(Object),
          })
        );
      });

      it('passing IOther should create a new form with FormGroup', () => {
        const formGroup = service.createOtherFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            useTopImage: expect.any(Object),
            topImage: expect.any(Object),
            useMainImage: expect.any(Object),
            mainImage: expect.any(Object),
            theme: expect.any(Object),
          })
        );
      });
    });

    describe('getOther', () => {
      it('should return NewOther for default Other initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOtherFormGroup(sampleWithNewData);

        const other = service.getOther(formGroup) as any;

        expect(other).toMatchObject(sampleWithNewData);
      });

      it('should return NewOther for empty Other initial value', () => {
        const formGroup = service.createOtherFormGroup();

        const other = service.getOther(formGroup) as any;

        expect(other).toMatchObject({});
      });

      it('should return IOther', () => {
        const formGroup = service.createOtherFormGroup(sampleWithRequiredData);

        const other = service.getOther(formGroup) as any;

        expect(other).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOther should not enable id FormControl', () => {
        const formGroup = service.createOtherFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOther should disable id FormControl', () => {
        const formGroup = service.createOtherFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
