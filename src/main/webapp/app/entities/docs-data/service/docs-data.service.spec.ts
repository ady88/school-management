import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDocsData } from '../docs-data.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../docs-data.test-samples';

import { DocsDataService, RestDocsData } from './docs-data.service';

const requireRestSample: RestDocsData = {
  ...sampleWithRequiredData,
  resourcedate: sampleWithRequiredData.resourcedate?.toJSON(),
};

describe('DocsData Service', () => {
  let service: DocsDataService;
  let httpMock: HttpTestingController;
  let expectedResult: IDocsData | IDocsData[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DocsDataService);
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

    it('should create a DocsData', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const docsData = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(docsData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DocsData', () => {
      const docsData = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(docsData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DocsData', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DocsData', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DocsData', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDocsDataToCollectionIfMissing', () => {
      it('should add a DocsData to an empty array', () => {
        const docsData: IDocsData = sampleWithRequiredData;
        expectedResult = service.addDocsDataToCollectionIfMissing([], docsData);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(docsData);
      });

      it('should not add a DocsData to an array that contains it', () => {
        const docsData: IDocsData = sampleWithRequiredData;
        const docsDataCollection: IDocsData[] = [
          {
            ...docsData,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDocsDataToCollectionIfMissing(docsDataCollection, docsData);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DocsData to an array that doesn't contain it", () => {
        const docsData: IDocsData = sampleWithRequiredData;
        const docsDataCollection: IDocsData[] = [sampleWithPartialData];
        expectedResult = service.addDocsDataToCollectionIfMissing(docsDataCollection, docsData);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(docsData);
      });

      it('should add only unique DocsData to an array', () => {
        const docsDataArray: IDocsData[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const docsDataCollection: IDocsData[] = [sampleWithRequiredData];
        expectedResult = service.addDocsDataToCollectionIfMissing(docsDataCollection, ...docsDataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const docsData: IDocsData = sampleWithRequiredData;
        const docsData2: IDocsData = sampleWithPartialData;
        expectedResult = service.addDocsDataToCollectionIfMissing([], docsData, docsData2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(docsData);
        expect(expectedResult).toContain(docsData2);
      });

      it('should accept null and undefined values', () => {
        const docsData: IDocsData = sampleWithRequiredData;
        expectedResult = service.addDocsDataToCollectionIfMissing([], null, docsData, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(docsData);
      });

      it('should return initial array if no DocsData is added', () => {
        const docsDataCollection: IDocsData[] = [sampleWithRequiredData];
        expectedResult = service.addDocsDataToCollectionIfMissing(docsDataCollection, undefined, null);
        expect(expectedResult).toEqual(docsDataCollection);
      });
    });

    describe('compareDocsData', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDocsData(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDocsData(entity1, entity2);
        const compareResult2 = service.compareDocsData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDocsData(entity1, entity2);
        const compareResult2 = service.compareDocsData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDocsData(entity1, entity2);
        const compareResult2 = service.compareDocsData(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
