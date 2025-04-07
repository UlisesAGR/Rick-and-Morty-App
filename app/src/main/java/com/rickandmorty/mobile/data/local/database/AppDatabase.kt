/*
 * AppDatabase.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rickandmorty.mobile.data.local.model.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
