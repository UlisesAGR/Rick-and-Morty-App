/*
 * CharacterMapper.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.domain.mapper

import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.domain.model.CharacterModel

fun CharacterEntity.toDomain(): CharacterModel =
    CharacterModel(id, name, species, status, gender, image)
