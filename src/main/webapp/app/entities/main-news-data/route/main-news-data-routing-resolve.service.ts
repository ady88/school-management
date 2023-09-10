import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMainNewsData } from '../main-news-data.model';
import { MainNewsDataService } from '../service/main-news-data.service';

@Injectable({ providedIn: 'root' })
export class MainNewsDataRoutingResolveService implements Resolve<IMainNewsData | null> {
  constructor(protected service: MainNewsDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMainNewsData | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mainNewsData: HttpResponse<IMainNewsData>) => {
          if (mainNewsData.body) {
            return of(mainNewsData.body);
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
