package com.tecsup.candidatoinfo.domain.usecase

import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.domain.model.CandidatoParlamentoAndino

class GetCandidatosParlamentoAndinoUseCase(
    private val repository: CandidatoRepository
) {
    suspend operator fun invoke(): Result<List<CandidatoParlamentoAndino>> {
        return repository.getCandidatosParlamentoAndino()
    }
}