/*
 * NavigationWrapper.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rickandmorty.mobile.presentation.ui.view.characters.CharactersScreen
import com.rickandmorty.mobile.presentation.ui.view.detail.CharacterDetailScreen
import com.rickandmorty.mobile.presentation.viewmodel.CharacterViewModel

@Composable
fun NavigationWrapper(viewModel: CharacterViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Characters,
    ) {
        composable<Characters> {
            CharactersScreen(
                viewModel = viewModel,
                navigateToCharacterDetail = { characterId ->
                    navController.navigate(CharacterDetail(characterId))
                },
            )
        }
        composable<CharacterDetail> { backStackEntry ->
            val characterDetail: CharacterDetail = backStackEntry.toRoute()
            CharacterDetailScreen(
                characterId = characterDetail.characterId,
                viewModel = viewModel,
                navigateToCharacters = {
                    navController.popBackStack()
                },
            )
        }
    }
}
