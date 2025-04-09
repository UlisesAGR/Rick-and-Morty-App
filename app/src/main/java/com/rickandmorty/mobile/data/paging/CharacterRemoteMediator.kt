/*
 * CharacterRepositoryImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.paging

import android.net.ConnectivityManager
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.data.local.source.CharacterLocalSource
import com.rickandmorty.mobile.data.network.source.CharacterNetworkSource
import com.rickandmorty.mobile.util.isNetworkAvailable
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRemoteMediator @Inject constructor(
    private val characterNetworkSource: CharacterNetworkSource,
    private val characterLocalSource: CharacterLocalSource,
    private val connectivityManager: ConnectivityManager,
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    // First page
                    1
                }
                LoadType.PREPEND -> {
                    // Previous page if we are loading up
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    // Next page if we are loading down
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }
            if (isNetworkAvailable(connectivityManager)) {
                val response = characterNetworkSource.getCharacters(page)
                if (response.isSuccessful()) {
                    val characters = response.data?.results ?: emptyList()
                    if (loadType == LoadType.REFRESH) {
                        characterLocalSource.deleteAllCharacters()
                    }
                    characterLocalSource.insertNewCharacters(characters)
                    return MediatorResult.Success(endOfPaginationReached = response.data?.results?.isEmpty() ?: true)
                } else {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            } else {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}
