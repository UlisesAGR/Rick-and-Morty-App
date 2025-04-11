/*
 * AppDatabase.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rickandmorty.mobile.data.local.database.dao.CharacterDao
import com.rickandmorty.mobile.data.local.database.dao.CharacterRemoteKeysDao
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.data.local.model.CharacterRemoteKeys

@Database(
    entities = [CharacterEntity::class, CharacterRemoteKeys::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterRemoteKeysDao(): CharacterRemoteKeysDao
}
