export interface IGeneral {
  id: number;
  siteName?: string | null;
  homePageName?: string | null;
  resourcesPageName?: string | null;
  staffPageName?: string | null;
  aboutPageName?: string | null;
  facebookAddress?: string | null;
  address?: string | null;
  phone?: string | null;
  email?: string | null;
  motto?: string | null;
  structure1?: string | null;
  structure2?: string | null;
  structure3?: string | null;
  structure4?: string | null;
  contactHeader?: string | null;
  structuresHeader?: string | null;
  mapUrl?: string | null;
}

export type NewGeneral = Omit<IGeneral, 'id'> & { id: null };
