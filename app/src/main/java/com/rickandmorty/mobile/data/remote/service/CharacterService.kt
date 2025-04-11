/*
 * CharacterService.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.remote.service

import com.rickandmorty.mobile.BuildConfig.ENDPOINT_CHARACTER
import com.rickandmorty.mobile.data.remote.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET(ENDPOINT_CHARACTER)
    suspend fun getCharacters(
        @Query("page") page: Int,
    ): Response<CharactersResponse>
}
