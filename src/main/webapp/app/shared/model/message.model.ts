import { IMessageBox } from 'app/shared/model/message-box.model';

export interface IMessage {
  id?: number;
  messageBody?: string | null;
  messageBox?: IMessageBox | null;
}

export const defaultValue: Readonly<IMessage> = {};
