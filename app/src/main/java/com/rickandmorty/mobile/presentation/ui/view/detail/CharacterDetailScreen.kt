/*
 * CharacterDetailScreen.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.presentation.viewmodel.CharacterUiState
import com.rickandmorty.mobile.presentation.viewmodel.CharacterViewModel

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel,
    characterId: Int,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToCharacters: () -> Unit,
) = with(sharedTransitionScope) {
    val characterUiState =
        viewModel.characterUiState.collectAsState(CharacterUiState.Loading(isLoading = true)).value

    LaunchedEffect(characterId) {
        viewModel.getCharacterById(characterId)
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .sharedBounds(
                rememberSharedContentState(key = "image_$characterId"),
                animatedVisibilityScope,
            ),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.character)) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateToCharacters()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                Crossfade(targetState = characterUiState) { state ->
                    when (state) {
                        is CharacterUiState.Loading -> {}
                        is CharacterUiState.ShowCharacter -> {
                            CharacterDetail(
                                character = state.character,
                                navigateToCharacters = navigateToCharacters,
                            )
                        }
                    }
                }
            }
        }
    )
}
