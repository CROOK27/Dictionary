import React, { useState, useEffect } from 'react';
import { BiChevronRight } from 'react-icons/bi';
import TranslationItem from './TranslationItem';
import { translationService } from '../../services/dictionaryService';
import { showError } from '../common/Notification';
import LoadingSpinner from '../common/LoadingSpinner';

const TranslationsList = ({ onEdit, onDelete, refreshTrigger }) => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [expanded, setExpanded] = useState(true);

  useEffect(() => {
    loadData();
  }, [refreshTrigger]);

  const loadData = async () => {
    try {
      setLoading(true);
      const response = await translationService.getAll();
      setItems(response.data);
    } catch (error) {
      showError('Ошибка при загрузке переводов');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id, type) => {
    if (window.confirm('Вы уверены, что хотите удалить этот перевод?')) {
      try {
        await translationService.delete(id);
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
          {' '}Переводы
        </span>
        <span className="count-badge">{items.length}</span>
      </div>

      <div className={`dictionary-content ${expanded ? 'expanded' : ''}`}>
        {loading ? (
          <LoadingSpinner />
        ) : items.length === 0 ? (
          <div className="empty-state">
            Переводов нет
          </div>
        ) : (
          items.map(item => (
            <TranslationItem
              key={item.id}
              item={item}
              onEdit={onEdit}
              onDelete={handleDelete}
            />
          ))
        )}
      </div>
    </div>
  );
};

export default TranslationsList;