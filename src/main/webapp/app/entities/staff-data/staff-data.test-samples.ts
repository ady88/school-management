import { IStaffData, NewStaffData } from './staff-data.model';

export const sampleWithRequiredData: IStaffData = {
  id: 84179,
  orderStaff: 84104,
  lastName: 'Ryan',
  firstName: 'Esperanza',
  jobType: 'Vermont Granite',
  unitName: 'capacitor',
};

export const sampleWithPartialData: IStaffData = {
  id: 91787,
  orderStaff: 66806,
  lastName: 'Pfeffer',
  firstName: 'Ozella',
  jobType: 'Facilitator',
  unitName: 'Berkshire Tenge',
};

export const sampleWithFullData: IStaffData = {
  id: 98190,
  orderStaff: 95033,
  lastName: 'Turner',
  firstName: 'Camren',
  jobType: 'white',
  unitName: 'Mouse',
};

export const sampleWithNewData: NewStaffData = {
  orderStaff: 68287,
  lastName: 'Toy',
  firstName: 'Ottilie',
  jobType: 'Markets Toys grey',
  unitName: 'Hat morph',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
