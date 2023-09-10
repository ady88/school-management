import dayjs from 'dayjs/esm';

import { IDocsData, NewDocsData } from './docs-data.model';

export const sampleWithRequiredData: IDocsData = {
  id: 87811,
  name: 'Georgia',
  doc: '../fake-data/blob/hipster.png',
  docContentType: 'unknown',
  orderdoc: 78861,
  resourcedate: dayjs('2023-09-08T15:25'),
};

export const sampleWithPartialData: IDocsData = {
  id: 62069,
  name: 'scale Global Home',
  doc: '../fake-data/blob/hipster.png',
  docContentType: 'unknown',
  orderdoc: 43353,
  resourcedate: dayjs('2023-09-09T07:57'),
};

export const sampleWithFullData: IDocsData = {
  id: 83895,
  name: 'Forges auxiliary Buckinghamshire',
  description: 'ADP',
  doc: '../fake-data/blob/hipster.png',
  docContentType: 'unknown',
  orderdoc: 2964,
  resourcedate: dayjs('2023-09-08T13:22'),
};

export const sampleWithNewData: NewDocsData = {
  name: 'communities Architect',
  doc: '../fake-data/blob/hipster.png',
  docContentType: 'unknown',
  orderdoc: 46405,
  resourcedate: dayjs('2023-09-09T07:16'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
