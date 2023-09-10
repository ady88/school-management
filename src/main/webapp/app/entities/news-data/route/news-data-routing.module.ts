import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NewsDataComponent } from '../list/news-data.component';
import { NewsDataDetailComponent } from '../detail/news-data-detail.component';
import { NewsDataUpdateComponent } from '../update/news-data-update.component';
import { NewsDataRoutingResolveService } from './news-data-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const newsDataRoute: Routes = [
  {
    path: '',
    component: NewsDataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NewsDataDetailComponent,
    resolve: {
      newsData: NewsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NewsDataUpdateComponent,
    resolve: {
      newsData: NewsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NewsDataUpdateComponent,
    resolve: {
      newsData: NewsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(newsDataRoute)],
  exports: [RouterModule],
})
export class NewsDataRoutingModule {}
