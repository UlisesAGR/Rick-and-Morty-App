/*
 * CharactersScreen.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.characters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.presentation.ui.components.EmptyStateRetry
import com.rickandmorty.mobile.presentation.ui.components.ProgressIndicator
import com.rickandmorty.mobile.presentation.viewmodel.CharacterViewModel
import com.rickandmorty.mobile.util.Constants.ANIMATION_DURATION

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToCharacterDetail: (characterId: Int) -> Unit,
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            characters.refresh()
        }
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.characters)) })
        },
        content = { innerPadding ->
            Crossfade(targetState = characters.loadState.refresh) { state ->
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                        .pullRefresh(pullRefreshState),

                    ) {
                    when (state) {
                        is LoadState.Loading -> {
                            ProgressIndicator(
                                modifier = modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background)
                                    .wrapContentSize(Alignment.Center),
                            )
                        }
                        is LoadState.Error -> {
                            EmptyStateRetry(
                                title = stringResource(R.string.error_loading_characters),
                                textButton = stringResource(R.string.retry),
                                modifier = modifier.align(Alignment.Center),
                                onClick = {
                                    characters.refresh()
                                },
                            )
                        }
                        is LoadState.NotLoading -> {
                            if (characters.itemCount > 0) {
                                CharactersList(
                                    characters = characters,
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    navigateToCharacterDetail = navigateToCharacterDetail,
                                )
                            } else {
                                AnimatedVisibility(
                                    visible = characters.itemCount == 0,
                                    enter = fadeIn(tween(durationMillis = ANIMATION_DURATION)),
                                ) {
                                    EmptyStateRetry(
                                        title = stringResource(R.string.no_characters_found),
                                        textButton = stringResource(R.string.retry),
                                        modifier = modifier.align(Alignment.Center),
                                        onClick = {
                                            characters.refresh()
                                        },
                                    )
                                }
                            }
                        }
                    }
                    PullRefreshIndicator(
                        refreshing = characters.loadState.refresh is LoadState.Loading && characters.itemCount > 0,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
                }
            }
        }
    )
}
