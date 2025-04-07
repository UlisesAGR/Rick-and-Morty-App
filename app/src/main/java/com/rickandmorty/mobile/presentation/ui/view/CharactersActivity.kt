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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rickandmorty.mobile.presentation.ui.navigation.NavigationWrapper
import com.rickandmorty.mobile.presentation.viewmodel.CharacterViewModel
import com.rickandmorty.mobile.theme.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : ComponentActivity() {

    private val loginViewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        splash.setKeepOnScreenCondition { false }
        enableEdgeToEdge()
        setContent {
            Screen {
                NavigationWrapper(viewModel = loginViewModel)
            }
        }
    }
}