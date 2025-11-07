package com.tecsup.candidatoinfo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PartidoDto(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("siglas") val siglas: String?,
    @SerializedName("logo_url") val logoUrl: String?,
    @SerializedName("color_principal") val colorPrincipal: String?,
    @SerializedName("tipo") val tipo: String?,
    @SerializedName("ideologia") val ideologia: String?,
    @SerializedName("lider") val lider: String?,
    @SerializedName("secretario_general") val secretarioGeneral: String?,
    @SerializedName("fundacion_año") val fundacionAno: Int?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("sitio_web") val sitioWeb: String?,
    @SerializedName("estado") val estado: String?
)