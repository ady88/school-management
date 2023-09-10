import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DocsDataComponent } from './list/docs-data.component';
import { DocsDataDetailComponent } from './detail/docs-data-detail.component';
import { DocsDataUpdateComponent } from './update/docs-data-update.component';
import { DocsDataDeleteDialogComponent } from './delete/docs-data-delete-dialog.component';
import { DocsDataRoutingModule } from './route/docs-data-routing.module';

@NgModule({
  imports: [SharedModule, DocsDataRoutingModule],
  declarations: [DocsDataComponent, DocsDataDetailComponent, DocsDataUpdateComponent, DocsDataDeleteDialogComponent],
})
export class DocsDataModule {}
