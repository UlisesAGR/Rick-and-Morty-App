/*
 * CharacterRepository.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.domain.repository

import androidx.paging.PagingData
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<PagingData<CharacterEntity>>
    suspend fun getCharacterById(characterId: Int): CharacterEntity?
}
