import { Theme } from 'app/entities/enumerations/theme.model';

import { IOther, NewOther } from './other.model';

export const sampleWithRequiredData: IOther = {
  id: 9688,
};

export const sampleWithPartialData: IOther = {
  id: 44740,
  useTopImage: true,
  useMainImage: true,
  mainImage: '../fake-data/blob/hipster.png',
  mainImageContentType: 'unknown',
};

export const sampleWithFullData: IOther = {
  id: 8615,
  useTopImage: true,
  topImage: '../fake-data/blob/hipster.png',
  topImageContentType: 'unknown',
  useMainImage: false,
  mainImage: '../fake-data/blob/hipster.png',
  mainImageContentType: 'unknown',
  theme: Theme['BLUE'],
};

export const sampleWithNewData: NewOther = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
