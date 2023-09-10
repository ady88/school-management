import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../docs-data.test-samples';

import { DocsDataFormService } from './docs-data-form.service';

describe('DocsData Form Service', () => {
  let service: DocsDataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocsDataFormService);
  });

  describe('Service methods', () => {
    describe('createDocsDataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocsDataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            doc: expect.any(Object),
            orderdoc: expect.any(Object),
            resourcedate: expect.any(Object),
          })
        );
      });

      it('passing IDocsData should create a new form with FormGroup', () => {
        const formGroup = service.createDocsDataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            doc: expect.any(Object),
            orderdoc: expect.any(Object),
            resourcedate: expect.any(Object),
          })
        );
      });
    });

    describe('getDocsData', () => {
      it('should return NewDocsData for default DocsData initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDocsDataFormGroup(sampleWithNewData);

        const docsData = service.getDocsData(formGroup) as any;

        expect(docsData).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocsData for empty DocsData initial value', () => {
        const formGroup = service.createDocsDataFormGroup();

        const docsData = service.getDocsData(formGroup) as any;

        expect(docsData).toMatchObject({});
      });

      it('should return IDocsData', () => {
        const formGroup = service.createDocsDataFormGroup(sampleWithRequiredData);

        const docsData = service.getDocsData(formGroup) as any;

        expect(docsData).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocsData should not enable id FormControl', () => {
        const formGroup = service.createDocsDataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocsData should disable id FormControl', () => {
        const formGroup = service.createDocsDataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
