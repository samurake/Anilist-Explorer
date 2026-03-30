package com.anilist.aniexplorer.ui.home

sealed interface HomeEvent {
    data class ShowSnackbar(val message: String) : HomeEvent
    data class NavigateToDetails(val id: Int) : HomeEvent
}
