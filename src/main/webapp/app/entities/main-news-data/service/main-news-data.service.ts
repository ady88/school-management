import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMainNewsData, NewMainNewsData } from '../main-news-data.model';

export type PartialUpdateMainNewsData = Partial<IMainNewsData> & Pick<IMainNewsData, 'id'>;

export type EntityResponseType = HttpResponse<IMainNewsData>;
export type EntityArrayResponseType = HttpResponse<IMainNewsData[]>;

@Injectable({ providedIn: 'root' })
export class MainNewsDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/main-news-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mainNewsData: NewMainNewsData): Observable<EntityResponseType> {
    return this.http.post<IMainNewsData>(this.resourceUrl, mainNewsData, { observe: 'response' });
  }

  update(mainNewsData: IMainNewsData): Observable<EntityResponseType> {
    return this.http.put<IMainNewsData>(`${this.resourceUrl}/${this.getMainNewsDataIdentifier(mainNewsData)}`, mainNewsData, {
      observe: 'response',
    });
  }

  partialUpdate(mainNewsData: PartialUpdateMainNewsData): Observable<EntityResponseType> {
    return this.http.patch<IMainNewsData>(`${this.resourceUrl}/${this.getMainNewsDataIdentifier(mainNewsData)}`, mainNewsData, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMainNewsData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMainNewsData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMainNewsDataIdentifier(mainNewsData: Pick<IMainNewsData, 'id'>): number {
    return mainNewsData.id;
  }

  compareMainNewsData(o1: Pick<IMainNewsData, 'id'> | null, o2: Pick<IMainNewsData, 'id'> | null): boolean {
    return o1 && o2 ? this.getMainNewsDataIdentifier(o1) === this.getMainNewsDataIdentifier(o2) : o1 === o2;
  }

  addMainNewsDataToCollectionIfMissing<Type extends Pick<IMainNewsData, 'id'>>(
    mainNewsDataCollection: Type[],
    ...mainNewsDataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mainNewsData: Type[] = mainNewsDataToCheck.filter(isPresent);
    if (mainNewsData.length > 0) {
      const mainNewsDataCollectionIdentifiers = mainNewsDataCollection.map(
        mainNewsDataItem => this.getMainNewsDataIdentifier(mainNewsDataItem)!
      );
      const mainNewsDataToAdd = mainNewsData.filter(mainNewsDataItem => {
        const mainNewsDataIdentifier = this.getMainNewsDataIdentifier(mainNewsDataItem);
        if (mainNewsDataCollectionIdentifiers.includes(mainNewsDataIdentifier)) {
          return false;
        }
        mainNewsDataCollectionIdentifiers.push(mainNewsDataIdentifier);
        return true;
      });
      return [...mainNewsDataToAdd, ...mainNewsDataCollection];
    }
    return mainNewsDataCollection;
  }
}
