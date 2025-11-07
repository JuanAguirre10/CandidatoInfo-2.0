package com.tecsup.candidatoinfo.data.repository

import android.util.Log
import com.tecsup.candidatoinfo.data.remote.api.ApiService
import com.tecsup.candidatoinfo.data.remote.dto.PartidoDto
import com.tecsup.candidatoinfo.domain.model.Partido

class PartidoRepository(private val apiService: ApiService) {

    suspend fun getPartidosActivos(): Result<List<Partido>> {
        return try {
            Log.d("PartidoRepository", "Fetching partidos activos")
            val response = apiService.getPartidosActivos()
            Log.d("PartidoRepository", "Received ${response.size} partidos")
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Log.e("PartidoRepository", "Error fetching partidos", e)
            Result.failure(e)
        }
    }

    private fun PartidoDto.toDomain(): Partido {
        return Partido(
            id = id,
            nombre = nombre ?: "",
            siglas = siglas ?: "",
            logoUrl = logoUrl,
            colorPrincipal = colorPrincipal,
            tipo = tipo,
            ideologia = ideologia,
            lider = lider,
            secretarioGeneral = secretarioGeneral,
            fundacionAno = fundacionAno,
            descripcion = descripcion,
            sitioWeb = sitioWeb,
            estado = estado
        )
    }
}