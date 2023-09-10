import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OtherFormService, OtherFormGroup } from './other-form.service';
import { IOther } from '../other.model';
import { OtherService } from '../service/other.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { Theme } from 'app/entities/enumerations/theme.model';

@Component({
  selector: 'jhi-other-update',
  templateUrl: './other-update.component.html',
})
export class OtherUpdateComponent implements OnInit {
  isSaving = false;
  other: IOther | null = null;
  themeValues = Object.keys(Theme);

  editForm: OtherFormGroup = this.otherFormService.createOtherFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected otherService: OtherService,
    protected otherFormService: OtherFormService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ other }) => {
      this.other = other;
      if (other) {
        this.updateForm(other);
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const other = this.otherFormService.getOther(this.editForm);
    if (other.id !== null) {
      this.subscribeToSaveResponse(this.otherService.update(other));
    } else {
      this.subscribeToSaveResponse(this.otherService.create(other));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOther>>): void {
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

  protected updateForm(other: IOther): void {
    this.other = other;
    this.otherFormService.resetForm(this.editForm, other);
  }
}
