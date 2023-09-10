import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INewsData, NewNewsData } from '../news-data.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INewsData for edit and NewNewsDataFormGroupInput for create.
 */
type NewsDataFormGroupInput = INewsData | PartialWithRequiredKeyOf<NewNewsData>;

type NewsDataFormDefaults = Pick<NewNewsData, 'id'>;

type NewsDataFormGroupContent = {
  id: FormControl<INewsData['id'] | NewNewsData['id']>;
  title: FormControl<INewsData['title']>;
  description: FormControl<INewsData['description']>;
  image: FormControl<INewsData['image']>;
  imageContentType: FormControl<INewsData['imageContentType']>;
  linkLabel: FormControl<INewsData['linkLabel']>;
  linkUrl: FormControl<INewsData['linkUrl']>;
  orderNews: FormControl<INewsData['orderNews']>;
};

export type NewsDataFormGroup = FormGroup<NewsDataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NewsDataFormService {
  createNewsDataFormGroup(newsData: NewsDataFormGroupInput = { id: null }): NewsDataFormGroup {
    const newsDataRawValue = {
      ...this.getFormDefaults(),
      ...newsData,
    };
    return new FormGroup<NewsDataFormGroupContent>({
      id: new FormControl(
        { value: newsDataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(newsDataRawValue.title, {
        validators: [Validators.required],
      }),
      description: new FormControl(newsDataRawValue.description, {
        validators: [Validators.required],
      }),
      image: new FormControl(newsDataRawValue.image),
      imageContentType: new FormControl(newsDataRawValue.imageContentType),
      linkLabel: new FormControl(newsDataRawValue.linkLabel),
      linkUrl: new FormControl(newsDataRawValue.linkUrl),
      orderNews: new FormControl(newsDataRawValue.orderNews, {
        validators: [Validators.required],
      }),
    });
  }

  getNewsData(form: NewsDataFormGroup): INewsData | NewNewsData {
    return form.getRawValue() as INewsData | NewNewsData;
  }

  resetForm(form: NewsDataFormGroup, newsData: NewsDataFormGroupInput): void {
    const newsDataRawValue = { ...this.getFormDefaults(), ...newsData };
    form.reset(
      {
        ...newsDataRawValue,
        id: { value: newsDataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NewsDataFormDefaults {
    return {
      id: null,
    };
  }
}
