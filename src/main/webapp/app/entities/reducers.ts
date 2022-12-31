import post from 'app/entities/post/post.reducer';
import userGamingAccount from 'app/entities/user-gaming-account/user-gaming-account.reducer';
import activity from 'app/entities/activity/activity.reducer';
import messageBox from 'app/entities/message-box/message-box.reducer';
import message from 'app/entities/message/message.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  post,
  userGamingAccount,
  activity,
  messageBox,
  message,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
