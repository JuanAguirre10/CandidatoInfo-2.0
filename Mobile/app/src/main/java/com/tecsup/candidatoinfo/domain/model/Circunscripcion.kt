package com.tecsup.candidatoinfo.domain.model

data class Circunscripcion(
    val id: Int,
    val nombre: String,
    val codigo: String,
    val tipo: String,
    val poblacion: Int?,
    val electoresRegistrados: Int?,
    val capital: String?,
    val imagenUrl: String?,
    val banderaUrl: String?,
    val escudoUrl: String?,
    val numeroDiputados: Int?,
    val numeroSenadores: Int?,
    val latitud: Double?,
    val longitud: Double?,
    val descripcion: String?
)