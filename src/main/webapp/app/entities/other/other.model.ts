import { Theme } from 'app/entities/enumerations/theme.model';

export interface IOther {
  id: number;
  useTopImage?: boolean | null;
  topImage?: string | null;
  topImageContentType?: string | null;
  useMainImage?: boolean | null;
  mainImage?: string | null;
  mainImageContentType?: string | null;
  theme?: Theme | null;
}

export type NewOther = Omit<IOther, 'id'> & { id: null };
