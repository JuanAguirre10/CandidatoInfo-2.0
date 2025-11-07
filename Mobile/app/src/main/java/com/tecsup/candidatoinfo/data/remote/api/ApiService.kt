package com.tecsup.candidatoinfo.data.remote.api

import com.tecsup.candidatoinfo.data.remote.dto.*
import retrofit2.http.Query
import retrofit2.http.GET

interface ApiService {

    @GET("api/circunscripciones/todas/")
    suspend fun getCircunscripciones(): CircunscripcionesResponse

    @GET("api/circunscripciones/select_list/")
    suspend fun getCircunscripcionesSelectList(): List<CircunscripcionDto>

    @GET("api/candidatos/presidenciales/todos/")
    suspend fun getCandidatosPresidenciales(): List<CandidatoPresidencialDto>

    @GET("api/candidatos/senadores-nacionales/todos/")
    suspend fun getSenadoresNacionales(): List<CandidatoSenadorNacionalDto>

    // Para senadores regionales, usar el endpoint por_circunscripcion
    @GET("api/candidatos/senadores-regionales/por_circunscripcion/")
    suspend fun getSenadoresRegionales(
        @Query("circunscripcion_id") circunscripcionId: Int
    ): List<CandidatoSenadorRegionalDto>

    // Para diputados, usar el endpoint por_circunscripcion
    @GET("api/candidatos/diputados/por_circunscripcion/")
    suspend fun getDiputados(
        @Query("circunscripcion_id") circunscripcionId: Int
    ): List<CandidatoDiputadoDto>

    @GET("api/candidatos/parlamento-andino/todos/")
    suspend fun getCandidatosParlamentoAndino(): List<CandidatoParlamentoAndinoDto>

    @GET("api/partidos/activos/")
    suspend fun getPartidosActivos(): List<PartidoDto>
}