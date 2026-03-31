package com.anilist.aniexplorer.ui.details

sealed class DetailsUiState {
    object Loading : DetailsUiState()
    data class Success(val uiModel: DetailsUiModel) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}
