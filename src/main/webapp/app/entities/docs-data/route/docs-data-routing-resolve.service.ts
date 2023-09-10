import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocsData } from '../docs-data.model';
import { DocsDataService } from '../service/docs-data.service';

@Injectable({ providedIn: 'root' })
export class DocsDataRoutingResolveService implements Resolve<IDocsData | null> {
  constructor(protected service: DocsDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocsData | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((docsData: HttpResponse<IDocsData>) => {
          if (docsData.body) {
            return of(docsData.body);
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
