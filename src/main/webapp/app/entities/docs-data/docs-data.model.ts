import dayjs from 'dayjs/esm';

export interface IDocsData {
  id: number;
  name?: string | null;
  description?: string | null;
  doc?: string | null;
  docContentType?: string | null;
  orderdoc?: number | null;
  resourcedate?: dayjs.Dayjs | null;
}

export type NewDocsData = Omit<IDocsData, 'id'> & { id: null };
