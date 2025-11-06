package com.tecsup.candidatoinfo.presentation.screens.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.domain.model.Circunscripcion
import com.tecsup.candidatoinfo.domain.usecase.GetCircunscripcionesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegionSelectionUiState(
    val isLoading: Boolean = false,
    val circunscripciones: List<Circunscripcion> = emptyList(),
    val selectedRegion: Circunscripcion? = null,
    val error: String? = null
)

class RegionSelectionViewModel(
    private val getCircunscripcionesUseCase: GetCircunscripcionesUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegionSelectionUiState())
    val uiState: StateFlow<RegionSelectionUiState> = _uiState.asStateFlow()

    init {
        loadCircunscripciones()
    }

    private fun loadCircunscripciones() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            getCircunscripcionesUseCase().fold(
                onSuccess = { circunscripciones ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        circunscripciones = circunscripciones,
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

    fun onRegionSelected(region: Circunscripcion) {
        _uiState.value = _uiState.value.copy(selectedRegion = region)
    }

    fun saveSelectedRegion(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value.selectedRegion?.let { region ->
                preferencesManager.saveSelectedRegion(region.id, region.nombre)
                onSuccess()
            }
        }
    }

    fun retry() {
        loadCircunscripciones()
    }
}