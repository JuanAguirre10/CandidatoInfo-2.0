package com.tecsup.candidatoinfo.presentation.screens.estadisticas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.repository.*

class EstadisticasViewModelFactory(
    private val repository: EstadisticasRepository,
    private val preferencesManager: PreferencesManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstadisticasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EstadisticasViewModel(repository, preferencesManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}