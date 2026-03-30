package com.anilist.aniexplorer.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilist.aniexplorer.domain.Resource
import com.anilist.aniexplorer.domain.model.AnimeDetails
import com.anilist.aniexplorer.domain.usecase.GetAnimeDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<DetailsEvent>()
    val event: SharedFlow<DetailsEvent> = _event.asSharedFlow()

    fun handleIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.LoadDetails -> loadDetails(intent.animeId)
            is DetailsIntent.OnBackClick -> {
                viewModelScope.launch {
                    _event.emit(DetailsEvent.NavigateBack)
                }
            }
            is DetailsIntent.OnActionClick -> {
                viewModelScope.launch {
                    _event.emit(DetailsEvent.ShowSnackbar("${intent.action} not implemented yet"))
                }
            }
        }
    }

    private fun loadDetails(id: Int) {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            when (val result = getAnimeDetailsUseCase(id)) {
                is Resource.Success -> {
                    _uiState.value = DetailsUiState.Success(mapToUiModel(result.data))
                }
                is Resource.Error -> {
                    _uiState.value = DetailsUiState.Error(result.message)
                }
                is Resource.Loading -> {
                    _uiState.value = DetailsUiState.Loading
                }
            }
        }
    }

    private fun mapToUiModel(details: AnimeDetails): DetailsUiModel {
        return DetailsUiModel(
            id = details.id,
            title = details.title,
            bannerImageUrl = details.bannerImageUrl,
            coverImageUrl = details.coverImageUrl,
            description = details.description,
            averageScore = details.averageScore?.let { "$it/10 IMDb" } ?: "N/A",
            rating = details.ratingString ?: "N/A",
            language = details.language ?: "N/A",
            duration = details.durationString ?: "N/A",
            genres = details.genres,
            cast = details.cast
        )
    }
}
