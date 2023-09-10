import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOther, NewOther } from '../other.model';

export type PartialUpdateOther = Partial<IOther> & Pick<IOther, 'id'>;

export type EntityResponseType = HttpResponse<IOther>;
export type EntityArrayResponseType = HttpResponse<IOther[]>;

@Injectable({ providedIn: 'root' })
export class OtherService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/others');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(other: NewOther): Observable<EntityResponseType> {
    return this.http.post<IOther>(this.resourceUrl, other, { observe: 'response' });
  }

  update(other: IOther): Observable<EntityResponseType> {
    return this.http.put<IOther>(`${this.resourceUrl}/${this.getOtherIdentifier(other)}`, other, { observe: 'response' });
  }

  partialUpdate(other: PartialUpdateOther): Observable<EntityResponseType> {
    return this.http.patch<IOther>(`${this.resourceUrl}/${this.getOtherIdentifier(other)}`, other, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOther>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOther[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOtherIdentifier(other: Pick<IOther, 'id'>): number {
    return other.id;
  }

  compareOther(o1: Pick<IOther, 'id'> | null, o2: Pick<IOther, 'id'> | null): boolean {
    return o1 && o2 ? this.getOtherIdentifier(o1) === this.getOtherIdentifier(o2) : o1 === o2;
  }

  addOtherToCollectionIfMissing<Type extends Pick<IOther, 'id'>>(
    otherCollection: Type[],
    ...othersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const others: Type[] = othersToCheck.filter(isPresent);
    if (others.length > 0) {
      const otherCollectionIdentifiers = otherCollection.map(otherItem => this.getOtherIdentifier(otherItem)!);
      const othersToAdd = others.filter(otherItem => {
        const otherIdentifier = this.getOtherIdentifier(otherItem);
        if (otherCollectionIdentifiers.includes(otherIdentifier)) {
          return false;
        }
        otherCollectionIdentifiers.push(otherIdentifier);
        return true;
      });
      return [...othersToAdd, ...otherCollection];
    }
    return otherCollection;
  }
}
