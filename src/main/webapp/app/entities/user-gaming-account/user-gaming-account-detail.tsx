import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-gaming-account.reducer';

export const UserGamingAccountDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userGamingAccountEntity = useAppSelector(state => state.userGamingAccount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userGamingAccountDetailsHeading">User Gaming Account</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userGamingAccountEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{userGamingAccountEntity.name}</dd>
          <dt>
            <span id="accountName">Account Name</span>
          </dt>
          <dd>{userGamingAccountEntity.accountName}</dd>
          <dt>User</dt>
          <dd>{userGamingAccountEntity.user ? userGamingAccountEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-gaming-account" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-gaming-account/${userGamingAccountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserGamingAccountDetail;
