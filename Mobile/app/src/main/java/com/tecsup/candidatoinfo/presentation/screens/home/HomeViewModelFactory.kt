package com.tecsup.candidatoinfo.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.domain.usecase.GetCandidatosPresidencialesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetSenadoresNacionalesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetSenadoresRegionalesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetDiputadosUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetCandidatosParlamentoAndinoUseCase

class HomeViewModelFactory(
    private val getCandidatosPresidencialesUseCase: GetCandidatosPresidencialesUseCase,
    private val getSenadoresNacionalesUseCase: GetSenadoresNacionalesUseCase,
    private val getSenadoresRegionalesUseCase: GetSenadoresRegionalesUseCase,
    private val getDiputadosUseCase: GetDiputadosUseCase,
    private val getCandidatosParlamentoAndinoUseCase: GetCandidatosParlamentoAndinoUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                getCandidatosPresidencialesUseCase,
                getSenadoresNacionalesUseCase,
                getSenadoresRegionalesUseCase,
                getDiputadosUseCase,
                getCandidatosParlamentoAndinoUseCase,
                preferencesManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}