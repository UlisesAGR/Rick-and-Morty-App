/*
 * Button.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rickandmorty.mobile.R

@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        onClick = { onClick() },
    ) {
        Text(
            style = MaterialTheme.typography.titleSmall,
            text = text,
        )
    }
}

@Composable
fun ButtonSecondary(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        onClick = { onClick() },
    ) {
        Text(
            style = MaterialTheme.typography.titleSmall,
            text = text,
        )
    }
}

@Composable
fun ButtonPrimaryEnable(
    modifier: Modifier = Modifier,
    text: String,
    enable: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        onClick = { onClick() },
        enabled = enable,
    ) {
        Text(
            style = MaterialTheme.typography.titleSmall,
            text = text,
        )
    }
}

@Composable
fun ButtonCircular(
    modifier: Modifier = Modifier,
    image: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier
            .size(60.dp)
            .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = image,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun ButtonText(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier.height(60.dp),
        onClick = { onClick() },
    ) {
        Text(
            style = MaterialTheme.typography.titleSmall,
            text = text,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonsPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_big)),
        verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.padding_big)),
    ) {
        ButtonPrimary(modifier = Modifier, text = stringResource(R.string.example), onClick = {})
        ButtonPrimaryEnable(
            modifier = Modifier,
            text = stringResource(R.string.example),
            enable = false,
            onClick = {},
        )
        ButtonSecondary(modifier = Modifier, text = stringResource(R.string.example), onClick = {})
        ButtonCircular(
            modifier = Modifier,
            image = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
            contentDescription = stringResource(R.string.example),
            onClick = {},
        )
        ButtonText(modifier = Modifier, text = stringResource(R.string.example), onClick = {})
    }
}
