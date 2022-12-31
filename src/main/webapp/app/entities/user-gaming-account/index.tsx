import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserGamingAccount from './user-gaming-account';
import UserGamingAccountDetail from './user-gaming-account-detail';
import UserGamingAccountUpdate from './user-gaming-account-update';
import UserGamingAccountDeleteDialog from './user-gaming-account-delete-dialog';

const UserGamingAccountRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserGamingAccount />} />
    <Route path="new" element={<UserGamingAccountUpdate />} />
    <Route path=":id">
      <Route index element={<UserGamingAccountDetail />} />
      <Route path="edit" element={<UserGamingAccountUpdate />} />
      <Route path="delete" element={<UserGamingAccountDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserGamingAccountRoutes;
