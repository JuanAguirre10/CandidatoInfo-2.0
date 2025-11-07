package com.tecsup.candidatoinfo.presentation.screens.comparacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.data.repository.InformacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.tecsup.candidatoinfo.domain.model.CandidatoComparacion
import com.tecsup.candidatoinfo.domain.model.ItemComparacion
import com.tecsup.candidatoinfo.domain.model.ComparacionUiState





class ComparacionViewModel(
    private val candidatoRepository: CandidatoRepository,
    private val informacionRepository: InformacionRepository,
    private val preferencesManager: PreferencesManager,
    private val tipoCandidato: String,
    private val candidatoId1: Int,
    private val candidatoId2: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(ComparacionUiState())
    val uiState: StateFlow<ComparacionUiState> = _uiState.asStateFlow()

    init {
        loadComparacionData()
    }

    private fun loadComparacionData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val candidato1Data = getCandidatoData(candidatoId1)
                val candidato2Data = getCandidatoData(candidatoId2)

                val propuestas1Result = informacionRepository.getPropuestasByCandidato(candidatoId1, tipoCandidato)
                val propuestas2Result = informacionRepository.getPropuestasByCandidato(candidatoId2, tipoCandidato)

                val proyectos1Result = informacionRepository.getProyectosByCandidato(candidatoId1, tipoCandidato)
                val proyectos2Result = informacionRepository.getProyectosByCandidato(candidatoId2, tipoCandidato)

                val denuncias1Result = informacionRepository.getDenunciasByCandidato(candidatoId1, tipoCandidato)
                val denuncias2Result = informacionRepository.getDenunciasByCandidato(candidatoId2, tipoCandidato)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    candidato1 = candidato1Data,
                    candidato2 = candidato2Data,
                    propuestasCandidato1 = propuestas1Result.getOrNull()?.map {
                        ItemComparacion(it.titulo ?: "Sin título", it.archivoUrl)
                    } ?: emptyList(),
                    propuestasCandidato2 = propuestas2Result.getOrNull()?.map {
                        ItemComparacion(it.titulo ?: "Sin título", it.archivoUrl)
                    } ?: emptyList(),
                    proyectosCandidato1 = proyectos1Result.getOrNull()?.map {
                        ItemComparacion(it.titulo ?: "Sin título", it.evidenciaUrl)
                    } ?: emptyList(),
                    proyectosCandidato2 = proyectos2Result.getOrNull()?.map {
                        ItemComparacion(it.titulo ?: "Sin título", it.evidenciaUrl)
                    } ?: emptyList(),
                    denunciasCandidato1 = denuncias1Result.getOrNull()?.map {
                        ItemComparacion(it.titulo ?: "Sin título", it.urlFuente)
                    } ?: emptyList(),
                    denunciasCandidato2 = denuncias2Result.getOrNull()?.map {
                        ItemComparacion(it.titulo ?: "Sin título", it.urlFuente)
                    } ?: emptyList(),
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar comparación"
                )
            }
        }
    }

    private suspend fun getCandidatoData(candidatoId: Int): CandidatoComparacion? {
        return when (tipoCandidato) {
            "presidencial" -> {
                candidatoRepository.getCandidatosPresidenciales().getOrNull()
                    ?.find { it.id == candidatoId }?.let { candidato ->
                        CandidatoComparacion(
                            id = candidato.id,
                            nombre = "${candidato.presidenteNombre ?: ""} ${candidato.presidenteApellidos ?: ""}".trim(),
                            fotoUrl = candidato.presidenteFotoUrl,
                            partido = candidato.partidoNombre,
                            partidoLogo = candidato.partidoLogo,
                            dni = candidato.presidenteDni,
                            genero = candidato.presidenteGenero,
                            edad = null,
                            profesion = candidato.presidenteProfesion,
                            biografia = null,
                            numeroLista = candidato.numeroLista,
                            posicionLista = null,
                            circunscripcion = null
                        )
                    }
            }
            "senador_nacional" -> {
                candidatoRepository.getSenadoresNacionales().getOrNull()
                    ?.find { it.id == candidatoId }?.let { candidato ->
                        CandidatoComparacion(
                            id = candidato.id,
                            nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                            fotoUrl = candidato.fotoUrl,
                            partido = candidato.partidoNombre,
                            partidoLogo = candidato.partidoLogo,
                            dni = candidato.dni,
                            genero = candidato.genero,
                            edad = candidato.edad,
                            profesion = candidato.profesion,
                            biografia = candidato.biografia,
                            numeroLista = null,
                            posicionLista = candidato.posicionLista,
                            circunscripcion = null
                        )
                    }
            }
            "senador_regional" -> {
                val regionId = preferencesManager.selectedRegionId.first()
                regionId?.let {
                    candidatoRepository.getSenadoresRegionales(it).getOrNull()
                        ?.find { candidato -> candidato.id == candidatoId }?.let { candidato ->
                            CandidatoComparacion(
                                id = candidato.id,
                                nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                                fotoUrl = candidato.fotoUrl,
                                partido = candidato.partidoNombre,
                                partidoLogo = candidato.partidoLogo,
                                dni = candidato.dni,
                                genero = candidato.genero,
                                edad = candidato.edad,
                                profesion = candidato.profesion,
                                biografia = candidato.biografia,
                                numeroLista = null,
                                posicionLista = candidato.posicionLista,
                                circunscripcion = candidato.circunscripcionNombre
                            )
                        }
                }
            }
            "diputado" -> {
                val regionId = preferencesManager.selectedRegionId.first()
                regionId?.let {
                    candidatoRepository.getDiputados(it).getOrNull()
                        ?.find { candidato -> candidato.id == candidatoId }?.let { candidato ->
                            CandidatoComparacion(
                                id = candidato.id,
                                nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                                fotoUrl = candidato.fotoUrl,
                                partido = candidato.partidoNombre,
                                partidoLogo = candidato.partidoLogo,
                                dni = candidato.dni,
                                genero = candidato.genero,
                                edad = candidato.edad,
                                profesion = candidato.profesion,
                                biografia = candidato.biografia,
                                numeroLista = null,
                                posicionLista = candidato.posicionLista,
                                circunscripcion = candidato.circunscripcionNombre
                            )
                        }
                }
            }
            "parlamento_andino" -> {
                candidatoRepository.getCandidatosParlamentoAndino().getOrNull()
                    ?.find { it.id == candidatoId }?.let { candidato ->
                        CandidatoComparacion(
                            id = candidato.id,
                            nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                            fotoUrl = candidato.fotoUrl,
                            partido = candidato.partidoNombre,
                            partidoLogo = candidato.partidoLogo,
                            dni = candidato.dni,
                            genero = candidato.genero,
                            edad = candidato.edad,
                            profesion = candidato.profesion,
                            biografia = candidato.biografia,
                            numeroLista = null,
                            posicionLista = candidato.posicionLista,
                            circunscripcion = null
                        )
                    }
            }
            else -> null
        }
    }

    fun retry() {
        loadComparacionData()
    }
}