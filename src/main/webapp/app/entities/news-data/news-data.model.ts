export interface INewsData {
  id: number;
  title?: string | null;
  description?: string | null;
  image?: string | null;
  imageContentType?: string | null;
  linkLabel?: string | null;
  linkUrl?: string | null;
  orderNews?: number | null;
}

export type NewNewsData = Omit<INewsData, 'id'> & { id: null };
