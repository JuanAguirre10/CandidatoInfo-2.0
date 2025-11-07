package com.tecsup.candidatoinfo.presentation.screens.partidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.candidatoinfo.data.repository.PartidoRepository

class PartidosViewModelFactory(
    private val partidoRepository: PartidoRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PartidosViewModel::class.java)) {
            return PartidosViewModel(partidoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}