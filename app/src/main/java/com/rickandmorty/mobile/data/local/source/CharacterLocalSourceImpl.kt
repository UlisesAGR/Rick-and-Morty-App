/*
 * CharacterLocalDataSourceImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.source

import com.rickandmorty.mobile.data.local.database.CharacterDao
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import javax.inject.Inject

class CharacterLocalSourceImpl @Inject constructor(
    private val characterDao: CharacterDao,
) : CharacterLocalSource {

    override suspend fun insertAllCharacters(characters: List<CharacterEntity>) {
        val existingCharacterId = characters.map { character ->
            character.id
        }
        val existingCharacters = characterDao.getCharactersById(existingCharacterId)

        val newCharacters = characters.filterNot { character ->
            existingCharacters.any { existingCharacter ->
                existingCharacter.id == character.id
            }
        }

        if (newCharacters.isNotEmpty()) {
            characterDao.insertAllCharacters(newCharacters)
        }
    }

    override suspend fun getAllCharacters(): List<CharacterEntity> =
        characterDao.getAllCharacters()

    override suspend fun getCharacterById(characterId: Int): CharacterEntity? =
        characterDao.getCharacterById(characterId)
}
