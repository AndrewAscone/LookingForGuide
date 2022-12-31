import { IUser } from 'app/shared/model/user.model';
import { IMessage } from 'app/shared/model/message.model';

export interface IMessageBox {
  id?: number;
  user?: IUser | null;
  messages?: IMessage[] | null;
}

export const defaultValue: Readonly<IMessageBox> = {};
