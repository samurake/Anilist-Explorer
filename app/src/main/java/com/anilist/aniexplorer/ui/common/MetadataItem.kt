package com.anilist.aniexplorer.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.anilist.aniexplorer.R
import com.anilist.aniexplorer.ui.textUnitResource
import com.anilist.aniexplorer.ui.theme.AnilistColors

@Composable
fun MetadataItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = textUnitResource(R.dimen.text_size_body_small),
            color = AnilistColors.durationGray()
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_xsmall)))
        Text(
            text = value,
            fontSize = textUnitResource(R.dimen.metadata_value_text_size),
            fontWeight = FontWeight.Bold,
            color = AnilistColors.appBlack()
        )
    }
}
