export interface IStaffData {
  id: number;
  orderStaff?: number | null;
  lastName?: string | null;
  firstName?: string | null;
  jobType?: string | null;
  unitName?: string | null;
}

export type NewStaffData = Omit<IStaffData, 'id'> & { id: null };
