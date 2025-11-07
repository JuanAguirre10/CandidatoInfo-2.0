package com.tecsup.candidatoinfo.presentation.screens.votacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.data.repository.VotacionRepository

class VotacionViewModelFactory(
    private val candidatoRepository: CandidatoRepository,
    private val votacionRepository: VotacionRepository,
    private val preferencesManager: PreferencesManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VotacionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VotacionViewModel(
                candidatoRepository,
                votacionRepository,
                preferencesManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}