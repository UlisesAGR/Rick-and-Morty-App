/*
 * CharacterLocalDataSourceImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.source

import androidx.paging.PagingSource
import com.rickandmorty.mobile.data.local.database.CharacterDao
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.data.network.model.CharacterResponse
import javax.inject.Inject

class CharacterLocalSourceImpl @Inject constructor(
    private val characterDao: CharacterDao,
) : CharacterLocalSource {

    override suspend fun insertNewCharacters(characters: List<CharacterResponse>) {
        runCatching {
            characterDao.insertAllCharacters(
                characters = characters.map { character ->
                    character.toEntity()
                }
            )
        }
    }

    override suspend fun deleteAllCharacters() {
        runCatching {
            characterDao.deleteAllCharacters()
        }
    }

    override fun getAllCharacters(): PagingSource<Int, CharacterEntity> =
        characterDao.getAllCharacters()

    override suspend fun getCharacterById(characterId: Int): CharacterEntity? =
        runCatching {
            characterDao.getCharacterById(characterId)
        }.getOrNull()
}
