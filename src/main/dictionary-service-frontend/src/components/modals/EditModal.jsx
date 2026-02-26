import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { BiTrash } from 'react-icons/bi';
import { fourLetterService, fiveDigitService, translationService } from '../../services/dictionaryService';
import { showSuccess, showError } from '../common/Notification';

const EditModal = ({ show, onHide, item, type, onSave }) => {
  const [word, setWord] = useState('');
  const [description, setDescription] = useState('');
  const [translations, setTranslations] = useState([]);
  const [newTranslation, setNewTranslation] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (item) {
      if (type === 'translation') {
        setWord(item.translatedWord || '');
      } else {
        setWord(item.word || '');
        setDescription(item.description || '');
      }

      if (type === 'fourLetter' || type === 'fiveDigit') {
        loadTranslations();
      }
    }
  }, [item, type]);

  const loadTranslations = async () => {
    try {
      if (type === 'fourLetter') {
        const response = await translationService.getByFourLetter(item.id);
        setTranslations(response.data);
      } else if (type === 'fiveDigit') {
        const response = await translationService.getByFiveDigit(item.id);
        setTranslations(response.data);
      }
    } catch (error) {
      showError('Ошибка при загрузке переводов');
    }
  };

  const handleSave = async () => {
    try {
      setLoading(true);

      if (type === 'fourLetter') {
        await fourLetterService.update(item.id, { word, description });
      } else if (type === 'fiveDigit') {
        await fiveDigitService.update(item.id, { word, description });
      } else if (type === 'translation') {
        await translationService.update(item.id, { translatedWord: word });
      }

      showSuccess('Запись обновлена');
      onSave();
      onHide();
    } catch (error) {
      showError('Ошибка при обновлении');
    } finally {
      setLoading(false);
    }
  };

  const addTranslation = async () => {
    if (!newTranslation.trim()) return;

    showInfo('Функция добавления переводов в разработке');
  };

  const deleteTranslation = async (translationId) => {
    if (window.confirm('Удалить этот перевод?')) {
      try {
        await translationService.delete(translationId);
        await loadTranslations();
        showSuccess('Перевод удален');
      } catch (error) {
        showError('Ошибка при удалении');
      }
    }
  };

  return (
    <Modal show={show} onHide={onHide} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>
          {type === 'fourLetter' && 'Редактирование 4-буквенного слова'}
          {type === 'fiveDigit' && 'Редактирование 5-значного слова'}
          {type === 'translation' && 'Редактирование перевода'}
        </Modal.Title>
      </Modal.Header>

      <Modal.Body>
        <Form>
          <Form.Group className="mb-3">
            <Form.Label>
              {type === 'translation' ? 'Перевод' : 'Слово'}
            </Form.Label>
            <Form.Control
              type="text"
              value={word}
              onChange={(e) => setWord(e.target.value)}
            />
          </Form.Group>

          {type !== 'translation' && (
            <Form.Group className="mb-3">
              <Form.Label>Описание</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </Form.Group>
          )}

          {(type === 'fourLetter' || type === 'fiveDigit') && (
            <div className="mt-4">
              <h6>Связанные переводы</h6>

              <div className="input-group mb-3">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Новый перевод"
                  value={newTranslation}
                  onChange={(e) => setNewTranslation(e.target.value)}
                />
                <button
                  className="btn btn-outline-primary"
                  type="button"
                  onClick={addTranslation}
                >
                  Добавить
                </button>
              </div>

              <div className="translations-list">
                {translations.map(t => (
                  <div key={t.id} className="modal-translation-item">
                    <span>{t.translatedWord}</span>
                    <BiTrash
                      className="text-danger"
                      style={{ cursor: 'pointer' }}
                      onClick={() => deleteTranslation(t.id)}
                    />
                  </div>
                ))}
                {translations.length === 0 && (
                  <div className="text-muted text-center py-2">
                    Нет связанных переводов
                  </div>
                )}
              </div>
            </div>
          )}
        </Form>
      </Modal.Body>

      <Modal.Footer>
        <Button variant="secondary" onClick={onHide}>
          Отмена
        </Button>
        <Button
          variant="primary"
          onClick={handleSave}
          disabled={loading}
        >
          {loading ? 'Сохранение...' : 'Сохранить'}
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default EditModal;