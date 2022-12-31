import { IUser } from 'app/shared/model/user.model';

export interface IUserGamingAccount {
  id?: number;
  name?: string | null;
  accountName?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IUserGamingAccount> = {};
