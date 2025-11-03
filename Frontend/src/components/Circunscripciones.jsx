import { useState, useEffect } from 'react';
import {
  getCircunscripciones,
  createCircunscripcion,
  updateCircunscripcion,
  deleteCircunscripcion,
  exportCircunscripciones,
  importCircunscripciones,
} from '../services/api';
import { Plus, Edit, Trash2, Download, Upload, Search, Image as ImageIcon } from 'lucide-react';

function Circunscripciones() {
  const [circunscripciones, setCircunscripciones] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingCircunscripcion, setEditingCircunscripcion] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [formData, setFormData] = useState({
    nombre: '',
    codigo: '',
    tipo: 'departamento',
    poblacion: '',
    electores_registrados: '',
    capital: '',
    imagen_url: '',
    bandera_url: '',
    escudo_url: '',
    numero_diputados: 1,
    numero_senadores: 1,
    latitud: '',
    longitud: '',
    descripcion: '',
  });

  useEffect(() => {
    loadCircunscripciones();
  }, [currentPage, searchTerm]);

  const loadCircunscripciones = async () => {
    try {
      setLoading(true);
      const response = await getCircunscripciones({ page: currentPage, search: searchTerm });
      setCircunscripciones(response.data.results || response.data);
      if (response.data.count) {
        setTotalPages(Math.ceil(response.data.count / 50));
      }
    } catch (error) {
      console.error('Error cargando circunscripciones:', error);
      alert('Error al cargar circunscripciones');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingCircunscripcion) {
        await updateCircunscripcion(editingCircunscripcion.id, formData);
      } else {
        await createCircunscripcion(formData);
      }
      setShowModal(false);
      setEditingCircunscripcion(null);
      resetForm();
      loadCircunscripciones();
    } catch (error) {
      console.error('Error guardando circunscripción:', error);
      alert('Error al guardar circunscripción');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar esta circunscripción?')) {
      try {
        await deleteCircunscripcion(id);
        loadCircunscripciones();
      } catch (error) {
        console.error('Error eliminando circunscripción:', error);
        alert('Error al eliminar circunscripción');
      }
    }
  };

  const handleExport = async () => {
    try {
      const response = await exportCircunscripciones();
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `circunscripciones_${new Date().getTime()}.xlsx`);
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
      await importCircunscripciones(file);
      alert('Importación exitosa');
      loadCircunscripciones();
    } catch (error) {
      console.error('Error importando:', error);
      alert('Error al importar');
    }
  };

  const openModal = (circunscripcion = null) => {
    if (circunscripcion) {
      setEditingCircunscripcion(circunscripcion);
      setFormData(circunscripcion);
    } else {
      setEditingCircunscripcion(null);
      resetForm();
    }
    setShowModal(true);
  };

  const resetForm = () => {
    setFormData({
      nombre: '',
      codigo: '',
      tipo: 'departamento',
      poblacion: '',
      electores_registrados: '',
      capital: '',
      imagen_url: '',
      bandera_url: '',
      escudo_url: '',
      numero_diputados: 1,
      numero_senadores: 1,
      latitud: '',
      longitud: '',
      descripcion: '',
    });
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Circunscripciones</h1>
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
            placeholder="Buscar circunscripción..."
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
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Escudo</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Código</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Nombre</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Tipo</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Capital</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Población</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Electores</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Diputados</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Senadores</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Acciones</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {circunscripciones.map((circ) => (
                    <tr key={circ.id}>
                      <td className="px-6 py-4 whitespace-nowrap">
                        {circ.escudo_url ? (
                          <img src={circ.escudo_url} alt={circ.nombre} className="w-10 h-10 object-contain" />
                        ) : (
                          <div className="w-10 h-10 bg-gray-200 rounded flex items-center justify-center">
                            <ImageIcon size={20} className="text-gray-400" />
                          </div>
                        )}
                      </td>
                      <td className="px-6 py-4 font-semibold">{circ.codigo}</td>
                      <td className="px-6 py-4">{circ.nombre}</td>
                      <td className="px-6 py-4">{circ.tipo}</td>
                      <td className="px-6 py-4">{circ.capital}</td>
                      <td className="px-6 py-4">{circ.poblacion?.toLocaleString() || '-'}</td>
                      <td className="px-6 py-4">{circ.electores_registrados?.toLocaleString() || '-'}</td>
                      <td className="px-6 py-4 text-center">{circ.numero_diputados}</td>
                      <td className="px-6 py-4 text-center">{circ.numero_senadores}</td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <button onClick={() => openModal(circ)} className="text-blue-600 hover:text-blue-800 mr-3">
                          <Edit size={18} />
                        </button>
                        <button onClick={() => handleDelete(circ.id)} className="text-red-600 hover:text-red-800">
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
            <h2 className="text-2xl font-bold mb-6">{editingCircunscripcion ? 'Editar' : 'Nueva'} Circunscripción</h2>
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
                  <label className="block text-sm font-medium mb-2">Código *</label>
                  <input
                    type="text"
                    required
                    value={formData.codigo}
                    onChange={(e) => setFormData({ ...formData, codigo: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Tipo</label>
                  <select
                    value={formData.tipo}
                    onChange={(e) => setFormData({ ...formData, tipo: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="departamento">Departamento</option>
                    <option value="provincia_constitucional">Provincia Constitucional</option>
                    <option value="exterior">Exterior</option>
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Capital</label>
                  <input
                    type="text"
                    value={formData.capital}
                    onChange={(e) => setFormData({ ...formData, capital: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Población</label>
                  <input
                    type="number"
                    value={formData.poblacion}
                    onChange={(e) => setFormData({ ...formData, poblacion: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Electores Registrados</label>
                  <input
                    type="number"
                    value={formData.electores_registrados}
                    onChange={(e) => setFormData({ ...formData, electores_registrados: e.target.value })}
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">N° Diputados</label>
                  <input
                    type="number"
                    value={formData.numero_diputados}
                    onChange={(e) => setFormData({ ...formData, numero_diputados: e.target.value })}
                    min="1"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">N° Senadores</label>
                  <input
                    type="number"
                    value={formData.numero_senadores}
                    onChange={(e) => setFormData({ ...formData, numero_senadores: e.target.value })}
                    min="1"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Latitud</label>
                  <input
                    type="number"
                    step="0.00000001"
                    value={formData.latitud}
                    onChange={(e) => setFormData({ ...formData, latitud: e.target.value })}
                    placeholder="-12.0464"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Longitud</label>
                  <input
                    type="number"
                    step="0.00000001"
                    value={formData.longitud}
                    onChange={(e) => setFormData({ ...formData, longitud: e.target.value })}
                    placeholder="-77.0428"
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">URL Imagen</label>
                  <input
                    type="url"
                    value={formData.imagen_url}
                    onChange={(e) => setFormData({ ...formData, imagen_url: e.target.value })}
                    placeholder="https://..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">URL Bandera</label>
                  <input
                    type="url"
                    value={formData.bandera_url}
                    onChange={(e) => setFormData({ ...formData, bandera_url: e.target.value })}
                    placeholder="https://..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">URL Escudo</label>
                  <input
                    type="url"
                    value={formData.escudo_url}
                    onChange={(e) => setFormData({ ...formData, escudo_url: e.target.value })}
                    placeholder="https://..."
                    className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                {formData.escudo_url && (
                  <div className="flex space-x-2">
                    <img src={formData.escudo_url} alt="Escudo" className="w-16 h-16 object-contain border rounded" />
                  </div>
                )}
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

export default Circunscripciones;