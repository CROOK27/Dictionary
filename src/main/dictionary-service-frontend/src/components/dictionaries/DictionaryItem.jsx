import React from 'react';
import { BiPencil, BiTrash } from 'react-icons/bi';

const DictionaryItem = ({ item, type, onEdit, onDelete }) => {
  return (
    <div className="entry-item">
      <div>
        <strong>{item.word}</strong>
        {item.description && (
          <div className="small text-muted">{item.description}</div>
        )}
      </div>
      <div>
        <BiPencil
          className="btn-icon text-primary"
          onClick={() => onEdit(item, type)}
          title="Редактировать"
        />
        <BiTrash
          className="btn-icon text-danger"
          onClick={() => onDelete(item.id, type)}
          title="Удалить"
        />
      </div>
    </div>
  );
};

export default DictionaryItem;