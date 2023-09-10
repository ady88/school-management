export interface IMainNewsData {
  id: number;
  title?: string | null;
  description?: string | null;
  image?: string | null;
  imageContentType?: string | null;
  orderNews?: number | null;
}

export type NewMainNewsData = Omit<IMainNewsData, 'id'> & { id: null };
