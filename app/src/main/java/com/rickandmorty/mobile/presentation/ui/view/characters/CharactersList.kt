/*
 * CharactersScreen.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.characters

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.rickandmorty.mobile.domain.model.CharacterModel

@Composable
fun CharactersList(
    modifier: Modifier = Modifier,
    gridState: LazyGridState,
    characters: LazyPagingItems<CharacterModel>,
    navigateToCharacterDetail: (characterId: Int) -> Unit,
) {
    LazyVerticalGrid(
        state = gridState,
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
    ) {
        items(characters.itemCount) { index ->
            characters[index]?.let { character ->
                CharacterItem(
                    character = character,
                    navigateToCharacterDetail = navigateToCharacterDetail,
                )
            }
        }
    }
}
