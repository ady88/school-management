import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MainNewsDataComponent } from './list/main-news-data.component';
import { MainNewsDataDetailComponent } from './detail/main-news-data-detail.component';
import { MainNewsDataUpdateComponent } from './update/main-news-data-update.component';
import { MainNewsDataDeleteDialogComponent } from './delete/main-news-data-delete-dialog.component';
import { MainNewsDataRoutingModule } from './route/main-news-data-routing.module';

@NgModule({
  imports: [SharedModule, MainNewsDataRoutingModule],
  declarations: [MainNewsDataComponent, MainNewsDataDetailComponent, MainNewsDataUpdateComponent, MainNewsDataDeleteDialogComponent],
})
export class MainNewsDataModule {}
