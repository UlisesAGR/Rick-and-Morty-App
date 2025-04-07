/*
 * CharacterItem.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.domain.model.CharacterModel
import com.rickandmorty.mobile.presentation.ui.components.CharacterStatus

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: CharacterModel,
    navigateToCharacterDetail: (characterId: Int) -> Unit,
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .clickable {
                navigateToCharacterDetail(character.id)
            },
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = modifier
                        .fillMaxWidth()
                        .size(dimensionResource(id = R.dimen.image_size)),
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
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                ) {
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = character.name ?: stringResource(R.string.empty),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = character.species ?: stringResource(R.string.empty),
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            CharacterStatus(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(dimensionResource(id = R.dimen.padding_small)),
                status = character.status
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterItemPreview() {
    CharacterItem(
        character = CharacterModel(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        ),
        navigateToCharacterDetail = {},
    )
}
