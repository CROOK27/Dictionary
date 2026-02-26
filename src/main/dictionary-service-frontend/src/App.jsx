import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, NavLink } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import { Container, Nav, Navbar } from 'react-bootstrap';
import { BiBook, BiPlusCircle } from 'react-icons/bi';
import DictionariesPage from './pages/DictionariesPage';
import AddPage from './pages/AddPage';
import './styles/App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar bg="primary" variant="dark" expand="lg" className="mb-4">
          <Container>
            <Navbar.Brand href="/">
              <BiBook className="me-2" />
              Dictionary Service
            </Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
              <Nav className="me-auto">
                <Nav.Link as={NavLink} to="/" end>
                  Словари
                </Nav.Link>
                <Nav.Link as={NavLink} to="/add">
                  <BiPlusCircle className="me-1" />
                  Добавление
                </Nav.Link>
              </Nav>
            </Navbar.Collapse>
          </Container>
        </Navbar>

        <Container>
          <Routes>
            <Route path="/" element={<DictionariesPage />} />
            <Route path="/add" element={<AddPage />} />
          </Routes>
        </Container>

        <ToastContainer
          position="top-right"
          autoClose={3000}
          hideProgressBar={false}
          newestOnTop
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
        />
      </div>
    </Router>
  );
}

export default App;