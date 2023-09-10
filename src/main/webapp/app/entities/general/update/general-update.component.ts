import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { GeneralFormService, GeneralFormGroup } from './general-form.service';
import { IGeneral } from '../general.model';
import { GeneralService } from '../service/general.service';

@Component({
  selector: 'jhi-general-update',
  templateUrl: './general-update.component.html',
})
export class GeneralUpdateComponent implements OnInit {
  isSaving = false;
  general: IGeneral | null = null;

  editForm: GeneralFormGroup = this.generalFormService.createGeneralFormGroup();

  constructor(
    protected generalService: GeneralService,
    protected generalFormService: GeneralFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ general }) => {
      this.general = general;
      if (general) {
        this.updateForm(general);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const general = this.generalFormService.getGeneral(this.editForm);
    if (general.id !== null) {
      this.subscribeToSaveResponse(this.generalService.update(general));
    } else {
      this.subscribeToSaveResponse(this.generalService.create(general));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGeneral>>): void {
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

  protected updateForm(general: IGeneral): void {
    this.general = general;
    this.generalFormService.resetForm(this.editForm, general);
  }
}
