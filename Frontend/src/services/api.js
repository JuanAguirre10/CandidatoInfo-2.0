import axios from 'axios';

const API_URL = 'http://localhost:8000/api';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});


export const login = (credentials) => api.post('/usuarios/login/', credentials);

export const getPartidos = (params) => api.get('/partidos/', { params });
export const createPartido = (data) => api.post('/partidos/', data);
export const updatePartido = (id, data) => api.put(`/partidos/${id}/`, data);
export const deletePartido = (id) => api.delete(`/partidos/${id}/`);
export const exportPartidos = () => api.get('/partidos/exportar_excel/', { responseType: 'blob' });
export const importPartidos = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/partidos/importar_excel/', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const getCircunscripciones = (params) => api.get('/circunscripciones/', { params });
export const createCircunscripcion = (data) => api.post('/circunscripciones/', data);
export const updateCircunscripcion = (id, data) => api.put(`/circunscripciones/${id}/`, data);
export const deleteCircunscripcion = (id) => api.delete(`/circunscripciones/${id}/`);
export const exportCircunscripciones = () => api.get('/circunscripciones/exportar_excel/', { responseType: 'blob' });
export const importCircunscripciones = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/circunscripciones/importar_excel/', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const getCandidatosPresidenciales = (params) => api.get('/candidatos/presidenciales/', { params });
export const createCandidatoPresidencial = (data) => api.post('/candidatos/presidenciales/', data);
export const updateCandidatoPresidencial = (id, data) => api.put(`/candidatos/presidenciales/${id}/`, data);
export const deleteCandidatoPresidencial = (id) => api.delete(`/candidatos/presidenciales/${id}/`);
export const exportCandidatosPresidenciales = () => api.get('/candidatos/presidenciales/exportar_excel/', { responseType: 'blob' });
export const importCandidatosPresidenciales = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/candidatos/presidenciales/importar_excel/', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const getSenadoresNacionales = (params) => api.get('/candidatos/senadores-nacionales/', { params });
export const createSenadorNacional = (data) => api.post('/candidatos/senadores-nacionales/', data);
export const updateSenadorNacional = (id, data) => api.put(`/candidatos/senadores-nacionales/${id}/`, data);
export const deleteSenadorNacional = (id) => api.delete(`/candidatos/senadores-nacionales/${id}/`);
export const exportSenadoresNacionales = () => api.get('/candidatos/senadores-nacionales/exportar_excel/', { responseType: 'blob' });
export const importSenadoresNacionales = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/candidatos/senadores-nacionales/importar_excel/', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const getSenadoresRegionales = (params) => api.get('/candidatos/senadores-regionales/', { params });
export const createSenadorRegional = (data) => api.post('/candidatos/senadores-regionales/', data);
export const updateSenadorRegional = (id, data) => api.put(`/candidatos/senadores-regionales/${id}/`, data);
export const deleteSenadorRegional = (id) => api.delete(`/candidatos/senadores-regionales/${id}/`);
export const exportSenadoresRegionales = () => api.get('/candidatos/senadores-regionales/exportar_excel/', { responseType: 'blob' });
export const importSenadoresRegionales = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/candidatos/senadores-regionales/importar_excel/', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const getDiputados = (params) => api.get('/candidatos/diputados/', { params });
export const createDiputado = (data) => api.post('/candidatos/diputados/', data);
export const updateDiputado = (id, data) => api.put(`/candidatos/diputados/${id}/`, data);
export const deleteDiputado = (id) => api.delete(`/candidatos/diputados/${id}/`);
export const exportDiputados = () => api.get('/candidatos/diputados/exportar_excel/', { responseType: 'blob' });
export const importDiputados = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/candidatos/diputados/importar_excel/', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const getParlamentoAndino = (params) => api.get('/candidatos/parlamento-andino/', { params });
export const createParlamentoAndino = (data) => api.post('/candidatos/parlamento-andino/', data);
export const updateParlamentoAndino = (id, data) => api.put(`/candidatos/parlamento-andino/${id}/`, data);
export const deleteParlamentoAndino = (id) => api.delete(`/candidatos/parlamento-andino/${id}/`);
export const exportParlamentoAndino = () => api.get('/candidatos/parlamento-andino/exportar_excel/', { responseType: 'blob' });
export const importParlamentoAndino = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/candidatos/parlamento-andino/importar_excel/', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const getSimulacroVotos = (params) => api.get('/simulacro/votos/', { params });
export const getEstadisticasSimulacro = (params) => api.get('/simulacro/votos/estadisticas/', { params });
export const getResultadosPorCandidato = (params) => api.get('/simulacro/votos/resultados_por_candidato/', { params });
export const exportSimulacroVotos = () => api.get('/simulacro/votos/exportar_excel/', { responseType: 'blob' });

export const getPropuestas = (params) => api.get('/informacion/propuestas/', { params });
export const createPropuesta = (data) => api.post('/informacion/propuestas/', data);
export const updatePropuesta = (id, data) => api.put(`/informacion/propuestas/${id}/`, data);
export const deletePropuesta = (id) => api.delete(`/informacion/propuestas/${id}/`);
export const exportPropuestas = () => api.get('/informacion/propuestas/exportar_excel/', { responseType: 'blob' });
export const importPropuestas = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/informacion/propuestas/importar_excel/', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const getProyectos = (params) => api.get('/informacion/proyectos/', { params });
export const createProyecto = (data) => api.post('/informacion/proyectos/', data);
export const updateProyecto = (id, data) => api.put(`/informacion/proyectos/${id}/`, data);
export const deleteProyecto = (id) => api.delete(`/informacion/proyectos/${id}/`);
export const exportProyectos = () => api.get('/informacion/proyectos/exportar_excel/', { responseType: 'blob' });
export const importProyectos = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/informacion/proyectos/importar_excel/', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const getDenuncias = (params) => api.get('/informacion/denuncias/', { params });
export const createDenuncia = (data) => api.post('/informacion/denuncias/', data);
export const updateDenuncia = (id, data) => api.put(`/informacion/denuncias/${id}/`, data);
export const deleteDenuncia = (id) => api.delete(`/informacion/denuncias/${id}/`);
export const exportDenuncias = () => api.get('/informacion/denuncias/exportar_excel/', { responseType: 'blob' });
export const importDenuncias = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/informacion/denuncias/importar_excel/', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};

export const getPartidosForSelect = () => api.get('/partidos/select_list/');
export const getCircunscripcionesForSelect = () => api.get('/circunscripciones/select_list/');
export const getCandidatosForSelect = (tipo) => api.get('/informacion/propuestas/candidatos_list/', { params: { tipo } });
export const getCandidatosForSelectProyectos = (tipo) => api.get('/informacion/proyectos/candidatos_list/', { params: { tipo } });
export const getCandidatosForSelectDenuncias = (tipo) => api.get('/informacion/denuncias/candidatos_list/', { params: { tipo } });
export const getTodasCircunscripciones = () => api.get('/circunscripciones/todas/');
export const getTodosCandidatosPresidenciales = () => api.get('/candidatos/presidenciales/todos/');
export const getTodosSenadoresNacionales = () => api.get('/candidatos/senadores-nacionales/todos/');
export const getTodosSenadoresRegionales = () => api.get('/candidatos/senadores-regionales/todos/');
export const getTodosDiputados = () => api.get('/candidatos/diputados/todos/');
export const getTodosParlamentoAndino = () => api.get('/candidatos/parlamento-andino/todos/');
export default api;