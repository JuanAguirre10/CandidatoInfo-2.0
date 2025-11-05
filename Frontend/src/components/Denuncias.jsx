import { useState, useEffect } from 'react';
import {
  getDenuncias,
  createDenuncia,
  updateDenuncia,
  deleteDenuncia,
  exportDenuncias,
  importDenuncias,
  getCandidatosForSelectDenuncias,
} from '../services/api';
import { Plus, Edit, Trash2, Download, Upload, Search } from 'lucide-react';

function Denuncias() {
  const [denuncias, setDenuncias] = useState([]);
  const [candidatos, setCandidatos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingDenuncia, setEditingDenuncia] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [formData, setFormData] = useState({
    candidato_id: '',
    tipo_candidato: 'presidencial',
    titulo: '',
    descripcion: '',
    tipo_denuncia: '',
    fecha_denuncia: '',
    entidad_denunciante: '',
    fuente: '',
    url_fuente: '',
    estado_proceso: 'investigando',
    monto_involucrado: '',
    documento_url: '',
    gravedad: 'leve',
  });

  useEffect(() => {
    loadDenuncias();
  }, [currentPage, searchTerm]);

  useEffect(() => {
    if (showModal && formData.tipo_candidato) {
      loadCandidatos(formData.tipo_candidato);
    }
  }, [formData.tipo_candidato, showModal]);

  const loadDenuncias = async () => {
    try {
      setLoading(true);
      const response = await getDenuncias({ page: currentPage, search: searchTerm, page_size: 10 });
      setDenuncias(response.data.results || response.data);
      if (response.data.count) {
        setTotalPages(Math.ceil(response.data.count / 10));
      }
    } catch (error) {
      console.error('Error cargando denuncias:', error);
    } finally {
      setLoading(false);
    }
  };

  const loadCandidatos = async (tipo) => {
    try {
      const response = await getCandidatosForSelectDenuncias(tipo);
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
      if (editingDenuncia) {
        await updateDenuncia(editingDenuncia.id, formData);
      } else {
        await createDenuncia(formData);
      }
      setShowModal(false);
      setEditingDenuncia(null);
      resetForm();
      loadDenuncias();
    } catch (error) {
      console.error('Error guardando denuncia:', error);
      alert('Error al guardar denuncia');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar esta denuncia?')) {
      try {
        await deleteDenuncia(id);
        loadDenuncias();
      } catch (error) {
        console.error('Error eliminando denuncia:', error);
        alert('Error al eliminar denuncia');
      }
    }
  };

  const handleExport = async () => {
    try {
      const response = await exportDenuncias();
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `denuncias_${new Date().getTime()}.xlsx`);
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
      await importDenuncias(file);
      alert('Importación exitosa');
      loadDenuncias();
    } catch (error) {
      console.error('Error importando:', error);
      alert('Error al importar');
    }
  };

  const openModal = (denuncia = null) => {
    if (denuncia) {
      setEditingDenuncia(denuncia);
      setFormData(denuncia);
    } else {
      setEditingDenuncia(null);
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
      tipo_denuncia: '',
      fecha_denuncia: '',
      entidad_denunciante: '',
      fuente: '',
      url_fuente: '',
      estado_proceso: 'investigando',
      monto_involucrado: '',
      documento_url: '',
      gravedad: 'leve',
    });
    setCandidatos([]);
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Denuncias</h1>
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
            placeholder="Buscar denuncia..."
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
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Tipo Denuncia</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Entidad</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Fecha</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Estado</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Gravedad</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Monto</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Acciones</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {denuncias.map((den) => (
                    <tr key={den.id}>
                      <td className="px-6 py-4">{den.candidato_id}</td>
                      <td className="px-6 py-4">
                        <span className="px-2 py-1 rounded-full text-xs bg-orange-100 text-orange-800">
                          {den.tipo_candidato}
                        </span>
                      </td>
                      <td className="px-6 py-4 max-w-xs truncate">{den.titulo}</td>
                      <td className="px-6 py-4">{den.tipo_denuncia || '-'}</td>
                      <td className="px-6 py-4">{den.entidad_denunciante || '-'}</td>
                      <td className="px-6 py-4">{den.fecha_denuncia || '-'}</td>
                      <td className="px-6 py-4">
                        <span className={`px-2 py-1 rounded-full text-xs ${
                          den.estado_proceso === 'comprobada' ? 'bg-red-100 text-red-800' : 
                          den.estado_proceso === 'sentenciado' ? 'bg-red-200 text-red-900' : 
                          den.estado_proceso === 'absuelto' ? 'bg-green-100 text-green-800' :
                          den.estado_proceso === 'archivada' ? 'bg-gray-100 text-gray-800' :
                          'bg-yellow-100 text-yellow-800'
                        }`}>
                          {den.estado_proceso}
                        </span>
                      </td>
                      <td className="px-6 py-4">
                        <span className={`px-2 py-1 rounded-full text-xs ${
                          den.gravedad === 'muy_grave' ? 'bg-red-100 text-red-800' : 
                          den.gravedad === 'grave' ? 'bg-orange-100 text-orange-800' : 
                          den.gravedad === 'moderada' ? 'bg-yellow-100 text-yellow-800' :
                          'bg-blue-100 text-blue-800'
                        }`}>
                          {den.gravedad}
                        </span>
                      </td>
                      <td className="px-6 py-4">
                        {den.monto_involucrado ? `S/ ${parseFloat(den.monto_involucrado).toLocaleString()}` : '-'}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <button onClick={() => openModal(den)} className="text-blue-600 hover:text-blue-800 mr-3">
                          <Edit size={18} />
                        </button>
                        <button onClick={() => handleDelete(den.id)} className="text-red-600 hover:text-red-800">
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
            <h2 className="text-2xl font-bold mb-6">{editingDenuncia ? 'Editar' : 'Nueva'} Denuncia</h2>
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
                  <label className="block text-sm font-medium mb-2">Tipo Denuncia</label>
                  <input
                    type="text"
                    value={formData.tipo_denuncia}
                    onChange={(e) => setFormData({ ...formData, tipo_denuncia: e.target.value })}
                    placeholder="Corrupción, Peculado, Malversación..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Fecha Denuncia</label>
                  <input
                    type="date"
                    value={formData.fecha_denuncia}
                    onChange={(e) => setFormData({ ...formData, fecha_denuncia: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Entidad Denunciante</label>
                  <input
                    type="text"
                    value={formData.entidad_denunciante}
                    onChange={(e) => setFormData({ ...formData, entidad_denunciante: e.target.value })}
                    placeholder="Fiscalía, Contraloría, etc."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Fuente</label>
                  <input
                    type="text"
                    value={formData.fuente}
                    onChange={(e) => setFormData({ ...formData, fuente: e.target.value })}
                    placeholder="Medio de comunicación, entidad..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Estado Proceso</label>
                  <select
                    value={formData.estado_proceso}
                    onChange={(e) => setFormData({ ...formData, estado_proceso: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="investigando">Investigando</option>
                    <option value="archivada">Archivada</option>
                    <option value="comprobada">Comprobada</option>
                    <option value="sentenciado">Sentenciado</option>
                    <option value="absuelto">Absuelto</option>
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Gravedad</label>
                  <select
                    value={formData.gravedad}
                    onChange={(e) => setFormData({ ...formData, gravedad: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="leve">Leve</option>
                    <option value="moderada">Moderada</option>
                    <option value="grave">Grave</option>
                    <option value="muy_grave">Muy Grave</option>
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Monto Involucrado</label>
                  <input
                    type="number"
                    step="0.01"
                    value={formData.monto_involucrado}
                    onChange={(e) => setFormData({ ...formData, monto_involucrado: e.target.value })}
                    placeholder="0.00"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">URL Fuente</label>
                  <input
                    type="url"
                    value={formData.url_fuente}
                    onChange={(e) => setFormData({ ...formData, url_fuente: e.target.value })}
                    placeholder="https://..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">URL Documento</label>
                  <input
                    type="url"
                    value={formData.documento_url}
                    onChange={(e) => setFormData({ ...formData, documento_url: e.target.value })}
                    placeholder="https://..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">Descripción</label>
                  <textarea
                    value={formData.descripcion}
                    onChange={(e) => setFormData({ ...formData, descripcion: e.target.value })}
                    rows="5"
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

export default Denuncias;