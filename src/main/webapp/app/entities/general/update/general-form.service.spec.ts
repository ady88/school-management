import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../general.test-samples';

import { GeneralFormService } from './general-form.service';

describe('General Form Service', () => {
  let service: GeneralFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GeneralFormService);
  });

  describe('Service methods', () => {
    describe('createGeneralFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGeneralFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            siteName: expect.any(Object),
            homePageName: expect.any(Object),
            resourcesPageName: expect.any(Object),
            staffPageName: expect.any(Object),
            aboutPageName: expect.any(Object),
            facebookAddress: expect.any(Object),
            address: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            motto: expect.any(Object),
            structure1: expect.any(Object),
            structure2: expect.any(Object),
            structure3: expect.any(Object),
            structure4: expect.any(Object),
            contactHeader: expect.any(Object),
            structuresHeader: expect.any(Object),
            mapUrl: expect.any(Object),
          })
        );
      });

      it('passing IGeneral should create a new form with FormGroup', () => {
        const formGroup = service.createGeneralFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            siteName: expect.any(Object),
            homePageName: expect.any(Object),
            resourcesPageName: expect.any(Object),
            staffPageName: expect.any(Object),
            aboutPageName: expect.any(Object),
            facebookAddress: expect.any(Object),
            address: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            motto: expect.any(Object),
            structure1: expect.any(Object),
            structure2: expect.any(Object),
            structure3: expect.any(Object),
            structure4: expect.any(Object),
            contactHeader: expect.any(Object),
            structuresHeader: expect.any(Object),
            mapUrl: expect.any(Object),
          })
        );
      });
    });

    describe('getGeneral', () => {
      it('should return NewGeneral for default General initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createGeneralFormGroup(sampleWithNewData);

        const general = service.getGeneral(formGroup) as any;

        expect(general).toMatchObject(sampleWithNewData);
      });

      it('should return NewGeneral for empty General initial value', () => {
        const formGroup = service.createGeneralFormGroup();

        const general = service.getGeneral(formGroup) as any;

        expect(general).toMatchObject({});
      });

      it('should return IGeneral', () => {
        const formGroup = service.createGeneralFormGroup(sampleWithRequiredData);

        const general = service.getGeneral(formGroup) as any;

        expect(general).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGeneral should not enable id FormControl', () => {
        const formGroup = service.createGeneralFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGeneral should disable id FormControl', () => {
        const formGroup = service.createGeneralFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
