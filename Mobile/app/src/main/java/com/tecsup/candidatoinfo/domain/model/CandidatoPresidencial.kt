package com.tecsup.candidatoinfo.domain.model

data class CandidatoPresidencial(
    val id: Int,
    val partidoId: Int?,
    val partidoNombre: String?,
    val partidoSiglas: String?,
    val partidoLogo: String?,
    val presidenteNombre: String?,
    val presidenteApellidos: String?,
    val presidenteDni: String?,
    val presidenteFotoUrl: String?,
    val presidenteGenero: String?,
    val presidenteProfesion: String?,
    val vicepresidente1Nombre: String?,
    val vicepresidente1Apellidos: String?,
    val vicepresidente1Profesion: String?,
    val vicepresidente2Nombre: String?,
    val vicepresidente2Apellidos: String?,
    val vicepresidente2Profesion: String?,
    val numeroLista: Int?,
    val estado: String?
)