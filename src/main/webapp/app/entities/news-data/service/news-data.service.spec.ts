import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INewsData } from '../news-data.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../news-data.test-samples';

import { NewsDataService } from './news-data.service';

const requireRestSample: INewsData = {
  ...sampleWithRequiredData,
};

describe('NewsData Service', () => {
  let service: NewsDataService;
  let httpMock: HttpTestingController;
  let expectedResult: INewsData | INewsData[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NewsDataService);
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

    it('should create a NewsData', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const newsData = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(newsData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NewsData', () => {
      const newsData = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(newsData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NewsData', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NewsData', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NewsData', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNewsDataToCollectionIfMissing', () => {
      it('should add a NewsData to an empty array', () => {
        const newsData: INewsData = sampleWithRequiredData;
        expectedResult = service.addNewsDataToCollectionIfMissing([], newsData);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(newsData);
      });

      it('should not add a NewsData to an array that contains it', () => {
        const newsData: INewsData = sampleWithRequiredData;
        const newsDataCollection: INewsData[] = [
          {
            ...newsData,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNewsDataToCollectionIfMissing(newsDataCollection, newsData);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NewsData to an array that doesn't contain it", () => {
        const newsData: INewsData = sampleWithRequiredData;
        const newsDataCollection: INewsData[] = [sampleWithPartialData];
        expectedResult = service.addNewsDataToCollectionIfMissing(newsDataCollection, newsData);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(newsData);
      });

      it('should add only unique NewsData to an array', () => {
        const newsDataArray: INewsData[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const newsDataCollection: INewsData[] = [sampleWithRequiredData];
        expectedResult = service.addNewsDataToCollectionIfMissing(newsDataCollection, ...newsDataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const newsData: INewsData = sampleWithRequiredData;
        const newsData2: INewsData = sampleWithPartialData;
        expectedResult = service.addNewsDataToCollectionIfMissing([], newsData, newsData2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(newsData);
        expect(expectedResult).toContain(newsData2);
      });

      it('should accept null and undefined values', () => {
        const newsData: INewsData = sampleWithRequiredData;
        expectedResult = service.addNewsDataToCollectionIfMissing([], null, newsData, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(newsData);
      });

      it('should return initial array if no NewsData is added', () => {
        const newsDataCollection: INewsData[] = [sampleWithRequiredData];
        expectedResult = service.addNewsDataToCollectionIfMissing(newsDataCollection, undefined, null);
        expect(expectedResult).toEqual(newsDataCollection);
      });
    });

    describe('compareNewsData', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNewsData(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNewsData(entity1, entity2);
        const compareResult2 = service.compareNewsData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNewsData(entity1, entity2);
        const compareResult2 = service.compareNewsData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNewsData(entity1, entity2);
        const compareResult2 = service.compareNewsData(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
