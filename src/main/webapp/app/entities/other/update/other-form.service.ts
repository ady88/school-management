import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOther, NewOther } from '../other.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOther for edit and NewOtherFormGroupInput for create.
 */
type OtherFormGroupInput = IOther | PartialWithRequiredKeyOf<NewOther>;

type OtherFormDefaults = Pick<NewOther, 'id' | 'useTopImage' | 'useMainImage'>;

type OtherFormGroupContent = {
  id: FormControl<IOther['id'] | NewOther['id']>;
  useTopImage: FormControl<IOther['useTopImage']>;
  topImage: FormControl<IOther['topImage']>;
  topImageContentType: FormControl<IOther['topImageContentType']>;
  useMainImage: FormControl<IOther['useMainImage']>;
  mainImage: FormControl<IOther['mainImage']>;
  mainImageContentType: FormControl<IOther['mainImageContentType']>;
  theme: FormControl<IOther['theme']>;
};

export type OtherFormGroup = FormGroup<OtherFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OtherFormService {
  createOtherFormGroup(other: OtherFormGroupInput = { id: null }): OtherFormGroup {
    const otherRawValue = {
      ...this.getFormDefaults(),
      ...other,
    };
    return new FormGroup<OtherFormGroupContent>({
      id: new FormControl(
        { value: otherRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      useTopImage: new FormControl(otherRawValue.useTopImage),
      topImage: new FormControl(otherRawValue.topImage),
      topImageContentType: new FormControl(otherRawValue.topImageContentType),
      useMainImage: new FormControl(otherRawValue.useMainImage),
      mainImage: new FormControl(otherRawValue.mainImage),
      mainImageContentType: new FormControl(otherRawValue.mainImageContentType),
      theme: new FormControl(otherRawValue.theme),
    });
  }

  getOther(form: OtherFormGroup): IOther | NewOther {
    return form.getRawValue() as IOther | NewOther;
  }

  resetForm(form: OtherFormGroup, other: OtherFormGroupInput): void {
    const otherRawValue = { ...this.getFormDefaults(), ...other };
    form.reset(
      {
        ...otherRawValue,
        id: { value: otherRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OtherFormDefaults {
    return {
      id: null,
      useTopImage: false,
      useMainImage: false,
    };
  }
}
