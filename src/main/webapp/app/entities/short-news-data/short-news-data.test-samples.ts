import { IShortNewsData, NewShortNewsData } from './short-news-data.model';

export const sampleWithRequiredData: IShortNewsData = {
  id: 74797,
  title: 'Managed Key synergies',
  orderNews: 68666,
};

export const sampleWithPartialData: IShortNewsData = {
  id: 39546,
  title: 'Global withdrawal Belize',
  orderNews: 74389,
};

export const sampleWithFullData: IShortNewsData = {
  id: 25188,
  title: 'transmit Account revolutionary',
  linkUrl: 'Kids Metal',
  orderNews: 71889,
};

export const sampleWithNewData: NewShortNewsData = {
  title: 'Toys',
  orderNews: 90235,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
