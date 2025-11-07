package com.tecsup.candidatoinfo.domain.model

data class Partido(
    val id: Int,
    val nombre: String,
    val siglas: String,
    val logoUrl: String?,
    val colorPrincipal: String?,
    val tipo: String?,
    val ideologia: String?,
    val lider: String?,
    val secretarioGeneral: String?,
    val fundacionAno: Int?,
    val descripcion: String?,
    val sitioWeb: String?,
    val estado: String?
)