/*
 * CharacterMock.kt - local
 * Created by Ulises Gonzalez on 27/02/25
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.util

import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.data.remote.model.CharacterResponse
import com.rickandmorty.mobile.data.remote.model.CharactersInfoResponse
import com.rickandmorty.mobile.data.remote.model.CharactersResponse
import com.rickandmorty.mobile.domain.model.CharacterModel

object CharacterMock {

    val characterResponse: CharacterResponse =
        CharacterResponse(
            id = 1,
            name = "character",
            species = "human",
            status = "alive",
            gender = "male",
            image = "image",
        )

    val characterEntity: CharacterEntity =
        CharacterEntity(
            id = 1,
            name = "character",
            species = "human",
            status = "alive",
            gender = "male",
            image = "image",
        )

    val characterModel: CharacterModel =
        CharacterModel(
            id = 1,
            name = "character",
            species = "human",
            status = "alive",
            gender = "male",
            image = "image",
        )

    private val charactersEntity: List<CharacterResponse> =
        listOf(
            CharacterResponse(
                id = 1,
                name = "character",
                species = "human",
                status = "alive",
                gender = "male",
                image = "image",
            ),
            CharacterResponse(
                id = 2,
                name = "character",
                species = "human",
                status = "alive",
                gender = "male",
                image = "image",
            ),
        )

    val charactersResponse: CharactersResponse =
        CharactersResponse(
            info = CharactersInfoResponse(
                pages = 1,
                next = "next",
            ),
            results = charactersEntity,
        )
}
