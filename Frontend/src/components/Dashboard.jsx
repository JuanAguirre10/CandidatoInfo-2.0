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
import { getPartidos, getCandidatosPresidenciales, getCircunscripciones, getSimulacroVotos } from '../services/api';
import { Users, MapPin, Vote, FileText } from 'lucide-react';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, ArcElement);

function Dashboard() {
  const [stats, setStats] = useState({
    partidos: 0,
    candidatosPresidenciales: 0,
    circunscripciones: 0,
    votosSimulacro: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    try {
      const [partidosRes, candidatosRes, circRes, votosRes] = await Promise.all([
        getPartidos(),
        getCandidatosPresidenciales(),
        getCircunscripciones(),
        getSimulacroVotos(),
      ]);

      setStats({
        partidos: partidosRes.data.count || partidosRes.data.length,
        candidatosPresidenciales: candidatosRes.data.count || candidatosRes.data.length,
        circunscripciones: circRes.data.count || circRes.data.length,
        votosSimulacro: votosRes.data.count || votosRes.data.length,
      });
    } catch (error) {
      console.error('Error cargando estadísticas:', error);
    } finally {
      setLoading(false);
    }
  };

  const barData = {
    labels: ['Partidos', 'Candidatos', 'Circunscripciones', 'Votos'],
    datasets: [
      {
        label: 'Estadísticas Generales',
        data: [stats.partidos, stats.candidatosPresidenciales, stats.circunscripciones, stats.votosSimulacro],
        backgroundColor: ['#3b82f6', '#10b981', '#f59e0b', '#ef4444'],
      },
    ],
  };

  const pieData = {
    labels: ['Partidos', 'Candidatos', 'Circunscripciones'],
    datasets: [
      {
        data: [stats.partidos, stats.candidatosPresidenciales, stats.circunscripciones],
        backgroundColor: ['#3b82f6', '#10b981', '#f59e0b'],
      },
    ],
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-xl text-gray-600">Cargando estadísticas...</div>
      </div>
    );
  }

  return (
    <div>
      <h1 className="text-3xl font-bold mb-8 text-gray-800">Dashboard</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-gray-500 text-sm">Partidos Políticos</p>
              <h3 className="text-3xl font-bold text-blue-600">{stats.partidos}</h3>
            </div>
            <Users size={48} className="text-blue-600" />
          </div>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-gray-500 text-sm">Candidatos Presidenciales</p>
              <h3 className="text-3xl font-bold text-green-600">{stats.candidatosPresidenciales}</h3>
            </div>
            <FileText size={48} className="text-green-600" />
          </div>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-gray-500 text-sm">Circunscripciones</p>
              <h3 className="text-3xl font-bold text-yellow-600">{stats.circunscripciones}</h3>
            </div>
            <MapPin size={48} className="text-yellow-600" />
          </div>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-gray-500 text-sm">Votos Simulacro</p>
              <h3 className="text-3xl font-bold text-red-600">{stats.votosSimulacro}</h3>
            </div>
            <Vote size={48} className="text-red-600" />
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-xl font-bold mb-4">Estadísticas por Categoría</h2>
          <Bar data={barData} />
        </div>

        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-xl font-bold mb-4">Distribución</h2>
          <Pie data={pieData} />
        </div>
      </div>
    </div>
  );
}

export default Dashboard;