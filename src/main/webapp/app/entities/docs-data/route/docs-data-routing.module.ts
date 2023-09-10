import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DocsDataComponent } from '../list/docs-data.component';
import { DocsDataDetailComponent } from '../detail/docs-data-detail.component';
import { DocsDataUpdateComponent } from '../update/docs-data-update.component';
import { DocsDataRoutingResolveService } from './docs-data-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const docsDataRoute: Routes = [
  {
    path: '',
    component: DocsDataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocsDataDetailComponent,
    resolve: {
      docsData: DocsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocsDataUpdateComponent,
    resolve: {
      docsData: DocsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocsDataUpdateComponent,
    resolve: {
      docsData: DocsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(docsDataRoute)],
  exports: [RouterModule],
})
export class DocsDataRoutingModule {}
