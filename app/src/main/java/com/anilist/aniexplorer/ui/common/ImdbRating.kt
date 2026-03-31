package com.anilist.aniexplorer.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.anilist.aniexplorer.R
import com.anilist.aniexplorer.ui.theme.AnilistColors

@Composable
fun ImdbRating(
    score: Double,
    modifier: Modifier = Modifier,
    starSize: Dp? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall
) {
    val resolvedStarSize = starSize ?: dimensionResource(R.dimen.home_duration_icon_size)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = AnilistColors.starYellow(),
            modifier = Modifier.size(resolvedStarSize)
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_xsmall)))
        Text(
            text = stringResource(R.string.score_format, score),
            style = textStyle,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
