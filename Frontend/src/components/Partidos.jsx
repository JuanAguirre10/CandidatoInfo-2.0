import { useState, useEffect } from 'react';
import {
  getPartidos,
  createPartido,
  updatePartido,
  deletePartido,
  exportPartidos,
  importPartidos,
} from '../services/api';
import { Plus, Edit, Trash2, Download, Upload, Search, Image as ImageIcon } from 'lucide-react';

function Partidos() {
  const [partidos, setPartidos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingPartido, setEditingPartido] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [formData, setFormData] = useState({
    nombre: '',
    siglas: '',
    logo_url: '',
    color_principal: '#3b82f6',
    tipo: 'partido',
    ideologia: '',
    lider: '',
    secretario_general: '',
    fundacion_año: '',
    descripcion: '',
    sitio_web: '',
    estado: 'activo',
  });

  useEffect(() => {
    loadPartidos();
  }, [currentPage, searchTerm]);

  const loadPartidos = async () => {
    try {
      setLoading(true);
      const response = await getPartidos({ page: currentPage, search: searchTerm });
      setPartidos(response.data.results || response.data);
      if (response.data.count) {
        setTotalPages(Math.ceil(response.data.count / 50));
      }
    } catch (error) {
      console.error('Error cargando partidos:', error);
      alert('Error al cargar partidos');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingPartido) {
        await updatePartido(editingPartido.id, formData);
      } else {
        await createPartido(formData);
      }
      setShowModal(false);
      setEditingPartido(null);
      resetForm();
      loadPartidos();
    } catch (error) {
      console.error('Error guardando partido:', error);
      alert('Error al guardar partido');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar este partido?')) {
      try {
        await deletePartido(id);
        loadPartidos();
      } catch (error) {
        console.error('Error eliminando partido:', error);
        alert('Error al eliminar partido');
      }
    }
  };

  const handleExport = async () => {
    try {
      const response = await exportPartidos();
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `partidos_${new Date().getTime()}.xlsx`);
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
      await importPartidos(file);
      alert('Importación exitosa');
      loadPartidos();
    } catch (error) {
      console.error('Error importando:', error);
      alert('Error al importar');
    }
  };

  const openModal = (partido = null) => {
    if (partido) {
      setEditingPartido(partido);
      setFormData(partido);
    } else {
      setEditingPartido(null);
      resetForm();
    }
    setShowModal(true);
  };

  const resetForm = () => {
    setFormData({
      nombre: '',
      siglas: '',
      logo_url: '',
      color_principal: '#3b82f6',
      tipo: 'partido',
      ideologia: '',
      lider: '',
      secretario_general: '',
      fundacion_año: '',
      descripcion: '',
      sitio_web: '',
      estado: 'activo',
    });
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Partidos Políticos</h1>
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
            placeholder="Buscar partido..."
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
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Logo</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Nombre</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Siglas</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Tipo</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Ideología</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Líder</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Fundación</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Estado</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Acciones</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {partidos.map((partido) => (
                    <tr key={partido.id}>
                      <td className="px-6 py-4 whitespace-nowrap">
                        {partido.logo_url ? (
                          <img src={partido.logo_url} alt={partido.nombre} className="w-10 h-10 object-contain" />
                        ) : (
                          <div className="w-10 h-10 bg-gray-200 rounded flex items-center justify-center">
                            <ImageIcon size={20} className="text-gray-400" />
                          </div>
                        )}
                      </td>
                      <td className="px-6 py-4">{partido.nombre}</td>
                      <td className="px-6 py-4">
                        <span 
                          className="px-2 py-1 rounded text-white font-semibold"
                          style={{ backgroundColor: partido.color_principal || '#3b82f6' }}
                        >
                          {partido.siglas}
                        </span>
                      </td>
                      <td className="px-6 py-4">{partido.tipo}</td>
                      <td className="px-6 py-4">{partido.ideologia || '-'}</td>
                      <td className="px-6 py-4">{partido.lider || '-'}</td>
                      <td className="px-6 py-4">{partido.fundacion_año || '-'}</td>
                      <td className="px-6 py-4">
                        <span className={`px-2 py-1 rounded-full text-xs ${
                          partido.estado === 'activo' ? 'bg-green-100 text-green-800' : 
                          partido.estado === 'inactivo' ? 'bg-gray-100 text-gray-800' : 
                          'bg-red-100 text-red-800'
                        }`}>
                          {partido.estado}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <button onClick={() => openModal(partido)} className="text-blue-600 hover:text-blue-800 mr-3">
                          <Edit size={18} />
                        </button>
                        <button onClick={() => handleDelete(partido.id)} className="text-red-600 hover:text-red-800">
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
            <h2 className="text-2xl font-bold mb-6">{editingPartido ? 'Editar' : 'Nuevo'} Partido</h2>
            <form onSubmit={handleSubmit}>
              <div className="grid grid-cols-2 gap-4">
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
                  <label className="block text-sm font-medium mb-2">Siglas *</label>
                  <input
                    type="text"
                    required
                    value={formData.siglas}
                    onChange={(e) => setFormData({ ...formData, siglas: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">URL del Logo</label>
                  <input
                    type="url"
                    value={formData.logo_url}
                    onChange={(e) => setFormData({ ...formData, logo_url: e.target.value })}
                    placeholder="https://..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                  {formData.logo_url && (
                    <img src={formData.logo_url} alt="Preview" className="mt-2 w-20 h-20 object-contain border rounded" />
                  )}
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Color Principal</label>
                  <input
                    type="color"
                    value={formData.color_principal}
                    onChange={(e) => setFormData({ ...formData, color_principal: e.target.value })}
                    className="w-full h-10 px-3 py-1 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Tipo</label>
                  <select
                    value={formData.tipo}
                    onChange={(e) => setFormData({ ...formData, tipo: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="partido">Partido</option>
                    <option value="alianza">Alianza</option>
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Estado</label>
                  <select
                    value={formData.estado}
                    onChange={(e) => setFormData({ ...formData, estado: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="activo">Activo</option>
                    <option value="inactivo">Inactivo</option>
                    <option value="inhabilitado">Inhabilitado</option>
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Ideología</label>
                  <input
                    type="text"
                    value={formData.ideologia}
                    onChange={(e) => setFormData({ ...formData, ideologia: e.target.value })}
                    placeholder="Izquierda, Derecha, Centro, etc."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Líder</label>
                  <input
                    type="text"
                    value={formData.lider}
                    onChange={(e) => setFormData({ ...formData, lider: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Secretario General</label>
                  <input
                    type="text"
                    value={formData.secretario_general}
                    onChange={(e) => setFormData({ ...formData, secretario_general: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Año de Fundación</label>
                  <input
                    type="number"
                    value={formData.fundacion_año}
                    onChange={(e) => setFormData({ ...formData, fundacion_año: e.target.value })}
                    min="1800"
                    max={new Date().getFullYear()}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium mb-2">Sitio Web</label>
                  <input
                    type="url"
                    value={formData.sitio_web}
                    onChange={(e) => setFormData({ ...formData, sitio_web: e.target.value })}
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

export default Partidos;