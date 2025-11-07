package com.tecsup.candidatoinfo.presentation.screens.votacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.data.repository.VotacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

enum class VotacionStep {
    VALIDACION_DNI,
    PRESIDENCIAL,
    SENADOR_NACIONAL,
    SENADOR_REGIONAL,
    DIPUTADO,
    PARLAMENTO_ANDINO,
    RESUMEN
}

data class CandidatoVotacion(
    val id: Int,
    val nombre: String,
    val fotoUrl: String?,
    val partido: String,
    val partidoLogo: String?,
    val numeroLista: Int?
)

data class VotacionUiState(
    val isLoading: Boolean = false,
    val currentStep: VotacionStep = VotacionStep.VALIDACION_DNI,
    val dni: String = "",
    val nombreVotante: String = "",
    val candidatosDisponibles: List<CandidatoVotacion> = emptyList(),
    val votosActuales: Set<Int> = emptySet(),
    val votosRegistrados: Map<String, List<Int>> = emptyMap(),
    val resumenVotos: Map<String, List<CandidatoVotacion>> = emptyMap(),
    val votoConfirmado: Boolean = false,
    val error: String? = null
)

class VotacionViewModel(
    private val candidatoRepository: CandidatoRepository,
    private val votacionRepository: VotacionRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(VotacionUiState())
    val uiState: StateFlow<VotacionUiState> = _uiState.asStateFlow()

    fun validarDNI(dni: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = votacionRepository.validarDNI(dni)

            result.fold(
                onSuccess = { response ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        dni = dni,
                        nombreVotante = response.nombreCompleto,
                        currentStep = VotacionStep.PRESIDENCIAL,
                        error = null
                    )
                    cargarCandidatos(VotacionStep.PRESIDENCIAL)
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "DNI no válido"
                    )
                }
            )
        }
    }

    private fun cargarCandidatos(step: VotacionStep) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val candidatos = when (step) {
                VotacionStep.PRESIDENCIAL -> {
                    candidatoRepository.getCandidatosPresidenciales().getOrNull()?.map { candidato ->
                        CandidatoVotacion(
                            id = candidato.id,
                            nombre = "${candidato.presidenteNombre ?: ""} ${candidato.presidenteApellidos ?: ""}".trim(),
                            fotoUrl = candidato.presidenteFotoUrl,
                            partido = candidato.partidoNombre ?: "Sin partido",
                            partidoLogo = candidato.partidoLogo,
                            numeroLista = candidato.numeroLista
                        )
                    } ?: emptyList()
                }
                VotacionStep.SENADOR_NACIONAL -> {
                    candidatoRepository.getSenadoresNacionales().getOrNull()?.map { candidato ->
                        CandidatoVotacion(
                            id = candidato.id,
                            nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                            fotoUrl = candidato.fotoUrl,
                            partido = candidato.partidoNombre ?: "Sin partido",
                            partidoLogo = candidato.partidoLogo,
                            numeroLista = candidato.numeroPreferencial
                        )
                    } ?: emptyList()
                }
                VotacionStep.SENADOR_REGIONAL -> {
                    val regionId = preferencesManager.selectedRegionId.first()
                    if (regionId != null) {
                        candidatoRepository.getSenadoresRegionales(regionId).getOrNull()?.map { candidato ->
                            CandidatoVotacion(
                                id = candidato.id,
                                nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                                fotoUrl = candidato.fotoUrl,
                                partido = candidato.partidoNombre ?: "Sin partido",
                                partidoLogo = candidato.partidoLogo,
                                numeroLista = candidato.numeroPreferencial
                            )
                        } ?: emptyList()
                    } else emptyList()
                }
                VotacionStep.DIPUTADO -> {
                    val regionId = preferencesManager.selectedRegionId.first()
                    if (regionId != null) {
                        candidatoRepository.getDiputados(regionId).getOrNull()?.map { candidato ->
                            CandidatoVotacion(
                                id = candidato.id,
                                nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                                fotoUrl = candidato.fotoUrl,
                                partido = candidato.partidoNombre ?: "Sin partido",
                                partidoLogo = candidato.partidoLogo,
                                numeroLista = candidato.numeroPreferencial
                            )
                        } ?: emptyList()
                    } else emptyList()
                }
                VotacionStep.PARLAMENTO_ANDINO -> {
                    candidatoRepository.getCandidatosParlamentoAndino().getOrNull()?.map { candidato ->
                        CandidatoVotacion(
                            id = candidato.id,
                            nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                            fotoUrl = candidato.fotoUrl,
                            partido = candidato.partidoNombre ?: "Sin partido",
                            partidoLogo = candidato.partidoLogo,
                            numeroLista = candidato.numeroPreferencial
                        )
                    } ?: emptyList()
                }
                else -> emptyList()
            }

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                candidatosDisponibles = candidatos,
                votosActuales = emptySet(),
                error = null
            )
        }
    }

    fun seleccionarCandidato(candidatoId: Int) {
        val nuevosVotos = _uiState.value.votosActuales + candidatoId
        _uiState.value = _uiState.value.copy(votosActuales = nuevosVotos)
    }

    fun deseleccionarCandidato(candidatoId: Int) {
        val nuevosVotos = _uiState.value.votosActuales - candidatoId
        _uiState.value = _uiState.value.copy(votosActuales = nuevosVotos)
    }

    fun continuarSiguienteCategoria() {
        val categoriaActual = getCategoriaActual()
        val votosRegistrados = _uiState.value.votosRegistrados.toMutableMap()
        votosRegistrados[categoriaActual] = _uiState.value.votosActuales.toList()

        val siguienteStep = when (_uiState.value.currentStep) {
            VotacionStep.PRESIDENCIAL -> VotacionStep.SENADOR_NACIONAL
            VotacionStep.SENADOR_NACIONAL -> VotacionStep.SENADOR_REGIONAL
            VotacionStep.SENADOR_REGIONAL -> VotacionStep.DIPUTADO
            VotacionStep.DIPUTADO -> VotacionStep.PARLAMENTO_ANDINO
            VotacionStep.PARLAMENTO_ANDINO -> {
                prepararResumen(votosRegistrados)
                VotacionStep.RESUMEN
            }
            else -> _uiState.value.currentStep
        }

        _uiState.value = _uiState.value.copy(
            votosRegistrados = votosRegistrados,
            currentStep = siguienteStep
        )

        if (siguienteStep != VotacionStep.RESUMEN) {
            cargarCandidatos(siguienteStep)
        }
    }

    private fun getCategoriaActual(): String {
        return when (_uiState.value.currentStep) {
            VotacionStep.PRESIDENCIAL -> "presidencial"
            VotacionStep.SENADOR_NACIONAL -> "senador_nacional"
            VotacionStep.SENADOR_REGIONAL -> "senador_regional"
            VotacionStep.DIPUTADO -> "diputado"
            VotacionStep.PARLAMENTO_ANDINO -> "parlamento_andino"
            else -> ""
        }
    }

    private fun prepararResumen(votosRegistrados: Map<String, List<Int>>) {
        val resumen = mutableMapOf<String, List<CandidatoVotacion>>()

        votosRegistrados.forEach { (categoria, ids) ->
            val candidatosSeleccionados = _uiState.value.candidatosDisponibles.filter { it.id in ids }
            if (candidatosSeleccionados.isNotEmpty()) {
                val nombreCategoria = when (categoria) {
                    "presidencial" -> "Presidente"
                    "senador_nacional" -> "Senadores Nacionales"
                    "senador_regional" -> "Senadores Regionales"
                    "diputado" -> "Diputados"
                    "parlamento_andino" -> "Parlamento Andino"
                    else -> categoria
                }
                resumen[nombreCategoria] = candidatosSeleccionados
            }
        }

        _uiState.value = _uiState.value.copy(resumenVotos = resumen)
    }

    fun volverAtras() {
        val anteriorStep = when (_uiState.value.currentStep) {
            VotacionStep.SENADOR_NACIONAL -> VotacionStep.PRESIDENCIAL
            VotacionStep.SENADOR_REGIONAL -> VotacionStep.SENADOR_NACIONAL
            VotacionStep.DIPUTADO -> VotacionStep.SENADOR_REGIONAL
            VotacionStep.PARLAMENTO_ANDINO -> VotacionStep.DIPUTADO
            VotacionStep.RESUMEN -> VotacionStep.PARLAMENTO_ANDINO
            else -> _uiState.value.currentStep
        }

        _uiState.value = _uiState.value.copy(currentStep = anteriorStep)
        cargarCandidatos(anteriorStep)
    }

    fun confirmarVotos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val regionIdUsuario = preferencesManager.selectedRegionId.first()
                val dni = _uiState.value.dni

                // Validar que tengamos la región del usuario para votos que la requieren
                if (regionIdUsuario == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "No se ha configurado tu región. Por favor, configúrala en tu perfil."
                    )
                    return@launch
                }

                // Lista para almacenar resultados
                val votosExitosos = mutableListOf<Pair<String, Int>>()
                val votosErroneos = mutableListOf<String>()

                // Registrar cada voto
                _uiState.value.votosRegistrados.forEach { (tipoEleccion, candidatosIds) ->
                    candidatosIds.forEach { candidatoId ->
                        // CORRECCIÓN: Determinar la circunscripción según el tipo de elección
                        val circunscripcionId = when (tipoEleccion) {
                            "presidencial" -> null
                            "senador_nacional" -> null
                            "parlamento_andino" -> null
                            "senador_regional" -> regionIdUsuario  // DEBE enviar la región
                            "diputado" -> regionIdUsuario           // DEBE enviar la región
                            else -> null
                        }

                        // Log para debugging (puedes quitarlo después)
                        println("Registrando voto: tipo=$tipoEleccion, candidato=$candidatoId, circunscripcion=$circunscripcionId")

                        val result = votacionRepository.registrarVoto(
                            dni = dni,
                            tipoEleccion = tipoEleccion,
                            candidatoId = candidatoId,
                            circunscripcionId = circunscripcionId
                        )

                        result.fold(
                            onSuccess = {
                                votosExitosos.add(Pair(tipoEleccion, candidatoId))
                                println("✓ Voto registrado: $tipoEleccion - Candidato $candidatoId")
                            },
                            onFailure = { error ->
                                val nombreCategoria = mapearNombreCategoria(tipoEleccion)
                                val mensajeError = error.message ?: "Error desconocido"
                                votosErroneos.add("$nombreCategoria: $mensajeError")
                                println("✗ Error en $tipoEleccion: $mensajeError")
                            }
                        )
                    }
                }

                // Evaluar resultados
                if (votosErroneos.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        votoConfirmado = true,
                        error = null
                    )
                } else {
                    val mensajeError = if (votosExitosos.isEmpty()) {
                        "No se pudo registrar ningún voto:\n${votosErroneos.joinToString("\n")}"
                    } else {
                        "Se registraron ${votosExitosos.size} votos, pero hubo errores en:\n${votosErroneos.joinToString("\n")}"
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        votoConfirmado = votosExitosos.isNotEmpty() && votosErroneos.isEmpty(),
                        error = mensajeError
                    )
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error al procesar votos: ${e.message}"
                )
                println("Exception en confirmarVotos: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun mapearNombreCategoria(tipoEleccion: String): String {
        return when (tipoEleccion) {
            "presidencial" -> "Presidente"
            "senador_nacional" -> "Senador Nacional"
            "senador_regional" -> "Senador Regional"
            "diputado" -> "Diputado"
            "parlamento_andino" -> "Parlamento Andino"
            else -> tipoEleccion
        }
    }

    fun limpiarError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}