export interface IActivity {
  id?: number;
  name?: string | null;
  ordinal?: number | null;
}

export const defaultValue: Readonly<IActivity> = {};
