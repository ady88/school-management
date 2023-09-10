import { INewsData, NewNewsData } from './news-data.model';

export const sampleWithRequiredData: INewsData = {
  id: 98560,
  title: 'Analyst Central Bedfordshire',
  description: 'Turnpike capability Secured',
  orderNews: 51066,
};

export const sampleWithPartialData: INewsData = {
  id: 15885,
  title: 'Pizza Refined',
  description: 'Loan overriding Ergonomic',
  orderNews: 51210,
};

export const sampleWithFullData: INewsData = {
  id: 49952,
  title: 'cyan Optimization',
  description: 'Card Officer initiatives',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  linkLabel: 'pixel',
  linkUrl: 'override',
  orderNews: 66052,
};

export const sampleWithNewData: NewNewsData = {
  title: 'revolutionary',
  description: 'Home group',
  orderNews: 27563,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
