import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat, byteSize, openFile } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './post.reducer';

export const PostDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const postEntity = useAppSelector(state => state.post.entity);
  return (
    <Row>
      <Col md="8" style={{ color: 'white' }}>
        {/* <h2 data-cy="postDetailsHeading">Post</h2> */}
        <dl className="jh-entity-details">
          {/* <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{postEntity.id}</dd> */}
          {/* <dt>
            <span id="title">Title</span>
          </dt> */}
          <dd style={{ fontSize: '1.4em' }}>{postEntity.title}</dd>
          <dt>Posted By</dt>
          <dd>
            <a href="https://www.bungie.net/7/en/User/Profile/1/4611686018436344035" target={'_blank'} style={{ textDecoration: 'none' }}>
              {postEntity.user ? postEntity.user.login : ''}
            </a>
          </dd>
          <dd>
            {postEntity.image ? (
              <div>
                {postEntity.imageContentType ? (
                  <img src={`data:${postEntity.imageContentType};base64,${postEntity.image}`} style={{ maxHeight: '300px' }} />
                ) : null}
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="postBody">Details</span>
          </dt>
          <dd>{postEntity.postBody}</dd>
          <dt>
            <span id="date">Date Posted</span>
          </dt>
          <dd>{postEntity.date ? <TextFormat value={postEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          {/* <dt>
            <span id="guideType">Guide Type</span>
          </dt>
          <dd>{postEntity.guideType}</dd> */}
          {/* <dt>
            <span id="image">Image</span>
          </dt> */}

          {/* <dt>
            <span id="userType">User Type</span>
          </dt>
          <dd>{postEntity.userType}</dd> */}
          <dt>Activity</dt>
          <dd>{postEntity.activity ? postEntity.activity.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/post" replace color="dark" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        {/* <Button tag={Link} to={`/post/${postEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button> */}
      </Col>
    </Row>
  );
};

export default PostDetail;
