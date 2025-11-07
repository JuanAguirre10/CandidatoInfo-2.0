package com.tecsup.candidatoinfo.data.repository

import android.util.Log
import com.tecsup.candidatoinfo.data.remote.api.ApiService
import com.tecsup.candidatoinfo.domain.model.Denuncia
import com.tecsup.candidatoinfo.domain.model.Propuesta
import com.tecsup.candidatoinfo.domain.model.ProyectoRealizado

class InformacionRepository(private val apiService: ApiService) {

    suspend fun getPropuestasByCandidato(
        candidatoId: Int,
        tipoCandidato: String
    ): Result<List<Propuesta>> {
        return try {
            Log.d("Repository", "Getting propuestas for candidato $candidatoId tipo $tipoCandidato")
            val response = apiService.getPropuestasByCandidato(candidatoId, tipoCandidato)
            Log.d("Repository", "Propuestas response size: ${response.size}")
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Log.e("Repository", "Error getting propuestas", e)
            Result.failure(e)
        }
    }

    suspend fun getProyectosByCandidato(
        candidatoId: Int,
        tipoCandidato: String
    ): Result<List<ProyectoRealizado>> {
        return try {
            Log.d("Repository", "Getting proyectos for candidato $candidatoId tipo $tipoCandidato")
            val response = apiService.getProyectosByCandidato(candidatoId, tipoCandidato)
            Log.d("Repository", "Proyectos response size: ${response.size}")
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Log.e("Repository", "Error getting proyectos", e)
            Result.failure(e)
        }
    }

    suspend fun getDenunciasByCandidato(
        candidatoId: Int,
        tipoCandidato: String
    ): Result<List<Denuncia>> {
        return try {
            Log.d("Repository", "Getting denuncias for candidato $candidatoId tipo $tipoCandidato")
            val response = apiService.getDenunciasByCandidato(candidatoId, tipoCandidato)
            Log.d("Repository", "Denuncias response size: ${response.size}")
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Log.e("Repository", "Error getting denuncias", e)
            Result.failure(e)
        }
    }
}