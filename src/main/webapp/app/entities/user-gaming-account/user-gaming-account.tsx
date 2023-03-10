import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserGamingAccount } from 'app/shared/model/user-gaming-account.model';
import { getEntities } from './user-gaming-account.reducer';

export const UserGamingAccount = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const userGamingAccountList = useAppSelector(state => state.userGamingAccount.entities);
  const loading = useAppSelector(state => state.userGamingAccount.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="user-gaming-account-heading" data-cy="UserGamingAccountHeading">
        User Gaming Accounts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/user-gaming-account/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new User Gaming Account
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userGamingAccountList && userGamingAccountList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Account Name</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userGamingAccountList.map((userGamingAccount, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/user-gaming-account/${userGamingAccount.id}`} color="link" size="sm">
                      {userGamingAccount.id}
                    </Button>
                  </td>
                  <td>{userGamingAccount.name}</td>
                  <td>{userGamingAccount.accountName}</td>
                  <td>{userGamingAccount.user ? userGamingAccount.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/user-gaming-account/${userGamingAccount.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-gaming-account/${userGamingAccount.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-gaming-account/${userGamingAccount.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No User Gaming Accounts found</div>
        )}
      </div>
    </div>
  );
};

export default UserGamingAccount;
