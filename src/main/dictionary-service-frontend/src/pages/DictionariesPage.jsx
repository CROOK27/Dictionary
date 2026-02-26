import React, { useState } from 'react';
import { Row, Col } from 'react-bootstrap';
import FourLetterDictionary from '../components/dictionaries/FourLetterDictionary';
import FiveDigitDictionary from '../components/dictionaries/FiveDigitDictionary';
import TranslationsList from '../components/translations/TranslationsList';
import SearchBar from '../components/common/SearchBar';
import EditModal from '../components/modals/EditModal';
import { searchService } from '../services/dictionaryService';
import { showInfo } from '../components/common/Notification';

const DictionariesPage = () => {
  const [refreshKey, setRefreshKey] = useState(0);
  const [searchResults, setSearchResults] = useState(null);
  const [editingItem, setEditingItem] = useState(null);
  const [editingType, setEditingType] = useState(null);
  const [showEditModal, setShowEditModal] = useState(false);

  const handleRefresh = () => {
    setRefreshKey(prev => prev + 1);
    setSearchResults(null);
  };

  const handleSearch = async (params) => {
    if (!params.text) {
      showInfo('Введите текст для поиска');
      return;
    }

    try {
      const results = await searchService.search(
        params.text,
        params.dictionary,
        params.type
      );
      setSearchResults(results);
    } catch (error) {
      showInfo('Поиск временно работает в демо-режиме');
      // Демо-поиск
      const demoResults = [
        { id: 1, word: 'cat', type: 'fourLetter', description: 'Кошка' },
        { id: 2, word: 'dog', type: 'fourLetter', description: 'Собака' },
        { id: 3, word: 'кот', type: 'fiveDigit', description: 'Кошка' },
        { id: 4, word: 'собака', type: 'fiveDigit', description: 'Собака' },
      ];
      setSearchResults(demoResults);
    }
  };

  const handleEdit = (item, type) => {
    setEditingItem(item);
    setEditingType(type);
    setShowEditModal(true);
  };

  const handleSaveEdit = () => {
    handleRefresh();
  };

  return (
    <div>
      <SearchBar onSearch={handleSearch} />

      {searchResults && (
        <div className="mb-4">
          <h3>Результаты поиска</h3>
          <div className="search-box">
            {searchResults.length === 0 ? (
              <div className="empty-state">
                Ничего не найдено
              </div>
            ) : (
              searchResults.map((result, index) => (
                <div key={index} className="entry-item">
                  <div>
                    <strong>{result.word}</strong>
                    <div className="small text-muted">
                      {result.type === 'fourLetter' && '4-буквенный словарь'}
                      {result.type === 'fiveDigit' && '5-значный словарь'}
                      {result.type === 'translation' && 'Перевод'}
                    </div>
                    {result.description && (
                      <div className="small">{result.description}</div>
                    )}
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      )}

      <Row>
        <Col md={6}>
          <FourLetterDictionary
            key={`four-${refreshKey}`}
            onEdit={handleEdit}
            onDelete={handleRefresh}
            refreshTrigger={refreshKey}
          />
        </Col>
        <Col md={6}>
          <FiveDigitDictionary
            key={`five-${refreshKey}`}
            onEdit={handleEdit}
            onDelete={handleRefresh}
            refreshTrigger={refreshKey}
          />
        </Col>
      </Row>

      <Row className="mt-4">
        <Col>
          <TranslationsList
            key={`trans-${refreshKey}`}
            onEdit={handleEdit}
            onDelete={handleRefresh}
            refreshTrigger={refreshKey}
          />
        </Col>
      </Row>

      <EditModal
        show={showEditModal}
        onHide={() => setShowEditModal(false)}
        item={editingItem}
        type={editingType}
        onSave={handleSaveEdit}
      />
    </div>
  );
};

export default DictionariesPage;