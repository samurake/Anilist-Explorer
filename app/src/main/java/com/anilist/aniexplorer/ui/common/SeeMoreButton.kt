package com.anilist.aniexplorer.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.anilist.aniexplorer.R
import com.anilist.aniexplorer.ui.theme.AnilistColors

@Composable
fun SeeMoreButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(dimensionResource(R.dimen.genre_chip_corner_radius)),
        contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.see_more_button_horizontal_padding)),
        border = BorderStroke(dimensionResource(R.dimen.see_more_border_width), AnilistColors.seeMoreBorder()),
        modifier = modifier
            .width(dimensionResource(R.dimen.see_more_button_width))
            .height(dimensionResource(R.dimen.see_more_button_height))
    ) {
        Text(
            text = stringResource(R.string.see_more),
            style = MaterialTheme.typography.labelSmall,
            color = AnilistColors.durationGray(),
            maxLines = 1
        )
    }
}
