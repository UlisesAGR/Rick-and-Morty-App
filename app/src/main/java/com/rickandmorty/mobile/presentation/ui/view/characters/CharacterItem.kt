/*
 * CharacterItem.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.domain.model.CharacterModel
import com.rickandmorty.mobile.util.getCharacterStatusColor

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
        Column(modifier = modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .size(dimensionResource(id = R.dimen.image_size)),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(context)
                    .data(character.image)
                    .transformations(RoundedCornersTransformation())
                    .crossfade(true)
                    .placeholder(R.drawable.il_logo)
                    .error(R.drawable.il_logo)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = character.name,
            )
            Spacer(
                modifier = modifier
                    .fillMaxWidth()
                    .size(dimensionResource(id = R.dimen.space_big)),
            )
            Row(modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.padding))) {
                Icon(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.icon_size))
                        .align(Alignment.CenterVertically),
                    tint = getCharacterStatusColor(character.status),
                    imageVector = Icons.Filled.Circle,
                    contentDescription = stringResource(R.string.circular_icon),
                )
                Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.space)))
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = character.name ?: stringResource(R.string.empty),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding)),
                text = character.species ?: stringResource(R.string.empty),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
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
