package com.anilist.aniexplorer.ui.details

sealed class DetailsEvent {
    object NavigateBack : DetailsEvent()
    data class ShowSnackbar(val message: String) : DetailsEvent()
}
