import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStaffData, NewStaffData } from '../staff-data.model';

export type PartialUpdateStaffData = Partial<IStaffData> & Pick<IStaffData, 'id'>;

export type EntityResponseType = HttpResponse<IStaffData>;
export type EntityArrayResponseType = HttpResponse<IStaffData[]>;

@Injectable({ providedIn: 'root' })
export class StaffDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/staff-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(staffData: NewStaffData): Observable<EntityResponseType> {
    return this.http.post<IStaffData>(this.resourceUrl, staffData, { observe: 'response' });
  }

  update(staffData: IStaffData): Observable<EntityResponseType> {
    return this.http.put<IStaffData>(`${this.resourceUrl}/${this.getStaffDataIdentifier(staffData)}`, staffData, { observe: 'response' });
  }

  partialUpdate(staffData: PartialUpdateStaffData): Observable<EntityResponseType> {
    return this.http.patch<IStaffData>(`${this.resourceUrl}/${this.getStaffDataIdentifier(staffData)}`, staffData, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStaffData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStaffData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStaffDataIdentifier(staffData: Pick<IStaffData, 'id'>): number {
    return staffData.id;
  }

  compareStaffData(o1: Pick<IStaffData, 'id'> | null, o2: Pick<IStaffData, 'id'> | null): boolean {
    return o1 && o2 ? this.getStaffDataIdentifier(o1) === this.getStaffDataIdentifier(o2) : o1 === o2;
  }

  addStaffDataToCollectionIfMissing<Type extends Pick<IStaffData, 'id'>>(
    staffDataCollection: Type[],
    ...staffDataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const staffData: Type[] = staffDataToCheck.filter(isPresent);
    if (staffData.length > 0) {
      const staffDataCollectionIdentifiers = staffDataCollection.map(staffDataItem => this.getStaffDataIdentifier(staffDataItem)!);
      const staffDataToAdd = staffData.filter(staffDataItem => {
        const staffDataIdentifier = this.getStaffDataIdentifier(staffDataItem);
        if (staffDataCollectionIdentifiers.includes(staffDataIdentifier)) {
          return false;
        }
        staffDataCollectionIdentifiers.push(staffDataIdentifier);
        return true;
      });
      return [...staffDataToAdd, ...staffDataCollection];
    }
    return staffDataCollection;
  }
}
