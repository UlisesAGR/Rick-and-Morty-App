/*
 * CharacterEntity.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_table")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String?,
    val species: String?,
    val status: String?,
    val gender: String?,
    val image: String?,
)
