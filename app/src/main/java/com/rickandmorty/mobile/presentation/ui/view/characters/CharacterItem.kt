/*
 * CharacterItem.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.presentation.ui.view.characters

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rickandmorty.mobile.R
import com.rickandmorty.mobile.domain.model.CharacterModel
import com.rickandmorty.mobile.presentation.ui.components.CharacterStatus
import com.rickandmorty.mobile.util.Constants.ANIMATION_DURATION

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: CharacterModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToCharacterDetail: (characterId: Int) -> Unit,
) = with(sharedTransitionScope) {
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .sharedBounds(
                rememberSharedContentState(key = "image_${character.id}"),
                animatedVisibilityScope,
            )
            .clickable {
                navigateToCharacterDetail(character.id)
            },
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.image)
                        .crossfade(ANIMATION_DURATION)
                        .placeholder(R.drawable.il_logo)
                        .error(R.drawable.il_logo)
                        .build(),
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium),
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding)),
                ) {
                    Text(
                        text = character.name ?: stringResource(R.string.empty),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Text(
                        text = character.species ?: stringResource(R.string.empty),
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
            CharacterStatus(
                status = character.status,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(dimensionResource(id = R.dimen.padding_small)),
            )
        }
    }
}
