/*
 * CharactersScreen.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.characters

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.rickandmorty.mobile.presentation.ui.components.EmptyState
import com.rickandmorty.mobile.presentation.viewmodel.CharacterUiEvent
import com.rickandmorty.mobile.presentation.viewmodel.CharacterViewModel
import com.rickandmorty.mobile.util.handleError
import com.rickandmorty.mobile.util.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel,
    navigateToCharacterDetail: (characterId: Int) -> Unit,
) {
    val context = LocalContext.current

    val characters = viewModel.characters.collectAsLazyPagingItems()

    val characterUiEvent = viewModel.characterUiEvent.collectAsState(CharacterUiEvent.Initial).value

    LaunchedEffect(characterUiEvent) {
        if (characterUiEvent is CharacterUiEvent.Error) {
            context.showToast(context.handleError(characterUiEvent.exception))
        }
    }

    Scaffold(
        modifier = modifier,
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
                ) {
                    when (state) {
                        is LoadState.Loading -> {
                            CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
                        }

                        is LoadState.NotLoading -> {
                            if (characters.itemCount == 0) {
                                EmptyState(
                                    modifier = modifier.align(Alignment.Center),
                                    title = stringResource(R.string.no_characters_available_for_show)
                                )
                            } else {
                                CharactersListScreen(
                                    characters = characters,
                                    navigateToCharacterDetail = navigateToCharacterDetail,
                                )
                            }
                        }

                        is LoadState.Error -> {
                            EmptyState(
                                modifier = modifier.align(Alignment.Center),
                                title = stringResource(R.string.an_error_has_occurred)
                            )
                        }
                    }
                }
            }
        }
    )
}
