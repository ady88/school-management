import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGeneral } from '../general.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../general.test-samples';

import { GeneralService } from './general.service';

const requireRestSample: IGeneral = {
  ...sampleWithRequiredData,
};

describe('General Service', () => {
  let service: GeneralService;
  let httpMock: HttpTestingController;
  let expectedResult: IGeneral | IGeneral[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GeneralService);
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

    it('should create a General', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const general = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(general).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a General', () => {
      const general = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(general).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a General', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of General', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a General', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGeneralToCollectionIfMissing', () => {
      it('should add a General to an empty array', () => {
        const general: IGeneral = sampleWithRequiredData;
        expectedResult = service.addGeneralToCollectionIfMissing([], general);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(general);
      });

      it('should not add a General to an array that contains it', () => {
        const general: IGeneral = sampleWithRequiredData;
        const generalCollection: IGeneral[] = [
          {
            ...general,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGeneralToCollectionIfMissing(generalCollection, general);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a General to an array that doesn't contain it", () => {
        const general: IGeneral = sampleWithRequiredData;
        const generalCollection: IGeneral[] = [sampleWithPartialData];
        expectedResult = service.addGeneralToCollectionIfMissing(generalCollection, general);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(general);
      });

      it('should add only unique General to an array', () => {
        const generalArray: IGeneral[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const generalCollection: IGeneral[] = [sampleWithRequiredData];
        expectedResult = service.addGeneralToCollectionIfMissing(generalCollection, ...generalArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const general: IGeneral = sampleWithRequiredData;
        const general2: IGeneral = sampleWithPartialData;
        expectedResult = service.addGeneralToCollectionIfMissing([], general, general2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(general);
        expect(expectedResult).toContain(general2);
      });

      it('should accept null and undefined values', () => {
        const general: IGeneral = sampleWithRequiredData;
        expectedResult = service.addGeneralToCollectionIfMissing([], null, general, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(general);
      });

      it('should return initial array if no General is added', () => {
        const generalCollection: IGeneral[] = [sampleWithRequiredData];
        expectedResult = service.addGeneralToCollectionIfMissing(generalCollection, undefined, null);
        expect(expectedResult).toEqual(generalCollection);
      });
    });

    describe('compareGeneral', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGeneral(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGeneral(entity1, entity2);
        const compareResult2 = service.compareGeneral(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGeneral(entity1, entity2);
        const compareResult2 = service.compareGeneral(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGeneral(entity1, entity2);
        const compareResult2 = service.compareGeneral(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
