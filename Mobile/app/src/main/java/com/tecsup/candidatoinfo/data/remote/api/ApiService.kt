package com.tecsup.candidatoinfo.data.remote.api

import com.tecsup.candidatoinfo.data.remote.dto.*
import com.tecsup.candidatoinfo.domain.model.EstadisticaVoto
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {

    @GET("api/circunscripciones/todas/")
    suspend fun getCircunscripciones(): CircunscripcionesResponse

    @GET("api/circunscripciones/select_list/")
    suspend fun getCircunscripcionesSelectList(): List<CircunscripcionDto>

    @GET("api/candidatos/presidenciales/todos/")
    suspend fun getCandidatosPresidenciales(): List<CandidatoPresidencialDto>

    @GET("api/candidatos/senadores-nacionales/todos/")
    suspend fun getSenadoresNacionales(): List<CandidatoSenadorNacionalDto>

    @GET("api/candidatos/senadores-regionales/por_circunscripcion/")
    suspend fun getSenadoresRegionales(
        @Query("circunscripcion_id") circunscripcionId: Int
    ): List<CandidatoSenadorRegionalDto>

    @GET("api/candidatos/diputados/por_circunscripcion/")
    suspend fun getDiputados(
        @Query("circunscripcion_id") circunscripcionId: Int
    ): List<CandidatoDiputadoDto>

    @GET("api/candidatos/parlamento-andino/todos/")
    suspend fun getCandidatosParlamentoAndino(): List<CandidatoParlamentoAndinoDto>

    @GET("api/partidos/activos/")
    suspend fun getPartidosActivos(): List<PartidoDto>

    @GET("api/informacion/propuestas/por_candidato/")
    suspend fun getPropuestasByCandidato(
        @Query("candidato_id") candidatoId: Int,
        @Query("tipo_candidato") tipoCandidato: String
    ): List<PropuestaDto>

    @GET("api/informacion/proyectos/por_candidato/")
    suspend fun getProyectosByCandidato(
        @Query("candidato_id") candidatoId: Int,
        @Query("tipo_candidato") tipoCandidato: String
    ): List<ProyectoRealizadoDto>

    @GET("api/informacion/denuncias/por_candidato/")
    suspend fun getDenunciasByCandidato(
        @Query("candidato_id") candidatoId: Int,
        @Query("tipo_candidato") tipoCandidato: String
    ): List<DenunciaDto>

    @GET("api/simulacro/votos/validar_dni/")
    suspend fun validarDNI(@Query("dni") dni: String): DNIValidacionResponse

    @POST("api/simulacro/votos/")
    suspend fun registrarVoto(@Body request: VotoRequest): VotoResponse

    @GET("api/simulacro/votos/resultados_por_candidato/")
    suspend fun getEstadisticasPorTipo(
        @Query("tipo_eleccion") tipoEleccion: String,
        @Query("mes_simulacro") mes: Int,
        @Query("anio_simulacro") anio: Int
    ): EstadisticasResponse

}