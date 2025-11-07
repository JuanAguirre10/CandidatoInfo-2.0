package com.tecsup.candidatoinfo.presentation.screens.candidato_detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.data.repository.InformacionRepository

class CandidatoDetalleViewModelFactory(
    private val candidatoRepository: CandidatoRepository,
    private val informacionRepository: InformacionRepository,
    private val preferencesManager: PreferencesManager,
    private val tipoCandidato: String,
    private val candidatoId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CandidatoDetalleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CandidatoDetalleViewModel(
                candidatoRepository,
                informacionRepository,
                preferencesManager,
                tipoCandidato,
                candidatoId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}