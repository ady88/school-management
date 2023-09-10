import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../main-news-data.test-samples';

import { MainNewsDataFormService } from './main-news-data-form.service';

describe('MainNewsData Form Service', () => {
  let service: MainNewsDataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MainNewsDataFormService);
  });

  describe('Service methods', () => {
    describe('createMainNewsDataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMainNewsDataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            description: expect.any(Object),
            image: expect.any(Object),
            orderNews: expect.any(Object),
          })
        );
      });

      it('passing IMainNewsData should create a new form with FormGroup', () => {
        const formGroup = service.createMainNewsDataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            description: expect.any(Object),
            image: expect.any(Object),
            orderNews: expect.any(Object),
          })
        );
      });
    });

    describe('getMainNewsData', () => {
      it('should return NewMainNewsData for default MainNewsData initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMainNewsDataFormGroup(sampleWithNewData);

        const mainNewsData = service.getMainNewsData(formGroup) as any;

        expect(mainNewsData).toMatchObject(sampleWithNewData);
      });

      it('should return NewMainNewsData for empty MainNewsData initial value', () => {
        const formGroup = service.createMainNewsDataFormGroup();

        const mainNewsData = service.getMainNewsData(formGroup) as any;

        expect(mainNewsData).toMatchObject({});
      });

      it('should return IMainNewsData', () => {
        const formGroup = service.createMainNewsDataFormGroup(sampleWithRequiredData);

        const mainNewsData = service.getMainNewsData(formGroup) as any;

        expect(mainNewsData).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMainNewsData should not enable id FormControl', () => {
        const formGroup = service.createMainNewsDataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMainNewsData should disable id FormControl', () => {
        const formGroup = service.createMainNewsDataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
