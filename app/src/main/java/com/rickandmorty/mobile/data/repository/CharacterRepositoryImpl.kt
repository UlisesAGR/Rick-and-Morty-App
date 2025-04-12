/*
 * CharacterRepositoryImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.data.local.source.CharacterLocalSource
import com.rickandmorty.mobile.data.paging.CharacterRemoteMediator
import com.rickandmorty.mobile.data.remote.source.CharacterRemoteSource
import com.rickandmorty.mobile.domain.repository.CharacterRepository
import com.rickandmorty.mobile.util.Constants.PAGE_SIZE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteSource: CharacterRemoteSource,
    private val characterLocalSource: CharacterLocalSource,
    private val dispatcher: CoroutineDispatcher,
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(): Flow<PagingData<CharacterEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
            ),
            remoteMediator = CharacterRemoteMediator(
                characterRemoteSource,
                characterLocalSource,
            ),
            pagingSourceFactory = characterLocalSource::getAllCharacters,
        ).flow.flowOn(dispatcher)

    override suspend fun getCharacterById(
        characterId: Int,
    ): CharacterEntity? = withContext(dispatcher) {
        characterLocalSource.getCharacterById(characterId)
    }
}
