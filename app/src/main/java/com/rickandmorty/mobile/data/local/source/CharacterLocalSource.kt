/*
 * CharacterLocalSource.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.source

import com.rickandmorty.mobile.data.local.model.CharacterEntity

interface CharacterLocalSource {
    suspend fun insertAllCharacters(characters: List<CharacterEntity>)
    suspend fun getAllCharacters(): List<CharacterEntity>
    suspend fun getCharacterById(characterId: Int): CharacterEntity?
}
