package com.tecsup.candidatoinfo.presentation.screens.candidatos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

enum class TipoCandidato {
    PRESIDENCIAL,
    SENADOR_NACIONAL,
    SENADOR_REGIONAL,
    DIPUTADO,
    PARLAMENTO_ANDINO
}

data class CandidatoUI(
    val id: Int,
    val tipo: String,
    val nombre: String,
    val fotoUrl: String?,
    val partidoNombre: String?,
    val partidoLogo: String?,
    val numeroLista: Int?
)

data class CandidatosUiState(
    val isLoading: Boolean = false,
    val candidatos: List<CandidatoUI> = emptyList(),
    val selectedTabIndex: Int = 0,
    val selectedCandidatos: Set<Int> = emptySet(),
    val error: String? = null
)

class CandidatosViewModel(
    private val repository: CandidatoRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CandidatosUiState())
    val uiState: StateFlow<CandidatosUiState> = _uiState.asStateFlow()

    fun setFilter(tipo: TipoCandidato) {
        clearSelection()
        _uiState.value = _uiState.value.copy(
            selectedTabIndex = tipo.ordinal
        )
        loadCandidatos(tipo)
    }

    fun toggleCandidatoSelection(candidatoId: Int) {
        val currentSelection = _uiState.value.selectedCandidatos.toMutableSet()

        if (currentSelection.contains(candidatoId)) {
            currentSelection.remove(candidatoId)
        } else {
            if (currentSelection.size < 2) {
                currentSelection.add(candidatoId)
            }
        }

        _uiState.value = _uiState.value.copy(selectedCandidatos = currentSelection)
    }

    fun clearSelection() {
        _uiState.value = _uiState.value.copy(selectedCandidatos = emptySet())
    }

    private fun loadCandidatos(tipo: TipoCandidato) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = when (tipo) {
                TipoCandidato.PRESIDENCIAL -> {
                    repository.getCandidatosPresidenciales().map { list ->
                        list.map { candidato ->
                            CandidatoUI(
                                id = candidato.id,
                                tipo = "presidencial",
                                nombre = "${candidato.presidenteNombre ?: ""} ${candidato.presidenteApellidos ?: ""}".trim(),
                                fotoUrl = candidato.presidenteFotoUrl,
                                partidoNombre = candidato.partidoNombre,
                                partidoLogo = candidato.partidoLogo,
                                numeroLista = candidato.numeroLista
                            )
                        }
                    }
                }
                TipoCandidato.SENADOR_NACIONAL -> {
                    repository.getSenadoresNacionales().map { list ->
                        list.map { candidato ->
                            CandidatoUI(
                                id = candidato.id,
                                tipo = "senador_nacional",
                                nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                                fotoUrl = candidato.fotoUrl,
                                partidoNombre = candidato.partidoNombre,
                                partidoLogo = candidato.partidoLogo,
                                numeroLista = candidato.numeroPreferencial
                            )
                        }
                    }
                }
                TipoCandidato.SENADOR_REGIONAL -> {
                    val regionId = preferencesManager.selectedRegionId.first()
                    if (regionId != null) {
                        repository.getSenadoresRegionales(regionId).map { list ->
                            list.map { candidato ->
                                CandidatoUI(
                                    id = candidato.id,
                                    tipo = "senador_regional",
                                    nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                                    fotoUrl = candidato.fotoUrl,
                                    partidoNombre = candidato.partidoNombre,
                                    partidoLogo = candidato.partidoLogo,
                                    numeroLista = candidato.numeroPreferencial
                                )
                            }
                        }
                    } else {
                        Result.failure(Exception("Debe seleccionar una región"))
                    }
                }
                TipoCandidato.DIPUTADO -> {
                    val regionId = preferencesManager.selectedRegionId.first()
                    if (regionId != null) {
                        repository.getDiputados(regionId).map { list ->
                            list.map { candidato ->
                                CandidatoUI(
                                    id = candidato.id,
                                    tipo = "diputado",
                                    nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                                    fotoUrl = candidato.fotoUrl,
                                    partidoNombre = candidato.partidoNombre,
                                    partidoLogo = candidato.partidoLogo,
                                    numeroLista = candidato.numeroPreferencial
                                )
                            }
                        }
                    } else {
                        Result.failure(Exception("Debe seleccionar una región"))
                    }
                }
                TipoCandidato.PARLAMENTO_ANDINO -> {
                    repository.getCandidatosParlamentoAndino().map { list ->
                        list.map { candidato ->
                            CandidatoUI(
                                id = candidato.id,
                                tipo = "parlamento_andino",
                                nombre = candidato.nombreCompleto ?: "${candidato.nombre ?: ""} ${candidato.apellidos ?: ""}".trim(),
                                fotoUrl = candidato.fotoUrl,
                                partidoNombre = candidato.partidoNombre,
                                partidoLogo = candidato.partidoLogo,
                                numeroLista = candidato.numeroPreferencial
                            )
                        }
                    }
                }
            }

            result.fold(
                onSuccess = { candidatos ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        candidatos = candidatos,
                        error = null
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error al cargar candidatos"
                    )
                }
            )
        }
    }

    fun retry() {
        val tipo = TipoCandidato.values()[_uiState.value.selectedTabIndex]
        loadCandidatos(tipo)
    }
}