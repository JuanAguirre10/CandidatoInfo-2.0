package com.tecsup.candidatoinfo.data.remote.api

import com.tecsup.candidatoinfo.data.remote.dto.CircunscripcionDto
import com.tecsup.candidatoinfo.data.remote.dto.CircunscripcionesResponse
import retrofit2.http.GET

interface ApiService {

    @GET("api/circunscripciones/todas/")
    suspend fun getCircunscripciones(): CircunscripcionesResponse

    @GET("api/circunscripciones/select_list/")
    suspend fun getCircunscripcionesSelectList(): List<CircunscripcionDto>
}