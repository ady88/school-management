import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGeneral, NewGeneral } from '../general.model';

export type PartialUpdateGeneral = Partial<IGeneral> & Pick<IGeneral, 'id'>;

export type EntityResponseType = HttpResponse<IGeneral>;
export type EntityArrayResponseType = HttpResponse<IGeneral[]>;

@Injectable({ providedIn: 'root' })
export class GeneralService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/generals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(general: NewGeneral): Observable<EntityResponseType> {
    return this.http.post<IGeneral>(this.resourceUrl, general, { observe: 'response' });
  }

  update(general: IGeneral): Observable<EntityResponseType> {
    return this.http.put<IGeneral>(`${this.resourceUrl}/${this.getGeneralIdentifier(general)}`, general, { observe: 'response' });
  }

  partialUpdate(general: PartialUpdateGeneral): Observable<EntityResponseType> {
    return this.http.patch<IGeneral>(`${this.resourceUrl}/${this.getGeneralIdentifier(general)}`, general, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGeneral>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGeneral[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGeneralIdentifier(general: Pick<IGeneral, 'id'>): number {
    return general.id;
  }

  compareGeneral(o1: Pick<IGeneral, 'id'> | null, o2: Pick<IGeneral, 'id'> | null): boolean {
    return o1 && o2 ? this.getGeneralIdentifier(o1) === this.getGeneralIdentifier(o2) : o1 === o2;
  }

  addGeneralToCollectionIfMissing<Type extends Pick<IGeneral, 'id'>>(
    generalCollection: Type[],
    ...generalsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const generals: Type[] = generalsToCheck.filter(isPresent);
    if (generals.length > 0) {
      const generalCollectionIdentifiers = generalCollection.map(generalItem => this.getGeneralIdentifier(generalItem)!);
      const generalsToAdd = generals.filter(generalItem => {
        const generalIdentifier = this.getGeneralIdentifier(generalItem);
        if (generalCollectionIdentifiers.includes(generalIdentifier)) {
          return false;
        }
        generalCollectionIdentifiers.push(generalIdentifier);
        return true;
      });
      return [...generalsToAdd, ...generalCollection];
    }
    return generalCollection;
  }
}
