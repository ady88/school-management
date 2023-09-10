import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOther } from '../other.model';
import { OtherService } from '../service/other.service';

@Injectable({ providedIn: 'root' })
export class OtherRoutingResolveService implements Resolve<IOther | null> {
  constructor(protected service: OtherService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOther | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((other: HttpResponse<IOther>) => {
          if (other.body) {
            return of(other.body);
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
