import { useState, useEffect } from 'react';
import {
  getProyectos,
  createProyecto,
  updateProyecto,
  deleteProyecto,
  exportProyectos,
  importProyectos,
  getCandidatosForSelectProyectos,
} from '../services/api';
import { Plus, Edit, Trash2, Download, Upload, Search } from 'lucide-react';

function Proyectos() {
  const [proyectos, setProyectos] = useState([]);
  const [candidatos, setCandidatos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingProyecto, setEditingProyecto] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [formData, setFormData] = useState({
    candidato_id: '',
    tipo_candidato: 'presidencial',
    titulo: '',
    descripcion: '',
    cargo_periodo: '',
    fecha_inicio: '',
    fecha_fin: '',
    monto_invertido: '',
    beneficiarios: '',
    resultados: '',
    ubicacion: '',
    evidencia_url: '',
    imagen_url: '',
    estado: 'completado',
  });

  useEffect(() => {
    loadProyectos();
  }, [currentPage, searchTerm]);

  useEffect(() => {
    if (showModal && formData.tipo_candidato) {
      loadCandidatos(formData.tipo_candidato);
    }
  }, [formData.tipo_candidato, showModal]);

  const loadProyectos = async () => {
    try {
      setLoading(true);
      const response = await getProyectos({ page: currentPage, search: searchTerm, page_size: 10 });
      setProyectos(response.data.results || response.data);
      if (response.data.count) {
        setTotalPages(Math.ceil(response.data.count / 10));
      }
    } catch (error) {
      console.error('Error cargando proyectos:', error);
    } finally {
      setLoading(false);
    }
  };

  const loadCandidatos = async (tipo) => {
    try {
      const response = await getCandidatosForSelectProyectos(tipo);
      setCandidatos(response.data);
    } catch (error) {
      console.error('Error cargando candidatos:', error);
      setCandidatos([]);
    }
  };

  const handleTipoCandidatoChange = (tipo) => {
    setFormData({ 
      ...formData, 
      tipo_candidato: tipo,
      candidato_id: ''
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingProyecto) {
        await updateProyecto(editingProyecto.id, formData);
      } else {
        await createProyecto(formData);
      }
      setShowModal(false);
      setEditingProyecto(null);
      resetForm();
      loadProyectos();
    } catch (error) {
      console.error('Error guardando proyecto:', error);
      alert('Error al guardar proyecto');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar este proyecto?')) {
      try {
        await deleteProyecto(id);
        loadProyectos();
      } catch (error) {
        console.error('Error eliminando proyecto:', error);
        alert('Error al eliminar proyecto');
      }
    }
  };

  const handleExport = async () => {
    try {
      const response = await exportProyectos();
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `proyectos_${new Date().getTime()}.xlsx`);
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
      await importProyectos(file);
      alert('Importación exitosa');
      loadProyectos();
    } catch (error) {
      console.error('Error importando:', error);
      alert('Error al importar');
    }
  };

  const openModal = (proyecto = null) => {
    if (proyecto) {
      setEditingProyecto(proyecto);
      setFormData(proyecto);
    } else {
      setEditingProyecto(null);
      resetForm();
    }
    setShowModal(true);
  };

  const resetForm = () => {
    setFormData({
      candidato_id: '',
      tipo_candidato: 'presidencial',
      titulo: '',
      descripcion: '',
      cargo_periodo: '',
      fecha_inicio: '',
      fecha_fin: '',
      monto_invertido: '',
      beneficiarios: '',
      resultados: '',
      ubicacion: '',
      evidencia_url: '',
      imagen_url: '',
      estado: 'completado',
    });
    setCandidatos([]);
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Proyectos Realizados</h1>
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
            placeholder="Buscar proyecto..."
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
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">ID Candidato</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Tipo</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Título</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Cargo/Periodo</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Ubicación</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Fecha Inicio</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Fecha Fin</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Monto</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Estado</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Acciones</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {proyectos.map((proy) => (
                    <tr key={proy.id}>
                      <td className="px-6 py-4">{proy.candidato_id}</td>
                      <td className="px-6 py-4">
                        <span className="px-2 py-1 rounded-full text-xs bg-indigo-100 text-indigo-800">
                          {proy.tipo_candidato}
                        </span>
                      </td>
                      <td className="px-6 py-4 max-w-xs truncate">{proy.titulo}</td>
                      <td className="px-6 py-4">{proy.cargo_periodo || '-'}</td>
                      <td className="px-6 py-4">{proy.ubicacion || '-'}</td>
                      <td className="px-6 py-4">{proy.fecha_inicio || '-'}</td>
                      <td className="px-6 py-4">{proy.fecha_fin || '-'}</td>
                      <td className="px-6 py-4">
                        {proy.monto_invertido ? `S/ ${parseFloat(proy.monto_invertido).toLocaleString()}` : '-'}
                      </td>
                      <td className="px-6 py-4">
                        <span className={`px-2 py-1 rounded-full text-xs ${
                          proy.estado === 'completado' ? 'bg-green-100 text-green-800' : 
                          proy.estado === 'en_ejecucion' ? 'bg-blue-100 text-blue-800' : 
                          'bg-red-100 text-red-800'
                        }`}>
                          {proy.estado}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <button onClick={() => openModal(proy)} className="text-blue-600 hover:text-blue-800 mr-3">
                          <Edit size={18} />
                        </button>
                        <button onClick={() => handleDelete(proy.id)} className="text-red-600 hover:text-red-800">
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
            <h2 className="text-2xl font-bold mb-6">{editingProyecto ? 'Editar' : 'Nuevo'} Proyecto</h2>
            <form onSubmit={handleSubmit}>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium mb-2">Tipo Candidato *</label>
                  <select
                    required
                    value={formData.tipo_candidato}
                    onChange={(e) => handleTipoCandidatoChange(e.target.value)}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="presidencial">Presidencial</option>
                    <option value="senador_nacional">Senador Nacional</option>
                    <option value="senador_regional">Senador Regional</option>
                    <option value="diputado">Diputado</option>
                    <option value="parlamento_andino">Parlamento Andino</option>
                  </select>
                </div>
                
                <div>
                  <label className="block text-sm font-medium mb-2">Candidato *</label>
                  <select
                    required
                    value={formData.candidato_id}
                    onChange={(e) => setFormData({ ...formData, candidato_id: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="">Seleccionar candidato</option>
                    {candidatos.map(cand => (
                      <option key={cand.id} value={cand.id}>
                        {cand.nombre_completo} - {cand.partido}
                        {cand.numero_lista && ` (Lista ${cand.numero_lista})`}
                        {cand.circunscripcion && ` - ${cand.circunscripcion}`}
                      </option>
                    ))}
                  </select>
                </div>
                
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">Título *</label>
                  <input
                    type="text"
                    required
                    value={formData.titulo}
                    onChange={(e) => setFormData({ ...formData, titulo: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Cargo/Periodo</label>
                  <input
                    type="text"
                    value={formData.cargo_periodo}
                    onChange={(e) => setFormData({ ...formData, cargo_periodo: e.target.value })}
                    placeholder="Alcalde 2015-2020"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Ubicación</label>
                  <input
                    type="text"
                    value={formData.ubicacion}
                    onChange={(e) => setFormData({ ...formData, ubicacion: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Fecha Inicio</label>
                  <input
                    type="date"
                    value={formData.fecha_inicio}
                    onChange={(e) => setFormData({ ...formData, fecha_inicio: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Fecha Fin</label>
                  <input
                    type="date"
                    value={formData.fecha_fin}
                    onChange={(e) => setFormData({ ...formData, fecha_fin: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Monto Invertido</label>
                  <input
                    type="number"
                    step="0.01"
                    value={formData.monto_invertido}
                    onChange={(e) => setFormData({ ...formData, monto_invertido: e.target.value })}
                    placeholder="0.00"
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
                    <option value="completado">Completado</option>
                    <option value="en_ejecucion">En Ejecución</option>
                    <option value="suspendido">Suspendido</option>
                  </select>
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">URL Evidencia</label>
                  <input
                    type="url"
                    value={formData.evidencia_url}
                    onChange={(e) => setFormData({ ...formData, evidencia_url: e.target.value })}
                    placeholder="https://..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">URL Imagen</label>
                  <input
                    type="url"
                    value={formData.imagen_url}
                    onChange={(e) => setFormData({ ...formData, imagen_url: e.target.value })}
                    placeholder="https://..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                  {formData.imagen_url && (
                    <img src={formData.imagen_url} alt="Imagen" className="mt-2 w-32 h-32 object-cover border rounded" />
                  )}
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">Descripción</label>
                  <textarea
                    value={formData.descripcion}
                    onChange={(e) => setFormData({ ...formData, descripcion: e.target.value })}
                    rows="4"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">Beneficiarios</label>
                  <textarea
                    value={formData.beneficiarios}
                    onChange={(e) => setFormData({ ...formData, beneficiarios: e.target.value })}
                    rows="3"
                    placeholder="Describa quiénes fueron beneficiados..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">Resultados</label>
                  <textarea
                    value={formData.resultados}
                    onChange={(e) => setFormData({ ...formData, resultados: e.target.value })}
                    rows="3"
                    placeholder="Describa los resultados obtenidos..."
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

export default Proyectos;