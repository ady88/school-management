import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOther } from '../other.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../other.test-samples';

import { OtherService } from './other.service';

const requireRestSample: IOther = {
  ...sampleWithRequiredData,
};

describe('Other Service', () => {
  let service: OtherService;
  let httpMock: HttpTestingController;
  let expectedResult: IOther | IOther[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OtherService);
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

    it('should create a Other', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const other = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(other).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Other', () => {
      const other = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(other).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Other', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Other', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Other', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOtherToCollectionIfMissing', () => {
      it('should add a Other to an empty array', () => {
        const other: IOther = sampleWithRequiredData;
        expectedResult = service.addOtherToCollectionIfMissing([], other);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(other);
      });

      it('should not add a Other to an array that contains it', () => {
        const other: IOther = sampleWithRequiredData;
        const otherCollection: IOther[] = [
          {
            ...other,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOtherToCollectionIfMissing(otherCollection, other);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Other to an array that doesn't contain it", () => {
        const other: IOther = sampleWithRequiredData;
        const otherCollection: IOther[] = [sampleWithPartialData];
        expectedResult = service.addOtherToCollectionIfMissing(otherCollection, other);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(other);
      });

      it('should add only unique Other to an array', () => {
        const otherArray: IOther[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const otherCollection: IOther[] = [sampleWithRequiredData];
        expectedResult = service.addOtherToCollectionIfMissing(otherCollection, ...otherArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const other: IOther = sampleWithRequiredData;
        const other2: IOther = sampleWithPartialData;
        expectedResult = service.addOtherToCollectionIfMissing([], other, other2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(other);
        expect(expectedResult).toContain(other2);
      });

      it('should accept null and undefined values', () => {
        const other: IOther = sampleWithRequiredData;
        expectedResult = service.addOtherToCollectionIfMissing([], null, other, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(other);
      });

      it('should return initial array if no Other is added', () => {
        const otherCollection: IOther[] = [sampleWithRequiredData];
        expectedResult = service.addOtherToCollectionIfMissing(otherCollection, undefined, null);
        expect(expectedResult).toEqual(otherCollection);
      });
    });

    describe('compareOther', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOther(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOther(entity1, entity2);
        const compareResult2 = service.compareOther(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOther(entity1, entity2);
        const compareResult2 = service.compareOther(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOther(entity1, entity2);
        const compareResult2 = service.compareOther(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
