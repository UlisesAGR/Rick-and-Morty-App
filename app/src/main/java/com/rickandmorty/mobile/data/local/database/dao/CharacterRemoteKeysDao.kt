/*
 * CharacterRemoteKeysDao.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rickandmorty.mobile.data.local.model.CharacterRemoteKeys

@Dao
interface CharacterRemoteKeysDao {

    @Query("SELECT * FROM character_remote_keys_table WHERE characterId = :characterId")
    suspend fun getCharacterRemoteKey(characterId: Int): CharacterRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharactersRemoteKeys(charactersRemoteKeys: List<CharacterRemoteKeys>)

    @Query("DELETE FROM character_remote_keys_table")
    suspend fun clearAllCharactersRemoteKeys()
}
