package com.tecsup.candidatoinfo.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.domain.model.CandidatoDiputado
import com.tecsup.candidatoinfo.domain.model.CandidatoParlamentoAndino
import com.tecsup.candidatoinfo.domain.model.CandidatoPresidencial
import com.tecsup.candidatoinfo.domain.model.CandidatoSenadorNacional
import com.tecsup.candidatoinfo.domain.model.CandidatoSenadorRegional
import com.tecsup.candidatoinfo.domain.usecase.GetCandidatosPresidencialesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetSenadoresNacionalesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetSenadoresRegionalesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetDiputadosUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetCandidatosParlamentoAndinoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class CandidatoType {
    data class Presidencial(val candidatos: List<CandidatoPresidencial>) : CandidatoType()
    data class SenadorNacional(val candidatos: List<CandidatoSenadorNacional>) : CandidatoType()
    data class SenadorRegional(val candidatos: List<CandidatoSenadorRegional>) : CandidatoType()
    data class Diputado(val candidatos: List<CandidatoDiputado>) : CandidatoType()
    data class ParlamentoAndino(val candidatos: List<CandidatoParlamentoAndino>) : CandidatoType()
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val selectedTab: Int = 0,
    val regionId: Int? = null,
    val regionName: String? = null,
    val candidatos: CandidatoType? = null,
    val error: String? = null,
    val countPresidenciales: Int = 0,
    val countSenadoresNacionales: Int = 0,
    val countSenadoresRegionales: Int = 0,
    val countDiputados: Int = 0,
    val countParlamentoAndino: Int = 0
)

class HomeViewModel(
    private val getCandidatosPresidencialesUseCase: GetCandidatosPresidencialesUseCase,
    private val getSenadoresNacionalesUseCase: GetSenadoresNacionalesUseCase,
    private val getSenadoresRegionalesUseCase: GetSenadoresRegionalesUseCase,
    private val getDiputadosUseCase: GetDiputadosUseCase,
    private val getCandidatosParlamentoAndinoUseCase: GetCandidatosParlamentoAndinoUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadRegionData()
        loadAllCounts()
    }

    private fun loadRegionData() {
        viewModelScope.launch {
            val regionId = preferencesManager.selectedRegionId.first()
            val regionName = preferencesManager.selectedRegionName.first()
            _uiState.value = _uiState.value.copy(
                regionId = regionId,
                regionName = regionName
            )
        }
    }

    private fun loadAllCounts() {
        viewModelScope.launch {
            val regionId = preferencesManager.selectedRegionId.first()
            android.util.Log.d("HomeViewModel", "Loading counts with regionId: $regionId")

            getCandidatosPresidencialesUseCase().onSuccess { list ->
                android.util.Log.d("HomeViewModel", "Presidenciales count: ${list.size}")
                _uiState.value = _uiState.value.copy(countPresidenciales = list.size)
            }

            getSenadoresNacionalesUseCase().onSuccess { list ->
                android.util.Log.d("HomeViewModel", "Senadores Nacionales count: ${list.size}")
                _uiState.value = _uiState.value.copy(countSenadoresNacionales = list.size)
            }

            if (regionId != null) {
                android.util.Log.d("HomeViewModel", "Calling getSenadoresRegionales with regionId: $regionId")
                getSenadoresRegionalesUseCase(regionId).onSuccess { list ->
                    android.util.Log.d("HomeViewModel", "Senadores Regionales count: ${list.size}")
                    _uiState.value = _uiState.value.copy(countSenadoresRegionales = list.size)
                }.onFailure { e ->
                    android.util.Log.e("HomeViewModel", "Error loading Senadores Regionales", e)
                }
            }

            if (regionId != null) {
                android.util.Log.d("HomeViewModel", "Calling getDiputados with regionId: $regionId")
                getDiputadosUseCase(regionId).onSuccess { list ->
                    android.util.Log.d("HomeViewModel", "Diputados count: ${list.size}")
                    _uiState.value = _uiState.value.copy(countDiputados = list.size)
                }.onFailure { e ->
                    android.util.Log.e("HomeViewModel", "Error loading Diputados", e)
                }
            }

            getCandidatosParlamentoAndinoUseCase().onSuccess { list ->
                android.util.Log.d("HomeViewModel", "Parlamento Andino count: ${list.size}")
                _uiState.value = _uiState.value.copy(countParlamentoAndino = list.size)
            }
        }
    }

    fun onTabSelected(tabIndex: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = tabIndex)
        loadCandidatos(tabIndex)
    }

    private fun loadCandidatos(tabIndex: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = when (tabIndex) {
                0 -> getCandidatosPresidencialesUseCase().map {
                    CandidatoType.Presidencial(it)
                }
                1 -> getSenadoresNacionalesUseCase().map {
                    CandidatoType.SenadorNacional(it)
                }
                2 -> {
                    val regionId = _uiState.value.regionId
                    if (regionId != null) {
                        getSenadoresRegionalesUseCase(regionId).map {
                            CandidatoType.SenadorRegional(it)
                        }
                    } else {
                        Result.failure(Exception("No se ha seleccionado región"))
                    }
                }
                3 -> {
                    val regionId = _uiState.value.regionId
                    if (regionId != null) {
                        getDiputadosUseCase(regionId).map {
                            CandidatoType.Diputado(it)
                        }
                    } else {
                        Result.failure(Exception("No se ha seleccionado región"))
                    }
                }
                4 -> getCandidatosParlamentoAndinoUseCase().map {
                    CandidatoType.ParlamentoAndino(it)
                }
                else -> Result.failure(Exception("Tab no válido"))
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
                        error = exception.message ?: "Error desconocido"
                    )
                }
            )
        }
    }

    fun retry() {
        loadCandidatos(_uiState.value.selectedTab)
    }

    fun refreshCounts() {
        loadAllCounts()
    }
}