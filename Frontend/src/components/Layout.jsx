import { Outlet, Link, useLocation } from 'react-router-dom';
import { LogOut, Home, Users, MapPin, FileText, Vote, MessageSquare, Briefcase, AlertTriangle } from 'lucide-react';

function Layout({ user, onLogout }) {
  const location = useLocation();

  const isActive = (path) => location.pathname === path;

  return (
    <div className="min-h-screen bg-gray-100">
      <nav className="bg-white shadow-lg">
        <div className="max-w-7xl mx-auto px-4">
          <div className="flex justify-between h-16">
            <div className="flex items-center">
              <h1 className="text-2xl font-bold text-blue-600">CandidatoInfo Admin</h1>
            </div>
            <div className="flex items-center space-x-4">
              <span className="text-gray-700">
                {user?.nombre_completo || user?.username}
              </span>
              <button
                onClick={onLogout}
                className="flex items-center space-x-2 bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700"
              >
                <LogOut size={18} />
                <span>Salir</span>
              </button>
            </div>
          </div>
        </div>
      </nav>

      <div className="flex">
        <aside className="w-64 bg-white shadow-lg min-h-screen">
          <nav className="p-4 space-y-2">
            <Link
              to="/"
              className={`flex items-center space-x-3 px-4 py-3 rounded-lg ${
                isActive('/') ? 'bg-blue-100 text-blue-600' : 'hover:bg-gray-100'
              }`}
            >
              <Home size={20} />
              <span>Dashboard</span>
            </Link>

            <Link
              to="/partidos"
              className={`flex items-center space-x-3 px-4 py-3 rounded-lg ${
                isActive('/partidos') ? 'bg-blue-100 text-blue-600' : 'hover:bg-gray-100'
              }`}
            >
              <Users size={20} />
              <span>Partidos</span>
            </Link>

            <Link
              to="/circunscripciones"
              className={`flex items-center space-x-3 px-4 py-3 rounded-lg ${
                isActive('/circunscripciones') ? 'bg-blue-100 text-blue-600' : 'hover:bg-gray-100'
              }`}
            >
              <MapPin size={20} />
              <span>Circunscripciones</span>
            </Link>

            <div className="pt-4 pb-2">
              <h3 className="px-4 text-xs font-semibold text-gray-500 uppercase">Candidatos</h3>
            </div>

            <Link to="/candidatos/presidenciales" className="flex items-center space-x-3 px-4 py-3 rounded-lg hover:bg-gray-100">
              <span>Presidenciales</span>
            </Link>
            <Link to="/candidatos/senadores-nacionales" className="flex items-center space-x-3 px-4 py-3 rounded-lg hover:bg-gray-100">
              <span>Senadores Nacionales</span>
            </Link>
            <Link to="/candidatos/senadores-regionales" className="flex items-center space-x-3 px-4 py-3 rounded-lg hover:bg-gray-100">
              <span>Senadores Regionales</span>
            </Link>
            <Link to="/candidatos/diputados" className="flex items-center space-x-3 px-4 py-3 rounded-lg hover:bg-gray-100">
              <span>Diputados</span>
            </Link>
            <Link to="/candidatos/parlamento-andino" className="flex items-center space-x-3 px-4 py-3 rounded-lg hover:bg-gray-100">
              <span>Parlamento Andino</span>
            </Link>

            <div className="pt-4 pb-2">
              <h3 className="px-4 text-xs font-semibold text-gray-500 uppercase">Información</h3>
            </div>

            <Link
              to="/propuestas"
              className={`flex items-center space-x-3 px-4 py-3 rounded-lg ${
                isActive('/propuestas') ? 'bg-blue-100 text-blue-600' : 'hover:bg-gray-100'
              }`}
            >
              <MessageSquare size={20} />
              <span>Propuestas</span>
            </Link>

            <Link
              to="/proyectos"
              className={`flex items-center space-x-3 px-4 py-3 rounded-lg ${
                isActive('/proyectos') ? 'bg-blue-100 text-blue-600' : 'hover:bg-gray-100'
              }`}
            >
              <Briefcase size={20} />
              <span>Proyectos</span>
            </Link>

            <Link
              to="/denuncias"
              className={`flex items-center space-x-3 px-4 py-3 rounded-lg ${
                isActive('/denuncias') ? 'bg-blue-100 text-blue-600' : 'hover:bg-gray-100'
              }`}
            >
              <AlertTriangle size={20} />
              <span>Denuncias</span>
            </Link>

            <Link
              to="/simulacro"
              className={`flex items-center space-x-3 px-4 py-3 rounded-lg ${
                isActive('/simulacro') ? 'bg-blue-100 text-blue-600' : 'hover:bg-gray-100'
              }`}
            >
              <Vote size={20} />
              <span>Simulacro</span>
            </Link>
          </nav>
        </aside>

        <main className="flex-1 p-8">
          <Outlet />
        </main>
      </div>
    </div>
  );
}

export default Layout;