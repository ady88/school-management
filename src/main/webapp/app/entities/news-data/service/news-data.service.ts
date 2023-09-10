import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INewsData, NewNewsData } from '../news-data.model';

export type PartialUpdateNewsData = Partial<INewsData> & Pick<INewsData, 'id'>;

export type EntityResponseType = HttpResponse<INewsData>;
export type EntityArrayResponseType = HttpResponse<INewsData[]>;

@Injectable({ providedIn: 'root' })
export class NewsDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/news-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(newsData: NewNewsData): Observable<EntityResponseType> {
    return this.http.post<INewsData>(this.resourceUrl, newsData, { observe: 'response' });
  }

  update(newsData: INewsData): Observable<EntityResponseType> {
    return this.http.put<INewsData>(`${this.resourceUrl}/${this.getNewsDataIdentifier(newsData)}`, newsData, { observe: 'response' });
  }

  partialUpdate(newsData: PartialUpdateNewsData): Observable<EntityResponseType> {
    return this.http.patch<INewsData>(`${this.resourceUrl}/${this.getNewsDataIdentifier(newsData)}`, newsData, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INewsData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INewsData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNewsDataIdentifier(newsData: Pick<INewsData, 'id'>): number {
    return newsData.id;
  }

  compareNewsData(o1: Pick<INewsData, 'id'> | null, o2: Pick<INewsData, 'id'> | null): boolean {
    return o1 && o2 ? this.getNewsDataIdentifier(o1) === this.getNewsDataIdentifier(o2) : o1 === o2;
  }

  addNewsDataToCollectionIfMissing<Type extends Pick<INewsData, 'id'>>(
    newsDataCollection: Type[],
    ...newsDataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const newsData: Type[] = newsDataToCheck.filter(isPresent);
    if (newsData.length > 0) {
      const newsDataCollectionIdentifiers = newsDataCollection.map(newsDataItem => this.getNewsDataIdentifier(newsDataItem)!);
      const newsDataToAdd = newsData.filter(newsDataItem => {
        const newsDataIdentifier = this.getNewsDataIdentifier(newsDataItem);
        if (newsDataCollectionIdentifiers.includes(newsDataIdentifier)) {
          return false;
        }
        newsDataCollectionIdentifiers.push(newsDataIdentifier);
        return true;
      });
      return [...newsDataToAdd, ...newsDataCollection];
    }
    return newsDataCollection;
  }
}
