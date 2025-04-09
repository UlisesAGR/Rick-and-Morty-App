/*
 * CharacterNetworkSource.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.network.source

import com.rickandmorty.mobile.data.network.model.CharactersResponse
import com.rickandmorty.mobile.util.network.Resource

interface CharacterNetworkSource {
    suspend fun getCharacters(page: Int): Resource<CharactersResponse>
}
