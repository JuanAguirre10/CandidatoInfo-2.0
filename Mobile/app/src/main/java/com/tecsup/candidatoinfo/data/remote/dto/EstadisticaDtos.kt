package com.tecsup.candidatoinfo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EstadisticasResponse(
    @SerializedName("tipo_eleccion") val tipoEleccion: String,
    @SerializedName("mes") val mes: Int,
    @SerializedName("anio") val anio: Int,
    @SerializedName("total_votos") val totalVotos: Int,
    @SerializedName("resultados") val resultados: List<EstadisticaVotoDto>
)

data class EstadisticaVotoDto(
    @SerializedName("candidato_id") val candidatoId: Int,
    @SerializedName("candidato_nombre") val candidatoNombre: String?,
    @SerializedName("partido_id") val partidoId: Int?,
    @SerializedName("partido_nombre") val partidoNombre: String?,
    @SerializedName("partido_siglas") val partidoSiglas: String?,
    @SerializedName("foto_url") val fotoUrl: String?,
    @SerializedName("votos") val votos: Int,
    @SerializedName("porcentaje") val porcentaje: Float
)