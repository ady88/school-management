export interface IShortNewsData {
  id: number;
  title?: string | null;
  linkUrl?: string | null;
  orderNews?: number | null;
}

export type NewShortNewsData = Omit<IShortNewsData, 'id'> & { id: null };
