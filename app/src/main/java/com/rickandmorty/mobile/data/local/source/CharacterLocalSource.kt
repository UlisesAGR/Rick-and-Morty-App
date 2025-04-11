/*
 * CharacterLocalSource.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.source

import androidx.paging.PagingSource
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.data.local.model.CharacterRemoteKeys
import com.rickandmorty.mobile.data.remote.model.CharacterResponse

interface CharacterLocalSource {
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>
    suspend fun insertAllCharacters(characters: List<CharacterResponse>)
    suspend fun clearAllCharacters()
    suspend fun getCharactersCount(): Int
    suspend fun getRemoteKey(characterId: Int): CharacterRemoteKeys?
    suspend fun insertAllKeys(keys: List<CharacterRemoteKeys>)
    suspend fun clearRemoteKeys()
    suspend fun getCharacterById(characterId: Int): CharacterEntity?
}
