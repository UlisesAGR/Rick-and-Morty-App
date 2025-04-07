/*
 * GetCharacterStatus.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.util.Constants.ALIVE
import com.rickandmorty.mobile.util.Constants.DEAD

@Composable
fun getCharacterStatusColor(status: String?): Color =
    when (status) {
        ALIVE -> colorResource(R.color.green)
        DEAD -> colorResource(R.color.red)
        else -> colorResource(R.color.gray)
    }
