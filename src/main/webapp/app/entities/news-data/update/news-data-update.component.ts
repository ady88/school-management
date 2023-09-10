import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { NewsDataFormService, NewsDataFormGroup } from './news-data-form.service';
import { INewsData } from '../news-data.model';
import { NewsDataService } from '../service/news-data.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-news-data-update',
  templateUrl: './news-data-update.component.html',
})
export class NewsDataUpdateComponent implements OnInit {
  isSaving = false;
  newsData: INewsData | null = null;

  editForm: NewsDataFormGroup = this.newsDataFormService.createNewsDataFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected newsDataService: NewsDataService,
    protected newsDataFormService: NewsDataFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ newsData }) => {
      this.newsData = newsData;
      if (newsData) {
        this.updateForm(newsData);
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
    const newsData = this.newsDataFormService.getNewsData(this.editForm);
    if (newsData.id !== null) {
      this.subscribeToSaveResponse(this.newsDataService.update(newsData));
    } else {
      this.subscribeToSaveResponse(this.newsDataService.create(newsData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INewsData>>): void {
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

  protected updateForm(newsData: INewsData): void {
    this.newsData = newsData;
    this.newsDataFormService.resetForm(this.editForm, newsData);
  }
}
