/*
 * GetCharactersUseCase.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.domain.usecase

import androidx.paging.PagingData
import com.rickandmorty.mobile.data.repository.CharacterRepositoryImpl
import com.rickandmorty.mobile.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepositoryImpl,
) {

    operator fun invoke(): Flow<PagingData<CharacterModel>> =
        characterRepository.getCharacters()
}
