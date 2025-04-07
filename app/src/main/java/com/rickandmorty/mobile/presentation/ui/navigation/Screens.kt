/*
 * Screens.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Characters

@Serializable
data class CharacterDetail(val characterId: Int)
