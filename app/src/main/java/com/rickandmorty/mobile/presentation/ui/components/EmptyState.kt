/*
 * EmptyState.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.rickandmorty.mobile.R

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    title: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .clip(MaterialTheme.shapes.small),
            painter = painterResource(id = R.drawable.il_logo),
            contentDescription = stringResource(R.string.empty_list),
        )
        Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.space_big)))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
fun EmptyStateRetry(
    modifier: Modifier = Modifier,
    textButton: String,
    title: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.padding_big),
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.il_logo),
            contentDescription = stringResource(R.string.empty_list),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .clip(MaterialTheme.shapes.small),
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )
        ButtonPrimary(
            text = textButton,
            onClick = {
                onClick()
            },
        )
    }
}
