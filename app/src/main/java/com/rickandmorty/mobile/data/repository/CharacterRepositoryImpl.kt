/*
 * CharacterRepositoryImpl.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data.repository

import android.net.ConnectivityManager
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickandmorty.mobile.data.local.source.CharacterLocalSource
import com.rickandmorty.mobile.data.network.source.CharacterNetworkSource
import com.rickandmorty.mobile.domain.model.CharacterModel
import com.rickandmorty.mobile.domain.repository.CharacterRepository
import com.rickandmorty.mobile.util.Constants.MAX_ITEMS
import com.rickandmorty.mobile.util.Constants.PREFETCH_ITEMS
import com.rickandmorty.mobile.util.isNetworkAvailable
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
) : CharacterRepository, PagingSource<Int, CharacterModel>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
        val page = params.key ?: 1
        return try {
            if (isNetworkAvailable(connectivityManager)) {
                val response = characterNetworkSource.getCharacters(page)
                val characters = response.results

                characterLocalSource.insertAllCharacters(
                    characters.map { it.toEntity() }
                )

                val prevKey = if (page > 0) page - 1 else null
                val nextKey = if (response.info.next != null) page + 1 else null

                LoadResult.Page(
                    data = characters.map { it.toDomain() },
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                val localCharacters = characterLocalSource.getAllCharacters()
                LoadResult.Page(
                    data = localCharacters.map { it.toDomain() },
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getCharacters(): Flow<PagingData<CharacterModel>> {
        return Pager(
            config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = { this },
        ).flow.flowOn(dispatcher)
    }

    override suspend fun getCharacterById(characterId: Int): CharacterModel? = withContext(dispatcher) {
        characterLocalSource.getCharacterById(characterId)?.toDomain()
    }
}
