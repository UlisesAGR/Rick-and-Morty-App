/*
 * CharacterRemoteSourceImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.remote.source

import com.rickandmorty.mobile.data.remote.model.CharactersResponse
import com.rickandmorty.mobile.data.remote.service.CharacterService
import com.rickandmorty.mobile.util.network.Resource
import com.rickandmorty.mobile.util.network.toResult
import javax.inject.Inject

class CharacterRemoteSourceImpl @Inject constructor(
    private val characterService: CharacterService,
) : CharacterRemoteSource {

    override suspend fun getCharacters(page: Int): Resource<CharactersResponse> =
        characterService.getCharacters(page).toResult()
}
