package com.tecsup.candidatoinfo.data.repository

import android.util.Log
import com.tecsup.candidatoinfo.data.remote.api.ApiService
import com.tecsup.candidatoinfo.data.remote.dto.DNIValidacionResponse
import com.tecsup.candidatoinfo.data.remote.dto.VotoRequest
import java.util.Calendar


class VotacionRepository(private val apiService: ApiService) {

    suspend fun registrarVoto(
        dni: String,
        tipoEleccion: String,
        candidatoId: Int,
        circunscripcionId: Int?
    ): Result<Unit> {
        return try {
            // Usar Calendar en lugar de LocalDate (compatible con API 24+)
            val calendar = Calendar.getInstance()
            val mes = calendar.get(Calendar.MONTH) + 1  // Calendar.MONTH es 0-based
            val anio = calendar.get(Calendar.YEAR)

            Log.d("VotacionRepository", """
                ═══════════════════════════════════
                Registrando voto:
                - DNI: $dni
                - Tipo: $tipoEleccion
                - Candidato ID: $candidatoId
                - Circunscripción: ${circunscripcionId ?: "null (no requiere)"}
                - Mes: $mes
                - Año: $anio
                ═══════════════════════════════════
            """.trimIndent())

            val request = VotoRequest(
                dni = dni,
                tipoEleccion = tipoEleccion,
                candidatoId = candidatoId,
                circunscripcion = circunscripcionId,  // ← Nombre correcto del parámetro
                mesSimulacro = mes,
                anioSimulacro = anio
            )

            Log.d("VotacionRepository", "Request JSON: $request")

            val response = apiService.registrarVoto(request)
            Log.d("VotacionRepository", "✓ Voto registrado exitosamente: ${response.message}")
            Result.success(Unit)

        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("VotacionRepository", """
                ✗ HTTP Error ${e.code()}: 
                Body: $errorBody
            """.trimIndent(), e)
            Result.failure(Exception("Error ${e.code()}: ${errorBody ?: e.message}"))
        } catch (e: Exception) {
            Log.e("VotacionRepository", "✗ Error registrando voto: ${e.message}", e)
            Result.failure(e)
        }
    }
}