import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../news-data.test-samples';

import { NewsDataFormService } from './news-data-form.service';

describe('NewsData Form Service', () => {
  let service: NewsDataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NewsDataFormService);
  });

  describe('Service methods', () => {
    describe('createNewsDataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNewsDataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            description: expect.any(Object),
            image: expect.any(Object),
            linkLabel: expect.any(Object),
            linkUrl: expect.any(Object),
            orderNews: expect.any(Object),
          })
        );
      });

      it('passing INewsData should create a new form with FormGroup', () => {
        const formGroup = service.createNewsDataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            description: expect.any(Object),
            image: expect.any(Object),
            linkLabel: expect.any(Object),
            linkUrl: expect.any(Object),
            orderNews: expect.any(Object),
          })
        );
      });
    });

    describe('getNewsData', () => {
      it('should return NewNewsData for default NewsData initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNewsDataFormGroup(sampleWithNewData);

        const newsData = service.getNewsData(formGroup) as any;

        expect(newsData).toMatchObject(sampleWithNewData);
      });

      it('should return NewNewsData for empty NewsData initial value', () => {
        const formGroup = service.createNewsDataFormGroup();

        const newsData = service.getNewsData(formGroup) as any;

        expect(newsData).toMatchObject({});
      });

      it('should return INewsData', () => {
        const formGroup = service.createNewsDataFormGroup(sampleWithRequiredData);

        const newsData = service.getNewsData(formGroup) as any;

        expect(newsData).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INewsData should not enable id FormControl', () => {
        const formGroup = service.createNewsDataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNewsData should disable id FormControl', () => {
        const formGroup = service.createNewsDataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
