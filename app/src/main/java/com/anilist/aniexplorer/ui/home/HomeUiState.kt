package com.anilist.aniexplorer.ui.home

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val uiModel: HomeUiModel) : HomeUiState
    object Empty : HomeUiState
    data class Error(val message: String) : HomeUiState
}
