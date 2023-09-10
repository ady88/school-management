import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OtherComponent } from '../list/other.component';
import { OtherDetailComponent } from '../detail/other-detail.component';
import { OtherUpdateComponent } from '../update/other-update.component';
import { OtherRoutingResolveService } from './other-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const otherRoute: Routes = [
  {
    path: '',
    component: OtherComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OtherDetailComponent,
    resolve: {
      other: OtherRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OtherUpdateComponent,
    resolve: {
      other: OtherRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OtherUpdateComponent,
    resolve: {
      other: OtherRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(otherRoute)],
  exports: [RouterModule],
})
export class OtherRoutingModule {}
