/*
 * CharacterLocalSource.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.source

import androidx.paging.PagingSource
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.data.network.model.CharacterResponse

interface CharacterLocalSource {
    suspend fun insertNewCharacters(characters: List<CharacterResponse>)
    suspend fun deleteAllCharacters()
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>
    suspend fun getCharacterById(characterId: Int): CharacterEntity?
}
