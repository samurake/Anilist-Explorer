package com.anilist.aniexplorer.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import com.anilist.aniexplorer.R
import com.anilist.aniexplorer.domain.model.AnimeSummary
import com.anilist.aniexplorer.ui.common.GenreChip
import com.anilist.aniexplorer.ui.common.ImdbRating
import com.anilist.aniexplorer.ui.fractionResource
import com.anilist.aniexplorer.ui.theme.*

@Composable
fun NotificationIcon(
    modifier: Modifier = Modifier,
    hasNotification: Boolean,
    contentDescription: String?
) {
    Box(modifier = modifier) {
        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurface
        )
        if (hasNotification) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.notification_dot_size))
                    .background(AnilistColors.appRed(), shape = CircleShape)
                    .align(Alignment.TopEnd)
                    .offset(
                        x = -dimensionResource(R.dimen.notification_dot_offset),
                        y = dimensionResource(R.dimen.notification_dot_offset)
                    )
            )
        }
    }
}

@Composable
fun AnimeHorizontalCard(
    anime: AnimeSummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val hasUrl = !anime.coverImageUrl.isNullOrEmpty()
    var isLoading by remember(anime.id) { mutableStateOf(hasUrl) }
    var isError by remember(anime.id) { mutableStateOf(!hasUrl) }
    val cardCornerRadius = dimensionResource(R.dimen.card_corner_radius_small)
    val cardShadowElevation = dimensionResource(R.dimen.home_card_shadow_elevation)
    val cardShadowAmbient = AnilistColors.cardShadowAmbient()
    val cardShadowSpot = AnilistColors.cardShadowSpot()
    
    Column(
        modifier = modifier
            .width(dimensionResource(R.dimen.home_card_width))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.home_card_height))
                .graphicsLayer {
                    shadowElevation = cardShadowElevation.toPx()
                    shape = RoundedCornerShape(cardCornerRadius)
                    clip = false
                    ambientShadowColor = cardShadowAmbient
                    spotShadowColor = cardShadowSpot
                }
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(cardCornerRadius)
                )
        ) {
            if (hasUrl) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(anime.coverImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = anime.title,
                    contentScale = ContentScale.Crop,
                    onLoading = { isLoading = true; isError = false },
                    onSuccess = { isLoading = false; isError = false },
                    onError = { isLoading = false; isError = true },
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(cardCornerRadius))
                )
            }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(dimensionResource(R.dimen.image_loading_indicator_size)).align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = dimensionResource(R.dimen.progress_indicator_stroke_width)
                )
            }
            
            if (isError || !hasUrl) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = fractionResource(R.fraction.alpha_placeholder_icon)
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))
        
        Text(
            text = anime.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
        
        ImdbRating(
            score = anime.averageScore ?: 0.0,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AnimeVerticalItem(
    anime: AnimeSummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val hasUrl = !anime.coverImageUrl.isNullOrEmpty()
    var isLoading by remember(anime.id) { mutableStateOf(hasUrl) }
    var isError by remember(anime.id) { mutableStateOf(!hasUrl) }

    Row(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.home_section_horizontal_padding))
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.home_list_item_height))
            .clickable { onClick() },
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(
                    width = dimensionResource(R.dimen.home_list_item_image_width),
                    height = dimensionResource(R.dimen.home_list_item_height)
                )
                .clip(RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius_small)))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            if (hasUrl) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(anime.coverImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = anime.title,
                    contentScale = ContentScale.Crop,
                    onLoading = { isLoading = true; isError = false },
                    onSuccess = { isLoading = false; isError = false },
                    onError = { isLoading = false; isError = true },
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(dimensionResource(R.dimen.image_loading_indicator_size)).align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = dimensionResource(R.dimen.progress_indicator_stroke_width)
                )
            }

            if (isError || !hasUrl) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = fractionResource(R.fraction.alpha_placeholder_icon)
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_large)))
        
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = anime.title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
            
            ImdbRating(
                score = anime.averageScore ?: 0.0,
                modifier = Modifier.height(dimensionResource(R.dimen.home_rating_height))
            )
            
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small)),
                modifier = Modifier.height(dimensionResource(R.dimen.genre_chip_height))
            ) {
                anime.genres.take(3).forEach { genre ->
                    GenreChip(genre.name)
                }
            }
            
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_xsmall)),
                modifier = Modifier.height(dimensionResource(R.dimen.home_rating_height))
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(dimensionResource(R.dimen.home_duration_icon_size))
                )
                Text(
                    text = anime.durationString ?: stringResource(R.string.unknown_value),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
