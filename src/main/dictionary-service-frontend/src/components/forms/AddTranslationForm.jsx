import React, { useState, useEffect } from 'react';
import { Form, Button, Card } from 'react-bootstrap';
import { fourLetterService, fiveDigitService, translationService } from '../../services/dictionaryService';
import { showSuccess, showError } from '../common/Notification';

const AddTranslationForm = ({ onSuccess }) => {
  const [translatedWord, setTranslatedWord] = useState('');
  const [fourLetterId, setFourLetterId] = useState('');
  const [fiveDigitId, setFiveDigitId] = useState('');
  const [fourLetterWords, setFourLetterWords] = useState([]);
  const [fiveDigitWords, setFiveDigitWords] = useState([]);
  const [loading, setLoading] = useState(false);
  const [loadingData, setLoadingData] = useState(true);

  useEffect(() => {
    loadSelects();
  }, []);

  const loadSelects = async () => {
    try {
      const [fourLetterRes, fiveDigitRes] = await Promise.all([
        fourLetterService.getAll(),
        fiveDigitService.getAll()
      ]);
      setFourLetterWords(fourLetterRes.data);
      setFiveDigitWords(fiveDigitRes.data);
    } catch (error) {
      showError('Ошибка при загрузке данных');
    } finally {
      setLoadingData(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!translatedWord.trim()) {
      showError('Введите перевод');
      return;
    }

    if (!fourLetterId) {
      showError('Выберите 4-буквенное слово');
      return;
    }

    if (!fiveDigitId) {
      showError('Выберите 5-значное слово');
      return;
    }

    try {
      setLoading(true);
      await translationService.create({
        translatedWord,
        fourLetterDictionaryId: fourLetterId,
        fiveDigitDictionaryId: fiveDigitId
      });
      showSuccess('Перевод успешно добавлен');
      setTranslatedWord('');
      setFourLetterId('');
      setFiveDigitId('');
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
        <h5 className="mb-0">Добавить перевод</h5>
      </Card.Header>
      <Card.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Перевод</Form.Label>
            <Form.Control
              type="text"
              value={translatedWord}
              onChange={(e) => setTranslatedWord(e.target.value)}
              placeholder="Введите перевод"
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>4-буквенное слово</Form.Label>
            <Form.Select
              value={fourLetterId}
              onChange={(e) => setFourLetterId(e.target.value)}
              required
              disabled={loadingData}
            >
              <option value="">Выберите слово</option>
              {fourLetterWords.map(word => (
                <option key={word.id} value={word.id}>
                  {word.word} {word.description ? `- ${word.description}` : ''}
                </option>
              ))}
            </Form.Select>
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>5-значное слово</Form.Label>
            <Form.Select
              value={fiveDigitId}
              onChange={(e) => setFiveDigitId(e.target.value)}
              required
              disabled={loadingData}
            >
              <option value="">Выберите слово</option>
              {fiveDigitWords.map(word => (
                <option key={word.id} value={word.id}>
                  {word.word} {word.description ? `- ${word.description}` : ''}
                </option>
              ))}
            </Form.Select>
          </Form.Group>

          <Button
            type="submit"
            variant="primary"
            className="w-100"
            disabled={loading || loadingData}
          >
            {loading ? 'Добавление...' : 'Добавить перевод'}
          </Button>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default AddTranslationForm;