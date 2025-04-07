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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxSize()) {
        if (character != null) {
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .size(dimensionResource(id = R.dimen.image_description_size)),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(context)
                    .data(character.image)
                    .crossfade(500)
                    .placeholder(R.drawable.il_logo)
                    .error(R.drawable.il_logo)
                    .build(),
                contentDescription = character.name,
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_big)),
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
                modifier = Modifier.fillMaxSize(),
                title = stringResource(R.string.no_character_available_for_show),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_big)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_big)),
        ) {
            Row(modifier = modifier.fillMaxWidth()) {
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
                    text = character.name ?: stringResource(R.string.empty),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(
                    id = R.string.species_inter,
                    character.species ?: stringResource(R.string.empty)
                ),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(
                    id = R.string.gender_inter,
                    character.gender ?: stringResource(R.string.empty)
                ),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterDetailPreview() {
    CharacterDetail(
        character = CharacterModel(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        ),
        navigateToCharacters = {},
    )
}
