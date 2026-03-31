package com.anilist.aniexplorer.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.anilist.aniexplorer.R
import com.anilist.aniexplorer.ui.textUnitResource
import com.anilist.aniexplorer.ui.theme.AnilistColors

@Composable
fun PlayTrailerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            onClick = onClick,
            shape = CircleShape,
            color = AnilistColors.appWhite(),
            modifier = Modifier.size(dimensionResource(R.dimen.play_trailer_button_size)),
            tonalElevation = dimensionResource(R.dimen.elevation_none)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.play_trailer_icon_padding))
                    .fillMaxSize(),
                tint = AnilistColors.appBlack()
            )
        }
        
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
        
        Text(
            text = stringResource(R.string.play_trailer),
            color = AnilistColors.appWhite(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = textUnitResource(R.dimen.text_size_play_trailer),
                fontWeight = FontWeight.Bold,
                lineHeight = textUnitResource(R.dimen.typography_play_trailer_line_height),
                letterSpacing = textUnitResource(R.dimen.typography_play_trailer_letter_spacing)
            ),
            modifier = Modifier
                .width(dimensionResource(R.dimen.play_trailer_text_width))
                .height(dimensionResource(R.dimen.play_trailer_text_height))
        )
    }
}
