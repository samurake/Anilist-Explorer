package com.anilist.aniexplorer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilist.aniexplorer.domain.Resource
import com.anilist.aniexplorer.domain.usecase.GetHomeSectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeSectionsUseCase: GetHomeSectionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
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
                        _uiState.value = HomeUiState.Success(HomeUiModel(sections))
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

    fun onRetry() {
        loadHomeData()
    }
}
