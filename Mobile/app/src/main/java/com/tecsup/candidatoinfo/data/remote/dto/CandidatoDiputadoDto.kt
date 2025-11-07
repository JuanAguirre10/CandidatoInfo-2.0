package com.tecsup.candidatoinfo.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.tecsup.candidatoinfo.domain.model.CandidatoDiputado

data class CandidatoDiputadoDto(
    @SerializedName("id") val id: Int,
    @SerializedName("partido") val partidoId: Int?,
    @SerializedName("partido_nombre") val partidoNombre: String?,
    @SerializedName("partido_siglas") val partidoSiglas: String?,
    @SerializedName("partido_logo") val partidoLogo: String?,
    @SerializedName("circunscripcion") val circunscripcionId: Int?,
    @SerializedName("circunscripcion_nombre") val circunscripcionNombre: String?,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("apellidos") val apellidos: String?,
    @SerializedName("nombre_completo") val nombreCompleto: String?,
    @SerializedName("dni") val dni: String?,
    @SerializedName("foto_url") val fotoUrl: String?,
    @SerializedName("genero") val genero: String?,
    @SerializedName("edad") val edad: Int?,
    @SerializedName("profesion") val profesion: String?,
    @SerializedName("posicion_lista") val posicionLista: Int?,
    @SerializedName("numero_preferencial") val numeroPreferencial: Int?,
    @SerializedName("biografia") val biografia: String?,
    @SerializedName("es_natural_circunscripcion") val esNaturalCircunscripcion: Boolean?,
    @SerializedName("estado") val estado: String?
) {
    fun toDomain() = CandidatoDiputado(
        id = id,
        partidoId = partidoId,
        partidoNombre = partidoNombre,
        partidoSiglas = partidoSiglas,
        partidoLogo = partidoLogo,
        circunscripcionId = circunscripcionId,
        circunscripcionNombre = circunscripcionNombre,
        nombre = nombre,
        apellidos = apellidos,
        nombreCompleto = nombreCompleto,
        dni = dni,
        fotoUrl = fotoUrl,
        genero = genero,
        edad = edad,
        profesion = profesion,
        posicionLista = posicionLista,
        numeroPreferencial = numeroPreferencial,
        biografia = biografia,
        esNaturalCircunscripcion = esNaturalCircunscripcion,
        estado = estado
    )
}