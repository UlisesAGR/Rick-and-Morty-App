/*
 * CharacterLocalDataSourceImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.network.source

import com.rickandmorty.mobile.data.network.service.CharacterService
import com.rickandmorty.mobile.data.network.model.CharactersResponse
import javax.inject.Inject

class CharacterNetworkSourceImpl @Inject constructor(
    private val characterService: CharacterService,
) : CharacterNetworkSource {

    override suspend fun getCharacters(page: Int): CharactersResponse =
        characterService.getCharacters(page)
}
