/*
 * GetCharacterByIdUseCase.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.domain.usecase

import com.rickandmorty.mobile.data.repository.CharacterRepositoryImpl
import com.rickandmorty.mobile.domain.mapper.toDomain
import com.rickandmorty.mobile.util.exception.GenericException
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepositoryImpl,
) {

    suspend operator fun invoke(characterId: Int): Result<Unit> =
        runCatching {
            characterRepository.getCharacterById(characterId)?.toDomain()
                ?: throw GenericException.CharacterNotFoundException()
        }
}
