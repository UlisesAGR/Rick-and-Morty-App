/*
 * CharacterRepositoryImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Transaction
import com.rickandmorty.mobile.data.local.model.CharacterEntity
import com.rickandmorty.mobile.data.local.model.CharacterRemoteKeys
import com.rickandmorty.mobile.data.local.source.CharacterLocalSource
import com.rickandmorty.mobile.data.remote.source.CharacterRemoteSource
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val characterRemoteSource: CharacterRemoteSource,
    private val characterLocalSource: CharacterLocalSource,
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null,
                    )
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null,
                    )
                    nextKey
                }
            }
            val response = characterRemoteSource.getCharacters(page = currentPage)
            val characters = response.data?.results.orEmpty()

            if (loadType == LoadType.REFRESH) {
                clearAllData()
            }

            val endOfPaginationReached = response.data?.info?.next == null
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextKey = if (endOfPaginationReached) null else currentPage + 1

            val charactersRemoteKeys = characters.map { character ->
                CharacterRemoteKeys(
                    characterId = character.id,
                    prevKey = prevPage,
                    nextKey = nextKey,
                )
            }

            characterLocalSource.insertAllCharactersRemoteKeys(charactersRemoteKeys)
            characterLocalSource.insertAllCharacters(characters)
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return if (characterLocalSource.getCharactersCount() > 0) {
                MediatorResult.Success(endOfPaginationReached = true)
            } else {
                MediatorResult.Error(exception)
            }
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterEntity>): CharacterRemoteKeys? =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                characterLocalSource.getCharacterRemoteKey(characterId = id)
            }
        }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>): CharacterRemoteKeys? =
        state.pages.firstOrNull { page ->
            page.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { character ->
            characterLocalSource.getCharacterRemoteKey(characterId = character.id)
        }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>): CharacterRemoteKeys? =
        state.pages.lastOrNull { page ->
            page.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { character ->
            characterLocalSource.getCharacterRemoteKey(characterId = character.id)
        }

    @Transaction
    private suspend fun clearAllData() {
        characterLocalSource.clearAllCharactersRemoteKeys()
        characterLocalSource.clearAllCharacters()
    }
}
