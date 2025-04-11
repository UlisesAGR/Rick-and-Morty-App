/*
 * CharactersResponse.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.remote.model

data class CharactersResponse(
    val info: CharactersInfoResponse?,
    val results: List<CharacterResponse>?,
)
