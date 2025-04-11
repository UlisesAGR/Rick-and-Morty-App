/*
 * CharactersActivity.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.presentation.ui.navigation.NavigationWrapper
import com.rickandmorty.mobile.presentation.viewmodel.CharacterUiEvent
import com.rickandmorty.mobile.presentation.viewmodel.CharacterViewModel
import com.rickandmorty.mobile.theme.Screen
import com.rickandmorty.mobile.util.exception.handleError
import com.rickandmorty.mobile.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : ComponentActivity() {

    private val viewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        splash.setKeepOnScreenCondition { false }
        initView()
    }

    private fun initView() {
        enableEdgeToEdge()
        setContent {
            ContainerScreen()
        }
    }

    @Composable
    private fun ContainerScreen() {
        Screen {
            val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()

            val characterUiEvent =
                viewModel.characterUiEvent.collectAsState(CharacterUiEvent.Initial).value

            LaunchedEffect(isConnected) {
                validatingConnection(isConnected)
            }

            LaunchedEffect(characterUiEvent) {
                if (characterUiEvent is CharacterUiEvent.Error) {
                    showToast(handleError(characterUiEvent.exception))
                }
            }

            NavigationWrapper(viewModel)
        }
    }

    private fun validatingConnection(isConnected: Boolean?) {
        isConnected?.let {
            if (isConnected) {
                showToast(getString(R.string.you_are_currently_online))
            } else {
                showToast(getString(R.string.you_are_currently_offline))
            }
        }
    }
}
