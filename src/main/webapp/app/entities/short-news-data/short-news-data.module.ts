import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ShortNewsDataComponent } from './list/short-news-data.component';
import { ShortNewsDataDetailComponent } from './detail/short-news-data-detail.component';
import { ShortNewsDataUpdateComponent } from './update/short-news-data-update.component';
import { ShortNewsDataDeleteDialogComponent } from './delete/short-news-data-delete-dialog.component';
import { ShortNewsDataRoutingModule } from './route/short-news-data-routing.module';

@NgModule({
  imports: [SharedModule, ShortNewsDataRoutingModule],
  declarations: [ShortNewsDataComponent, ShortNewsDataDetailComponent, ShortNewsDataUpdateComponent, ShortNewsDataDeleteDialogComponent],
})
export class ShortNewsDataModule {}
