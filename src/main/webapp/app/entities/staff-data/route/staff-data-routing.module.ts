import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StaffDataComponent } from '../list/staff-data.component';
import { StaffDataDetailComponent } from '../detail/staff-data-detail.component';
import { StaffDataUpdateComponent } from '../update/staff-data-update.component';
import { StaffDataRoutingResolveService } from './staff-data-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const staffDataRoute: Routes = [
  {
    path: '',
    component: StaffDataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StaffDataDetailComponent,
    resolve: {
      staffData: StaffDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StaffDataUpdateComponent,
    resolve: {
      staffData: StaffDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StaffDataUpdateComponent,
    resolve: {
      staffData: StaffDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(staffDataRoute)],
  exports: [RouterModule],
})
export class StaffDataRoutingModule {}
