import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IShortNewsData } from '../short-news-data.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../short-news-data.test-samples';

import { ShortNewsDataService } from './short-news-data.service';

const requireRestSample: IShortNewsData = {
  ...sampleWithRequiredData,
};

describe('ShortNewsData Service', () => {
  let service: ShortNewsDataService;
  let httpMock: HttpTestingController;
  let expectedResult: IShortNewsData | IShortNewsData[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ShortNewsDataService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ShortNewsData', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const shortNewsData = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(shortNewsData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ShortNewsData', () => {
      const shortNewsData = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(shortNewsData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ShortNewsData', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ShortNewsData', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ShortNewsData', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addShortNewsDataToCollectionIfMissing', () => {
      it('should add a ShortNewsData to an empty array', () => {
        const shortNewsData: IShortNewsData = sampleWithRequiredData;
        expectedResult = service.addShortNewsDataToCollectionIfMissing([], shortNewsData);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shortNewsData);
      });

      it('should not add a ShortNewsData to an array that contains it', () => {
        const shortNewsData: IShortNewsData = sampleWithRequiredData;
        const shortNewsDataCollection: IShortNewsData[] = [
          {
            ...shortNewsData,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addShortNewsDataToCollectionIfMissing(shortNewsDataCollection, shortNewsData);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ShortNewsData to an array that doesn't contain it", () => {
        const shortNewsData: IShortNewsData = sampleWithRequiredData;
        const shortNewsDataCollection: IShortNewsData[] = [sampleWithPartialData];
        expectedResult = service.addShortNewsDataToCollectionIfMissing(shortNewsDataCollection, shortNewsData);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shortNewsData);
      });

      it('should add only unique ShortNewsData to an array', () => {
        const shortNewsDataArray: IShortNewsData[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const shortNewsDataCollection: IShortNewsData[] = [sampleWithRequiredData];
        expectedResult = service.addShortNewsDataToCollectionIfMissing(shortNewsDataCollection, ...shortNewsDataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const shortNewsData: IShortNewsData = sampleWithRequiredData;
        const shortNewsData2: IShortNewsData = sampleWithPartialData;
        expectedResult = service.addShortNewsDataToCollectionIfMissing([], shortNewsData, shortNewsData2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shortNewsData);
        expect(expectedResult).toContain(shortNewsData2);
      });

      it('should accept null and undefined values', () => {
        const shortNewsData: IShortNewsData = sampleWithRequiredData;
        expectedResult = service.addShortNewsDataToCollectionIfMissing([], null, shortNewsData, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shortNewsData);
      });

      it('should return initial array if no ShortNewsData is added', () => {
        const shortNewsDataCollection: IShortNewsData[] = [sampleWithRequiredData];
        expectedResult = service.addShortNewsDataToCollectionIfMissing(shortNewsDataCollection, undefined, null);
        expect(expectedResult).toEqual(shortNewsDataCollection);
      });
    });

    describe('compareShortNewsData', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareShortNewsData(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareShortNewsData(entity1, entity2);
        const compareResult2 = service.compareShortNewsData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareShortNewsData(entity1, entity2);
        const compareResult2 = service.compareShortNewsData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareShortNewsData(entity1, entity2);
        const compareResult2 = service.compareShortNewsData(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
