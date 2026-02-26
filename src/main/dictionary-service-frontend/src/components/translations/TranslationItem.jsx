import React from 'react';
import { BiPencil, BiTrash } from 'react-icons/bi';

const TranslationItem = ({ item, onEdit, onDelete }) => {
  return (
    <div className="entry-item">
      <div>
        <strong>{item.translatedWord}</strong>
        <div className="small">
          <span className="translation-badge">
            4-букв: {item.fourLetterDictionary?.word || '?'}
          </span>
          <span className="translation-badge">
            5-знач: {item.fiveDigitDictionary?.word || '?'}
          </span>
        </div>
      </div>
      <div>
        <BiPencil
          className="btn-icon text-primary"
          onClick={() => onEdit(item, 'translation')}
          title="Редактировать"
        />
        <BiTrash
          className="btn-icon text-danger"
          onClick={() => onDelete(item.id, 'translation')}
          title="Удалить"
        />
      </div>
    </div>
  );
};

export default TranslationItem;