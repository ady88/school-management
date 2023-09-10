import { IMainNewsData, NewMainNewsData } from './main-news-data.model';

export const sampleWithRequiredData: IMainNewsData = {
  id: 77933,
  title: 'blockchains',
  description: 'primary Guilder blockchains',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  orderNews: 10255,
};

export const sampleWithPartialData: IMainNewsData = {
  id: 87159,
  title: 'enterprise Granite',
  description: 'pink',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  orderNews: 36110,
};

export const sampleWithFullData: IMainNewsData = {
  id: 11406,
  title: 'e-markets',
  description: 'copying',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  orderNews: 51069,
};

export const sampleWithNewData: NewMainNewsData = {
  title: 'proactive Sausages',
  description: 'Tobago',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  orderNews: 17213,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
