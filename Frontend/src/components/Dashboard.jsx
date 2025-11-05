import { useState, useEffect } from 'react';
import { Bar, Pie } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
} from 'chart.js';
import { 
  getPartidos, 
  getCandidatosPresidenciales, 
  getCircunscripciones, 
  getSimulacroVotos,
  getSenadoresNacionales,
  getSenadoresRegionales,
  getDiputados,
  getParlamentoAndino,
  getPropuestas,
  getProyectos,
  getDenuncias
} from '../services/api';
import { Users, MapPin, Vote, FileText, UserCheck, Building, AlertTriangle, Briefcase, MessageSquare } from 'lucide-react';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, ArcElement);

function Dashboard() {
  const [stats, setStats] = useState({
    partidos: 0,
    circunscripciones: 0,
    presidenciales: 0,
    senadoresNacionales: 0,
    senadoresRegionales: 0,
    diputados: 0,
    parlamentoAndino: 0,
    propuestas: 0,
    proyectos: 0,
    denuncias: 0,
    votosSimulacro: 0,
  });
  const [votosPorPartido, setVotosPorPartido] = useState({
    presidencial: [],
    senador_nacional: [],
    senador_regional: [],
    diputado: [],
    parlamento_andino: []
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadStats();
    loadVotosPorTipo();
  }, []);

  const loadStats = async () => {
    try {
      const [
        partidosRes,
        candidatosRes,
        circRes,
        votosRes,
        senadoresNacRes,
        senadoresRegRes,
        diputadosRes,
        parlamentoRes,
        propuestasRes,
        proyectosRes,
        denunciasRes
      ] = await Promise.all([
        getPartidos(),
        getCandidatosPresidenciales(),
        getCircunscripciones(),
        getSimulacroVotos(),
        getSenadoresNacionales(),
        getSenadoresRegionales(),
        getDiputados(),
        getParlamentoAndino(),
        getPropuestas(),
        getProyectos(),
        getDenuncias()
      ]);

      setStats({
        partidos: partidosRes.data.count || partidosRes.data.length,
        circunscripciones: circRes.data.count || circRes.data.length,
        presidenciales: candidatosRes.data.count || candidatosRes.data.length,
        senadoresNacionales: senadoresNacRes.data.count || senadoresNacRes.data.length,
        senadoresRegionales: senadoresRegRes.data.count || senadoresRegRes.data.length,
        diputados: diputadosRes.data.count || diputadosRes.data.length,
        parlamentoAndino: parlamentoRes.data.count || parlamentoRes.data.length,
        propuestas: propuestasRes.data.count || propuestasRes.data.length,
        proyectos: proyectosRes.data.count || proyectosRes.data.length,
        denuncias: denunciasRes.data.count || denunciasRes.data.length,
        votosSimulacro: votosRes.data.count || votosRes.data.length,
      });
    } catch (error) {
      console.error('Error cargando estadisticas:', error);
    } finally {
      setLoading(false);
    }
  };

  const loadVotosPorTipo = async () => {
    try {
      const tiposEleccion = ['presidencial', 'senador_nacional', 'senador_regional', 'diputado', 'parlamento_andino'];
      const resultados = {};

      for (const tipo of tiposEleccion) {
        const response = await getSimulacroVotos({ tipo_eleccion: tipo });
        const votos = response.data.results || response.data;
        
        const votosPorPartidoMap = {};
        votos.forEach(voto => {
          const partido = voto.partido_siglas || 'Sin Partido';
          if (!votosPorPartidoMap[partido]) {
            votosPorPartidoMap[partido] = {
              partido: partido,
              votos: 0,
              logo: voto.partido_logo
            };
          }
          votosPorPartidoMap[partido].votos++;
        });

        resultados[tipo] = Object.values(votosPorPartidoMap)
          .sort((a, b) => b.votos - a.votos)
          .slice(0, 5);
      }

      setVotosPorPartido(resultados);
    } catch (error) {
      console.error('Error cargando votos por tipo:', error);
    }
  };

  const createChartData = (datos, titulo) => {
    const colores = ['#3b82f6', '#ef4444', '#10b981', '#f59e0b', '#8b5cf6'];
    
    return {
      labels: datos.map(d => d.partido),
      datasets: [
        {
          label: 'Votos',
          data: datos.map(d => d.votos),
          backgroundColor: colores,
        },
      ],
    };
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-xl text-gray-600">Cargando estadisticas...</div>
      </div>
    );
  }

  return (
    <div>
      <h1 className="text-3xl font-bold mb-8 text-gray-800">Dashboard Electoral</h1>

      <div className="mb-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm font-medium">Partidos</p>
                <h3 className="text-3xl font-bold text-blue-600">{stats.partidos}</h3>
              </div>
              <Users size={48} className="text-blue-600" />
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm font-medium">Circunscripciones</p>
                <h3 className="text-3xl font-bold text-yellow-600">{stats.circunscripciones}</h3>
              </div>
              <MapPin size={48} className="text-yellow-600" />
            </div>
          </div>
        </div>
      </div>

      <div className="mb-6">
        <h2 className="text-sm font-semibold text-gray-400 uppercase tracking-wider mb-3">Candidatos</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm font-medium">Presidenciales</p>
                <h3 className="text-3xl font-bold text-green-600">{stats.presidenciales}</h3>
              </div>
              <UserCheck size={48} className="text-green-600" />
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm font-medium">Senadores Nacionales</p>
                <h3 className="text-3xl font-bold text-purple-600">{stats.senadoresNacionales}</h3>
              </div>
              <Building size={48} className="text-purple-600" />
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm font-medium">Senadores Regionales</p>
                <h3 className="text-3xl font-bold text-indigo-600">{stats.senadoresRegionales}</h3>
              </div>
              <Building size={48} className="text-indigo-600" />
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm font-medium">Diputados</p>
                <h3 className="text-3xl font-bold text-teal-600">{stats.diputados}</h3>
              </div>
              <Users size={48} className="text-teal-600" />
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm font-medium">Parlamento Andino</p>
                <h3 className="text-3xl font-bold text-orange-600">{stats.parlamentoAndino}</h3>
              </div>
              <Building size={48} className="text-orange-600" />
            </div>
          </div>
        </div>
      </div>

      <div className="mb-6">
        <h2 className="text-sm font-semibold text-gray-400 uppercase tracking-wider mb-3">Informacion</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm font-medium">Propuestas</p>
                <h3 className="text-3xl font-bold text-blue-500">{stats.propuestas}</h3>
              </div>
              <MessageSquare size={48} className="text-blue-500" />
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm font-medium">Proyectos</p>
                <h3 className="text-3xl font-bold text-cyan-600">{stats.proyectos}</h3>
              </div>
              <Briefcase size={48} className="text-cyan-600" />
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm font-medium">Denuncias</p>
                <h3 className="text-3xl font-bold text-red-600">{stats.denuncias}</h3>
              </div>
              <AlertTriangle size={48} className="text-red-600" />
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm font-medium">Votos Simulacro</p>
                <h3 className="text-3xl font-bold text-pink-600">{stats.votosSimulacro}</h3>
              </div>
              <Vote size={48} className="text-pink-600" />
            </div>
          </div>
        </div>
      </div>

      <div className="mb-6">
        <h2 className="text-2xl font-bold mb-4 text-gray-800">Resultados por Tipo de Elección</h2>
        
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {votosPorPartido.presidencial && votosPorPartido.presidencial.length > 0 && (
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-lg font-bold mb-4 text-green-700">Elección Presidencial</h3>
              <Bar 
                data={createChartData(votosPorPartido.presidencial, 'Presidencial')}
                options={{
                  responsive: true,
                  plugins: {
                    legend: { display: false },
                    title: { display: false }
                  }
                }}
              />
              <div className="mt-4 space-y-2">
                {votosPorPartido.presidencial.map((dato, idx) => (
                  <div key={idx} className="flex items-center justify-between text-sm">
                    <span className="font-medium">{dato.partido}</span>
                    <span className="text-gray-600">{dato.votos} votos</span>
                  </div>
                ))}
              </div>
            </div>
          )}

          {votosPorPartido.senador_nacional && votosPorPartido.senador_nacional.length > 0 && (
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-lg font-bold mb-4 text-purple-700">Senadores Nacionales</h3>
              <Bar 
                data={createChartData(votosPorPartido.senador_nacional, 'Senador Nacional')}
                options={{
                  responsive: true,
                  plugins: {
                    legend: { display: false },
                    title: { display: false }
                  }
                }}
              />
              <div className="mt-4 space-y-2">
                {votosPorPartido.senador_nacional.map((dato, idx) => (
                  <div key={idx} className="flex items-center justify-between text-sm">
                    <span className="font-medium">{dato.partido}</span>
                    <span className="text-gray-600">{dato.votos} votos</span>
                  </div>
                ))}
              </div>
            </div>
          )}

          {votosPorPartido.senador_regional && votosPorPartido.senador_regional.length > 0 && (
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-lg font-bold mb-4 text-indigo-700">Senadores Regionales</h3>
              <Bar 
                data={createChartData(votosPorPartido.senador_regional, 'Senador Regional')}
                options={{
                  responsive: true,
                  plugins: {
                    legend: { display: false },
                    title: { display: false }
                  }
                }}
              />
              <div className="mt-4 space-y-2">
                {votosPorPartido.senador_regional.map((dato, idx) => (
                  <div key={idx} className="flex items-center justify-between text-sm">
                    <span className="font-medium">{dato.partido}</span>
                    <span className="text-gray-600">{dato.votos} votos</span>
                  </div>
                ))}
              </div>
            </div>
          )}

          {votosPorPartido.diputado && votosPorPartido.diputado.length > 0 && (
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-lg font-bold mb-4 text-teal-700">Diputados</h3>
              <Bar 
                data={createChartData(votosPorPartido.diputado, 'Diputado')}
                options={{
                  responsive: true,
                  plugins: {
                    legend: { display: false },
                    title: { display: false }
                  }
                }}
              />
              <div className="mt-4 space-y-2">
                {votosPorPartido.diputado.map((dato, idx) => (
                  <div key={idx} className="flex items-center justify-between text-sm">
                    <span className="font-medium">{dato.partido}</span>
                    <span className="text-gray-600">{dato.votos} votos</span>
                  </div>
                ))}
              </div>
            </div>
          )}

          {votosPorPartido.parlamento_andino && votosPorPartido.parlamento_andino.length > 0 && (
            <div className="bg-white p-6 rounded-lg shadow-md">
              <h3 className="text-lg font-bold mb-4 text-orange-700">Parlamento Andino</h3>
              <Bar 
                data={createChartData(votosPorPartido.parlamento_andino, 'Parlamento Andino')}
                options={{
                  responsive: true,
                  plugins: {
                    legend: { display: false },
                    title: { display: false }
                  }
                }}
              />
              <div className="mt-4 space-y-2">
                {votosPorPartido.parlamento_andino.map((dato, idx) => (
                  <div key={idx} className="flex items-center justify-between text-sm">
                    <span className="font-medium">{dato.partido}</span>
                    <span className="text-gray-600">{dato.votos} votos</span>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default Dashboard;