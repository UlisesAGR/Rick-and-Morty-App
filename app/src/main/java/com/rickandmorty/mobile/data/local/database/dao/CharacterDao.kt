/*
 * CharacterDao.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rickandmorty.mobile.data.local.model.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters_table")
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CharacterEntity>)

    @Query("DELETE FROM characters_table")
    suspend fun deleteAllCharacters()

    @Query("SELECT COUNT(*) FROM characters_table")
    suspend fun getCharactersCount(): Int

    @Query("SELECT * FROM characters_table WHERE id = :characterId LIMIT 1")
    suspend fun getCharacterById(characterId: Int): CharacterEntity?
}
