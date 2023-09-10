import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GeneralComponent } from '../list/general.component';
import { GeneralDetailComponent } from '../detail/general-detail.component';
import { GeneralUpdateComponent } from '../update/general-update.component';
import { GeneralRoutingResolveService } from './general-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const generalRoute: Routes = [
  {
    path: '',
    component: GeneralComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GeneralDetailComponent,
    resolve: {
      general: GeneralRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GeneralUpdateComponent,
    resolve: {
      general: GeneralRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GeneralUpdateComponent,
    resolve: {
      general: GeneralRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(generalRoute)],
  exports: [RouterModule],
})
export class GeneralRoutingModule {}
