package com.anilist.aniexplorer.ui.home

import android.app.Application
import com.anilist.aniexplorer.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilist.aniexplorer.domain.Resource
import com.anilist.aniexplorer.domain.usecase.GetHomeSectionsUseCase
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
class HomeViewModel @Inject constructor(
    private val application: Application,
    private val getHomeSectionsUseCase: GetHomeSectionsUseCase,
    @com.anilist.aniexplorer.graphql.di.DefaultDispatcher private val defaultDispatcher: kotlinx.coroutines.CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    init {
        loadHomeData()
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.RetryLoad -> loadHomeData()
            is HomeIntent.OnAnimeClick -> {
                viewModelScope.launch {
                    _event.emit(HomeEvent.NavigateToDetails(intent.id))
                }
            }
            is HomeIntent.OnDisabledFeatureClick -> {
                viewModelScope.launch {
                    _event.emit(HomeEvent.ShowSnackbar(application.getString(R.string.feature_not_implemented)))
                }
            }
        }
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            when (val result = getHomeSectionsUseCase()) {
                is Resource.Success -> {
                    val sections = result.data
                    if (sections.isEmpty()) {
                        _uiState.value = HomeUiState.Empty
                    } else {
                        val uiModel = kotlinx.coroutines.withContext(defaultDispatcher) {
                            HomeUiModel(sections)
                        }
                        _uiState.value = HomeUiState.Success(uiModel)
                    }
                }
                is Resource.Error -> {
                    _uiState.value = HomeUiState.Error(result.message)
                }
                is Resource.Loading -> {
                    _uiState.value = HomeUiState.Loading
                }
            }
        }
    }
}
