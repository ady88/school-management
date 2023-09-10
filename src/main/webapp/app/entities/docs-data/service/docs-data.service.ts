import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocsData, NewDocsData } from '../docs-data.model';

export type PartialUpdateDocsData = Partial<IDocsData> & Pick<IDocsData, 'id'>;

type RestOf<T extends IDocsData | NewDocsData> = Omit<T, 'resourcedate'> & {
  resourcedate?: string | null;
};

export type RestDocsData = RestOf<IDocsData>;

export type NewRestDocsData = RestOf<NewDocsData>;

export type PartialUpdateRestDocsData = RestOf<PartialUpdateDocsData>;

export type EntityResponseType = HttpResponse<IDocsData>;
export type EntityArrayResponseType = HttpResponse<IDocsData[]>;

@Injectable({ providedIn: 'root' })
export class DocsDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/docs-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(docsData: NewDocsData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docsData);
    return this.http
      .post<RestDocsData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(docsData: IDocsData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docsData);
    return this.http
      .put<RestDocsData>(`${this.resourceUrl}/${this.getDocsDataIdentifier(docsData)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(docsData: PartialUpdateDocsData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docsData);
    return this.http
      .patch<RestDocsData>(`${this.resourceUrl}/${this.getDocsDataIdentifier(docsData)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDocsData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDocsData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDocsDataIdentifier(docsData: Pick<IDocsData, 'id'>): number {
    return docsData.id;
  }

  compareDocsData(o1: Pick<IDocsData, 'id'> | null, o2: Pick<IDocsData, 'id'> | null): boolean {
    return o1 && o2 ? this.getDocsDataIdentifier(o1) === this.getDocsDataIdentifier(o2) : o1 === o2;
  }

  addDocsDataToCollectionIfMissing<Type extends Pick<IDocsData, 'id'>>(
    docsDataCollection: Type[],
    ...docsDataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const docsData: Type[] = docsDataToCheck.filter(isPresent);
    if (docsData.length > 0) {
      const docsDataCollectionIdentifiers = docsDataCollection.map(docsDataItem => this.getDocsDataIdentifier(docsDataItem)!);
      const docsDataToAdd = docsData.filter(docsDataItem => {
        const docsDataIdentifier = this.getDocsDataIdentifier(docsDataItem);
        if (docsDataCollectionIdentifiers.includes(docsDataIdentifier)) {
          return false;
        }
        docsDataCollectionIdentifiers.push(docsDataIdentifier);
        return true;
      });
      return [...docsDataToAdd, ...docsDataCollection];
    }
    return docsDataCollection;
  }

  protected convertDateFromClient<T extends IDocsData | NewDocsData | PartialUpdateDocsData>(docsData: T): RestOf<T> {
    return {
      ...docsData,
      resourcedate: docsData.resourcedate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDocsData: RestDocsData): IDocsData {
    return {
      ...restDocsData,
      resourcedate: restDocsData.resourcedate ? dayjs(restDocsData.resourcedate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDocsData>): HttpResponse<IDocsData> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDocsData[]>): HttpResponse<IDocsData[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
