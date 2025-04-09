/*
 * CharacterService.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.network.service

import com.rickandmorty.mobile.BuildConfig.CHARACTER_ENDPOINT
import com.rickandmorty.mobile.data.network.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET(CHARACTER_ENDPOINT)
    suspend fun getCharacters(
        @Query("page") page: Int,
    ): Response<CharactersResponse>
}
