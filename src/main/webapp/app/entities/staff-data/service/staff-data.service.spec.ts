import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStaffData } from '../staff-data.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../staff-data.test-samples';

import { StaffDataService } from './staff-data.service';

const requireRestSample: IStaffData = {
  ...sampleWithRequiredData,
};

describe('StaffData Service', () => {
  let service: StaffDataService;
  let httpMock: HttpTestingController;
  let expectedResult: IStaffData | IStaffData[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StaffDataService);
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

    it('should create a StaffData', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const staffData = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(staffData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StaffData', () => {
      const staffData = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(staffData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StaffData', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StaffData', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a StaffData', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStaffDataToCollectionIfMissing', () => {
      it('should add a StaffData to an empty array', () => {
        const staffData: IStaffData = sampleWithRequiredData;
        expectedResult = service.addStaffDataToCollectionIfMissing([], staffData);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(staffData);
      });

      it('should not add a StaffData to an array that contains it', () => {
        const staffData: IStaffData = sampleWithRequiredData;
        const staffDataCollection: IStaffData[] = [
          {
            ...staffData,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStaffDataToCollectionIfMissing(staffDataCollection, staffData);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StaffData to an array that doesn't contain it", () => {
        const staffData: IStaffData = sampleWithRequiredData;
        const staffDataCollection: IStaffData[] = [sampleWithPartialData];
        expectedResult = service.addStaffDataToCollectionIfMissing(staffDataCollection, staffData);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(staffData);
      });

      it('should add only unique StaffData to an array', () => {
        const staffDataArray: IStaffData[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const staffDataCollection: IStaffData[] = [sampleWithRequiredData];
        expectedResult = service.addStaffDataToCollectionIfMissing(staffDataCollection, ...staffDataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const staffData: IStaffData = sampleWithRequiredData;
        const staffData2: IStaffData = sampleWithPartialData;
        expectedResult = service.addStaffDataToCollectionIfMissing([], staffData, staffData2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(staffData);
        expect(expectedResult).toContain(staffData2);
      });

      it('should accept null and undefined values', () => {
        const staffData: IStaffData = sampleWithRequiredData;
        expectedResult = service.addStaffDataToCollectionIfMissing([], null, staffData, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(staffData);
      });

      it('should return initial array if no StaffData is added', () => {
        const staffDataCollection: IStaffData[] = [sampleWithRequiredData];
        expectedResult = service.addStaffDataToCollectionIfMissing(staffDataCollection, undefined, null);
        expect(expectedResult).toEqual(staffDataCollection);
      });
    });

    describe('compareStaffData', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStaffData(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStaffData(entity1, entity2);
        const compareResult2 = service.compareStaffData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStaffData(entity1, entity2);
        const compareResult2 = service.compareStaffData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStaffData(entity1, entity2);
        const compareResult2 = service.compareStaffData(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
