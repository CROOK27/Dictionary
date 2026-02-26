import api from './api';

// 4-буквенный словарь
export const fourLetterService = {
  getAll: () => api.get('/four-letter-dictionary'),
  getById: (id) => api.get(`/four-letter-dictionary/${id}`),
  getByWord: (word) => api.get(`/four-letter-dictionary/word/${word}`),
  create: (data) => api.post('/four-letter-dictionary', data),
  update: (id, data) => api.put(`/four-letter-dictionary/${id}`, data),
  delete: (id) => api.delete(`/four-letter-dictionary/${id}`),
};

// 5-значный словарь
export const fiveDigitService = {
  getAll: () => api.get('/five-digit-dictionary'),
  getById: (id) => api.get(`/five-digit-dictionary/${id}`),
  getByWord: (word) => api.get(`/five-digit-dictionary/word/${word}`),
  create: (data) => api.post('/five-digit-dictionary', data),
  update: (id, data) => api.put(`/five-digit-dictionary/${id}`, data),
  delete: (id) => api.delete(`/five-digit-dictionary/${id}`),
};

// Переводы
export const translationService = {
  getAll: () => api.get('/translations'),
  getById: (id) => api.get(`/translations/${id}`),
  getByFiveDigit: (id) => api.get(`/translations/five-digit/${id}`),
  getByFourLetter: (id) => api.get(`/translations/four-letter/${id}`),
  getByPair: (fiveId, fourId) => api.get(`/translations/pair?fiveDigitDictId=${fiveId}&fourLetterDictId=${fourId}`),
  create: (data) => api.post('/translations', data),
  update: (id, data) => api.put(`/translations/${id}`, data),
  delete: (id) => api.delete(`/translations/${id}`),
};

// Поиск
export const searchService = {
  search: (text, dictionary, type) => {
    // Здесь нужно реализовать поиск на бэкенде
    // Пока возвращаем все данные и фильтруем на клиенте
    return Promise.all([
      fourLetterService.getAll(),
      fiveDigitService.getAll(),
      translationService.getAll()
    ]).then(([fourLetter, fiveDigit, translations]) => {
      let results = [];
      const searchText = text.toLowerCase();

      if (dictionary === 'all' || dictionary === 'fourLetter') {
        fourLetter.data.forEach(item => {
          if (type === 'key' && item.word.toLowerCase().includes(searchText)) {
            results.push({ ...item, type: 'fourLetter' });
          } else if (type === 'value' && item.description?.toLowerCase().includes(searchText)) {
            results.push({ ...item, type: 'fourLetter' });
          }
        });
      }

      if (dictionary === 'all' || dictionary === 'fiveDigit') {
        fiveDigit.data.forEach(item => {
          if (type === 'key' && item.word.toLowerCase().includes(searchText)) {
            results.push({ ...item, type: 'fiveDigit' });
          } else if (type === 'value' && item.description?.toLowerCase().includes(searchText)) {
            results.push({ ...item, type: 'fiveDigit' });
          }
        });
      }

      if (dictionary === 'all' || dictionary === 'translations') {
        translations.data.forEach(item => {
          if (type === 'key' && item.translatedWord.toLowerCase().includes(searchText)) {
            results.push({ ...item, type: 'translation' });
          }
        });
      }

      return results;
    });
  }
};