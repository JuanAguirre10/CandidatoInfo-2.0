package com.tecsup.candidatoinfo.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.tecsup.candidatoinfo.domain.model.Propuesta
import com.tecsup.candidatoinfo.domain.model.ProyectoRealizado
import com.tecsup.candidatoinfo.domain.model.Denuncia

data class PropuestaDto(
    @SerializedName("id") val id: Int,
    @SerializedName("candidato_id") val candidatoId: Int?,
    @SerializedName("tipo_candidato") val tipoCandidato: String?,
    @SerializedName("titulo") val titulo: String?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("categoria") val categoria: String?,
    @SerializedName("eje_tematico") val ejeTematico: String?,
    @SerializedName("costo_estimado") val costoEstimado: String?,
    @SerializedName("plazo_implementacion") val plazoImplementacion: String?,
    @SerializedName("beneficiarios") val beneficiarios: String?,
    @SerializedName("archivo_url") val archivoUrl: String?,
    @SerializedName("fecha_publicacion") val fechaPublicacion: String?
) {
    fun toDomain() = Propuesta(
        id = id,
        candidatoId = candidatoId,
        tipoCandidato = tipoCandidato,
        titulo = titulo,
        descripcion = descripcion,
        categoria = categoria,
        ejeTematico = ejeTematico,
        costoEstimado = costoEstimado,
        plazoImplementacion = plazoImplementacion,
        beneficiarios = beneficiarios,
        archivoUrl = archivoUrl,
        fechaPublicacion = fechaPublicacion
    )
}

data class ProyectoRealizadoDto(
    @SerializedName("id") val id: Int,
    @SerializedName("candidato_id") val candidatoId: Int?,
    @SerializedName("tipo_candidato") val tipoCandidato: String?,
    @SerializedName("titulo") val titulo: String?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("cargo_periodo") val cargoPeriodo: String?,
    @SerializedName("fecha_inicio") val fechaInicio: String?,
    @SerializedName("fecha_fin") val fechaFin: String?,
    @SerializedName("monto_invertido") val montoInvertido: String?,
    @SerializedName("beneficiarios") val beneficiarios: String?,
    @SerializedName("resultados") val resultados: String?,
    @SerializedName("ubicacion") val ubicacion: String?,
    @SerializedName("evidencia_url") val evidenciaUrl: String?,
    @SerializedName("imagen_url") val imagenUrl: String?,
    @SerializedName("estado") val estado: String?
) {
    fun toDomain() = ProyectoRealizado(
        id = id,
        candidatoId = candidatoId,
        tipoCandidato = tipoCandidato,
        titulo = titulo,
        descripcion = descripcion,
        cargoPeriodo = cargoPeriodo,
        fechaInicio = fechaInicio,
        fechaFin = fechaFin,
        montoInvertido = montoInvertido,
        beneficiarios = beneficiarios,
        resultados = resultados,
        ubicacion = ubicacion,
        evidenciaUrl = evidenciaUrl,
        imagenUrl = imagenUrl,
        estado = estado
    )
}

data class DenunciaDto(
    @SerializedName("id") val id: Int,
    @SerializedName("candidato_id") val candidatoId: Int?,
    @SerializedName("tipo_candidato") val tipoCandidato: String?,
    @SerializedName("titulo") val titulo: String?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("tipo_denuncia") val tipoDenuncia: String?,
    @SerializedName("fecha_denuncia") val fechaDenuncia: String?,
    @SerializedName("entidad_denunciante") val entidadDenunciante: String?,
    @SerializedName("fuente") val fuente: String?,
    @SerializedName("url_fuente") val urlFuente: String?,
    @SerializedName("estado_proceso") val estadoProceso: String?,
    @SerializedName("monto_involucrado") val montoInvolucrado: String?,
    @SerializedName("documento_url") val documentoUrl: String?,
    @SerializedName("gravedad") val gravedad: String?
) {
    fun toDomain() = Denuncia(
        id = id,
        candidatoId = candidatoId,
        tipoCandidato = tipoCandidato,
        titulo = titulo,
        descripcion = descripcion,
        tipoDenuncia = tipoDenuncia,
        fechaDenuncia = fechaDenuncia,
        entidadDenunciante = entidadDenunciante,
        fuente = fuente,
        urlFuente = urlFuente,
        estadoProceso = estadoProceso,
        montoInvolucrado = montoInvolucrado,
        documentoUrl = documentoUrl,
        gravedad = gravedad
    )
}