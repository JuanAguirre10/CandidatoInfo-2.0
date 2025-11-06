package com.tecsup.candidatoinfo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CircunscripcionDto(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("codigo") val codigo: String?,
    @SerializedName("tipo") val tipo: String?,
    @SerializedName("poblacion") val poblacion: Int?,
    @SerializedName("electores_registrados") val electoresRegistrados: Int?,
    @SerializedName("capital") val capital: String?,
    @SerializedName("imagen_url") val imagenUrl: String?,
    @SerializedName("bandera_url") val banderaUrl: String?,
    @SerializedName("escudo_url") val escudoUrl: String?,
    @SerializedName("numero_diputados") val numeroDiputados: Int?,
    @SerializedName("numero_senadores") val numeroSenadores: Int?,
    @SerializedName("latitud") val latitud: String?,
    @SerializedName("longitud") val longitud: String?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("fecha_registro") val fechaRegistro: String?
)

data class CircunscripcionesResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<CircunscripcionDto>
)