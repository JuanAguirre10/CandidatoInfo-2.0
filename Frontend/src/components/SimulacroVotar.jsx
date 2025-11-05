import { useState, useEffect } from 'react';
import {
  getTodosCandidatosPresidenciales,
  getTodosSenadoresNacionales,
  getTodosSenadoresRegionales,
  getTodosDiputados,
  getTodosParlamentoAndino,
  getTodasCircunscripciones
} from '../services/api';
import { Vote, CheckCircle, AlertCircle, User, MapPin } from 'lucide-react';
import api from '../services/api';

function SimulacroVotar() {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    dni: '',
    circunscripcion: '',
    tipo_eleccion: '',
    candidato_id: ''
  });
  const [candidatos, setCandidatos] = useState([]);
  const [circunscripciones, setCircunscripciones] = useState([]);
  const [circunscripcionSeleccionada, setCircunscripcionSeleccionada] = useState(null);
  const [loading, setLoading] = useState(false);
  const [loadingCandidatos, setLoadingCandidatos] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const [votanteInfo, setVotanteInfo] = useState(null);
  const [fechaActual, setFechaActual] = useState({ mes: null, anio: null });

  useEffect(() => {
    loadCircunscripciones();
    obtenerFechaServidor();
  }, []);

  useEffect(() => {
    if (formData.tipo_eleccion && formData.circunscripcion) {
      loadCandidatos();
    }
  }, [formData.tipo_eleccion, formData.circunscripcion]);

  const obtenerFechaServidor = async () => {
    try {
      const response = await api.get('/simulacro/votos/fecha_actual/');
      setFechaActual({ mes: response.data.mes, anio: response.data.anio });
    } catch (error) {
      const now = new Date();
      setFechaActual({ mes: now.getMonth() + 1, anio: now.getFullYear() });
    }
  };

  const loadCircunscripciones = async () => {
    try {
      setLoading(true);
      const response = await getTodasCircunscripciones();
      const data = response.data.results || response.data;
      setCircunscripciones(data);
    } catch (error) {
      console.error('Error cargando circunscripciones:', error);
      setError('Error al cargar las circunscripciones');
    } finally {
      setLoading(false);
    }
  };

  const loadCandidatos = async () => {
  try {
    setLoadingCandidatos(true);
    setError('');
    let response;
    let todosCandidatos = [];
    
    switch (formData.tipo_eleccion) {
      case 'presidencial':
        response = await getTodosCandidatosPresidenciales();
        console.log('Presidenciales response:', response);
        todosCandidatos = response.data || [];
        break;
      case 'senador_nacional':
        response = await getTodosSenadoresNacionales();
        console.log('Senadores Nacionales response:', response);
        todosCandidatos = response.data || [];
        break;
      case 'senador_regional':
        response = await getTodosSenadoresRegionales();
        console.log('Senadores Regionales response:', response);
        const senadoresRegionales = response.data || [];
        todosCandidatos = senadoresRegionales.filter(
          c => c.circunscripcion === parseInt(formData.circunscripcion)
        );
        console.log('Filtrados por circunscripcion:', todosCandidatos);
        break;
      case 'diputado':
        response = await getTodosDiputados();
        console.log('Diputados response:', response);
        const diputados = response.data || [];
        todosCandidatos = diputados.filter(
          c => c.circunscripcion === parseInt(formData.circunscripcion)
        );
        console.log('Filtrados por circunscripcion:', todosCandidatos);
        break;
      case 'parlamento_andino':
        response = await getTodosParlamentoAndino();
        console.log('Parlamento Andino response:', response);
        todosCandidatos = response.data || [];
        break;
      default:
        return;
    }
    
    console.log('Total candidatos:', todosCandidatos.length);
    setCandidatos(todosCandidatos);
    
    if (todosCandidatos.length === 0) {
      const requiereFiltro = ['senador_regional', 'diputado'].includes(formData.tipo_eleccion);
      if (requiereFiltro) {
        setError(`No hay candidatos disponibles para ${getTipoEleccionLabel(formData.tipo_eleccion)} en ${circunscripcionSeleccionada?.nombre}`);
      } else {
        setError(`No hay candidatos disponibles para ${getTipoEleccionLabel(formData.tipo_eleccion)}`);
      }
    }
  } catch (error) {
    console.error('Error cargando candidatos:', error);
    setError('Error al cargar los candidatos');
  } finally {
    setLoadingCandidatos(false);
  }
};

  const validarDNI = async () => {
    try {
      setLoading(true);
      setError('');
      
      try {
        const dniResponse = await api.get(`/simulacro/votos/validar_dni/?dni=${formData.dni}`);
        if (dniResponse.data.success) {
          setVotanteInfo(dniResponse.data.nombre_completo);
        }
      } catch (error) {
        console.log('No se pudo validar DNI con RENIEC');
      }
      
      return true;
    } catch (error) {
      console.error('Error verificando DNI:', error);
      setError('Error al verificar el DNI');
      return false;
    } finally {
      setLoading(false);
    }
  };

  const verificarVoto = async () => {
    try {
      setLoading(true);
      setError('');
      
      const votoResponse = await api.post('/simulacro/votos/verificar_voto/', {
        dni: formData.dni,
        tipo_eleccion: formData.tipo_eleccion
      });
      
      if (votoResponse.data.ya_voto) {
        setError(votoResponse.data.mensaje);
        return false;
      }
      
      return true;
    } catch (error) {
      console.error('Error verificando voto:', error);
      return true;
    } finally {
      setLoading(false);
    }
  };

  const handleStep1Next = async () => {
    setError('');
    
    if (!formData.dni || formData.dni.length !== 8) {
      setError('El DNI debe tener 8 dígitos');
      return;
    }
    if (!formData.circunscripcion) {
      setError('Debe seleccionar una circunscripción');
      return;
    }
    
    const circ = circunscripciones.find(c => c.id === parseInt(formData.circunscripcion));
    setCircunscripcionSeleccionada(circ);
    
    const puedeVotar = await validarDNI();
    if (!puedeVotar) return;
    
    setStep(2);
  };

  const handleStep2Next = async () => {
    setError('');
    
    if (!formData.tipo_eleccion) {
      setError('Debe seleccionar un tipo de elección');
      return;
    }
    
    const puedeVotar = await verificarVoto();
    if (!puedeVotar) return;
    
    setStep(3);
  };

  const handleStep3Next = () => {
    setError('');
    
    if (!formData.candidato_id) {
      setError('Debe seleccionar un candidato');
      return;
    }
    
    setStep(4);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    
    try {
      const response = await api.post('/simulacro/votos/', {
        dni: formData.dni,
        tipo_eleccion: formData.tipo_eleccion,
        candidato_id: parseInt(formData.candidato_id),
        circunscripcion: parseInt(formData.circunscripcion)
      });
      
      setSuccess(true);
      setVotanteInfo(response.data.votante);
      setStep(5);
    } catch (error) {
      console.error('Error registrando voto:', error);
      if (error.response?.data?.error) {
        setError(error.response.data.error);
      } else if (error.response?.data) {
        const errorMessages = Object.values(error.response.data).flat().join(', ');
        setError(errorMessages);
      } else {
        setError('Error al registrar el voto');
      }
    } finally {
      setLoading(false);
    }
  };

  const resetForm = () => {
    setFormData({
      dni: '',
      circunscripcion: '',
      tipo_eleccion: '',
      candidato_id: ''
    });
    setStep(1);
    setError('');
    setSuccess(false);
    setVotanteInfo(null);
    setCircunscripcionSeleccionada(null);
    setCandidatos([]);
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

  const getMesNombre = (mes) => {
    const meses = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 
                   'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
    return meses[mes - 1] || mes;
  };

  const getCandidatoSeleccionado = () => {
    return candidatos.find(c => c.id === parseInt(formData.candidato_id));
  };

  const getNombreCandidato = (candidato) => {
    if (formData.tipo_eleccion === 'presidencial') {
      return `${candidato.presidente_nombre || ''} ${candidato.presidente_apellidos || ''}`.trim();
    }
    return `${candidato.nombre || ''} ${candidato.apellidos || ''}`.trim();
  };

  const getNumeroLista = (candidato) => {
    if (formData.tipo_eleccion === 'presidencial') {
      return candidato.numero_lista;
    }
    return candidato.posicion_lista || candidato.numero_preferencial || candidato.id;
  };

  if (success && step === 5) {
    return (
      <div className="max-w-2xl mx-auto">
        <div className="bg-white rounded-lg shadow-lg p-8 text-center">
          <div className="flex justify-center mb-4">
            <CheckCircle size={80} className="text-green-500" />
          </div>
          <h2 className="text-3xl font-bold text-gray-800 mb-4">¡Voto Registrado Exitosamente!</h2>
          <p className="text-gray-600 mb-6">Su voto ha sido registrado correctamente en el simulacro electoral.</p>
          
          {votanteInfo && (
            <div className="bg-blue-50 rounded-lg p-4 mb-6">
              <p className="text-sm text-gray-600">Votante:</p>
              <p className="text-lg font-semibold text-gray-800">{votanteInfo}</p>
              <p className="text-sm text-gray-600 mt-2">DNI: {formData.dni}</p>
            </div>
          )}
          
          <div className="space-y-2 text-left bg-gray-50 rounded-lg p-4 mb-6">
            <p><strong>Circunscripción:</strong> {circunscripcionSeleccionada?.nombre}</p>
            <p><strong>Tipo de Elección:</strong> {getTipoEleccionLabel(formData.tipo_eleccion)}</p>
            <p><strong>Período:</strong> {getMesNombre(fechaActual.mes)} {fechaActual.anio}</p>
          </div>
          
          <button
            onClick={resetForm}
            className="bg-blue-600 text-white px-8 py-3 rounded-lg hover:bg-blue-700 font-semibold"
          >
            Realizar Otro Voto
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto">
      <div className="bg-white rounded-lg shadow-lg p-6 mb-6">
        <div className="flex items-center justify-between mb-6">
          <h1 className="text-3xl font-bold text-gray-800">Simulacro de Votación</h1>
          <Vote size={40} className="text-blue-600" />
        </div>

        {fechaActual.mes && fechaActual.anio && (
          <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-6">
            <p className="text-sm text-blue-800">
              <strong>Simulacro del período:</strong> {getMesNombre(fechaActual.mes)} {fechaActual.anio}
            </p>
            {circunscripcionSeleccionada && step > 1 && (
              <p className="text-sm text-blue-800 mt-1">
                <strong>Circunscripción:</strong> {circunscripcionSeleccionada.nombre}
              </p>
            )}
          </div>
        )}
        
        <div className="flex items-center justify-between mb-8">
          <div className={`flex items-center ${step >= 1 ? 'text-blue-600' : 'text-gray-400'}`}>
            <div className={`w-10 h-10 rounded-full flex items-center justify-center ${step >= 1 ? 'bg-blue-600 text-white' : 'bg-gray-300'} font-bold`}>1</div>
            <span className="ml-2 font-semibold hidden md:inline">DNI</span>
          </div>
          <div className={`flex-1 h-1 mx-4 ${step >= 2 ? 'bg-blue-600' : 'bg-gray-300'}`}></div>
          <div className={`flex items-center ${step >= 2 ? 'text-blue-600' : 'text-gray-400'}`}>
            <div className={`w-10 h-10 rounded-full flex items-center justify-center ${step >= 2 ? 'bg-blue-600 text-white' : 'bg-gray-300'} font-bold`}>2</div>
            <span className="ml-2 font-semibold hidden md:inline">Tipo</span>
          </div>
          <div className={`flex-1 h-1 mx-4 ${step >= 3 ? 'bg-blue-600' : 'bg-gray-300'}`}></div>
          <div className={`flex items-center ${step >= 3 ? 'text-blue-600' : 'text-gray-400'}`}>
            <div className={`w-10 h-10 rounded-full flex items-center justify-center ${step >= 3 ? 'bg-blue-600 text-white' : 'bg-gray-300'} font-bold`}>3</div>
            <span className="ml-2 font-semibold hidden md:inline">Candidato</span>
          </div>
          <div className={`flex-1 h-1 mx-4 ${step >= 4 ? 'bg-blue-600' : 'bg-gray-300'}`}></div>
          <div className={`flex items-center ${step >= 4 ? 'text-blue-600' : 'text-gray-400'}`}>
            <div className={`w-10 h-10 rounded-full flex items-center justify-center ${step >= 4 ? 'bg-blue-600 text-white' : 'bg-gray-300'} font-bold`}>4</div>
            <span className="ml-2 font-semibold hidden md:inline">Confirmar</span>
          </div>
        </div>

        {error && (
          <div className="bg-red-50 border border-red-200 rounded-lg p-4 mb-6 flex items-center">
            <AlertCircle className="text-red-600 mr-3" size={24} />
            <p className="text-red-700">{error}</p>
          </div>
        )}

        {step === 1 && (
          <div className="space-y-6">
            <h2 className="text-xl font-bold text-gray-800 mb-4">Paso 1: Datos del Votante</h2>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                <User className="inline mr-2" size={18} />
                DNI del Votante
              </label>
              <input
                type="text"
                maxLength="8"
                value={formData.dni}
                onChange={(e) => setFormData({...formData, dni: e.target.value.replace(/\D/g, '')})}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Ingrese su DNI (8 dígitos)"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                <MapPin className="inline mr-2" size={18} />
                Seleccione su Circunscripción
              </label>
              <select
                value={formData.circunscripcion}
                onChange={(e) => setFormData({...formData, circunscripcion: e.target.value})}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                <option value="">Seleccione una circunscripción</option>
                {circunscripciones.map(circ => (
                  <option key={circ.id} value={circ.id}>{circ.nombre}</option>
                ))}
              </select>
            </div>

            <button
              onClick={handleStep1Next}
              disabled={loading}
              className="w-full bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700 font-semibold disabled:opacity-50"
            >
              {loading ? 'Verificando...' : 'Continuar'}
            </button>
          </div>
        )}

        {step === 2 && (
          <div className="space-y-6">
            <h2 className="text-xl font-bold text-gray-800 mb-4">Paso 2: Tipo de Elección</h2>
            
            {votanteInfo && (
              <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-4">
                <p className="text-sm text-gray-600">Votante identificado:</p>
                <p className="text-lg font-semibold text-gray-800">{votanteInfo}</p>
                <p className="text-sm text-gray-600 mt-1">DNI: {formData.dni}</p>
              </div>
            )}

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                <Vote className="inline mr-2" size={18} />
                Tipo de Elección
              </label>
              <select
                value={formData.tipo_eleccion}
                onChange={(e) => setFormData({...formData, tipo_eleccion: e.target.value, candidato_id: ''})}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                <option value="">Seleccione el tipo de elección</option>
                <option value="presidencial">Presidencial</option>
                <option value="senador_nacional">Senador Nacional</option>
                <option value="senador_regional">Senador Regional</option>
                <option value="diputado">Diputado</option>
                <option value="parlamento_andino">Parlamento Andino</option>
              </select>
            </div>

            <div className="flex space-x-4">
              <button
                onClick={() => setStep(1)}
                className="flex-1 bg-gray-300 text-gray-700 py-3 rounded-lg hover:bg-gray-400 font-semibold"
              >
                Anterior
              </button>
              <button
                onClick={handleStep2Next}
                disabled={loading}
                className="flex-1 bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700 font-semibold disabled:opacity-50"
              >
                {loading ? 'Verificando...' : 'Continuar'}
              </button>
            </div>
          </div>
        )}

        {step === 3 && (
          <div className="space-y-6">
            <h2 className="text-xl font-bold text-gray-800 mb-4">Paso 3: Seleccione su Candidato</h2>
            
            {loadingCandidatos ? (
              <div className="text-center py-8">
                <div className="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
                <p className="mt-2 text-gray-600">Cargando candidatos...</p>
              </div>
            ) : candidatos.length > 0 ? (
              <>
                <div className="bg-green-50 border border-green-200 rounded-lg p-3 mb-4">
                  <p className="text-sm text-green-800">
                    Se encontraron <strong>{candidatos.length}</strong> candidatos disponibles
                  </p>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4 max-h-96 overflow-y-auto">
                  {candidatos.map(candidato => (
                    <div
                      key={candidato.id}
                      onClick={() => setFormData({...formData, candidato_id: candidato.id.toString()})}
                      className={`border-2 rounded-lg p-4 cursor-pointer transition-all ${
                        formData.candidato_id === candidato.id.toString()
                          ? 'border-blue-600 bg-blue-50'
                          : 'border-gray-300 hover:border-blue-400'
                      }`}
                    >
                      <div className="flex items-center space-x-4">
                        <div className={`w-12 h-12 rounded-full flex items-center justify-center text-white font-bold text-xl ${
                          formData.candidato_id === candidato.id.toString() ? 'bg-blue-600' : 'bg-gray-400'
                        }`}>
                          {getNumeroLista(candidato)}
                        </div>
                        <div className="flex-1">
                          <h3 className="font-bold text-gray-800">{getNombreCandidato(candidato)}</h3>
                          {candidato.partido_nombre && (
                            <p className="text-sm text-gray-600">{candidato.partido_nombre}</p>
                          )}
                        </div>
                        {formData.candidato_id === candidato.id.toString() && (
                          <CheckCircle className="text-blue-600" size={24} />
                        )}
                      </div>
                    </div>
                  ))}
                </div>
              </>
            ) : (
              <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-4 text-center">
                <AlertCircle className="inline-block text-yellow-600 mb-2" size={40} />
                <p className="text-yellow-800">No hay candidatos disponibles</p>
              </div>
            )}

            <div className="flex space-x-4">
              <button
                onClick={() => setStep(2)}
                className="flex-1 bg-gray-300 text-gray-700 py-3 rounded-lg hover:bg-gray-400 font-semibold"
              >
                Anterior
              </button>
              <button
                onClick={handleStep3Next}
                disabled={!formData.candidato_id}
                className="flex-1 bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700 font-semibold disabled:opacity-50"
              >
                Continuar
              </button>
            </div>
          </div>
        )}

        {step === 4 && (
          <div className="space-y-6">
            <h2 className="text-xl font-bold text-gray-800 mb-4">Paso 4: Confirme su Voto</h2>
            
            <div className="bg-gray-50 rounded-lg p-6 space-y-4">
              <div>
                <p className="text-sm text-gray-600">DNI del Votante:</p>
                <p className="text-lg font-semibold">{formData.dni}</p>
              </div>
              
              <div>
                <p className="text-sm text-gray-600">Circunscripción:</p>
                <p className="text-lg font-semibold">{circunscripcionSeleccionada?.nombre}</p>
              </div>
              
              <div>
                <p className="text-sm text-gray-600">Tipo de Elección:</p>
                <p className="text-lg font-semibold">{getTipoEleccionLabel(formData.tipo_eleccion)}</p>
              </div>
              
              <div>
                <p className="text-sm text-gray-600">Candidato Seleccionado:</p>
                {getCandidatoSeleccionado() && (
                  <div className="bg-white rounded-lg p-4 mt-2 border-2 border-blue-600">
                    <p className="text-xl font-bold text-gray-800">{getNombreCandidato(getCandidatoSeleccionado())}</p>
                    {getCandidatoSeleccionado().partido_nombre && (
                      <p className="text-gray-600">{getCandidatoSeleccionado().partido_nombre}</p>
                    )}
                  </div>
                )}
              </div>
            </div>

            <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-4">
              <p className="text-yellow-800 text-sm">
                <strong>Importante:</strong> Verifique que todos los datos sean correctos antes de confirmar. 
                Una vez registrado el voto, no podrá modificarlo.
              </p>
            </div>

            <div className="flex space-x-4">
              <button
                onClick={() => setStep(3)}
                className="flex-1 bg-gray-300 text-gray-700 py-3 rounded-lg hover:bg-gray-400 font-semibold"
              >
                Anterior
              </button>
              <button
                onClick={handleSubmit}
                disabled={loading}
                className="flex-1 bg-green-600 text-white py-3 rounded-lg hover:bg-green-700 font-semibold disabled:opacity-50"
              >
                {loading ? 'Registrando...' : 'Confirmar Voto'}
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default SimulacroVotar;