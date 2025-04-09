/*
 * CharacterRepositoryImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.repository

import android.net.ConnectivityManager
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.data.local.source.CharacterLocalSource
import com.rickandmorty.mobile.data.network.source.CharacterNetworkSource
import com.rickandmorty.mobile.data.paging.CharacterRemoteMediator
import com.rickandmorty.mobile.domain.repository.CharacterRepository
import com.rickandmorty.mobile.util.Constants.MAX_ITEMS
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterNetworkSource: CharacterNetworkSource,
    private val characterLocalSource: CharacterLocalSource,
    private val connectivityManager: ConnectivityManager,
    private val dispatcher: CoroutineDispatcher,
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(): Flow<PagingData<CharacterEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = MAX_ITEMS,
                initialLoadSize = MAX_ITEMS,
            ),
            remoteMediator = CharacterRemoteMediator(
                characterNetworkSource,
                characterLocalSource,
                connectivityManager,
            ),
            pagingSourceFactory = {
                characterLocalSource.getAllCharacters()
            },
        ).flow.flowOn(dispatcher)

    override suspend fun getCharacterById(
        characterId: Int,
    ): CharacterEntity? = withContext(dispatcher) {
        characterLocalSource.getCharacterById(characterId)
    }
}

