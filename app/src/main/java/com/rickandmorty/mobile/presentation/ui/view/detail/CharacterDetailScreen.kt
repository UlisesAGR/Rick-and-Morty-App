/*
 * CharacterDetailScreen.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.presentation.viewmodel.CharacterUiState
import com.rickandmorty.mobile.presentation.viewmodel.CharacterViewModel
import com.rickandmorty.mobile.util.handleError
import com.rickandmorty.mobile.util.showToast

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    characterId: Int,
    viewModel: CharacterViewModel,
    navigateToCharacters: () -> Unit,
) {
    val context = LocalContext.current

    val characterUiState = viewModel.characterUiState.collectAsState(CharacterUiState.Empty).value

    LaunchedEffect(characterId) {
        viewModel.getCharacterById(characterId)
    }

    LaunchedEffect(characterUiState) {
        if (characterUiState is CharacterUiState.Error) {
            context.showToast(context.handleError(characterUiState.exception))
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_big)),
    ) {
        when (characterUiState) {
            is CharacterUiState.Loading -> {
                CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
            }

            is CharacterUiState.ShowCharacter -> {
                CharacterDetail(
                    character = characterUiState.character,
                    navigateToCharacters = navigateToCharacters,
                )
            }

            else -> {}
        }
    }
}
