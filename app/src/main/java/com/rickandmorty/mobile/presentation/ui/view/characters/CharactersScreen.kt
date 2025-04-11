/*
 * CharactersScreen.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.characters

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.presentation.ui.components.EmptyStateRetry
import com.rickandmorty.mobile.presentation.viewmodel.CharacterUiEvent
import com.rickandmorty.mobile.presentation.viewmodel.CharacterViewModel
import com.rickandmorty.mobile.util.exception.handleError
import com.rickandmorty.mobile.util.showToast

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
    val context = LocalContext.current

    val characters = viewModel.characters.collectAsLazyPagingItems()

    val characterUiEvent = viewModel.characterUiEvent.collectAsState(CharacterUiEvent.Initial).value

    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            characters.refresh()
        }
    )

    LaunchedEffect(characterUiEvent) {
        if (characterUiEvent is CharacterUiEvent.Error) {
            context.showToast(context.handleError(characterUiEvent.exception))
        }
    }

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
                            CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
                        }
                        is LoadState.Error -> {
                            EmptyStateRetry(
                                modifier = modifier.align(Alignment.Center),
                                title = context.handleError(state.error),
                                textButton = stringResource(R.string.retry),
                                onClick = {
                                    characters.refresh()
                                },
                            )
                        }
                        is LoadState.NotLoading -> {
                            CharactersList(
                                characters = characters,
                                sharedTransitionScope = sharedTransitionScope,
                                animatedVisibilityScope = animatedVisibilityScope,
                                navigateToCharacterDetail = navigateToCharacterDetail,
                            )
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
