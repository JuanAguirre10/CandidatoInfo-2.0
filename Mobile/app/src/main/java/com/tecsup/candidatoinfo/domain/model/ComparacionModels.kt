package com.tecsup.candidatoinfo.domain.model

data class CandidatoComparacion(
    val id: Int,
    val nombre: String,
    val fotoUrl: String?,
    val partido: String?,
    val partidoLogo: String?,
    val dni: String?,
    val genero: String?,
    val edad: Int?,
    val profesion: String?,
    val biografia: String?,
    val numeroLista: Int?,
    val posicionLista: Int?,
    val circunscripcion: String?
)

data class ItemComparacion(
    val titulo: String,
    val url: String?
)

data class ComparacionUiState(
    val isLoading: Boolean = false,
    val candidato1: CandidatoComparacion? = null,
    val candidato2: CandidatoComparacion? = null,
    val propuestasCandidato1: List<ItemComparacion> = emptyList(),
    val propuestasCandidato2: List<ItemComparacion> = emptyList(),
    val proyectosCandidato1: List<ItemComparacion> = emptyList(),
    val proyectosCandidato2: List<ItemComparacion> = emptyList(),
    val denunciasCandidato1: List<ItemComparacion> = emptyList(),
    val denunciasCandidato2: List<ItemComparacion> = emptyList(),
    val error: String? = null
)