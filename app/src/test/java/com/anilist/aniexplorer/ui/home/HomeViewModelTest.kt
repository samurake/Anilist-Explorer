package com.anilist.aniexplorer.ui.home

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.anilist.aniexplorer.R
import com.anilist.aniexplorer.domain.Resource
import com.anilist.aniexplorer.domain.usecase.GetHomeSectionsUseCase
import com.anilist.aniexplorer.testutil.FakeAnimeRepository
import com.anilist.aniexplorer.testutil.MainDispatcherRule
import com.anilist.aniexplorer.testutil.createHomeSections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val application: Application = ApplicationProvider.getApplicationContext()

    @Test
    fun `init exposes success state when repository returns sections`() = runTest {
        val sections = createHomeSections()
        val repository = FakeAnimeRepository(homeResults = listOf(Resource.Success(sections)))

        val viewModel = HomeViewModel(
            application = application,
            getHomeSectionsUseCase = GetHomeSectionsUseCase(repository),
            defaultDispatcher = mainDispatcherRule.testDispatcher
        )

        advanceUntilIdle()

        assertEquals(HomeUiState.Success(HomeUiModel(sections)), viewModel.uiState.value)
    }

    @Test
    fun `init exposes empty state when repository returns no sections`() = runTest {
        val repository = FakeAnimeRepository(homeResults = listOf(Resource.Success(emptyList())))

        val viewModel = HomeViewModel(
            application = application,
            getHomeSectionsUseCase = GetHomeSectionsUseCase(repository),
            defaultDispatcher = mainDispatcherRule.testDispatcher
        )

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is HomeUiState.Empty)
    }

    @Test
    fun `retry uses latest repository result and updates state`() = runTest {
        val sections = createHomeSections()
        val repository = FakeAnimeRepository(
            homeResults = listOf(
                Resource.Error("Initial failure"),
                Resource.Success(sections)
            )
        )

        val viewModel = HomeViewModel(
            application = application,
            getHomeSectionsUseCase = GetHomeSectionsUseCase(repository),
            defaultDispatcher = mainDispatcherRule.testDispatcher
        )
        advanceUntilIdle()

        assertEquals(HomeUiState.Error("Initial failure"), viewModel.uiState.value)

        viewModel.handleIntent(HomeIntent.RetryLoad)
        advanceUntilIdle()

        assertEquals(HomeUiState.Success(HomeUiModel(sections)), viewModel.uiState.value)
    }

    @Test
    fun `anime click emits navigation event`() = runTest {
        val repository = FakeAnimeRepository(homeResults = listOf(Resource.Success(createHomeSections())))
        val viewModel = HomeViewModel(
            application = application,
            getHomeSectionsUseCase = GetHomeSectionsUseCase(repository),
            defaultDispatcher = mainDispatcherRule.testDispatcher
        )
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.handleIntent(HomeIntent.OnAnimeClick(42))

            assertEquals(HomeEvent.NavigateToDetails(42), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `disabled feature click emits snackbar message`() = runTest {
        val repository = FakeAnimeRepository(homeResults = listOf(Resource.Success(createHomeSections())))
        val viewModel = HomeViewModel(
            application = application,
            getHomeSectionsUseCase = GetHomeSectionsUseCase(repository),
            defaultDispatcher = mainDispatcherRule.testDispatcher
        )
        advanceUntilIdle()

        viewModel.event.test {
            viewModel.handleIntent(HomeIntent.OnDisabledFeatureClick)

            assertEquals(
                HomeEvent.ShowSnackbar(application.getString(R.string.feature_not_implemented)),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}
