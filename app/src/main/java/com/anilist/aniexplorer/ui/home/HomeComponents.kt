package com.anilist.aniexplorer.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import com.anilist.aniexplorer.domain.model.AnimeSummary
import com.anilist.aniexplorer.ui.theme.*

@Composable
fun HomeHeader(
    title: String,
    onSeeMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = HeaderBlue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedButton(
            onClick = onSeeMoreClick,
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
            border = ButtonDefaults.outlinedButtonBorder(enabled = false)
        ) {
            Text(
                text = "See more",
                fontSize = 12.sp,
                color = DurationGray
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
    Column(
        modifier = modifier
            .width(150.dp)
            .clickable { onClick() }
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(anime.coverImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = anime.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(BackgroundGray),
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = HeaderBlue,
                        strokeWidth = 2.dp
                    )
                }
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Image not available",
                        tint = DurationGray,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = anime.title,
            color = HeaderBlue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = StarYellow,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${anime.averageScore ?: 0.0}/10 IMDb",
                color = DurationGray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun AnimeVerticalItem(
    anime: AnimeSummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(anime.coverImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = anime.title,
            modifier = Modifier
                .size(width = 85.dp, height = 120.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BackgroundGray),
            contentScale = ContentScale.Crop,
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Image not available",
                        tint = DurationGray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = anime.title,
                color = HeaderBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = StarYellow,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${anime.averageScore ?: 0.0}/10 IMDb",
                    color = DurationGray,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                anime.genres.take(3).forEach { genre ->
                    GenreChip(genre.name)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = DurationGray,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = anime.durationString ?: "Unknown",
                    color = HeaderBlue,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun GenreChip(name: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(GenreChipBackground)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = name.uppercase(),
            color = GenreChipText,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
