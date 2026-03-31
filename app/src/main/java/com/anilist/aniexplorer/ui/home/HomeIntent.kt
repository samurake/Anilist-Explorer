package com.anilist.aniexplorer.ui.home

sealed interface HomeIntent {
    object RetryLoad : HomeIntent
    data class OnAnimeClick(val id: Int) : HomeIntent
    object OnDisabledFeatureClick : HomeIntent
}
