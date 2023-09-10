import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IShortNewsData } from '../short-news-data.model';
import { ShortNewsDataService } from '../service/short-news-data.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './short-news-data-delete-dialog.component.html',
})
export class ShortNewsDataDeleteDialogComponent {
  shortNewsData?: IShortNewsData;

  constructor(protected shortNewsDataService: ShortNewsDataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shortNewsDataService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
