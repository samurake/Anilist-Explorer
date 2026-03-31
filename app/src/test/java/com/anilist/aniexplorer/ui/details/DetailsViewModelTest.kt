package com.anilist.aniexplorer.ui.details

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.anilist.aniexplorer.R
import com.anilist.aniexplorer.domain.Resource
import com.anilist.aniexplorer.domain.usecase.GetAnimeDetailsUseCase
import com.anilist.aniexplorer.testutil.FakeAnimeRepository
import com.anilist.aniexplorer.testutil.MainDispatcherRule
import com.anilist.aniexplorer.testutil.createAnimeDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class DetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val application: Application = ApplicationProvider.getApplicationContext()

    @Test
    fun `init maps domain model into details ui state`() = runTest {
        val details = createAnimeDetails(
            id = 7,
            title = "Attack on Titan",
            ratingString = null,
            language = null,
            durationString = null
        )
        val repository = FakeAnimeRepository(detailsResults = listOf(Resource.Success(details)))

        val viewModel = DetailsViewModel(
            application = application,
            getAnimeDetailsUseCase = GetAnimeDetailsUseCase(repository),
            defaultDispatcher = mainDispatcherRule.testDispatcher,
            savedStateHandle = SavedStateHandle(mapOf("animeId" to 7))
        )

        advanceUntilIdle()

        assertEquals(
            DetailsUiState.Success(
                DetailsUiModel(
                    id = 7,
                    title = "Attack on Titan",
                    bannerImageUrl = details.bannerImageUrl,
                    coverImageUrl = details.coverImageUrl,
                    description = details.description,
                    averageScore = 9.4,
                    rating = application.getString(R.string.not_available),
                    language = application.getString(R.string.not_available),
                    duration = application.getString(R.string.not_available),
                    genres = details.genres,
                    cast = details.cast
                )
            ),
            viewModel.uiState.value
        )
        assertEquals(listOf(7), repository.requestedAnimeIds)
    }

    @Test
    fun `retry reloads same anime id and replaces error state`() = runTest {
        val successDetails = createAnimeDetails(id = 12, title = "One Piece")
        val repository = FakeAnimeRepository(
            detailsResults = listOf(
                Resource.Error("No details"),
                Resource.Success(successDetails)
            )
        )

        val viewModel = DetailsViewModel(
            application = application,
            getAnimeDetailsUseCase = GetAnimeDetailsUseCase(repository),
            defaultDispatcher = mainDispatcherRule.testDispatcher,
            savedStateHandle = SavedStateHandle(mapOf("animeId" to 12))
        )
        advanceUntilIdle()

        assertEquals(DetailsUiState.Error("No details"), viewModel.uiState.value)

        viewModel.handleIntent(DetailsIntent.RetryLoad)
        advanceUntilIdle()

        assertEquals(listOf(12, 12), repository.requestedAnimeIds)
        assertEquals(
            DetailsUiState.Success(
                DetailsUiModel(
                    id = 12,
                    title = "One Piece",
                    bannerImageUrl = successDetails.bannerImageUrl,
                    coverImageUrl = successDetails.coverImageUrl,
                    description = successDetails.description,
                    averageScore = 9.4,
                    rating = "PG-13",
                    language = "Japanese",
                    duration = "0h 24min",
                    genres = successDetails.genres,
                    cast = successDetails.cast
                )
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `back click emits navigate back event`() = runTest {
        val repository = FakeAnimeRepository(detailsResults = listOf(Resource.Success(createAnimeDetails())))
        val viewModel = DetailsViewModel(
            application = application,
            getAnimeDetailsUseCase = GetAnimeDetailsUseCase(repository),
            defaultDispatcher = mainDispatcherRule.testDispatcher,
            savedStateHandle = SavedStateHandle(mapOf("animeId" to 1))
        )
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.handleIntent(DetailsIntent.OnBackClick)

            assertEquals(DetailsEvent.NavigateBack, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `action click emits formatted snackbar event`() = runTest {
        val repository = FakeAnimeRepository(detailsResults = listOf(Resource.Success(createAnimeDetails())))
        val viewModel = DetailsViewModel(
            application = application,
            getAnimeDetailsUseCase = GetAnimeDetailsUseCase(repository),
            defaultDispatcher = mainDispatcherRule.testDispatcher,
            savedStateHandle = SavedStateHandle(mapOf("animeId" to 1))
        )
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.handleIntent(DetailsIntent.OnActionClick(R.string.favorite_description))

            assertEquals(
                DetailsEvent.ShowSnackbar(
                    application.getString(
                        R.string.action_not_implemented,
                        application.getString(R.string.favorite_description)
                    )
                ),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}
