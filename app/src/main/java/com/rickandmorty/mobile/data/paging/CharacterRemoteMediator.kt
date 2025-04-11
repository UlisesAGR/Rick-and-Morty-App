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
import com.rickandmorty.mobile.data.remote.source.CharacterRemoteSource
import com.rickandmorty.mobile.util.exception.GenericException
import com.rickandmorty.mobile.util.isNetworkAvailable

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val characterRemoteSource: CharacterRemoteSource,
    private val characterLocalSource: CharacterLocalSource,
    private val connectivityManager: ConnectivityManager,
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        return if (isNetworkAvailable(connectivityManager)) {
            getCharactersFromRemote(loadType, state)
        } else {
            getCharactersFromLocal(state)
        }
    }

    private suspend fun getCharactersFromRemote(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        try {
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
            val response = characterRemoteSource.getCharacters(page)
            val characters = response.data?.results
            if (!characters.isNullOrEmpty()) {
                val endOfPagination = response.data.info?.next == null
                if (loadType == LoadType.REFRESH) {
                    clearAllData()
                }
                val keys = characters.map {
                    CharacterRemoteKeys(
                        characterId = it.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (endOfPagination) null else page + 1,
                    )
                }
                characterLocalSource.insertAllKeys(keys)
                characterLocalSource.insertAllCharacters(characters)
                return MediatorResult.Success(endOfPaginationReached = endOfPagination)
            } else {
                return MediatorResult.Error(GenericException.EmptyCharactersException())
            }
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>): CharacterRemoteKeys? =
        state.pages.lastOrNull { page ->
            page.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { character ->
            characterLocalSource.getRemoteKey(character.id)
        }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>): CharacterRemoteKeys? =
        state.pages.firstOrNull { page ->
            page.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { character ->
            characterLocalSource.getRemoteKey(character.id)
        }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterEntity>): CharacterRemoteKeys? =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { characterId ->
                characterLocalSource.getRemoteKey(characterId)
            }
        }

    @Transaction
    private suspend fun clearAllData() {
        characterLocalSource.clearRemoteKeys()
        characterLocalSource.clearAllCharacters()
    }

    private suspend fun getCharactersFromLocal(
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        return try {
            if (characterLocalSource.getCharactersCount() > 0) {
                val response = characterLocalSource.getAllCharacters()
                val localPage = response.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = state.config.pageSize,
                        placeholdersEnabled = false,
                    )
                )
                when (localPage) {
                    is PagingSource.LoadResult.Error -> {
                        MediatorResult.Error(localPage.throwable)
                    }
                    is PagingSource.LoadResult.Invalid -> {
                        MediatorResult.Error(GenericException.EmptyCharactersException())
                    }
                    is PagingSource.LoadResult.Page -> {
                        MediatorResult.Success(endOfPaginationReached = localPage.data.isEmpty())
                    }
                }
            } else {
                return MediatorResult.Error(GenericException.EmptyCharactersException())
            }
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }
}
