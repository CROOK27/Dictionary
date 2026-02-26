import React from 'react';
import { Row, Col } from 'react-bootstrap';
import AddFourLetterForm from '../components/forms/AddFourLetterForm';
import AddFiveDigitForm from '../components/forms/AddFiveDigitForm';
import AddTranslationForm from '../components/forms/AddTranslationForm';

const AddPage = () => {
  const handleSuccess = () => {
    // Можно добавить уведомление или обновление данных
  };

  return (
    <div>
      <h2 className="mb-4">Добавление новых записей</h2>

      <Row>
        <Col md={4}>
          <AddFourLetterForm onSuccess={handleSuccess} />
        </Col>
        <Col md={4}>
          <AddFiveDigitForm onSuccess={handleSuccess} />
        </Col>
        <Col md={4}>
          <AddTranslationForm onSuccess={handleSuccess} />
        </Col>
      </Row>
    </div>
  );
};

export default AddPage;