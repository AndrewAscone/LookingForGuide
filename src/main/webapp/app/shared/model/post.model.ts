import dayjs from 'dayjs';
import { IActivity } from 'app/shared/model/activity.model';
import { IUser } from 'app/shared/model/user.model';
import { GuideType } from 'app/shared/model/enumerations/guide-type.model';
import { UserType } from 'app/shared/model/enumerations/user-type.model';

export interface IPost {
  id?: number;
  title?: string;
  postBody?: string;
  date?: string | null;
  guideType?: GuideType | null;
  imageContentType?: string | null;
  image?: string | null;
  userType?: UserType | null;
  activity?: IActivity | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IPost> = {};
