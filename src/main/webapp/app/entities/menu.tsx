import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/post">
        Post
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-gaming-account">
        User Gaming Account
      </MenuItem>
      <MenuItem icon="asterisk" to="/activity">
        Activity
      </MenuItem>
      <MenuItem icon="asterisk" to="/message-box">
        Message Box
      </MenuItem>
      <MenuItem icon="asterisk" to="/message">
        Message
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
