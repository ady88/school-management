import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDocsData, NewDocsData } from '../docs-data.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocsData for edit and NewDocsDataFormGroupInput for create.
 */
type DocsDataFormGroupInput = IDocsData | PartialWithRequiredKeyOf<NewDocsData>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDocsData | NewDocsData> = Omit<T, 'resourcedate'> & {
  resourcedate?: string | null;
};

type DocsDataFormRawValue = FormValueOf<IDocsData>;

type NewDocsDataFormRawValue = FormValueOf<NewDocsData>;

type DocsDataFormDefaults = Pick<NewDocsData, 'id' | 'resourcedate'>;

type DocsDataFormGroupContent = {
  id: FormControl<DocsDataFormRawValue['id'] | NewDocsData['id']>;
  name: FormControl<DocsDataFormRawValue['name']>;
  description: FormControl<DocsDataFormRawValue['description']>;
  doc: FormControl<DocsDataFormRawValue['doc']>;
  docContentType: FormControl<DocsDataFormRawValue['docContentType']>;
  orderdoc: FormControl<DocsDataFormRawValue['orderdoc']>;
  resourcedate: FormControl<DocsDataFormRawValue['resourcedate']>;
};

export type DocsDataFormGroup = FormGroup<DocsDataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocsDataFormService {
  createDocsDataFormGroup(docsData: DocsDataFormGroupInput = { id: null }): DocsDataFormGroup {
    const docsDataRawValue = this.convertDocsDataToDocsDataRawValue({
      ...this.getFormDefaults(),
      ...docsData,
    });
    return new FormGroup<DocsDataFormGroupContent>({
      id: new FormControl(
        { value: docsDataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(docsDataRawValue.name, {
        validators: [Validators.required],
      }),
      description: new FormControl(docsDataRawValue.description),
      doc: new FormControl(docsDataRawValue.doc, {
        validators: [Validators.required],
      }),
      docContentType: new FormControl(docsDataRawValue.docContentType),
      orderdoc: new FormControl(docsDataRawValue.orderdoc, {
        validators: [Validators.required],
      }),
      resourcedate: new FormControl(docsDataRawValue.resourcedate, {
        validators: [Validators.required],
      }),
    });
  }

  getDocsData(form: DocsDataFormGroup): IDocsData | NewDocsData {
    return this.convertDocsDataRawValueToDocsData(form.getRawValue() as DocsDataFormRawValue | NewDocsDataFormRawValue);
  }

  resetForm(form: DocsDataFormGroup, docsData: DocsDataFormGroupInput): void {
    const docsDataRawValue = this.convertDocsDataToDocsDataRawValue({ ...this.getFormDefaults(), ...docsData });
    form.reset(
      {
        ...docsDataRawValue,
        id: { value: docsDataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DocsDataFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      resourcedate: currentTime,
    };
  }

  private convertDocsDataRawValueToDocsData(rawDocsData: DocsDataFormRawValue | NewDocsDataFormRawValue): IDocsData | NewDocsData {
    return {
      ...rawDocsData,
      resourcedate: dayjs(rawDocsData.resourcedate, DATE_TIME_FORMAT),
    };
  }

  private convertDocsDataToDocsDataRawValue(
    docsData: IDocsData | (Partial<NewDocsData> & DocsDataFormDefaults)
  ): DocsDataFormRawValue | PartialWithRequiredKeyOf<NewDocsDataFormRawValue> {
    return {
      ...docsData,
      resourcedate: docsData.resourcedate ? docsData.resourcedate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
