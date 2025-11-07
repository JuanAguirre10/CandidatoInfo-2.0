package com.tecsup.candidatoinfo.domain.usecase

import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.domain.model.CandidatoSenadorRegional

class GetSenadoresRegionalesUseCase(
    private val repository: CandidatoRepository
) {
    suspend operator fun invoke(circunscripcionId: Int): Result<List<CandidatoSenadorRegional>> {
        return repository.getSenadoresRegionales(circunscripcionId)
    }
}