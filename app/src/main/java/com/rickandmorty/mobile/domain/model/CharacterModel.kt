/*
 * CharacterModel.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.domain.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val species: String,
    val status: String,
    val image: String,
) {
    fun toDomain(): CharacterModel = CharacterModel(
        id = id,
        name = name,
        species = species,
        status = status,
        image = image,
    )
}
