package com.anilist.aniexplorer.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.anilist.aniexplorer.R
import com.anilist.aniexplorer.ui.textUnitResource
import com.anilist.aniexplorer.ui.theme.AnilistColors

@Composable
fun GenreChip(name: String, modifier: Modifier = Modifier) {
    Surface(
        color = AnilistColors.genreChipBackground(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.genre_chip_corner_radius)),
        modifier = modifier.height(dimensionResource(R.dimen.genre_chip_height))
    ) {
        Box(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.genre_chip_horizontal_padding)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.uppercase(),
                color = AnilistColors.genreChipText(),
                style = TextStyle(
                    fontSize = textUnitResource(R.dimen.text_size_genre_chip),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = textUnitResource(R.dimen.typography_genre_chip_letter_spacing)
                )
            )
        }
    }
}
