package com.tecsup.candidatoinfo.presentation.screens.estadisticas

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.presentation.theme.PinkPrimary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.tecsup.candidatoinfo.data.repository.*

enum class TipoEleccion {
    PRESIDENCIAL,
    SENADOR_NACIONAL,
    SENADOR_REGIONAL,
    DIPUTADO,
    PARLAMENTO_ANDINO
}

data class EstadisticaCandidato(
    val nombre: String,
    val votos: Int,
    val color: Color
)

data class VotosPartido(
    val nombre: String,
    val votos: Int,
    val porcentaje: Float,
    val color: Color
)

data class CandidatoRanking(
    val id: Int,
    val nombre: String,
    val partido: String,
    val fotoUrl: String?,
    val votos: Int,
    val porcentaje: Float
)

data class EstadisticasUiState(
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val totalVotos: Int = 0,
    val top5Candidatos: List<EstadisticaCandidato> = emptyList(),
    val votosPorPartido: List<VotosPartido> = emptyList(),
    val rankingCompleto: List<CandidatoRanking> = emptyList(),
    val error: String? = null
)

class EstadisticasViewModel(
    private val repository: EstadisticasRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstadisticasUiState())
    val uiState: StateFlow<EstadisticasUiState> = _uiState.asStateFlow()

    private val colores = listOf(
        Color(0xFFE91E63),
        Color(0xFF2196F3),
        Color(0xFFFF9800),
        Color(0xFF4CAF50),
        Color(0xFF9C27B0),
        Color(0xFFFF5722)
    )

    init {
        setTipoEleccion(TipoEleccion.PRESIDENCIAL)
    }

    fun setTipoEleccion(tipo: TipoEleccion) {
        _uiState.value = _uiState.value.copy(selectedTabIndex = tipo.ordinal)
        cargarEstadisticas(tipo)
    }

    private fun cargarEstadisticas(tipo: TipoEleccion) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val tipoEleccion = when (tipo) {
                TipoEleccion.PRESIDENCIAL -> "presidencial"
                TipoEleccion.SENADOR_NACIONAL -> "senador_nacional"
                TipoEleccion.SENADOR_REGIONAL -> "senador_regional"
                TipoEleccion.DIPUTADO -> "diputado"
                TipoEleccion.PARLAMENTO_ANDINO -> "parlamento_andino"
            }

            val result = repository.getEstadisticas(tipoEleccion)

            result.fold(
                onSuccess = { estadisticas ->
                    // Filtrar los que tienen datos válidos
                    val estadisticasValidas = estadisticas.filter {
                        !it.candidatoNombre.isNullOrBlank()
                    }

                    val totalVotos = estadisticasValidas.sumOf { it.votos }

                    // Top 5 candidatos
                    val top5 = estadisticasValidas.take(5).mapIndexed { index, est ->
                        EstadisticaCandidato(
                            nombre = est.candidatoNombre ?: "Sin nombre",
                            votos = est.votos,
                            color = colores[index % colores.size]
                        )
                    }.toMutableList()

                    // Otros (del 6to en adelante)
                    val votosOtros = estadisticasValidas.drop(5).sumOf { it.votos }
                    if (votosOtros > 0) {
                        top5.add(
                            EstadisticaCandidato(
                                nombre = "Otros",
                                votos = votosOtros,
                                color = Color.Gray
                            )
                        )
                    }

                    // Votos por partido
                    val votosPorPartido = estadisticasValidas
                        .groupBy { it.partidoNombre ?: "Sin partido" }
                        .map { (partido, lista) ->
                            val votos = lista.sumOf { it.votos }
                            val porcentaje = if (totalVotos > 0) {
                                (votos.toFloat() / totalVotos * 100)
                            } else 0f
                            VotosPartido(
                                nombre = partido,
                                votos = votos,
                                porcentaje = String.format("%.1f", porcentaje).toFloat(),
                                color = colores[Math.abs(partido.hashCode()) % colores.size]
                            )
                        }
                        .sortedByDescending { it.votos }

                    // Ranking completo
                    val ranking = estadisticasValidas.map { est ->
                        CandidatoRanking(
                            id = est.candidatoId,
                            nombre = est.candidatoNombre ?: "Sin nombre",
                            partido = est.partidoSiglas ?: est.partidoNombre ?: "Sin partido",
                            fotoUrl = est.fotoUrl,
                            votos = est.votos,
                            porcentaje = if (totalVotos > 0) {
                                String.format("%.1f", (est.votos.toFloat() / totalVotos * 100)).toFloat()
                            } else 0f
                        )
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        totalVotos = totalVotos,
                        top5Candidatos = top5,
                        votosPorPartido = votosPorPartido,
                        rankingCompleto = ranking,
                        error = null
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Error al cargar estadísticas"
                    )
                }
            )
        }
    }
}