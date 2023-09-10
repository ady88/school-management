import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMainNewsData } from '../main-news-data.model';
import { MainNewsDataService } from '../service/main-news-data.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './main-news-data-delete-dialog.component.html',
})
export class MainNewsDataDeleteDialogComponent {
  mainNewsData?: IMainNewsData;

  constructor(protected mainNewsDataService: MainNewsDataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mainNewsDataService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
