import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShortNewsData } from '../short-news-data.model';
import { ShortNewsDataService } from '../service/short-news-data.service';

@Injectable({ providedIn: 'root' })
export class ShortNewsDataRoutingResolveService implements Resolve<IShortNewsData | null> {
  constructor(protected service: ShortNewsDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShortNewsData | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((shortNewsData: HttpResponse<IShortNewsData>) => {
          if (shortNewsData.body) {
            return of(shortNewsData.body);
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
