/*
 * CharacterResponse.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.network.model

import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.domain.model.CharacterModel

data class CharacterResponse(
    val id: Int,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: OriginResponse,
    val location: LocationResponse,
    val image: String?,
    val episode: List<String>?,
    val url: String?,
    val created: String?,
) {
    fun toDomain(): CharacterModel = CharacterModel(
        id = id,
        name = name,
        species = species,
        status = status,
        gender = gender,
        image = image,
    )

    fun toEntity(): CharacterEntity = CharacterEntity(
        id = id,
        name = name,
        species = species,
        status = status,
        gender = gender,
        image = image,
    )
}
