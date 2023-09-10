import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DocsDataFormService, DocsDataFormGroup } from './docs-data-form.service';
import { IDocsData } from '../docs-data.model';
import { DocsDataService } from '../service/docs-data.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-docs-data-update',
  templateUrl: './docs-data-update.component.html',
})
export class DocsDataUpdateComponent implements OnInit {
  isSaving = false;
  docsData: IDocsData | null = null;

  editForm: DocsDataFormGroup = this.docsDataFormService.createDocsDataFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected docsDataService: DocsDataService,
    protected docsDataFormService: DocsDataFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ docsData }) => {
      this.docsData = docsData;
      if (docsData) {
        this.updateForm(docsData);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('schoolManagementApp.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const docsData = this.docsDataFormService.getDocsData(this.editForm);
    if (docsData.id !== null) {
      this.subscribeToSaveResponse(this.docsDataService.update(docsData));
    } else {
      this.subscribeToSaveResponse(this.docsDataService.create(docsData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocsData>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(docsData: IDocsData): void {
    this.docsData = docsData;
    this.docsDataFormService.resetForm(this.editForm, docsData);
  }
}
