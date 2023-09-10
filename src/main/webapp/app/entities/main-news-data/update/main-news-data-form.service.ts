import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMainNewsData, NewMainNewsData } from '../main-news-data.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMainNewsData for edit and NewMainNewsDataFormGroupInput for create.
 */
type MainNewsDataFormGroupInput = IMainNewsData | PartialWithRequiredKeyOf<NewMainNewsData>;

type MainNewsDataFormDefaults = Pick<NewMainNewsData, 'id'>;

type MainNewsDataFormGroupContent = {
  id: FormControl<IMainNewsData['id'] | NewMainNewsData['id']>;
  title: FormControl<IMainNewsData['title']>;
  description: FormControl<IMainNewsData['description']>;
  image: FormControl<IMainNewsData['image']>;
  imageContentType: FormControl<IMainNewsData['imageContentType']>;
  orderNews: FormControl<IMainNewsData['orderNews']>;
};

export type MainNewsDataFormGroup = FormGroup<MainNewsDataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MainNewsDataFormService {
  createMainNewsDataFormGroup(mainNewsData: MainNewsDataFormGroupInput = { id: null }): MainNewsDataFormGroup {
    const mainNewsDataRawValue = {
      ...this.getFormDefaults(),
      ...mainNewsData,
    };
    return new FormGroup<MainNewsDataFormGroupContent>({
      id: new FormControl(
        { value: mainNewsDataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(mainNewsDataRawValue.title, {
        validators: [Validators.required],
      }),
      description: new FormControl(mainNewsDataRawValue.description, {
        validators: [Validators.required],
      }),
      image: new FormControl(mainNewsDataRawValue.image, {
        validators: [Validators.required],
      }),
      imageContentType: new FormControl(mainNewsDataRawValue.imageContentType),
      orderNews: new FormControl(mainNewsDataRawValue.orderNews, {
        validators: [Validators.required],
      }),
    });
  }

  getMainNewsData(form: MainNewsDataFormGroup): IMainNewsData | NewMainNewsData {
    return form.getRawValue() as IMainNewsData | NewMainNewsData;
  }

  resetForm(form: MainNewsDataFormGroup, mainNewsData: MainNewsDataFormGroupInput): void {
    const mainNewsDataRawValue = { ...this.getFormDefaults(), ...mainNewsData };
    form.reset(
      {
        ...mainNewsDataRawValue,
        id: { value: mainNewsDataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MainNewsDataFormDefaults {
    return {
      id: null,
    };
  }
}
