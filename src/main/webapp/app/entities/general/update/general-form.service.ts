import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGeneral, NewGeneral } from '../general.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGeneral for edit and NewGeneralFormGroupInput for create.
 */
type GeneralFormGroupInput = IGeneral | PartialWithRequiredKeyOf<NewGeneral>;

type GeneralFormDefaults = Pick<NewGeneral, 'id'>;

type GeneralFormGroupContent = {
  id: FormControl<IGeneral['id'] | NewGeneral['id']>;
  siteName: FormControl<IGeneral['siteName']>;
  homePageName: FormControl<IGeneral['homePageName']>;
  resourcesPageName: FormControl<IGeneral['resourcesPageName']>;
  staffPageName: FormControl<IGeneral['staffPageName']>;
  aboutPageName: FormControl<IGeneral['aboutPageName']>;
  facebookAddress: FormControl<IGeneral['facebookAddress']>;
  address: FormControl<IGeneral['address']>;
  phone: FormControl<IGeneral['phone']>;
  email: FormControl<IGeneral['email']>;
  motto: FormControl<IGeneral['motto']>;
  structure1: FormControl<IGeneral['structure1']>;
  structure2: FormControl<IGeneral['structure2']>;
  structure3: FormControl<IGeneral['structure3']>;
  structure4: FormControl<IGeneral['structure4']>;
  contactHeader: FormControl<IGeneral['contactHeader']>;
  structuresHeader: FormControl<IGeneral['structuresHeader']>;
  mapUrl: FormControl<IGeneral['mapUrl']>;
};

export type GeneralFormGroup = FormGroup<GeneralFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GeneralFormService {
  createGeneralFormGroup(general: GeneralFormGroupInput = { id: null }): GeneralFormGroup {
    const generalRawValue = {
      ...this.getFormDefaults(),
      ...general,
    };
    return new FormGroup<GeneralFormGroupContent>({
      id: new FormControl(
        { value: generalRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      siteName: new FormControl(generalRawValue.siteName, {
        validators: [Validators.required],
      }),
      homePageName: new FormControl(generalRawValue.homePageName),
      resourcesPageName: new FormControl(generalRawValue.resourcesPageName),
      staffPageName: new FormControl(generalRawValue.staffPageName),
      aboutPageName: new FormControl(generalRawValue.aboutPageName),
      facebookAddress: new FormControl(generalRawValue.facebookAddress),
      address: new FormControl(generalRawValue.address),
      phone: new FormControl(generalRawValue.phone),
      email: new FormControl(generalRawValue.email),
      motto: new FormControl(generalRawValue.motto),
      structure1: new FormControl(generalRawValue.structure1),
      structure2: new FormControl(generalRawValue.structure2),
      structure3: new FormControl(generalRawValue.structure3),
      structure4: new FormControl(generalRawValue.structure4),
      contactHeader: new FormControl(generalRawValue.contactHeader),
      structuresHeader: new FormControl(generalRawValue.structuresHeader),
      mapUrl: new FormControl(generalRawValue.mapUrl),
    });
  }

  getGeneral(form: GeneralFormGroup): IGeneral | NewGeneral {
    return form.getRawValue() as IGeneral | NewGeneral;
  }

  resetForm(form: GeneralFormGroup, general: GeneralFormGroupInput): void {
    const generalRawValue = { ...this.getFormDefaults(), ...general };
    form.reset(
      {
        ...generalRawValue,
        id: { value: generalRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): GeneralFormDefaults {
    return {
      id: null,
    };
  }
}
