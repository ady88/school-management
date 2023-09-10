import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INewsData } from '../news-data.model';
import { NewsDataService } from '../service/news-data.service';

@Injectable({ providedIn: 'root' })
export class NewsDataRoutingResolveService implements Resolve<INewsData | null> {
  constructor(protected service: NewsDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INewsData | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((newsData: HttpResponse<INewsData>) => {
          if (newsData.body) {
            return of(newsData.body);
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
