package com.tecsup.candidatoinfo.domain.usecase

import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.domain.model.CandidatoSenadorNacional

class GetSenadoresNacionalesUseCase(
    private val repository: CandidatoRepository
) {
    suspend operator fun invoke(): Result<List<CandidatoSenadorNacional>> {
        return repository.getSenadoresNacionales()
    }
}