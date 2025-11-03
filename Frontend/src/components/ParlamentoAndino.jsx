import { useState, useEffect } from 'react';
import {
  getParlamentoAndino,
  createParlamentoAndino,
  updateParlamentoAndino,
  deleteParlamentoAndino,
  exportParlamentoAndino,
  importParlamentoAndino,
} from '../services/api';
import { getPartidos } from '../services/api';
import { Plus, Edit, Trash2, Download, Upload, Search, Image as ImageIcon } from 'lucide-react';

function ParlamentoAndino() {
  const [candidatos, setCandidatos] = useState([]);
  const [partidos, setPartidos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingCandidato, setEditingCandidato] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [formData, setFormData] = useState({
    partido: '',
    nombre: '',
    apellidos: '',
    dni: '',
    foto_url: '',
    fecha_nacimiento: '',
    edad: '',
    genero: 'M',
    profesion: '',
    experiencia_politica: '',
    experiencia_internacional: '',
    biografia: '',
    hoja_vida_url: '',
    posicion_lista: '',
    numero_preferencial: '',
    idiomas: '',
    estado: 'inscrito',
    fecha_inscripcion: '',
  });

  useEffect(() => {
    loadData();
  }, [currentPage, searchTerm]);

  const loadData = async () => {
    try {
      setLoading(true);
      const [candidatosRes, partidosRes] = await Promise.all([
        getParlamentoAndino({ page: currentPage, search: searchTerm }),
        getPartidos(),
      ]);
      setCandidatos(candidatosRes.data.results || candidatosRes.data);
      setPartidos(partidosRes.data.results || partidosRes.data);
      if (candidatosRes.data.count) {
        setTotalPages(Math.ceil(candidatosRes.data.count / 50));
      }
    } catch (error) {
      console.error('Error cargando datos:', error);
    } finally {
      setLoading(false);
    }
  };

  const cleanFormData = (data) => {
  const cleaned = { ...data };
  
  // Campos de fecha
  const dateFields = ['fecha_nacimiento', 'fecha_inscripcion'];
  
  // Campos numéricos
  const numericFields = ['edad', 'posicion_lista', 'numero_preferencial', 'partido', 'circunscripcion'];
  
  Object.keys(cleaned).forEach(key => {
    // Convertir strings vacíos a null
    if (cleaned[key] === '' || cleaned[key] === undefined) {
      cleaned[key] = null;
    }
    
    // Validar formato de fechas
    if (dateFields.includes(key) && cleaned[key] !== null) {
      // Verificar que la fecha tenga formato YYYY-MM-DD
      const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
      if (!dateRegex.test(cleaned[key])) {
        cleaned[key] = null; // Si no tiene formato correcto, convertir a null
      }
    }
    
    // Convertir strings a números
    if (numericFields.includes(key) && cleaned[key] !== null && cleaned[key] !== '') {
      const parsed = parseInt(cleaned[key]);
      cleaned[key] = isNaN(parsed) ? null : parsed;
    }
  });
  
  return cleaned;
};

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingCandidato) {
        await updateParlamentoAndino(editingCandidato.id, formData);
      } else {
        await createParlamentoAndino(formData);
      }
      setShowModal(false);
      setEditingCandidato(null);
      resetForm();
      loadData();
    } catch (error) {
      if (error.response) {
      console.error('Error detalle:', error.response.data);
      alert('Error al guardar: ' + JSON.stringify(error.response.data));
    } else {
      console.error('Error inesperado:', error);
      alert('Error inesperado al guardar');
    }
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar este candidato?')) {
      try {
        await deleteParlamentoAndino(id);
        loadData();
      } catch (error) {
        console.error('Error eliminando candidato:', error);
        alert('Error al eliminar candidato');
      }
    }
  };

  const handleExport = async () => {
    try {
      const response = await exportParlamentoAndino();
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `parlamento_andino_${new Date().getTime()}.xlsx`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error('Error exportando:', error);
      alert('Error al exportar');
    }
  };

  const handleImport = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    try {
      await importParlamentoAndino(file);
      alert('Importación exitosa');
      loadData();
    } catch (error) {
      console.error('Error importando:', error);
      alert('Error al importar');
    }
  };

  const openModal = (candidato = null) => {
    if (candidato) {
      setEditingCandidato(candidato);
      setFormData(candidato);
    } else {
      setEditingCandidato(null);
      resetForm();
    }
    setShowModal(true);
  };

  const resetForm = () => {
    setFormData({
      partido: '',
      nombre: '',
      apellidos: '',
      dni: '',
      foto_url: '',
      fecha_nacimiento: '',
      edad: '',
      genero: 'M',
      profesion: '',
      experiencia_politica: '',
      experiencia_internacional: '',
      biografia: '',
      hoja_vida_url: '',
      posicion_lista: '',
      numero_preferencial: '',
      idiomas: '',
      estado: 'inscrito',
      fecha_inscripcion: '',
    });
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Parlamento Andino</h1>
        <div className="flex space-x-2">
          <button onClick={handleExport} className="flex items-center space-x-2 bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700">
            <Download size={18} />
            <span>Exportar</span>
          </button>
          <label className="flex items-center space-x-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 cursor-pointer">
            <Upload size={18} />
            <span>Importar</span>
            <input type="file" accept=".xlsx,.xls" onChange={handleImport} className="hidden" />
          </label>
          <button onClick={() => openModal()} className="flex items-center space-x-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
            <Plus size={18} />
            <span>Nuevo</span>
          </button>
        </div>
      </div>

      <div className="mb-4">
        <div className="relative">
          <Search className="absolute left-3 top-3 text-gray-400" size={20} />
          <input
            type="text"
            placeholder="Buscar candidato..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
      </div>

      {loading ? (
        <div className="text-center py-8">Cargando...</div>
      ) : (
        <>
          <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <div className="overflow-x-auto">
              <table className="min-w-full">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Foto</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Partido</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Nombre Completo</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">DNI</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Género</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Profesión</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Idiomas</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Posición</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">N° Pref.</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Estado</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Acciones</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {candidatos.map((cand) => (
                    <tr key={cand.id}>
                      <td className="px-6 py-4">
                        {cand.foto_url ? (
                          <img src={cand.foto_url} alt={cand.nombre} className="w-12 h-12 rounded-full object-cover" />
                        ) : (
                          <div className="w-12 h-12 bg-gray-200 rounded-full flex items-center justify-center">
                            <ImageIcon size={24} className="text-gray-400" />
                          </div>
                        )}
                      </td>
                      <td className="px-6 py-4">{cand.partido_siglas || '-'}</td>
                      <td className="px-6 py-4">{cand.nombre} {cand.apellidos}</td>
                      <td className="px-6 py-4">{cand.dni}</td>
                      <td className="px-6 py-4">{cand.genero}</td>
                      <td className="px-6 py-4">{cand.profesion || '-'}</td>
                      <td className="px-6 py-4">{cand.idiomas || '-'}</td>
                      <td className="px-6 py-4 text-center">{cand.posicion_lista}</td>
                      <td className="px-6 py-4 text-center">{cand.numero_preferencial || '-'}</td>
                      <td className="px-6 py-4">
                        <span className={`px-2 py-1 rounded-full text-xs ${
                          cand.estado === 'aprobado' ? 'bg-green-100 text-green-800' : 
                          cand.estado === 'observado' ? 'bg-yellow-100 text-yellow-800' : 
                          cand.estado === 'excluido' ? 'bg-red-100 text-red-800' :
                          'bg-blue-100 text-blue-800'
                        }`}>
                          {cand.estado}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <button onClick={() => openModal(cand)} className="text-blue-600 hover:text-blue-800 mr-3">
                          <Edit size={18} />
                        </button>
                        <button onClick={() => handleDelete(cand.id)} className="text-red-600 hover:text-red-800">
                          <Trash2 size={18} />
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>

          <div className="flex justify-center mt-4 space-x-2">
            <button
              onClick={() => setCurrentPage(p => Math.max(1, p - 1))}
              disabled={currentPage === 1}
              className="px-4 py-2 bg-blue-600 text-white rounded disabled:opacity-50"
            >
              Anterior
            </button>
            <span className="px-4 py-2">Página {currentPage} de {totalPages}</span>
            <button
              onClick={() => setCurrentPage(p => Math.min(totalPages, p + 1))}
              disabled={currentPage === totalPages}
              className="px-4 py-2 bg-blue-600 text-white rounded disabled:opacity-50"
            >
              Siguiente
            </button>
          </div>
        </>
      )}

      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-lg p-8 max-w-4xl w-full max-h-screen overflow-y-auto">
            <h2 className="text-2xl font-bold mb-6">{editingCandidato ? 'Editar' : 'Nuevo'} Candidato Parlamento Andino</h2>
            <form onSubmit={handleSubmit}>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium mb-2">Partido *</label>
                  <select
                    required
                    value={formData.partido}
                    onChange={(e) => setFormData({ ...formData, partido: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="">Seleccionar partido</option>
                    {partidos.map(p => (
                      <option key={p.id} value={p.id}>{p.nombre} ({p.siglas})</option>
                    ))}
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Posición Lista *</label>
                  <input
                    type="number"
                    required
                    value={formData.posicion_lista}
                    onChange={(e) => setFormData({ ...formData, posicion_lista: e.target.value })}
                    min="1"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Nombre *</label>
                  <input
                    type="text"
                    required
                    value={formData.nombre}
                    onChange={(e) => setFormData({ ...formData, nombre: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Apellidos *</label>
                  <input
                    type="text"
                    required
                    value={formData.apellidos}
                    onChange={(e) => setFormData({ ...formData, apellidos: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">DNI *</label>
                  <input
                    type="text"
                    required
                    maxLength="8"
                    value={formData.dni}
                    onChange={(e) => setFormData({ ...formData, dni: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Género</label>
                  <select
                    value={formData.genero}
                    onChange={(e) => setFormData({ ...formData, genero: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="M">Masculino</option>
                    <option value="F">Femenino</option>
                    <option value="Otro">Otro</option>
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Fecha Nacimiento</label>
                  <input
                    type="date"
                    value={formData.fecha_nacimiento}
                    onChange={(e) => setFormData({ ...formData, fecha_nacimiento: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Edad</label>
                  <input
                    type="number"
                    value={formData.edad}
                    onChange={(e) => setFormData({ ...formData, edad: e.target.value })}
                    min="18"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Profesión</label>
                  <input
                    type="text"
                    value={formData.profesion}
                    onChange={(e) => setFormData({ ...formData, profesion: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">N° Preferencial</label>
                  <input
                    type="number"
                    value={formData.numero_preferencial}
                    onChange={(e) => setFormData({ ...formData, numero_preferencial: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Estado</label>
                  <select
                    value={formData.estado}
                    onChange={(e) => setFormData({ ...formData, estado: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="inscrito">Inscrito</option>
                    <option value="observado">Observado</option>
                    <option value="excluido">Excluido</option>
                    <option value="aprobado">Aprobado</option>
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Fecha Inscripción</label>
                  <input
                    type="date"
                    value={formData.fecha_inscripcion}
                    onChange={(e) => setFormData({ ...formData, fecha_inscripcion: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">Idiomas</label>
                  <input
                    type="text"
                    value={formData.idiomas}
                    onChange={(e) => setFormData({ ...formData, idiomas: e.target.value })}
                    placeholder="Español, Inglés, Francés"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">URL Foto</label>
                  <input
                    type="url"
                    value={formData.foto_url}
                    onChange={(e) => setFormData({ ...formData, foto_url: e.target.value })}
                    placeholder="https://..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                  {formData.foto_url && (
                    <img src={formData.foto_url} alt="Foto" className="mt-2 w-24 h-24 rounded-full object-cover border" />
                  )}
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">URL Hoja de Vida</label>
                  <input
                    type="url"
                    value={formData.hoja_vida_url}
                    onChange={(e) => setFormData({ ...formData, hoja_vida_url: e.target.value })}
                    placeholder="https://..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">Experiencia Política</label>
                  <textarea
                    value={formData.experiencia_politica}
                    onChange={(e) => setFormData({ ...formData, experiencia_politica: e.target.value })}
                    rows="3"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">Experiencia Internacional</label>
                  <textarea
                    value={formData.experiencia_internacional}
                    onChange={(e) => setFormData({ ...formData, experiencia_internacional: e.target.value })}
                    rows="3"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">Biografía</label>
                  <textarea
                    value={formData.biografia}
                    onChange={(e) => setFormData({ ...formData, biografia: e.target.value })}
                    rows="4"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
              </div>

              <div className="flex justify-end space-x-2 mt-6">
                <button
                  type="button"
                  onClick={() => setShowModal(false)}
                  className="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600"
                >
                  Cancelar
                </button>
                <button type="submit" className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                  Guardar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default ParlamentoAndino;