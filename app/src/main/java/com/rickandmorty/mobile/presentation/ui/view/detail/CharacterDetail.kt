/*
 * CharacterDetail.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.domain.model.CharacterModel
import com.rickandmorty.mobile.presentation.ui.components.ButtonPrimary
import com.rickandmorty.mobile.presentation.ui.components.EmptyState
import com.rickandmorty.mobile.util.getCharacterStatusColor

@Composable
fun CharacterDetail(
    modifier: Modifier = Modifier,
    character: CharacterModel?,
    navigateToCharacters: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        if (character != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(500)
                    .placeholder(R.drawable.il_logo)
                    .error(R.drawable.il_logo)
                    .build(),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium),
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_big)),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding)),
            ) {
                CharacterDetailForm(
                    modifier = modifier,
                    character = character,
                )
                ButtonPrimary(
                    text = stringResource(R.string.back),
                    onClick = {
                        navigateToCharacters()
                    },
                )
            }
        } else {
            EmptyState(
                title = stringResource(R.string.no_character_available_for_show),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun CharacterDetailForm(
    modifier: Modifier = Modifier,
    character: CharacterModel,
) {
    Card {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_big)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_big)),
        ) {
            Row(modifier = modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Filled.Circle,
                    contentDescription = stringResource(R.string.circular_icon),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.icon_size))
                        .align(Alignment.CenterVertically),
                    tint = getCharacterStatusColor(character.status),
                )
                Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.space_big)))
                Text(
                    text = character.name ?: stringResource(R.string.empty),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Text(
                text = stringResource(
                    id = R.string.species_inter,
                    character.species ?: stringResource(R.string.empty)
                ),
                modifier = modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = stringResource(
                    id = R.string.gender_inter,
                    character.gender ?: stringResource(R.string.empty)
                ),
                modifier = modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
