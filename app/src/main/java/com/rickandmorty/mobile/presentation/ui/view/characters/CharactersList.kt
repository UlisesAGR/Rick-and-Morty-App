/*
 * CharactersList.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.characters

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.domain.model.CharacterModel
import com.rickandmorty.mobile.presentation.ui.components.ButtonPrimary

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharactersList(
    modifier: Modifier = Modifier,
    characters: LazyPagingItems<CharacterModel>,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToCharacterDetail: (characterId: Int) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
    ) {
        items(characters.itemCount) { index ->
            characters[index]?.let { character ->
                CharacterItem(
                    character = character,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    navigateToCharacterDetail = navigateToCharacterDetail,
                )
            }
        }
        item {
            when (characters.loadState.append) {
                is LoadState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                }
                is LoadState.Error -> {
                    ButtonPrimary(
                        text = stringResource(R.string.retry),
                        onClick = {
                            characters.retry()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_small)),
                    )
                }
                is LoadState.NotLoading -> {}
            }
        }
    }
}
