/*
 * CharacterRepositoryImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.paging

import android.net.ConnectivityManager
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Transaction
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.data.local.model.CharacterRemoteKeys
import com.rickandmorty.mobile.data.local.source.CharacterLocalSource
import com.rickandmorty.mobile.data.network.source.CharacterNetworkSource
import com.rickandmorty.mobile.util.exception.GenericException
import com.rickandmorty.mobile.util.isNetworkAvailable
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val characterNetworkSource: CharacterNetworkSource,
    private val characterLocalSource: CharacterLocalSource,
    private val connectivityManager: ConnectivityManager
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        // Offline
        if (!isNetworkAvailable(connectivityManager)) {
            val localPagingSource = characterLocalSource.getAllCharacters()
            return try {
                val localPage = localPagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = state.config.pageSize,
                        placeholdersEnabled = false,
                    )
                )
                when (localPage) {
                    is PagingSource.LoadResult.Page -> {
                        MediatorResult.Success(endOfPaginationReached = localPage.data.isEmpty())
                    }

                    is PagingSource.LoadResult.Error -> {
                        MediatorResult.Error(localPage.throwable)
                    }

                    else -> {
                        MediatorResult.Error(GenericException.EmptyCharactersException())
                    }
                }
            } catch (e: Exception) {
                MediatorResult.Error(e)
            }
        }
        // Online
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                nextKey
            }
        }
        try {
            val response = characterNetworkSource.getCharacters(page)
            val characters = response.data?.results.orEmpty()
            val endOfPagination = response.data?.info?.next == null
            if (loadType == LoadType.REFRESH) {
                clearAllData()
            }
            val keys = characters.map {
                CharacterRemoteKeys(
                    characterId = it.id,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (endOfPagination) null else page + 1
                )
            }
            characterLocalSource.insertAllKeys(keys)
            characterLocalSource.insertAllCharacters(characters)
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>): CharacterRemoteKeys? =
        state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { character -> characterLocalSource.getRemoteKey(character.id) }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>): CharacterRemoteKeys? =
        state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { character -> characterLocalSource.getRemoteKey(character.id) }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterEntity>): CharacterRemoteKeys? =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { characterId ->
                characterLocalSource.getRemoteKey(characterId)
            }
        }

    @Transaction
    suspend fun clearAllData() {
        characterLocalSource.clearRemoteKeys()
        characterLocalSource.clearAllCharacters()
    }
}
