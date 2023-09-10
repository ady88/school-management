import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { StaffDataFormService, StaffDataFormGroup } from './staff-data-form.service';
import { IStaffData } from '../staff-data.model';
import { StaffDataService } from '../service/staff-data.service';

@Component({
  selector: 'jhi-staff-data-update',
  templateUrl: './staff-data-update.component.html',
})
export class StaffDataUpdateComponent implements OnInit {
  isSaving = false;
  staffData: IStaffData | null = null;

  editForm: StaffDataFormGroup = this.staffDataFormService.createStaffDataFormGroup();

  constructor(
    protected staffDataService: StaffDataService,
    protected staffDataFormService: StaffDataFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ staffData }) => {
      this.staffData = staffData;
      if (staffData) {
        this.updateForm(staffData);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const staffData = this.staffDataFormService.getStaffData(this.editForm);
    if (staffData.id !== null) {
      this.subscribeToSaveResponse(this.staffDataService.update(staffData));
    } else {
      this.subscribeToSaveResponse(this.staffDataService.create(staffData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStaffData>>): void {
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

  protected updateForm(staffData: IStaffData): void {
    this.staffData = staffData;
    this.staffDataFormService.resetForm(this.editForm, staffData);
  }
}
