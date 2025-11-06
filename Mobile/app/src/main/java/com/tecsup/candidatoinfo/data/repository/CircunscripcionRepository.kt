package com.tecsup.candidatoinfo.data.repository

import com.tecsup.candidatoinfo.data.remote.api.ApiService
import com.tecsup.candidatoinfo.data.remote.dto.CircunscripcionDto
import com.tecsup.candidatoinfo.domain.model.Circunscripcion

class CircunscripcionRepository(
    private val apiService: ApiService
) {

    suspend fun getCircunscripciones(): Result<List<Circunscripcion>> {
        return try {
            val response = apiService.getCircunscripciones()
            val circunscripciones = response.results.map { it.toDomainModel() }
            Result.success(circunscripciones)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCircunscripcionesSelectList(): Result<List<Circunscripcion>> {
        return try {
            val response = apiService.getCircunscripcionesSelectList()
            val circunscripciones = response.map { it.toDomainModel() }
            Result.success(circunscripciones)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun CircunscripcionDto.toDomainModel(): Circunscripcion {
        return Circunscripcion(
            id = id,
            nombre = nombre ?: "",
            codigo = codigo ?: "",
            tipo = tipo ?: "",
            poblacion = poblacion,
            electoresRegistrados = electoresRegistrados,
            capital = capital,
            imagenUrl = imagenUrl,
            banderaUrl = banderaUrl,
            escudoUrl = escudoUrl,
            numeroDiputados = numeroDiputados,
            numeroSenadores = numeroSenadores,
            latitud = latitud?.toDoubleOrNull(),
            longitud = longitud?.toDoubleOrNull(),
            descripcion = descripcion
        )
    }
}