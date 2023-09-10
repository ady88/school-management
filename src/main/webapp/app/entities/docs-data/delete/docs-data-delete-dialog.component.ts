import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocsData } from '../docs-data.model';
import { DocsDataService } from '../service/docs-data.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './docs-data-delete-dialog.component.html',
})
export class DocsDataDeleteDialogComponent {
  docsData?: IDocsData;

  constructor(protected docsDataService: DocsDataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.docsDataService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
