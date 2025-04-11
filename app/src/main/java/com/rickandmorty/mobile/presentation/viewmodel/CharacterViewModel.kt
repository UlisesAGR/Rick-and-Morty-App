/*
 * CharacterViewModel.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rickandmorty.mobile.domain.model.CharacterModel
import com.rickandmorty.mobile.domain.usecase.GetCharacterByIdUseCase
import com.rickandmorty.mobile.domain.usecase.GetCharactersUseCase
import com.rickandmorty.mobile.util.connection.ConnectivityObserverManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    getCharactersUseCase: GetCharactersUseCase,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    connectivityObserver: ConnectivityObserverManager
) : ViewModel() {

    private var _characterUiEvent = MutableSharedFlow<CharacterUiEvent>()
    val characterUiEvent: SharedFlow<CharacterUiEvent> = _characterUiEvent

    private var _characterUiState = MutableSharedFlow<CharacterUiState>()
    val characterUiState: SharedFlow<CharacterUiState> = _characterUiState

    val characters: Flow<PagingData<CharacterModel>> = getCharactersUseCase()
        .cachedIn(viewModelScope)

    val isConnected = connectivityObserver.isConnected
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = null,
        )

    fun getCharacterById(characterId: Int) = viewModelScope.launch {
        _characterUiState.emit(CharacterUiState.Loading(isLoading = true))
        getCharacterByIdUseCase(characterId)
            .onSuccess { character ->
                _characterUiState.apply {
                    emit(CharacterUiState.Loading(isLoading = false))
                    emit(CharacterUiState.ShowCharacter(character))
                }
            }
            .onFailure { exception ->
                _characterUiState.emit(CharacterUiState.Loading(isLoading = false))
                _characterUiEvent.emit(CharacterUiEvent.Error(exception))
            }
    }
}
