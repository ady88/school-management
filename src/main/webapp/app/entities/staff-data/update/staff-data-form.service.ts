import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStaffData, NewStaffData } from '../staff-data.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStaffData for edit and NewStaffDataFormGroupInput for create.
 */
type StaffDataFormGroupInput = IStaffData | PartialWithRequiredKeyOf<NewStaffData>;

type StaffDataFormDefaults = Pick<NewStaffData, 'id'>;

type StaffDataFormGroupContent = {
  id: FormControl<IStaffData['id'] | NewStaffData['id']>;
  orderStaff: FormControl<IStaffData['orderStaff']>;
  lastName: FormControl<IStaffData['lastName']>;
  firstName: FormControl<IStaffData['firstName']>;
  jobType: FormControl<IStaffData['jobType']>;
  unitName: FormControl<IStaffData['unitName']>;
};

export type StaffDataFormGroup = FormGroup<StaffDataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StaffDataFormService {
  createStaffDataFormGroup(staffData: StaffDataFormGroupInput = { id: null }): StaffDataFormGroup {
    const staffDataRawValue = {
      ...this.getFormDefaults(),
      ...staffData,
    };
    return new FormGroup<StaffDataFormGroupContent>({
      id: new FormControl(
        { value: staffDataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      orderStaff: new FormControl(staffDataRawValue.orderStaff, {
        validators: [Validators.required],
      }),
      lastName: new FormControl(staffDataRawValue.lastName, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(staffDataRawValue.firstName, {
        validators: [Validators.required],
      }),
      jobType: new FormControl(staffDataRawValue.jobType, {
        validators: [Validators.required],
      }),
      unitName: new FormControl(staffDataRawValue.unitName, {
        validators: [Validators.required],
      }),
    });
  }

  getStaffData(form: StaffDataFormGroup): IStaffData | NewStaffData {
    return form.getRawValue() as IStaffData | NewStaffData;
  }

  resetForm(form: StaffDataFormGroup, staffData: StaffDataFormGroupInput): void {
    const staffDataRawValue = { ...this.getFormDefaults(), ...staffData };
    form.reset(
      {
        ...staffDataRawValue,
        id: { value: staffDataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StaffDataFormDefaults {
    return {
      id: null,
    };
  }
}
