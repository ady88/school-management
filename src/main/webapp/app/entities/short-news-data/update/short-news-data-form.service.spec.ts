import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../short-news-data.test-samples';

import { ShortNewsDataFormService } from './short-news-data-form.service';

describe('ShortNewsData Form Service', () => {
  let service: ShortNewsDataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShortNewsDataFormService);
  });

  describe('Service methods', () => {
    describe('createShortNewsDataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createShortNewsDataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            linkUrl: expect.any(Object),
            orderNews: expect.any(Object),
          })
        );
      });

      it('passing IShortNewsData should create a new form with FormGroup', () => {
        const formGroup = service.createShortNewsDataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            linkUrl: expect.any(Object),
            orderNews: expect.any(Object),
          })
        );
      });
    });

    describe('getShortNewsData', () => {
      it('should return NewShortNewsData for default ShortNewsData initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createShortNewsDataFormGroup(sampleWithNewData);

        const shortNewsData = service.getShortNewsData(formGroup) as any;

        expect(shortNewsData).toMatchObject(sampleWithNewData);
      });

      it('should return NewShortNewsData for empty ShortNewsData initial value', () => {
        const formGroup = service.createShortNewsDataFormGroup();

        const shortNewsData = service.getShortNewsData(formGroup) as any;

        expect(shortNewsData).toMatchObject({});
      });

      it('should return IShortNewsData', () => {
        const formGroup = service.createShortNewsDataFormGroup(sampleWithRequiredData);

        const shortNewsData = service.getShortNewsData(formGroup) as any;

        expect(shortNewsData).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IShortNewsData should not enable id FormControl', () => {
        const formGroup = service.createShortNewsDataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewShortNewsData should disable id FormControl', () => {
        const formGroup = service.createShortNewsDataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
