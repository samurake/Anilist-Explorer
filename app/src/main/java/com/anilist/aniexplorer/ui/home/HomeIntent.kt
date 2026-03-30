package com.anilist.aniexplorer.ui.home

sealed interface HomeIntent {
    object LoadHomeData : HomeIntent
    data class OnAnimeClick(val id: Int) : HomeIntent
    object OnDisabledFeatureClick : HomeIntent
}
