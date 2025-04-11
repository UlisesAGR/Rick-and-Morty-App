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

    @Query("SELECT * FROM character_remote_keys WHERE characterId = :characterId")
    suspend fun getRemoteKey(characterId: Int): CharacterRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(keys: List<CharacterRemoteKeys>)

    @Query("DELETE FROM character_remote_keys")
    suspend fun clearRemoteKeys()
}
