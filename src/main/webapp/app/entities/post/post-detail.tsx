import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
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
      <Col md="8">
        <h2 data-cy="postDetailsHeading">Post</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{postEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{postEntity.title}</dd>
          <dt>
            <span id="postBody">Post Body</span>
          </dt>
          <dd>{postEntity.postBody}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{postEntity.date ? <TextFormat value={postEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="guideType">Guide Type</span>
          </dt>
          <dd>{postEntity.guideType}</dd>
          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>
            {postEntity.image ? (
              <div>
                {postEntity.imageContentType ? (
                  <a onClick={openFile(postEntity.imageContentType, postEntity.image)}>
                    <img src={`data:${postEntity.imageContentType};base64,${postEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {postEntity.imageContentType}, {byteSize(postEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="userType">User Type</span>
          </dt>
          <dd>{postEntity.userType}</dd>
          <dt>Activity</dt>
          <dd>{postEntity.activity ? postEntity.activity.name : ''}</dd>
          <dt>User</dt>
          <dd>{postEntity.user ? postEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/post" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post/${postEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PostDetail;
