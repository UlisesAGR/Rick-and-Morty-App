/*
 * GetCharactersUseCase.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.rickandmorty.mobile.data.repository.CharacterRepositoryImpl
import com.rickandmorty.mobile.domain.mapper.toDomain
import com.rickandmorty.mobile.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepositoryImpl,
) {

    operator fun invoke(): Flow<PagingData<CharacterModel>> =
        characterRepository.getCharacters().map { value ->
            value.map { character ->
                character.toDomain()
            }
        }
}
