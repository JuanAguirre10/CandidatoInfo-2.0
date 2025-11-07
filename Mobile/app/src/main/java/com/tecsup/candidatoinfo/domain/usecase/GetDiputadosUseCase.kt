package com.tecsup.candidatoinfo.domain.usecase

import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.domain.model.CandidatoDiputado

class GetDiputadosUseCase(
    private val repository: CandidatoRepository
) {
    suspend operator fun invoke(circunscripcionId: Int): Result<List<CandidatoDiputado>> {
        return repository.getDiputados(circunscripcionId)
    }
}