package com.tecsup.candidatoinfo.data.repository

import android.util.Log
import com.tecsup.candidatoinfo.data.remote.api.ApiService
import com.tecsup.candidatoinfo.domain.model.CandidatoDiputado
import com.tecsup.candidatoinfo.domain.model.CandidatoParlamentoAndino
import com.tecsup.candidatoinfo.domain.model.CandidatoPresidencial
import com.tecsup.candidatoinfo.domain.model.CandidatoSenadorNacional
import com.tecsup.candidatoinfo.domain.model.CandidatoSenadorRegional

class CandidatoRepository(private val apiService: ApiService) {

    suspend fun getCandidatosPresidenciales(): Result<List<CandidatoPresidencial>> {
        return try {
            Log.d("Repository", "Calling getCandidatosPresidenciales (endpoint: /todos/)")
            val response = apiService.getCandidatosPresidenciales()
            Log.d("Repository", "Presidenciales response size: ${response.size}")
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Log.e("Repository", "Error getting candidatos presidenciales", e)
            Result.failure(e)
        }
    }

    suspend fun getSenadoresNacionales(): Result<List<CandidatoSenadorNacional>> {
        return try {
            Log.d("Repository", "Calling getSenadoresNacionales (endpoint: /todos/)")
            val response = apiService.getSenadoresNacionales()
            Log.d("Repository", "Senadores Nacionales response size: ${response.size}")
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Log.e("Repository", "Error getting senadores nacionales", e)
            Result.failure(e)
        }
    }

    suspend fun getSenadoresRegionales(circunscripcionId: Int): Result<List<CandidatoSenadorRegional>> {
        return try {
            Log.d("Repository", "Calling getSenadoresRegionales with circunscripcionId=$circunscripcionId")
            val response = apiService.getSenadoresRegionales(circunscripcionId)
            Log.d("Repository", "Senadores Regionales response size: ${response.size}")
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Log.e("Repository", "Error getting senadores regionales for circunscripcion $circunscripcionId", e)
            Result.failure(e)
        }
    }

    suspend fun getDiputados(circunscripcionId: Int): Result<List<CandidatoDiputado>> {
        return try {
            Log.d("Repository", "Calling getDiputados with circunscripcionId=$circunscripcionId")
            val response = apiService.getDiputados(circunscripcionId)
            Log.d("Repository", "Diputados response size: ${response.size}")
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Log.e("Repository", "Error getting diputados for circunscripcion $circunscripcionId", e)
            Result.failure(e)
        }
    }

    suspend fun getCandidatosParlamentoAndino(): Result<List<CandidatoParlamentoAndino>> {
        return try {
            Log.d("Repository", "Calling getCandidatosParlamentoAndino (endpoint: /todos/)")
            val response = apiService.getCandidatosParlamentoAndino()
            Log.d("Repository", "Parlamento Andino response size: ${response.size}")
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Log.e("Repository", "Error getting parlamento andino", e)
            Result.failure(e)
        }
    }
}