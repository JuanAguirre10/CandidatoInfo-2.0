package com.tecsup.candidatoinfo.data.repository

import android.util.Log
import com.tecsup.candidatoinfo.data.remote.api.ApiService
import com.tecsup.candidatoinfo.data.remote.dto.DNIValidacionResponse
import com.tecsup.candidatoinfo.data.remote.dto.VotoRequest
import java.util.Calendar

class VotacionRepository(private val apiService: ApiService) {

    suspend fun validarDNI(dni: String): Result<DNIValidacionResponse> {
        return try {
            Log.d("VotacionRepository", "Validando DNI: $dni")
            val response = apiService.validarDNI(dni)

            if (response.success) {
                Log.d("VotacionRepository", "✓ DNI válido: ${response.nombreCompleto}")
                Result.success(response)
            } else {
                Log.e("VotacionRepository", "✗ DNI inválido: ${response.mensaje}")
                Result.failure(Exception(response.mensaje))
            }
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("VotacionRepository", "✗ HTTP Error ${e.code()}: $errorBody", e)
            Result.failure(Exception("Error ${e.code()}: ${errorBody ?: e.message}"))
        } catch (e: Exception) {
            Log.e("VotacionRepository", "✗ Error validando DNI: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun registrarVoto(
        dni: String,
        tipoEleccion: String,
        candidatoId: Int,
        circunscripcionId: Int?
    ): Result<Unit> {
        return try {
            val calendar = Calendar.getInstance()
            val mes = calendar.get(Calendar.MONTH) + 1
            val anio = calendar.get(Calendar.YEAR)

            Log.d("VotacionRepository", """
                ═══════════════════════════════════
                Registrando voto:
                - DNI: $dni
                - Tipo: $tipoEleccion
                - Candidato ID: $candidatoId
                - Circunscripción: ${circunscripcionId ?: "null"}
                - Mes: $mes
                - Año: $anio
                ═══════════════════════════════════
            """.trimIndent())

            val request = VotoRequest(
                dni = dni,
                tipoEleccion = tipoEleccion,
                candidatoId = candidatoId,
                circunscripcion = circunscripcionId,
                mesSimulacro = mes,
                anioSimulacro = anio
            )

            val response = apiService.registrarVoto(request)
            Log.d("VotacionRepository", "✓ Voto registrado: ${response.message}")
            Result.success(Unit)

        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("VotacionRepository", "✗ HTTP Error ${e.code()}: $errorBody", e)
            Result.failure(Exception("Error ${e.code()}: ${errorBody ?: e.message}"))
        } catch (e: Exception) {
            Log.e("VotacionRepository", "✗ Error registrando voto: ${e.message}", e)
            Result.failure(e)
        }
    }
}