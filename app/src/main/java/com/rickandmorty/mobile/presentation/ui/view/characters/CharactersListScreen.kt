/*
 * CharactersListScreen.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.characters

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.rickandmorty.mobile.domain.model.CharacterModel

@Composable
fun CharactersListScreen(
    modifier: Modifier = Modifier,
    characters: LazyPagingItems<CharacterModel>,
    navigateToCharacterDetail: (characterId: Int) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = lazyGridState,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
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
