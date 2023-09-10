import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShortNewsDataComponent } from '../list/short-news-data.component';
import { ShortNewsDataDetailComponent } from '../detail/short-news-data-detail.component';
import { ShortNewsDataUpdateComponent } from '../update/short-news-data-update.component';
import { ShortNewsDataRoutingResolveService } from './short-news-data-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const shortNewsDataRoute: Routes = [
  {
    path: '',
    component: ShortNewsDataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShortNewsDataDetailComponent,
    resolve: {
      shortNewsData: ShortNewsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShortNewsDataUpdateComponent,
    resolve: {
      shortNewsData: ShortNewsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShortNewsDataUpdateComponent,
    resolve: {
      shortNewsData: ShortNewsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(shortNewsDataRoute)],
  exports: [RouterModule],
})
export class ShortNewsDataRoutingModule {}
