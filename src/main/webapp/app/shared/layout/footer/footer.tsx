import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <h6>About the developer</h6>
        <p>
          <a href="https://github.com/AndrewAscone" target="_blank">
            Github
          </a>{' '}
          <a href="https://www.linkedin.com/in/andrew-ascone/" target="_blank">
            LinkedIn
          </a>
        </p>
      </Col>
    </Row>
  </div>
);

export default Footer;
