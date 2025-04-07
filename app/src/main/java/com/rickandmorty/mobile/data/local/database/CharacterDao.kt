/*
 * CharacterDao.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rickandmorty.mobile.data.local.model.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id IN (:characterId)")
    suspend fun getCharactersById(characterId: List<Int>): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :characterId LIMIT 1")
    suspend fun getCharacterById(characterId: Int): CharacterEntity?
}
