import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StaffDataComponent } from './list/staff-data.component';
import { StaffDataDetailComponent } from './detail/staff-data-detail.component';
import { StaffDataUpdateComponent } from './update/staff-data-update.component';
import { StaffDataDeleteDialogComponent } from './delete/staff-data-delete-dialog.component';
import { StaffDataRoutingModule } from './route/staff-data-routing.module';

@NgModule({
  imports: [SharedModule, StaffDataRoutingModule],
  declarations: [StaffDataComponent, StaffDataDetailComponent, StaffDataUpdateComponent, StaffDataDeleteDialogComponent],
})
export class StaffDataModule {}
