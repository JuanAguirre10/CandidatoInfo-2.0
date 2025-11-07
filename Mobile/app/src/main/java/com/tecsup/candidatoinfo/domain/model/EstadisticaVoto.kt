package com.tecsup.candidatoinfo.domain.model

data class EstadisticaVoto(
    val candidatoId: Int,
    val candidatoNombre: String,
    val partidoId: Int,
    val partidoNombre: String,
    val fotoUrl: String?,
    val votos: Int
)