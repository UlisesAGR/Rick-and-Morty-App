/*
 * NavigationWrapper.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rickandmorty.mobile.presentation.ui.view.characters.CharactersScreen
import com.rickandmorty.mobile.presentation.ui.view.detail.CharacterDetailScreen
import com.rickandmorty.mobile.presentation.viewmodel.CharacterViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationWrapper(viewModel: CharacterViewModel) {
    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Characters,
        ) {
            composable<Characters> {
                CharactersScreen(
                    viewModel = viewModel,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    navigateToCharacterDetail = { characterId ->
                        navController.navigate(CharacterDetail(characterId)) {
                            launchSingleTop = true
                        }
                    },
                )
            }
            composable<CharacterDetail> { backStackEntry ->
                val characterDetail: CharacterDetail = backStackEntry.toRoute()
                CharacterDetailScreen(
                    viewModel = viewModel,
                    characterId = characterDetail.characterId,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    navigateToCharacters = {
                        navController.popBackStack()
                    },
                )
            }
        }
    }
}
