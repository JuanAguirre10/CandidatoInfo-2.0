package com.tecsup.candidatoinfo.presentation.screens.partidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.candidatoinfo.data.repository.PartidoRepository
import com.tecsup.candidatoinfo.domain.model.Partido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PartidosUiState(
    val isLoading: Boolean = false,
    val partidos: List<Partido> = emptyList(),
    val error: String? = null
)

class PartidosViewModel(
    private val partidoRepository: PartidoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PartidosUiState())
    val uiState: StateFlow<PartidosUiState> = _uiState.asStateFlow()

    init {
        loadPartidos()
    }

    private fun loadPartidos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            partidoRepository.getPartidosActivos().fold(
                onSuccess = { partidos ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        partidos = partidos,
                        error = null
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error desconocido"
                    )
                }
            )
        }
    }

    fun retry() {
        loadPartidos()
    }
}