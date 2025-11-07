package com.tecsup.candidatoinfo.presentation.screens.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.domain.usecase.GetCircunscripcionesUseCase

class RegionSelectionViewModelFactory(
    private val getCircunscripcionesUseCase: GetCircunscripcionesUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegionSelectionViewModel::class.java)) {
            return RegionSelectionViewModel(getCircunscripcionesUseCase, preferencesManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}