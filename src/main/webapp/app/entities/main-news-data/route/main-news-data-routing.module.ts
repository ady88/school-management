import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MainNewsDataComponent } from '../list/main-news-data.component';
import { MainNewsDataDetailComponent } from '../detail/main-news-data-detail.component';
import { MainNewsDataUpdateComponent } from '../update/main-news-data-update.component';
import { MainNewsDataRoutingResolveService } from './main-news-data-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const mainNewsDataRoute: Routes = [
  {
    path: '',
    component: MainNewsDataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MainNewsDataDetailComponent,
    resolve: {
      mainNewsData: MainNewsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MainNewsDataUpdateComponent,
    resolve: {
      mainNewsData: MainNewsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MainNewsDataUpdateComponent,
    resolve: {
      mainNewsData: MainNewsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mainNewsDataRoute)],
  exports: [RouterModule],
})
export class MainNewsDataRoutingModule {}
