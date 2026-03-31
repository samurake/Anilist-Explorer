package com.anilist.aniexplorer.ui

import androidx.annotation.DimenRes
import androidx.annotation.FractionRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun textUnitResource(@DimenRes id: Int): TextUnit {
    val resources = LocalContext.current.resources
    val density = LocalDensity.current
    val pixels = resources.getDimension(id)
    return (pixels / density.density / density.fontScale).sp
}

@Composable
fun fractionResource(@FractionRes id: Int): Float {
    return LocalContext.current.resources.getFraction(id, 1, 1)
}
