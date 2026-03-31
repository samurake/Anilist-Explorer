package com.anilist.aniexplorer.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.anilist.aniexplorer.R

object AnilistColors {
    val Unspecified = Color.Unspecified

    @Composable fun appBlack() = colorResource(R.color.app_black)
    @Composable fun appWhite() = colorResource(R.color.app_white)
    @Composable fun appRed() = colorResource(R.color.app_red)
    @Composable fun appTransparent() = colorResource(R.color.app_transparent)
    @Composable fun navy() = colorResource(R.color.navy)
    @Composable fun lightBlue() = colorResource(R.color.light_blue)
    @Composable fun backgroundGray() = colorResource(R.color.background_gray)
    @Composable fun genreChipBackground() = colorResource(R.color.genre_chip_background)
    @Composable fun genreChipText() = colorResource(R.color.genre_chip_text)
    @Composable fun starYellow() = colorResource(R.color.star_yellow)
    @Composable fun durationGray() = colorResource(R.color.duration_gray)
    @Composable fun headerBlue() = colorResource(R.color.header_blue)
    @Composable fun navBarShadow() = colorResource(R.color.nav_bar_shadow)
    @Composable fun cardShadow() = colorResource(R.color.card_shadow)
    @Composable fun homeSideRectangle() = colorResource(R.color.home_side_rectangle)
    @Composable fun seeMoreBorder() = colorResource(R.color.see_more_border)
    @Composable fun cardShadowAmbient() = colorResource(R.color.card_shadow_ambient)
    @Composable fun cardShadowSpot() = colorResource(R.color.card_shadow_spot)
    @Composable fun darkBackground() = colorResource(R.color.dark_background)
    @Composable fun darkSurface() = colorResource(R.color.dark_surface)
    @Composable fun darkPrimary() = colorResource(R.color.dark_primary)
    @Composable fun darkSecondary() = colorResource(R.color.dark_secondary)
    @Composable fun darkTertiary() = colorResource(R.color.dark_tertiary)
    @Composable fun darkOnBackground() = colorResource(R.color.dark_on_background)
    @Composable fun darkOnSurface() = colorResource(R.color.dark_on_surface)
}
