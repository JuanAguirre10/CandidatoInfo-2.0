import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import Login from './components/Login';
import Layout from './components/Layout';
import Dashboard from './components/Dashboard';
import Partidos from './components/Partidos';
import Circunscripciones from './components/Circunscripciones';
import CandidatosPresidenciales from './components/CandidatosPresidenciales';
import SenadoresNacionales from './components/SenadoresNacionales';
import SenadoresRegionales from './components/SenadoresRegionales';
import Diputados from './components/Diputados';
import ParlamentoAndino from './components/ParlamentoAndino';
import Simulacro from './components/Simulacro';
import Propuestas from './components/Propuestas';
import Proyectos from './components/Proyectos';
import Denuncias from './components/Denuncias';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
      setIsAuthenticated(true);
    }
  }, []);

  const handleLogin = (userData) => {
    setUser(userData);
    setIsAuthenticated(true);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  const handleLogout = () => {
    setUser(null);
    setIsAuthenticated(false);
    localStorage.removeItem('user');
  };

  if (!isAuthenticated) {
    return <Login onLogin={handleLogin} />;
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout user={user} onLogout={handleLogout} />}>
          <Route index element={<Dashboard />} />
          <Route path="partidos" element={<Partidos />} />
          <Route path="circunscripciones" element={<Circunscripciones />} />
          <Route path="candidatos/presidenciales" element={<CandidatosPresidenciales />} />
          <Route path="candidatos/senadores-nacionales" element={<SenadoresNacionales />} />
          <Route path="candidatos/senadores-regionales" element={<SenadoresRegionales />} />
          <Route path="candidatos/diputados" element={<Diputados />} />
          <Route path="candidatos/parlamento-andino" element={<ParlamentoAndino />} />
          <Route path="simulacro" element={<Simulacro />} />
          <Route path="propuestas" element={<Propuestas />} />
          <Route path="proyectos" element={<Proyectos />} />
          <Route path="denuncias" element={<Denuncias />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;