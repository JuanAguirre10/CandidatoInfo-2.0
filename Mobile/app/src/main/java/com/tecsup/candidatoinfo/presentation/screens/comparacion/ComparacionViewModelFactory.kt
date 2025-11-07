package com.tecsup.candidatoinfo.presentation.screens.comparacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.data.repository.InformacionRepository

class ComparacionViewModelFactory(
    private val candidatoRepository: CandidatoRepository,
    private val informacionRepository: InformacionRepository,
    private val preferencesManager: PreferencesManager,
    private val tipoCandidato: String,
    private val candidatoId1: Int,
    private val candidatoId2: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComparacionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ComparacionViewModel(
                candidatoRepository,
                informacionRepository,
                preferencesManager,
                tipoCandidato,
                candidatoId1,
                candidatoId2
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}