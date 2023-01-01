import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPost } from 'app/shared/model/post.model';
import { getEntities } from './post.reducer';

export const Post = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const postList = useAppSelector(state => state.post.entities);
  const loading = useAppSelector(state => state.post.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="post-heading" data-cy="PostHeading">
        Posts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/post/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Post
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {postList && postList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                {/* <th>ID</th> */}
                <th>Title</th>
                {/* <th>Post Body</th> */}
                {/* <th>Date</th> */}
                {/* <th>Guide Type</th> */}
                <th>Emblem</th>
                {/* <th>User Type</th> */}
                {/* <th>Activity</th> */}
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {postList.map((post, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  {/* <td>
                    <Button tag={Link} to={`/post/${post.id}`} color="link" size="sm" style={{textDecoration:"none"}}>
                      {post.id}
                    </Button>
                  </td> */}
                  <td>
                    <Button tag={Link} to={`/post/${post.id}`} size="sm" style={{ color: 'black', backgroundColor: 'grey, 0' }}>
                      {post.title}
                    </Button>
                  </td>
                  {/* <td>{post.postBody}</td> */}
                  {/* <td>{post.date ? <TextFormat type="date" value={post.date} format={APP_DATE_FORMAT} /> : null}</td> */}
                  {/* <td>{post.guideType}</td> */}
                  <td>
                    {post.image ? (
                      <div>
                        {post.imageContentType ? (
                          <a onClick={openFile(post.imageContentType, post.image)}>
                            <img
                              src={`data:${post.imageContentType};base64,${post.image}`}
                              style={{ maxHeight: '30px', borderRadius: '50%' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        {/* <span>
                          {post.imageContentType}, {byteSize(post.image)}
                        </span> */}
                      </div>
                    ) : null}
                  </td>
                  {/* <td>{post.userType}</td> */}
                  {/* <td>{post.activity ? <Link to={`/activity/${post.activity.id}`}>{post.activity.name}</Link> : ''}</td> */}
                  <td style={{ color: 'black' }}>{post.user ? post.user.login : ''}</td>
                  <td className="text-end">
                    {/* <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/post/${post.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/post/${post.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/post/${post.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div> */}
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Posts found</div>
        )}
      </div>
    </div>
  );
};

export default Post;
