/*
 * CharacterUiState.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.viewmodel

import com.rickandmorty.mobile.domain.model.CharacterModel

sealed class CharacterUiState {
    internal data class Loading(val isLoading: Boolean) : CharacterUiState()
    internal data class ShowCharacter(val character: CharacterModel?) : CharacterUiState()
}
