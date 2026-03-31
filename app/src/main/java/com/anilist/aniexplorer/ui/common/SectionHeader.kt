package com.anilist.aniexplorer.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.anilist.aniexplorer.R
import com.anilist.aniexplorer.ui.theme.AnilistColors

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    onSeeMoreClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.section_header_height)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = AnilistColors.headerBlue()
        )
        if (onSeeMoreClick != null) {
            SeeMoreButton(onClick = onSeeMoreClick)
        }
    }
}
