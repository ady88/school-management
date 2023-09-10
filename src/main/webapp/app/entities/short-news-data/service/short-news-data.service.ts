import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShortNewsData, NewShortNewsData } from '../short-news-data.model';

export type PartialUpdateShortNewsData = Partial<IShortNewsData> & Pick<IShortNewsData, 'id'>;

export type EntityResponseType = HttpResponse<IShortNewsData>;
export type EntityArrayResponseType = HttpResponse<IShortNewsData[]>;

@Injectable({ providedIn: 'root' })
export class ShortNewsDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/short-news-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(shortNewsData: NewShortNewsData): Observable<EntityResponseType> {
    return this.http.post<IShortNewsData>(this.resourceUrl, shortNewsData, { observe: 'response' });
  }

  update(shortNewsData: IShortNewsData): Observable<EntityResponseType> {
    return this.http.put<IShortNewsData>(`${this.resourceUrl}/${this.getShortNewsDataIdentifier(shortNewsData)}`, shortNewsData, {
      observe: 'response',
    });
  }

  partialUpdate(shortNewsData: PartialUpdateShortNewsData): Observable<EntityResponseType> {
    return this.http.patch<IShortNewsData>(`${this.resourceUrl}/${this.getShortNewsDataIdentifier(shortNewsData)}`, shortNewsData, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShortNewsData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShortNewsData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getShortNewsDataIdentifier(shortNewsData: Pick<IShortNewsData, 'id'>): number {
    return shortNewsData.id;
  }

  compareShortNewsData(o1: Pick<IShortNewsData, 'id'> | null, o2: Pick<IShortNewsData, 'id'> | null): boolean {
    return o1 && o2 ? this.getShortNewsDataIdentifier(o1) === this.getShortNewsDataIdentifier(o2) : o1 === o2;
  }

  addShortNewsDataToCollectionIfMissing<Type extends Pick<IShortNewsData, 'id'>>(
    shortNewsDataCollection: Type[],
    ...shortNewsDataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const shortNewsData: Type[] = shortNewsDataToCheck.filter(isPresent);
    if (shortNewsData.length > 0) {
      const shortNewsDataCollectionIdentifiers = shortNewsDataCollection.map(
        shortNewsDataItem => this.getShortNewsDataIdentifier(shortNewsDataItem)!
      );
      const shortNewsDataToAdd = shortNewsData.filter(shortNewsDataItem => {
        const shortNewsDataIdentifier = this.getShortNewsDataIdentifier(shortNewsDataItem);
        if (shortNewsDataCollectionIdentifiers.includes(shortNewsDataIdentifier)) {
          return false;
        }
        shortNewsDataCollectionIdentifiers.push(shortNewsDataIdentifier);
        return true;
      });
      return [...shortNewsDataToAdd, ...shortNewsDataCollection];
    }
    return shortNewsDataCollection;
  }
}
