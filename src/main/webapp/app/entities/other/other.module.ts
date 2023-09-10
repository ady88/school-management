import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OtherComponent } from './list/other.component';
import { OtherDetailComponent } from './detail/other-detail.component';
import { OtherUpdateComponent } from './update/other-update.component';
import { OtherDeleteDialogComponent } from './delete/other-delete-dialog.component';
import { OtherRoutingModule } from './route/other-routing.module';

@NgModule({
  imports: [SharedModule, OtherRoutingModule],
  declarations: [OtherComponent, OtherDetailComponent, OtherUpdateComponent, OtherDeleteDialogComponent],
})
export class OtherModule {}
