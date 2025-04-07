/*
 * CharacterUiState.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.viewmodel

sealed class CharacterUiEvent {
    internal data object Initial : CharacterUiEvent()
    internal data class Error(val exception: Throwable?) : CharacterUiEvent()
}
