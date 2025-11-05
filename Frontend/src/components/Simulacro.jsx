import { useState, useEffect } from 'react';
import {
  getSimulacroVotos,
  getEstadisticasSimulacro,
  exportSimulacroVotos,
} from '../services/api';
import { Download, Search, BarChart3, Users, TrendingUp } from 'lucide-react';

function Simulacro() {
  const [votos, setVotos] = useState([]);
  const [estadisticas, setEstadisticas] = useState(null);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [filtroTipo, setFiltroTipo] = useState('');
  const [filtroMes, setFiltroMes] = useState('');
  const [filtroAnio, setFiltroAnio] = useState(new Date().getFullYear());

  useEffect(() => {
    loadData();
  }, [currentPage, searchTerm, filtroTipo, filtroMes, filtroAnio]);

  const loadData = async () => {
    try {
      setLoading(true);
      const params = {
        page: currentPage,
        search: searchTerm,
        tipo_eleccion: filtroTipo,
        mes_simulacro: filtroMes,
        anio_simulacro: filtroAnio,
      };
      
      const [votosRes, estadisticasRes] = await Promise.all([
        getSimulacroVotos(params),
        getEstadisticasSimulacro(params),
      ]);
      
      setVotos(votosRes.data.results || votosRes.data);
      setEstadisticas(estadisticasRes.data);
      
      if (votosRes.data.count) {
        setTotalPages(Math.ceil(votosRes.data.count / 10));
      }
    } catch (error) {
      console.error('Error cargando datos:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleExport = async () => {
    try {
      const params = {
        tipo_eleccion: filtroTipo,
        mes_simulacro: filtroMes,
        anio_simulacro: filtroAnio,
      };
      const response = await exportSimulacroVotos(params);
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `simulacro_votos_${new Date().getTime()}.xlsx`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error('Error exportando:', error);
      alert('Error al exportar');
    }
  };

  const getTipoEleccionLabel = (tipo) => {
    const labels = {
      'presidencial': 'Presidencial',
      'senador_nacional': 'Senador Nacional',
      'senador_regional': 'Senador Regional',
      'diputado': 'Diputado',
      'parlamento_andino': 'Parlamento Andino',
    };
    return labels[tipo] || tipo;
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Simulacro de Votación</h1>
        <div className="flex space-x-2">
          <button onClick={handleExport} className="flex items-center space-x-2 bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700">
            <Download size={18} />
            <span>Exportar</span>
          </button>
        </div>
      </div>

      {estadisticas && (
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
          <div className="bg-white rounded-lg shadow-md p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm">Total Votos</p>
                <p className="text-3xl font-bold text-blue-600">{estadisticas.total_votos?.toLocaleString() || 0}</p>
              </div>
              <Users size={40} className="text-blue-600" />
            </div>
          </div>
          
          <div className="bg-white rounded-lg shadow-md p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm">Votantes Únicos</p>
                <p className="text-3xl font-bold text-green-600">{estadisticas.votantes_unicos?.toLocaleString() || 0}</p>
              </div>
              <TrendingUp size={40} className="text-green-600" />
            </div>
          </div>
          
          <div className="bg-white rounded-lg shadow-md p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm">Participación</p>
                <p className="text-3xl font-bold text-purple-600">{estadisticas.porcentaje_participacion || 0}%</p>
              </div>
              <BarChart3 size={40} className="text-purple-600" />
            </div>
          </div>
          
          <div className="bg-white rounded-lg shadow-md p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm">Elecciones Activas</p>
                <p className="text-3xl font-bold text-orange-600">{estadisticas.tipos_eleccion || 0}</p>
              </div>
              <BarChart3 size={40} className="text-orange-600" />
            </div>
          </div>
        </div>
      )}

      <div className="bg-white rounded-lg shadow-md p-4 mb-4">
        <div className="grid grid-cols-1 md:grid-cols-5 gap-4">
          <div className="relative">
            <Search className="absolute left-3 top-3 text-gray-400" size={20} />
            <input
              type="text"
              placeholder="Buscar por DNI o nombre..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          
          <select
            value={filtroTipo}
            onChange={(e) => setFiltroTipo(e.target.value)}
            className="px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="">Todos los tipos</option>
            <option value="presidencial">Presidencial</option>
            <option value="senador_nacional">Senador Nacional</option>
            <option value="senador_regional">Senador Regional</option>
            <option value="diputado">Diputado</option>
            <option value="parlamento_andino">Parlamento Andino</option>
          </select>
          
          <select
            value={filtroMes}
            onChange={(e) => setFiltroMes(e.target.value)}
            className="px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="">Todos los meses</option>
            <option value="1">Enero</option>
            <option value="2">Febrero</option>
            <option value="3">Marzo</option>
            <option value="4">Abril</option>
            <option value="5">Mayo</option>
            <option value="6">Junio</option>
            <option value="7">Julio</option>
            <option value="8">Agosto</option>
            <option value="9">Septiembre</option>
            <option value="10">Octubre</option>
            <option value="11">Noviembre</option>
            <option value="12">Diciembre</option>
          </select>
          
          <input
            type="number"
            value={filtroAnio}
            onChange={(e) => setFiltroAnio(e.target.value)}
            min="2020"
            max="2030"
            className="px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          
          <button
            onClick={() => {
              setFiltroTipo('');
              setFiltroMes('');
              setFiltroAnio(new Date().getFullYear());
              setSearchTerm('');
            }}
            className="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600"
          >
            Limpiar
          </button>
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
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">DNI</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Votante</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Tipo Elección</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Partido</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Candidato</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Circunscripción</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Mes/Año</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Fecha Voto</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {votos.map((voto) => (
                    <tr key={voto.id} className="hover:bg-gray-50">
                      <td className="px-6 py-4 font-semibold">{voto.dni}</td>
                      <td className="px-6 py-4">{voto.nombre_completo || '-'}</td>
                      <td className="px-6 py-4">
                        <span className="px-2 py-1 rounded-full text-xs bg-blue-100 text-blue-800">
                          {voto.tipo_eleccion_display || getTipoEleccionLabel(voto.tipo_eleccion)}
                        </span>
                      </td>
                      <td className="px-6 py-4">
                        <div className="flex items-center space-x-2">
                          {voto.partido_logo && (
                            <img 
                              src={voto.partido_logo} 
                              alt={voto.partido_siglas}
                              className="w-10 h-10 object-contain rounded"
                            />
                          )}
                          <div>
                            <p className="font-semibold text-sm">{voto.partido_siglas || '-'}</p>
                            <p className="text-xs text-gray-500">{voto.partido_nombre || '-'}</p>
                          </div>
                        </div>
                      </td>
                      <td className="px-6 py-4">
                        <div>
                          <p className="font-medium">{voto.candidato_nombre || `ID: ${voto.candidato_id}`}</p>
                          <p className="text-xs text-gray-500">ID: {voto.candidato_id}</p>
                        </div>
                      </td>
                      <td className="px-6 py-4">{voto.circunscripcion_nombre || '-'}</td>
                      <td className="px-6 py-4">{voto.mes_simulacro}/{voto.anio_simulacro}</td>
                      <td className="px-6 py-4 text-sm">{new Date(voto.fecha_voto).toLocaleString()}</td>
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

      {estadisticas && estadisticas.resultados_por_candidato && (
        <div className="bg-white rounded-lg shadow-md p-6 mt-6">
          <h2 className="text-xl font-bold mb-4">Resultados por Candidato</h2>
          <div className="overflow-x-auto">
            <table className="min-w-full">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Tipo Elección</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Candidato</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Total Votos</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Porcentaje</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {estadisticas.resultados_por_candidato.map((resultado, index) => (
                  <tr key={index}>
                    <td className="px-6 py-4">
                      <span className="px-2 py-1 rounded-full text-xs bg-purple-100 text-purple-800">
                        {getTipoEleccionLabel(resultado.tipo_eleccion)}
                      </span>
                    </td>
                    <td className="px-6 py-4">
                      <div className="flex items-center space-x-2">
                        <span className="font-semibold">{resultado.candidato_nombre || `ID: ${resultado.candidato_id}`}</span>
                      </div>
                    </td>
                    <td className="px-6 py-4 text-center font-semibold">{resultado.votos_candidato}</td>
                    <td className="px-6 py-4">
                      <div className="flex items-center">
                        <div className="w-full bg-gray-200 rounded-full h-2.5 mr-2">
                          <div 
                            className="bg-blue-600 h-2.5 rounded-full" 
                            style={{ width: `${resultado.porcentaje || 0}%` }}
                          ></div>
                        </div>
                        <span className="text-sm font-semibold min-w-[50px]">{resultado.porcentaje || 0}%</span>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
}

export default Simulacro;