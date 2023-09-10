import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStaffData } from '../staff-data.model';
import { StaffDataService } from '../service/staff-data.service';

@Injectable({ providedIn: 'root' })
export class StaffDataRoutingResolveService implements Resolve<IStaffData | null> {
  constructor(protected service: StaffDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStaffData | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((staffData: HttpResponse<IStaffData>) => {
          if (staffData.body) {
            return of(staffData.body);
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
