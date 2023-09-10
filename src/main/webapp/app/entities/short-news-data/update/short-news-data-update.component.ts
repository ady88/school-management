import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ShortNewsDataFormService, ShortNewsDataFormGroup } from './short-news-data-form.service';
import { IShortNewsData } from '../short-news-data.model';
import { ShortNewsDataService } from '../service/short-news-data.service';

@Component({
  selector: 'jhi-short-news-data-update',
  templateUrl: './short-news-data-update.component.html',
})
export class ShortNewsDataUpdateComponent implements OnInit {
  isSaving = false;
  shortNewsData: IShortNewsData | null = null;

  editForm: ShortNewsDataFormGroup = this.shortNewsDataFormService.createShortNewsDataFormGroup();

  constructor(
    protected shortNewsDataService: ShortNewsDataService,
    protected shortNewsDataFormService: ShortNewsDataFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shortNewsData }) => {
      this.shortNewsData = shortNewsData;
      if (shortNewsData) {
        this.updateForm(shortNewsData);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shortNewsData = this.shortNewsDataFormService.getShortNewsData(this.editForm);
    if (shortNewsData.id !== null) {
      this.subscribeToSaveResponse(this.shortNewsDataService.update(shortNewsData));
    } else {
      this.subscribeToSaveResponse(this.shortNewsDataService.create(shortNewsData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShortNewsData>>): void {
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

  protected updateForm(shortNewsData: IShortNewsData): void {
    this.shortNewsData = shortNewsData;
    this.shortNewsDataFormService.resetForm(this.editForm, shortNewsData);
  }
}
