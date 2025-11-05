import { useState, useEffect } from 'react';
import {
  getPropuestas,
  createPropuesta,
  updatePropuesta,
  deletePropuesta,
  exportPropuestas,
  importPropuestas,
  getCandidatosForSelect,
} from '../services/api';
import { Plus, Edit, Trash2, Download, Upload, Search } from 'lucide-react';

function Propuestas() {
  const [propuestas, setPropuestas] = useState([]);
  const [candidatos, setCandidatos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingPropuesta, setEditingPropuesta] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [formData, setFormData] = useState({
    candidato_id: '',
    tipo_candidato: 'presidencial',
    titulo: '',
    descripcion: '',
    categoria: '',
    eje_tematico: '',
    costo_estimado: '',
    plazo_implementacion: '',
    beneficiarios: '',
    archivo_url: '',
    fecha_publicacion: '',
  });

  useEffect(() => {
    loadPropuestas();
  }, [currentPage, searchTerm]);

  useEffect(() => {
    if (showModal && formData.tipo_candidato) {
      loadCandidatos(formData.tipo_candidato);
    }
  }, [formData.tipo_candidato, showModal]);

  const loadPropuestas = async () => {
    try {
      setLoading(true);
      const response = await getPropuestas({ page: currentPage, search: searchTerm, page_size: 10 });
      setPropuestas(response.data.results || response.data);
      if (response.data.count) {
        setTotalPages(Math.ceil(response.data.count / 10));
      }
    } catch (error) {
      console.error('Error cargando propuestas:', error);
    } finally {
      setLoading(false);
    }
  };

  const loadCandidatos = async (tipo) => {
    try {
      const response = await getCandidatosForSelect(tipo);
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
      if (editingPropuesta) {
        await updatePropuesta(editingPropuesta.id, formData);
      } else {
        await createPropuesta(formData);
      }
      setShowModal(false);
      setEditingPropuesta(null);
      resetForm();
      loadPropuestas();
    } catch (error) {
      console.error('Error guardando propuesta:', error);
      alert('Error al guardar propuesta');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar esta propuesta?')) {
      try {
        await deletePropuesta(id);
        loadPropuestas();
      } catch (error) {
        console.error('Error eliminando propuesta:', error);
        alert('Error al eliminar propuesta');
      }
    }
  };

  const handleExport = async () => {
    try {
      const response = await exportPropuestas();
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `propuestas_${new Date().getTime()}.xlsx`);
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
      await importPropuestas(file);
      alert('Importación exitosa');
      loadPropuestas();
    } catch (error) {
      console.error('Error importando:', error);
      alert('Error al importar');
    }
  };

  const openModal = (propuesta = null) => {
    if (propuesta) {
      setEditingPropuesta(propuesta);
      setFormData(propuesta);
    } else {
      setEditingPropuesta(null);
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
      categoria: '',
      eje_tematico: '',
      costo_estimado: '',
      plazo_implementacion: '',
      beneficiarios: '',
      archivo_url: '',
      fecha_publicacion: '',
    });
    setCandidatos([]);
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Propuestas</h1>
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
            placeholder="Buscar propuesta..."
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
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Categoría</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Eje Temático</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Costo Estimado</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Plazo</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Fecha Pub.</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Acciones</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {propuestas.map((prop) => (
                    <tr key={prop.id}>
                      <td className="px-6 py-4">{prop.candidato_id}</td>
                      <td className="px-6 py-4">
                        <span className="px-2 py-1 rounded-full text-xs bg-purple-100 text-purple-800">
                          {prop.tipo_candidato}
                        </span>
                      </td>
                      <td className="px-6 py-4 max-w-xs truncate">{prop.titulo}</td>
                      <td className="px-6 py-4">{prop.categoria || '-'}</td>
                      <td className="px-6 py-4">{prop.eje_tematico || '-'}</td>
                      <td className="px-6 py-4">
                        {prop.costo_estimado ? `S/ ${parseFloat(prop.costo_estimado).toLocaleString()}` : '-'}
                      </td>
                      <td className="px-6 py-4">{prop.plazo_implementacion || '-'}</td>
                      <td className="px-6 py-4">{prop.fecha_publicacion || '-'}</td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <button onClick={() => openModal(prop)} className="text-blue-600 hover:text-blue-800 mr-3">
                          <Edit size={18} />
                        </button>
                        <button onClick={() => handleDelete(prop.id)} className="text-red-600 hover:text-red-800">
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
            <h2 className="text-2xl font-bold mb-6">{editingPropuesta ? 'Editar' : 'Nueva'} Propuesta</h2>
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
                  <label className="block text-sm font-medium mb-2">Categoría</label>
                  <input
                    type="text"
                    value={formData.categoria}
                    onChange={(e) => setFormData({ ...formData, categoria: e.target.value })}
                    placeholder="Educación, Salud, Economía..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Eje Temático</label>
                  <input
                    type="text"
                    value={formData.eje_tematico}
                    onChange={(e) => setFormData({ ...formData, eje_tematico: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Costo Estimado</label>
                  <input
                    type="number"
                    step="0.01"
                    value={formData.costo_estimado}
                    onChange={(e) => setFormData({ ...formData, costo_estimado: e.target.value })}
                    placeholder="0.00"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Plazo Implementación</label>
                  <input
                    type="text"
                    value={formData.plazo_implementacion}
                    onChange={(e) => setFormData({ ...formData, plazo_implementacion: e.target.value })}
                    placeholder="6 meses, 1 año, 5 años..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Fecha Publicación</label>
                  <input
                    type="date"
                    value={formData.fecha_publicacion}
                    onChange={(e) => setFormData({ ...formData, fecha_publicacion: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">URL Archivo</label>
                  <input
                    type="url"
                    value={formData.archivo_url}
                    onChange={(e) => setFormData({ ...formData, archivo_url: e.target.value })}
                    placeholder="https://..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
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
                    placeholder="Describa quiénes serán beneficiados..."
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

export default Propuestas;