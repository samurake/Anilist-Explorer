package com.anilist.aniexplorer.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.anilist.aniexplorer.R
import com.anilist.aniexplorer.ui.textUnitResource

@Composable
fun anilistTypography(): Typography {
    return Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = textUnitResource(R.dimen.text_size_body_large),
            lineHeight = textUnitResource(R.dimen.typography_body_large_line_height),
            letterSpacing = textUnitResource(R.dimen.typography_body_large_letter_spacing)
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Black,
            fontSize = textUnitResource(R.dimen.text_size_title_large),
            lineHeight = textUnitResource(R.dimen.typography_title_large_line_height),
            letterSpacing = textUnitResource(R.dimen.typography_title_large_letter_spacing)
        ),
        titleMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = textUnitResource(R.dimen.text_size_title_medium),
            lineHeight = textUnitResource(R.dimen.typography_title_medium_line_height),
            letterSpacing = textUnitResource(R.dimen.typography_title_medium_letter_spacing)
        ),
        bodySmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = textUnitResource(R.dimen.text_size_body_small),
            lineHeight = textUnitResource(R.dimen.typography_body_small_line_height),
            letterSpacing = textUnitResource(R.dimen.typography_body_small_letter_spacing)
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = textUnitResource(R.dimen.text_size_label_small),
            lineHeight = textUnitResource(R.dimen.typography_label_small_line_height),
            letterSpacing = textUnitResource(R.dimen.typography_label_small_letter_spacing)
        )
    )
}
