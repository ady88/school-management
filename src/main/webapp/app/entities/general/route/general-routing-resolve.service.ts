import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGeneral } from '../general.model';
import { GeneralService } from '../service/general.service';

@Injectable({ providedIn: 'root' })
export class GeneralRoutingResolveService implements Resolve<IGeneral | null> {
  constructor(protected service: GeneralService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGeneral | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((general: HttpResponse<IGeneral>) => {
          if (general.body) {
            return of(general.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
