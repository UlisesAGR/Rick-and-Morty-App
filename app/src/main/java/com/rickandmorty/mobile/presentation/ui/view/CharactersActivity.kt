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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.presentation.ui.navigation.NavigationWrapper
import com.rickandmorty.mobile.presentation.viewmodel.CharacterViewModel
import com.rickandmorty.mobile.theme.Screen
import com.rickandmorty.mobile.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : ComponentActivity() {

    private val loginViewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        splash.setKeepOnScreenCondition { false }
        initView()
    }

    private fun initView() {
        enableEdgeToEdge()
        setContent {
            Screen {
                val isConnected by loginViewModel.isConnected.collectAsStateWithLifecycle()
                LaunchedEffect(isConnected) {
                    validatingConnection(isConnected)
                }
                NavigationWrapper(viewModel = loginViewModel)
            }
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