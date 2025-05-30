/*
 * CharacterStatus.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.util.getCharacterStatusColor

@Composable
fun CharacterStatus(
    status: String?,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                shape = CircleShape,
                color = getCharacterStatusColor(status),
            ),
    ) {
        Text(
            text = status ?: stringResource(R.string.unknown),
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_status)),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterStatusPreview() {
    CharacterStatus(status = "Alive")
}
