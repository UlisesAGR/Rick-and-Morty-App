/*
 * CharacterDetail.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.domain.model.CharacterModel
import com.rickandmorty.mobile.presentation.ui.components.ButtonCircular
import com.rickandmorty.mobile.presentation.ui.components.EmptyState
import com.rickandmorty.mobile.util.getCharacterStatusColor

@Composable
fun CharacterDetail(
    modifier: Modifier = Modifier,
    character: CharacterModel?,
    navigateToCharacters: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        ButtonCircular(
            image = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
            contentDescription = stringResource(R.string.back),
            onClick = {
                navigateToCharacters()
            },
        )
        Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.space_big)))
        if (character != null) {
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .size(dimensionResource(id = R.dimen.image_description_size))
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                model = character.image,
                contentDescription = character.name,
            )
            Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.space_big)))
            Row {
                Icon(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.icon_size))
                        .align(Alignment.CenterVertically),
                    tint = getCharacterStatusColor(character.status),
                    imageVector = Icons.Filled.Circle,
                    contentDescription = stringResource(R.string.circular_icon),
                )
                Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.space_big)))
                Text(
                    text = stringResource(id = R.string.name_inter, character.name),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.space_big)))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(id = R.string.species_inter, character.species),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            EmptyState(
                modifier = Modifier.fillMaxSize(),
                title = stringResource(R.string.no_character_available_for_show),
            )
        }
    }
}
