package com.anilist.aniexplorer.ui.details

sealed class DetailsIntent {
    data class LoadDetails(val animeId: Int) : DetailsIntent()
    object OnBackClick : DetailsIntent()
    data class OnActionClick(val action: String) : DetailsIntent()
}
