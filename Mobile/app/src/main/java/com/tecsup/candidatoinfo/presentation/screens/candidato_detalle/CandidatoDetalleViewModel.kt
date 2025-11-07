package com.tecsup.candidatoinfo.presentation.screens.candidato_detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.data.repository.InformacionRepository
import com.tecsup.candidatoinfo.domain.model.Denuncia
import com.tecsup.candidatoinfo.domain.model.Propuesta
import com.tecsup.candidatoinfo.domain.model.ProyectoRealizado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class PropuestaUI(
    val titulo: String,
    val descripcion: String,
    val archivoUrl: String?
)

data class ProyectoUI(
    val titulo: String,
    val descripcion: String,
    val evidenciaUrl: String?
)

data class DenunciaUI(
    val titulo: String,
    val descripcion: String,
    val urlFuente: String?
)

data class CandidatoDetalleUiState(
    val isLoading: Boolean = false,
    val nombreCompleto: String = "",
    val fotoUrl: String? = null,
    val partidoNombre: String? = null,
    val partidoLogo: String? = null,
    val dni: String? = null,
    val genero: String? = null,
    val edad: Int? = null,
    val fechaNacimiento: String? = null,
    val profesion: String? = null,
    val experienciaPolitica: String? = null,
    val biografia: String? = null,
    val numeroLista: Int? = null,
    val posicionLista: Int? = null,
    val numeroPreferencial: Int? = null,
    val circunscripcion: String? = null,
    val esNaturalCircunscripcion: Boolean? = null,
    val vicepresidente1: String? = null,
    val vicepresidente1Profesion: String? = null,
    val vicepresidente2: String? = null,
    val vicepresidente2Profesion: String? = null,
    val propuestas: List<PropuestaUI> = emptyList(),
    val proyectos: List<ProyectoUI> = emptyList(),
    val denuncias: List<DenunciaUI> = emptyList(),
    val error: String? = null
)

class CandidatoDetalleViewModel(
    private val candidatoRepository: CandidatoRepository,
    private val informacionRepository: InformacionRepository,
    private val preferencesManager: PreferencesManager,
    private val tipoCandidato: String,
    private val candidatoId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(CandidatoDetalleUiState())
    val uiState: StateFlow<CandidatoDetalleUiState> = _uiState.asStateFlow()

    init {
        loadCandidatoData()
    }

    private fun loadCandidatoData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                var nombreCompleto = ""
                var fotoUrl: String? = null
                var partidoNombre: String? = null
                var partidoLogo: String? = null
                var dni: String? = null
                var genero: String? = null
                var edad: Int? = null
                var fechaNacimiento: String? = null
                var profesion: String? = null
                var experienciaPolitica: String? = null
                var biografia: String? = null
                var numeroLista: Int? = null
                var posicionLista: Int? = null
                var numeroPreferencial: Int? = null
                var circunscripcion: String? = null
                var esNaturalCircunscripcion: Boolean? = null
                var vicepresidente1: String? = null
                var vicepresidente1Profesion: String? = null
                var vicepresidente2: String? = null
                var vicepresidente2Profesion: String? = null

                when (tipoCandidato) {
                    "presidencial" -> {
                        candidatoRepository.getCandidatosPresidenciales().getOrNull()
                            ?.find { it.id == candidatoId }?.let { candidato ->
                                nombreCompleto = "${candidato.presidenteNombre ?: ""} ${candidato.presidenteApellidos ?: ""}".trim()
                                fotoUrl = candidato.presidenteFotoUrl
                                partidoNombre = candidato.partidoNombre
                                partidoLogo = candidato.partidoLogo
                                dni = candidato.presidenteDni
                                genero = candidato.presidenteGenero
                                profesion = candidato.presidenteProfesion
                                numeroLista = candidato.numeroLista
                                vicepresidente1 = "${candidato.vicepresidente1Nombre ?: ""} ${candidato.vicepresidente1Apellidos ?: ""}".trim().takeIf { it.isNotBlank() }
                                vicepresidente1Profesion = candidato.vicepresidente1Profesion
                                vicepresidente2 = "${candidato.vicepresidente2Nombre ?: ""} ${candidato.vicepresidente2Apellidos ?: ""}".trim().takeIf { it.isNotBlank() }
                                vicepresidente2Profesion = candidato.vicepresidente2Profesion
                            }
                    }
                    "senador_nacional" -> {
                        candidatoRepository.getSenadoresNacionales().getOrNull()
                            ?.find { it.id == candidatoId }?.let { candidato ->
                                nombreCompleto = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim()
                                fotoUrl = candidato.fotoUrl
                                partidoNombre = candidato.partidoNombre
                                partidoLogo = candidato.partidoLogo
                                dni = candidato.dni
                                genero = candidato.genero
                                edad = candidato.edad
                                profesion = candidato.profesion
                                biografia = candidato.biografia
                                posicionLista = candidato.posicionLista
                                numeroPreferencial = candidato.numeroPreferencial
                            }
                    }
                    "senador_regional" -> {
                        val regionId = preferencesManager.selectedRegionId.first()
                        if (regionId != null) {
                            candidatoRepository.getSenadoresRegionales(regionId).getOrNull()
                                ?.find { it.id == candidatoId }?.let { candidato ->
                                    nombreCompleto = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim()
                                    fotoUrl = candidato.fotoUrl
                                    partidoNombre = candidato.partidoNombre
                                    partidoLogo = candidato.partidoLogo
                                    dni = candidato.dni
                                    genero = candidato.genero
                                    edad = candidato.edad
                                    profesion = candidato.profesion
                                    biografia = candidato.biografia
                                    posicionLista = candidato.posicionLista
                                    numeroPreferencial = candidato.numeroPreferencial
                                    circunscripcion = candidato.circunscripcionNombre
                                    esNaturalCircunscripcion = candidato.esNaturalCircunscripcion
                                }
                        }
                    }
                    "diputado" -> {
                        val regionId = preferencesManager.selectedRegionId.first()
                        if (regionId != null) {
                            candidatoRepository.getDiputados(regionId).getOrNull()
                                ?.find { it.id == candidatoId }?.let { candidato ->
                                    nombreCompleto = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim()
                                    fotoUrl = candidato.fotoUrl
                                    partidoNombre = candidato.partidoNombre
                                    partidoLogo = candidato.partidoLogo
                                    dni = candidato.dni
                                    genero = candidato.genero
                                    edad = candidato.edad
                                    profesion = candidato.profesion
                                    biografia = candidato.biografia
                                    posicionLista = candidato.posicionLista
                                    numeroPreferencial = candidato.numeroPreferencial
                                    circunscripcion = candidato.circunscripcionNombre
                                    esNaturalCircunscripcion = candidato.esNaturalCircunscripcion
                                }
                        }
                    }
                    "parlamento_andino" -> {
                        candidatoRepository.getCandidatosParlamentoAndino().getOrNull()
                            ?.find { it.id == candidatoId }?.let { candidato ->
                                nombreCompleto = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim()
                                fotoUrl = candidato.fotoUrl
                                partidoNombre = candidato.partidoNombre
                                partidoLogo = candidato.partidoLogo
                                dni = candidato.dni
                                genero = candidato.genero
                                edad = candidato.edad
                                profesion = candidato.profesion
                                biografia = candidato.biografia
                                posicionLista = candidato.posicionLista
                                numeroPreferencial = candidato.numeroPreferencial
                            }
                    }
                }

                val propuestasResult = informacionRepository.getPropuestasByCandidato(candidatoId, tipoCandidato)
                val proyectosResult = informacionRepository.getProyectosByCandidato(candidatoId, tipoCandidato)
                val denunciasResult = informacionRepository.getDenunciasByCandidato(candidatoId, tipoCandidato)

                val propuestas = propuestasResult.getOrNull()?.map {
                    PropuestaUI(
                        titulo = it.titulo ?: "Sin título",
                        descripcion = it.descripcion ?: "Sin descripción",
                        archivoUrl = it.archivoUrl
                    )
                } ?: emptyList()

                val proyectos = proyectosResult.getOrNull()?.map {
                    ProyectoUI(
                        titulo = it.titulo ?: "Sin título",
                        descripcion = it.descripcion ?: "Sin descripción",
                        evidenciaUrl = it.evidenciaUrl
                    )
                } ?: emptyList()

                val denuncias = denunciasResult.getOrNull()?.map {
                    DenunciaUI(
                        titulo = it.titulo ?: "Sin título",
                        descripcion = it.descripcion ?: "Sin descripción",
                        urlFuente = it.urlFuente
                    )
                } ?: emptyList()

                if (nombreCompleto.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        nombreCompleto = nombreCompleto,
                        fotoUrl = fotoUrl,
                        partidoNombre = partidoNombre,
                        partidoLogo = partidoLogo,
                        dni = dni,
                        genero = genero,
                        edad = edad,
                        fechaNacimiento = fechaNacimiento,
                        profesion = profesion,
                        experienciaPolitica = experienciaPolitica,
                        biografia = biografia,
                        numeroLista = numeroLista,
                        posicionLista = posicionLista,
                        numeroPreferencial = numeroPreferencial,
                        circunscripcion = circunscripcion,
                        esNaturalCircunscripcion = esNaturalCircunscripcion,
                        vicepresidente1 = vicepresidente1,
                        vicepresidente1Profesion = vicepresidente1Profesion,
                        vicepresidente2 = vicepresidente2,
                        vicepresidente2Profesion = vicepresidente2Profesion,
                        propuestas = propuestas,
                        proyectos = proyectos,
                        denuncias = denuncias,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Candidato no encontrado"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar datos del candidato"
                )
            }
        }
    }

    fun retry() {
        loadCandidatoData()
    }
}