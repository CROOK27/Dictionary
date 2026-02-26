import React, { useState, useEffect } from 'react';
import { BiChevronRight } from 'react-icons/bi';
import DictionaryItem from './DictionaryItem';
import { fiveDigitService } from '../../services/dictionaryService';
import { showError } from '../common/Notification';
import LoadingSpinner from '../common/LoadingSpinner';

const FiveDigitDictionary = ({ onEdit, onDelete, refreshTrigger }) => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [expanded, setExpanded] = useState(true);

  useEffect(() => {
    loadData();
  }, [refreshTrigger]);

  const loadData = async () => {
    try {
      setLoading(true);
      const response = await fiveDigitService.getAll();
      // Проверяем структуру ответа
      const data = response.data || response;
      setItems(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error('Ошибка загрузки:', error);
      showError('Ошибка при загрузке 5-значного словаря');
      setItems([]);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id, type) => {
    if (window.confirm('Вы уверены, что хотите удалить эту запись?')) {
      try {
        await fiveDigitService.delete(id);
        await loadData();
        onDelete();
      } catch (error) {
        console.error('Ошибка удаления:', error);
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
          {' '}5-значный словарь
        </span>
        <span className="count-badge">{items.length}</span>
      </div>

      <div className={`dictionary-content ${expanded ? 'expanded' : ''}`}>
        {loading ? (
          <LoadingSpinner />
        ) : !Array.isArray(items) || items.length === 0 ? (
          <div className="empty-state">
            Словарь пуст
          </div>
        ) : (
          items.map(item => (
            <DictionaryItem
              key={item.id}
              item={item}
              type="fiveDigit"
              onEdit={onEdit}
              onDelete={handleDelete}
            />
          ))
        )}
      </div>
    </div>
  );
};

export default FiveDigitDictionary;