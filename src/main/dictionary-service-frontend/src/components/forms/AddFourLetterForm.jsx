import React, { useState } from 'react';
import { Form, Button, Card } from 'react-bootstrap';
import { fourLetterService } from '../../services/dictionaryService';
import { showSuccess, showError } from '../common/Notification';

const AddFourLetterForm = ({ onSuccess }) => {
  const [word, setWord] = useState('');
  const [description, setDescription] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!word.trim()) {
      showError('Введите слово');
      return;
    }

    try {
      setLoading(true);
      await fourLetterService.create({ word, description });
      showSuccess('Слово успешно добавлено');
      setWord('');
      setDescription('');
      onSuccess();
    } catch (error) {
      showError(error.response?.data?.message || 'Ошибка при добавлении');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card>
      <Card.Header>
        <h5 className="mb-0">Добавить в 4-буквенный словарь</h5>
      </Card.Header>
      <Card.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Слово (4 буквы)</Form.Label>
            <Form.Control
              type="text"
              value={word}
              onChange={(e) => setWord(e.target.value)}
              placeholder="Введите слово"
              maxLength="4"
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Описание</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Введите описание (необязательно)"
            />
          </Form.Group>

          <Button
            type="submit"
            variant="primary"
            className="w-100"
            disabled={loading}
          >
            {loading ? 'Добавление...' : 'Добавить'}
          </Button>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default AddFourLetterForm;