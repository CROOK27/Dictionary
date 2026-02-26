import React, { useState } from 'react';
import { Form, Row, Col, Button } from 'react-bootstrap';
import { BiSearch } from 'react-icons/bi';

const SearchBar = ({ onSearch }) => {
  const [searchText, setSearchText] = useState('');
  const [searchDictionary, setSearchDictionary] = useState('all');
  const [searchType, setSearchType] = useState('key');

  const handleSubmit = (e) => {
    e.preventDefault();
    onSearch({
      text: searchText,
      dictionary: searchDictionary,
      type: searchType
    });
  };

  return (
    <div className="search-box">
      <Form onSubmit={handleSubmit}>
        <Row>
          <Col md={4}>
            <Form.Control
              type="text"
              placeholder="Введите текст для поиска"
              value={searchText}
              onChange={(e) => setSearchText(e.target.value)}
            />
          </Col>
          <Col md={3}>
            <Form.Select
              value={searchDictionary}
              onChange={(e) => setSearchDictionary(e.target.value)}
            >
              <option value="all">Все словари</option>
              <option value="fourLetter">4-буквенный словарь</option>
              <option value="fiveDigit">5-значный словарь</option>
              <option value="translations">Переводы</option>
            </Form.Select>
          </Col>
          <Col md={2}>
            <Form.Select
              value={searchType}
              onChange={(e) => setSearchType(e.target.value)}
            >
              <option value="key">По ключу</option>
              <option value="value">По значению</option>
            </Form.Select>
          </Col>
          <Col md={3}>
            <Button type="submit" variant="primary" className="w-100">
              <BiSearch className="me-2" />
              Поиск
            </Button>
          </Col>
        </Row>
      </Form>
    </div>
  );
};

export default SearchBar;