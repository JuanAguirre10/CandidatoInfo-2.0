package com.tecsup.candidatoinfo.domain.usecase

import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.domain.model.CandidatoPresidencial

class GetCandidatosPresidencialesUseCase(
    private val repository: CandidatoRepository
) {
    suspend operator fun invoke(): Result<List<CandidatoPresidencial>> {
        return repository.getCandidatosPresidenciales()
    }
}