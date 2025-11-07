package com.tecsup.candidatoinfo.domain.model

data class CandidatoParlamentoAndino(
    val id: Int,
    val partidoId: Int?,
    val partidoNombre: String?,
    val partidoSiglas: String?,
    val partidoLogo: String?,
    val nombre: String?,
    val apellidos: String?,
    val nombreCompleto: String?,
    val dni: String?,
    val fotoUrl: String?,
    val genero: String?,
    val edad: Int?,
    val profesion: String?,
    val posicionLista: Int?,
    val numeroPreferencial: Int?,
    val biografia: String?,
    val idiomas: String?,
    val estado: String?
)