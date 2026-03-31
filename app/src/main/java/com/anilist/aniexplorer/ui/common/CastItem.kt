package com.anilist.aniexplorer.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.anilist.aniexplorer.R
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anilist.aniexplorer.domain.model.Character
import com.anilist.aniexplorer.ui.theme.AnilistColors

@Composable
fun CastItem(character: Character) {
    val context = LocalContext.current
    val hasImageUrl = !character.imageUrl.isNullOrEmpty()
    var isLoading by remember(character.imageUrl) { mutableStateOf(hasImageUrl) }
    var isError by remember(character.imageUrl) { mutableStateOf(!hasImageUrl) }

    val imageRequest = remember(character.imageUrl) {
        ImageRequest.Builder(context)
            .data(character.imageUrl?.ifEmpty { null })
            .crossfade(true)
            .build()
    }

    Column(
        modifier = Modifier.width(dimensionResource(R.dimen.cast_item_width)),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .size(dimensionResource(R.dimen.cast_item_image_size))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius_small)))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                onLoading = { isLoading = true },
                onSuccess = { isLoading = false; isError = false },
                onError = { isLoading = false; isError = true },
                modifier = Modifier.fillMaxSize()
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(dimensionResource(R.dimen.progress_indicator_size)).align(Alignment.Center),
                    strokeWidth = dimensionResource(R.dimen.progress_indicator_stroke_width)
                )
            }
            
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(dimensionResource(R.dimen.image_loading_indicator_size)).align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
        Text(
            text = character.name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = AnilistColors.headerBlue()
        )
    }
}
