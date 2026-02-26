import React, { useState, useEffect } from 'react';
import { BiChevronRight } from 'react-icons/bi';
import DictionaryItem from './DictionaryItem';
import { fourLetterService } from '../../services/dictionaryService';
import { showError } from '../common/Notification';
import LoadingSpinner from '../common/LoadingSpinner';

const FourLetterDictionary = ({ onEdit, onDelete, refreshTrigger }) => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [expanded, setExpanded] = useState(true);

  useEffect(() => {
    loadData();
  }, [refreshTrigger]);

  const loadData = async () => {
    try {
      setLoading(true);
      const response = await fourLetterService.getAll();
      setItems(response.data);
    } catch (error) {
      showError('Ошибка при загрузке 4-буквенного словаря');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id, type) => {
    if (window.confirm('Вы уверены, что хотите удалить эту запись?')) {
      try {
        await fourLetterService.delete(id);
        await loadData();
        onDelete();
      } catch (error) {
        showError('Ошибка при удалении');
      }
    }
  };

  return (
    <div className="dictionary-section">
      <div
        className="dictionary-header"
        onClick={() => setExpanded(!expanded)}
      >
        <span>
          <BiChevronRight
            className={`chevron-icon ${expanded ? 'expanded' : ''}`}
          />
          {' '}4-буквенный словарь
        </span>
        <span className="count-badge">{items.length}</span>
      </div>

      <div className={`dictionary-content ${expanded ? 'expanded' : ''}`}>
        {loading ? (
          <LoadingSpinner />
        ) : items.length === 0 ? (
          <div className="empty-state">
            Словарь пуст
          </div>
        ) : (
          items.map(item => (
            <DictionaryItem
              key={item.id}
              item={item}
              type="fourLetter"
              onEdit={onEdit}
              onDelete={handleDelete}
            />
          ))
        )}
      </div>
    </div>
  );
};

export default FourLetterDictionary;