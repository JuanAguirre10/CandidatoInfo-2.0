package com.tecsup.candidatoinfo.domain.usecase

import com.tecsup.candidatoinfo.data.repository.CircunscripcionRepository
import com.tecsup.candidatoinfo.domain.model.Circunscripcion

class GetCircunscripcionesUseCase(
    private val repository: CircunscripcionRepository
) {
    suspend operator fun invoke(): Result<List<Circunscripcion>> {
        return repository.getCircunscripciones()
    }
}