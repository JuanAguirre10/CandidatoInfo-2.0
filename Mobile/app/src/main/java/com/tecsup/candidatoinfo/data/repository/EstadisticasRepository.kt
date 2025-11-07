package com.tecsup.candidatoinfo.data.repository

import android.util.Log
import com.tecsup.candidatoinfo.data.remote.api.ApiService
import com.tecsup.candidatoinfo.data.remote.dto.EstadisticaVotoDto
import java.util.Calendar

class EstadisticasRepository(private val apiService: ApiService) {

    suspend fun getEstadisticas(tipoEleccion: String): Result<List<EstadisticaVotoDto>> {
        return try {
            val calendar = Calendar.getInstance()
            val mes = calendar.get(Calendar.MONTH) + 1
            val anio = calendar.get(Calendar.YEAR)

            Log.d("EstadisticasRepository", """
            ═══════════════════════════════════
            Obteniendo estadísticas:
            - Tipo: $tipoEleccion
            - Mes: $mes
            - Año: $anio
            - URL: api/simulacro/votos/resultados_por_candidato/?tipo_eleccion=$tipoEleccion&mes_simulacro=$mes&anio_simulacro=$anio
            ═══════════════════════════════════
        """.trimIndent())

            val response = apiService.getEstadisticasPorTipo(
                tipoEleccion = tipoEleccion,
                mes = mes,
                anio = anio
            )

            Log.d("EstadisticasRepository", """
            ✓ Respuesta completa:
            - Total votos: ${response.totalVotos}
            - Resultados: ${response.resultados.size}
        """.trimIndent())

            if (response.resultados.isEmpty()) {
                Log.w("EstadisticasRepository", "⚠️ No hay resultados en la respuesta")
            }

            response.resultados.forEachIndexed { index, est ->
                Log.d("EstadisticasRepository", """
                Candidato ${index + 1}:
                - ID: ${est.candidatoId}
                - Nombre: '${est.candidatoNombre}'
                - Partido: '${est.partidoNombre}'
                - Siglas: '${est.partidoSiglas}'
                - Votos: ${est.votos}
                - %: ${est.porcentaje}
            """.trimIndent())
            }

            Result.success(response.resultados)

        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("EstadisticasRepository", "✗ HTTP Error ${e.code()}: $errorBody", e)
            Result.failure(Exception("Error ${e.code()}: ${errorBody ?: e.message}"))
        } catch (e: Exception) {
            Log.e("EstadisticasRepository", "✗ Error obteniendo estadísticas: ${e.message}", e)
            e.printStackTrace()
            Result.failure(e)
        }
    }
}