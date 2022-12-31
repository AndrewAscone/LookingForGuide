import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MessageBox from './message-box';
import MessageBoxDetail from './message-box-detail';
import MessageBoxUpdate from './message-box-update';
import MessageBoxDeleteDialog from './message-box-delete-dialog';

const MessageBoxRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MessageBox />} />
    <Route path="new" element={<MessageBoxUpdate />} />
    <Route path=":id">
      <Route index element={<MessageBoxDetail />} />
      <Route path="edit" element={<MessageBoxUpdate />} />
      <Route path="delete" element={<MessageBoxDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MessageBoxRoutes;
