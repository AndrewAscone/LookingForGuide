import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivity } from 'app/shared/model/activity.model';
import { getEntities as getActivities } from 'app/entities/activity/activity.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IPost } from 'app/shared/model/post.model';
import { GuideType } from 'app/shared/model/enumerations/guide-type.model';
import { UserType } from 'app/shared/model/enumerations/user-type.model';
import { getEntity, updateEntity, createEntity, reset } from './post.reducer';

export const PostUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const activities = useAppSelector(state => state.activity.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const postEntity = useAppSelector(state => state.post.entity);
  const loading = useAppSelector(state => state.post.loading);
  const updating = useAppSelector(state => state.post.updating);
  const updateSuccess = useAppSelector(state => state.post.updateSuccess);
  const guideTypeValues = Object.keys(GuideType);
  const userTypeValues = Object.keys(UserType);

  const handleClose = () => {
    navigate('/post');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getActivities({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...postEntity,
      ...values,
      activity: activities.find(it => it.id.toString() === values.activity.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          guideType: 'TUTORIAL',
          userType: 'MENTEE',
          ...postEntity,
          date: convertDateTimeFromServer(postEntity.date),
          activity: postEntity?.activity?.id,
          user: postEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lookingForGuideApp.post.home.createOrEditLabel" data-cy="PostCreateUpdateHeading">
            Create or edit a Post
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="post-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Title"
                id="post-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Post Body"
                id="post-postBody"
                name="postBody"
                data-cy="postBody"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Date" id="post-date" name="date" data-cy="date" type="datetime-local" placeholder="YYYY-MM-DD HH:mm" />
              <ValidatedField label="Guide Type" id="post-guideType" name="guideType" data-cy="guideType" type="select">
                {guideTypeValues.map(guideType => (
                  <option value={guideType} key={guideType}>
                    {guideType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedBlobField label="Image" id="post-image" name="image" data-cy="image" isImage accept="image/*" />
              <ValidatedField label="User Type" id="post-userType" name="userType" data-cy="userType" type="select">
                {userTypeValues.map(userType => (
                  <option value={userType} key={userType}>
                    {userType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="post-activity" name="activity" data-cy="activity" label="Activity" type="select">
                <option value="" key="0" />
                {activities
                  ? activities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="post-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/post" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PostUpdate;
