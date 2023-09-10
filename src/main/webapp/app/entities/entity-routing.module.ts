import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'general',
        data: { pageTitle: 'schoolManagementApp.general.home.title' },
        loadChildren: () => import('./general/general.module').then(m => m.GeneralModule),
      },
      {
        path: 'staff-data',
        data: { pageTitle: 'schoolManagementApp.staffData.home.title' },
        loadChildren: () => import('./staff-data/staff-data.module').then(m => m.StaffDataModule),
      },
      {
        path: 'news-data',
        data: { pageTitle: 'schoolManagementApp.newsData.home.title' },
        loadChildren: () => import('./news-data/news-data.module').then(m => m.NewsDataModule),
      },
      {
        path: 'short-news-data',
        data: { pageTitle: 'schoolManagementApp.shortNewsData.home.title' },
        loadChildren: () => import('./short-news-data/short-news-data.module').then(m => m.ShortNewsDataModule),
      },
      {
        path: 'main-news-data',
        data: { pageTitle: 'schoolManagementApp.mainNewsData.home.title' },
        loadChildren: () => import('./main-news-data/main-news-data.module').then(m => m.MainNewsDataModule),
      },
      {
        path: 'docs-data',
        data: { pageTitle: 'schoolManagementApp.docsData.home.title' },
        loadChildren: () => import('./docs-data/docs-data.module').then(m => m.DocsDataModule),
      },
      {
        path: 'other',
        data: { pageTitle: 'schoolManagementApp.other.home.title' },
        loadChildren: () => import('./other/other.module').then(m => m.OtherModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
