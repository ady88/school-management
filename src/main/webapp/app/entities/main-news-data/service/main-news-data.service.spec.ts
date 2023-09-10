import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMainNewsData } from '../main-news-data.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../main-news-data.test-samples';

import { MainNewsDataService } from './main-news-data.service';

const requireRestSample: IMainNewsData = {
  ...sampleWithRequiredData,
};

describe('MainNewsData Service', () => {
  let service: MainNewsDataService;
  let httpMock: HttpTestingController;
  let expectedResult: IMainNewsData | IMainNewsData[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MainNewsDataService);
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

    it('should create a MainNewsData', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const mainNewsData = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mainNewsData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MainNewsData', () => {
      const mainNewsData = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mainNewsData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MainNewsData', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MainNewsData', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MainNewsData', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMainNewsDataToCollectionIfMissing', () => {
      it('should add a MainNewsData to an empty array', () => {
        const mainNewsData: IMainNewsData = sampleWithRequiredData;
        expectedResult = service.addMainNewsDataToCollectionIfMissing([], mainNewsData);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mainNewsData);
      });

      it('should not add a MainNewsData to an array that contains it', () => {
        const mainNewsData: IMainNewsData = sampleWithRequiredData;
        const mainNewsDataCollection: IMainNewsData[] = [
          {
            ...mainNewsData,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMainNewsDataToCollectionIfMissing(mainNewsDataCollection, mainNewsData);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MainNewsData to an array that doesn't contain it", () => {
        const mainNewsData: IMainNewsData = sampleWithRequiredData;
        const mainNewsDataCollection: IMainNewsData[] = [sampleWithPartialData];
        expectedResult = service.addMainNewsDataToCollectionIfMissing(mainNewsDataCollection, mainNewsData);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mainNewsData);
      });

      it('should add only unique MainNewsData to an array', () => {
        const mainNewsDataArray: IMainNewsData[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mainNewsDataCollection: IMainNewsData[] = [sampleWithRequiredData];
        expectedResult = service.addMainNewsDataToCollectionIfMissing(mainNewsDataCollection, ...mainNewsDataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mainNewsData: IMainNewsData = sampleWithRequiredData;
        const mainNewsData2: IMainNewsData = sampleWithPartialData;
        expectedResult = service.addMainNewsDataToCollectionIfMissing([], mainNewsData, mainNewsData2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mainNewsData);
        expect(expectedResult).toContain(mainNewsData2);
      });

      it('should accept null and undefined values', () => {
        const mainNewsData: IMainNewsData = sampleWithRequiredData;
        expectedResult = service.addMainNewsDataToCollectionIfMissing([], null, mainNewsData, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mainNewsData);
      });

      it('should return initial array if no MainNewsData is added', () => {
        const mainNewsDataCollection: IMainNewsData[] = [sampleWithRequiredData];
        expectedResult = service.addMainNewsDataToCollectionIfMissing(mainNewsDataCollection, undefined, null);
        expect(expectedResult).toEqual(mainNewsDataCollection);
      });
    });

    describe('compareMainNewsData', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMainNewsData(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMainNewsData(entity1, entity2);
        const compareResult2 = service.compareMainNewsData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMainNewsData(entity1, entity2);
        const compareResult2 = service.compareMainNewsData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMainNewsData(entity1, entity2);
        const compareResult2 = service.compareMainNewsData(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
