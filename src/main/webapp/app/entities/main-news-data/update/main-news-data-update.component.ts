import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MainNewsDataFormService, MainNewsDataFormGroup } from './main-news-data-form.service';
import { IMainNewsData } from '../main-news-data.model';
import { MainNewsDataService } from '../service/main-news-data.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-main-news-data-update',
  templateUrl: './main-news-data-update.component.html',
})
export class MainNewsDataUpdateComponent implements OnInit {
  isSaving = false;
  mainNewsData: IMainNewsData | null = null;

  editForm: MainNewsDataFormGroup = this.mainNewsDataFormService.createMainNewsDataFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected mainNewsDataService: MainNewsDataService,
    protected mainNewsDataFormService: MainNewsDataFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mainNewsData }) => {
      this.mainNewsData = mainNewsData;
      if (mainNewsData) {
        this.updateForm(mainNewsData);
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
    const mainNewsData = this.mainNewsDataFormService.getMainNewsData(this.editForm);
    if (mainNewsData.id !== null) {
      this.subscribeToSaveResponse(this.mainNewsDataService.update(mainNewsData));
    } else {
      this.subscribeToSaveResponse(this.mainNewsDataService.create(mainNewsData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMainNewsData>>): void {
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

  protected updateForm(mainNewsData: IMainNewsData): void {
    this.mainNewsData = mainNewsData;
    this.mainNewsDataFormService.resetForm(this.editForm, mainNewsData);
  }
}
