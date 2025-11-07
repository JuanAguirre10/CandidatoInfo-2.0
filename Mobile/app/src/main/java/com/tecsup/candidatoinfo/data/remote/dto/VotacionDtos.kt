package com.tecsup.candidatoinfo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DNIValidacionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("dni") val dni: String,
    @SerializedName("nombre_completo") val nombreCompleto: String,
    @SerializedName("mensaje") val mensaje: String
)

data class VotoRequest(
    @SerializedName("dni") val dni: String,
    @SerializedName("tipo_eleccion") val tipoEleccion: String,
    @SerializedName("candidato_id") val candidatoId: Int,
    @SerializedName("circunscripcion") val circunscripcion: Int?,
    @SerializedName("mes_simulacro") val mesSimulacro: Int,
    @SerializedName("anio_simulacro") val anioSimulacro: Int
)

data class VotoResponse(
    @SerializedName("message") val message: String,
    @SerializedName("votante") val votante: String
)