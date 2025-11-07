package com.tecsup.candidatoinfo.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.tecsup.candidatoinfo.domain.model.CandidatoPresidencial

data class CandidatoPresidencialDto(
    @SerializedName("id") val id: Int,
    @SerializedName("partido") val partidoId: Int?,
    @SerializedName("partido_nombre") val partidoNombre: String?,
    @SerializedName("partido_siglas") val partidoSiglas: String?,
    @SerializedName("partido_logo") val partidoLogo: String?,
    @SerializedName("presidente_nombre") val presidenteNombre: String?,
    @SerializedName("presidente_apellidos") val presidenteApellidos: String?,
    @SerializedName("presidente_dni") val presidenteDni: String?,
    @SerializedName("presidente_foto_url") val presidenteFotoUrl: String?,
    @SerializedName("presidente_genero") val presidenteGenero: String?,
    @SerializedName("presidente_profesion") val presidenteProfesion: String?,
    @SerializedName("vicepresidente1_nombre") val vicepresidente1Nombre: String?,
    @SerializedName("vicepresidente1_apellidos") val vicepresidente1Apellidos: String?,
    @SerializedName("vicepresidente1_profesion") val vicepresidente1Profesion: String?,
    @SerializedName("vicepresidente2_nombre") val vicepresidente2Nombre: String?,
    @SerializedName("vicepresidente2_apellidos") val vicepresidente2Apellidos: String?,
    @SerializedName("vicepresidente2_profesion") val vicepresidente2Profesion: String?,
    @SerializedName("numero_lista") val numeroLista: Int?,
    @SerializedName("estado") val estado: String?
) {
    fun toDomain() = CandidatoPresidencial(
        id = id,
        partidoId = partidoId,
        partidoNombre = partidoNombre,
        partidoSiglas = partidoSiglas,
        partidoLogo = partidoLogo,
        presidenteNombre = presidenteNombre,
        presidenteApellidos = presidenteApellidos,
        presidenteDni = presidenteDni,
        presidenteFotoUrl = presidenteFotoUrl,
        presidenteGenero = presidenteGenero,
        presidenteProfesion = presidenteProfesion,
        vicepresidente1Nombre = vicepresidente1Nombre,
        vicepresidente1Apellidos = vicepresidente1Apellidos,
        vicepresidente1Profesion = vicepresidente1Profesion,
        vicepresidente2Nombre = vicepresidente2Nombre,
        vicepresidente2Apellidos = vicepresidente2Apellidos,
        vicepresidente2Profesion = vicepresidente2Profesion,
        numeroLista = numeroLista,
        estado = estado
    )
}