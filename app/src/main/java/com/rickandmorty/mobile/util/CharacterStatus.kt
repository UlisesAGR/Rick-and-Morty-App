/*
 * CharacterStatus.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.util

import androidx.compose.ui.graphics.Color
import com.rickandmorty.mobile.util.Constants.ALIVE
import com.rickandmorty.mobile.util.Constants.DEAD

fun getCharacterStatusColor(status: String?): Color =
    when (status) {
        ALIVE -> Color.Green
        DEAD -> Color.Red
        else -> Color.Gray
    }
