/*
 * Loader.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.rickandmorty.mobile.R

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(dimensionResource(id = R.dimen.padding)),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoaderPreview() {
    ProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .wrapContentSize(Alignment.Center),
    )
}