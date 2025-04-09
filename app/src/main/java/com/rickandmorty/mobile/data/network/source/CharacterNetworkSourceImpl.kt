/*
 * CharacterLocalDataSourceImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.network.source

import com.rickandmorty.mobile.data.network.model.CharactersResponse
import com.rickandmorty.mobile.data.network.service.CharacterService
import com.rickandmorty.mobile.util.network.Resource
import com.rickandmorty.mobile.util.network.toResult
import javax.inject.Inject

class CharacterNetworkSourceImpl @Inject constructor(
    private val characterService: CharacterService,
) : CharacterNetworkSource {

    override suspend fun getCharacters(page: Int): Resource<CharactersResponse> =
        characterService.getCharacters(page).toResult()
}
