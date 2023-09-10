import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GeneralComponent } from './list/general.component';
import { GeneralDetailComponent } from './detail/general-detail.component';
import { GeneralUpdateComponent } from './update/general-update.component';
import { GeneralDeleteDialogComponent } from './delete/general-delete-dialog.component';
import { GeneralRoutingModule } from './route/general-routing.module';

@NgModule({
  imports: [SharedModule, GeneralRoutingModule],
  declarations: [GeneralComponent, GeneralDetailComponent, GeneralUpdateComponent, GeneralDeleteDialogComponent],
})
export class GeneralModule {}
