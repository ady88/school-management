import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IShortNewsData, NewShortNewsData } from '../short-news-data.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IShortNewsData for edit and NewShortNewsDataFormGroupInput for create.
 */
type ShortNewsDataFormGroupInput = IShortNewsData | PartialWithRequiredKeyOf<NewShortNewsData>;

type ShortNewsDataFormDefaults = Pick<NewShortNewsData, 'id'>;

type ShortNewsDataFormGroupContent = {
  id: FormControl<IShortNewsData['id'] | NewShortNewsData['id']>;
  title: FormControl<IShortNewsData['title']>;
  linkUrl: FormControl<IShortNewsData['linkUrl']>;
  orderNews: FormControl<IShortNewsData['orderNews']>;
};

export type ShortNewsDataFormGroup = FormGroup<ShortNewsDataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShortNewsDataFormService {
  createShortNewsDataFormGroup(shortNewsData: ShortNewsDataFormGroupInput = { id: null }): ShortNewsDataFormGroup {
    const shortNewsDataRawValue = {
      ...this.getFormDefaults(),
      ...shortNewsData,
    };
    return new FormGroup<ShortNewsDataFormGroupContent>({
      id: new FormControl(
        { value: shortNewsDataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(shortNewsDataRawValue.title, {
        validators: [Validators.required],
      }),
      linkUrl: new FormControl(shortNewsDataRawValue.linkUrl),
      orderNews: new FormControl(shortNewsDataRawValue.orderNews, {
        validators: [Validators.required],
      }),
    });
  }

  getShortNewsData(form: ShortNewsDataFormGroup): IShortNewsData | NewShortNewsData {
    return form.getRawValue() as IShortNewsData | NewShortNewsData;
  }

  resetForm(form: ShortNewsDataFormGroup, shortNewsData: ShortNewsDataFormGroupInput): void {
    const shortNewsDataRawValue = { ...this.getFormDefaults(), ...shortNewsData };
    form.reset(
      {
        ...shortNewsDataRawValue,
        id: { value: shortNewsDataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ShortNewsDataFormDefaults {
    return {
      id: null,
    };
  }
}
