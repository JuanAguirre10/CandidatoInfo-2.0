package com.tecsup.candidatoinfo.presentation.screens.candidatos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.repository.CandidatoRepository

class CandidatosViewModelFactory(
    private val repository: CandidatoRepository,
    private val preferencesManager: PreferencesManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CandidatosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CandidatosViewModel(repository, preferencesManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}