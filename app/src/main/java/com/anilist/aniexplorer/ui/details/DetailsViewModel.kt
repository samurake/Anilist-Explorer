package com.anilist.aniexplorer.ui.details

import android.app.Application
import com.anilist.aniexplorer.R
import androidx.lifecycle.SavedStateHandle
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
    private val application: Application,
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase,
    @com.anilist.aniexplorer.graphql.di.DefaultDispatcher private val defaultDispatcher: kotlinx.coroutines.CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val animeId: Int = checkNotNull(savedStateHandle["animeId"])

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<DetailsEvent>()
    val event: SharedFlow<DetailsEvent> = _event.asSharedFlow()

    init {
        loadDetails()
    }

    fun handleIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.RetryLoad -> loadDetails()
            is DetailsIntent.OnBackClick -> {
                viewModelScope.launch {
                    _event.emit(DetailsEvent.NavigateBack)
                }
            }
            is DetailsIntent.OnActionClick -> {
                viewModelScope.launch {
                    _event.emit(
                        DetailsEvent.ShowSnackbar(
                            application.getString(
                                R.string.action_not_implemented,
                                application.getString(intent.actionResId)
                            )
                        )
                    )
                }
            }
        }
    }

    private fun loadDetails() {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            when (val result = getAnimeDetailsUseCase(animeId)) {
                is Resource.Success -> {
                    val uiModel = kotlinx.coroutines.withContext(defaultDispatcher) {
                        mapToUiModel(result.data)
                    }
                    _uiState.value = DetailsUiState.Success(uiModel)
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
            averageScore = details.averageScore ?: 0.0,
            rating = details.ratingString ?: application.getString(R.string.not_available),
            language = details.language ?: application.getString(R.string.not_available),
            duration = details.durationString ?: application.getString(R.string.not_available),
            genres = details.genres,
            cast = details.cast
        )
    }
}
