/*
 * CharacterLocalDataSourceImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.source

import androidx.paging.PagingSource
import com.rickandmorty.mobile.data.local.database.dao.CharacterDao
import com.rickandmorty.mobile.data.local.database.dao.CharacterRemoteKeysDao
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.data.local.model.CharacterRemoteKeys
import com.rickandmorty.mobile.data.remote.model.CharacterResponse
import javax.inject.Inject

class CharacterLocalSourceImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterRemoteKeysDao: CharacterRemoteKeysDao,
) : CharacterLocalSource {

    override fun getAllCharacters(): PagingSource<Int, CharacterEntity> =
        characterDao.getAllCharacters()

    override suspend fun insertAllCharacters(characters: List<CharacterResponse>) {
        runCatching {
            characterDao.insertAllCharacters(
                characters = characters.map { character ->
                    character.toEntity()
                }
            )
        }
    }

    override suspend fun clearAllCharacters() {
        runCatching {
            characterDao.deleteAllCharacters()
        }
    }

    override suspend fun getCharactersCount(): Int =
        runCatching {
            characterDao.getCharactersCount()
        }.getOrDefault(0)

    override suspend fun getCharacterRemoteKey(characterId: Int): CharacterRemoteKeys? =
        runCatching {
            characterRemoteKeysDao.getCharacterRemoteKey(characterId)
        }.getOrNull()

    override suspend fun insertAllCharactersRemoteKeys(charactersRemoteKeys: List<CharacterRemoteKeys>) {
        runCatching {
            characterRemoteKeysDao.insertAllCharactersRemoteKeys(charactersRemoteKeys)
        }
    }

    override suspend fun clearAllCharactersRemoteKeys() {
        runCatching {
            characterRemoteKeysDao.clearAllCharactersRemoteKeys()
        }
    }

    override suspend fun getCharacterById(characterId: Int): CharacterEntity? =
        runCatching {
            characterDao.getCharacterById(characterId)
        }.getOrNull()
}
