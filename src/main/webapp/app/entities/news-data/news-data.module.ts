import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NewsDataComponent } from './list/news-data.component';
import { NewsDataDetailComponent } from './detail/news-data-detail.component';
import { NewsDataUpdateComponent } from './update/news-data-update.component';
import { NewsDataDeleteDialogComponent } from './delete/news-data-delete-dialog.component';
import { NewsDataRoutingModule } from './route/news-data-routing.module';

@NgModule({
  imports: [SharedModule, NewsDataRoutingModule],
  declarations: [NewsDataComponent, NewsDataDetailComponent, NewsDataUpdateComponent, NewsDataDeleteDialogComponent],
})
export class NewsDataModule {}
