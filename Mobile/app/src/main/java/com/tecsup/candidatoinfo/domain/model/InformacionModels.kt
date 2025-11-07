package com.tecsup.candidatoinfo.domain.model

data class Propuesta(
    val id: Int,
    val candidatoId: Int?,
    val tipoCandidato: String?,
    val titulo: String?,
    val descripcion: String?,
    val categoria: String?,
    val ejeTematico: String?,
    val costoEstimado: String?,
    val plazoImplementacion: String?,
    val beneficiarios: String?,
    val archivoUrl: String?,
    val fechaPublicacion: String?
)

data class ProyectoRealizado(
    val id: Int,
    val candidatoId: Int?,
    val tipoCandidato: String?,
    val titulo: String?,
    val descripcion: String?,
    val cargoPeriodo: String?,
    val fechaInicio: String?,
    val fechaFin: String?,
    val montoInvertido: String?,
    val beneficiarios: String?,
    val resultados: String?,
    val ubicacion: String?,
    val evidenciaUrl: String?,
    val imagenUrl: String?,
    val estado: String?
)

data class Denuncia(
    val id: Int,
    val candidatoId: Int?,
    val tipoCandidato: String?,
    val titulo: String?,
    val descripcion: String?,
    val tipoDenuncia: String?,
    val fechaDenuncia: String?,
    val entidadDenunciante: String?,
    val fuente: String?,
    val urlFuente: String?,
    val estadoProceso: String?,
    val montoInvolucrado: String?,
    val documentoUrl: String?,
    val gravedad: String?
)