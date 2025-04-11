/*
 * CharacterRemoteSource.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.remote.source

import com.rickandmorty.mobile.data.remote.model.CharactersResponse
import com.rickandmorty.mobile.util.network.Resource

interface CharacterRemoteSource {
    suspend fun getCharacters(page: Int): Resource<CharactersResponse>
}
