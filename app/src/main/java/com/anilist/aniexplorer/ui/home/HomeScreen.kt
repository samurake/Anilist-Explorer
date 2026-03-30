package com.anilist.aniexplorer.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anilist.aniexplorer.domain.model.HomeSection
import com.anilist.aniexplorer.ui.theme.HeaderBlue
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetails: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is HomeEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is HomeEvent.NavigateToDetails -> {
                    onNavigateToDetails(event.id)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Anilist",
                        color = HeaderBlue,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.handleIntent(HomeIntent.OnDisabledFeatureClick) }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = HeaderBlue)
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.handleIntent(HomeIntent.OnDisabledFeatureClick) }) {
                        Icon(Icons.Default.Info, contentDescription = "Notifications", tint = HeaderBlue)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Already selected */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
                )
                NavigationBarItem(
                    selected = false,
                    enabled = false,
                    onClick = { viewModel.handleIntent(HomeIntent.OnDisabledFeatureClick) },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Saved") }
                )
                NavigationBarItem(
                    selected = false,
                    enabled = false,
                    onClick = { viewModel.handleIntent(HomeIntent.OnDisabledFeatureClick) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is HomeUiState.Success -> {
                    HomeContent(
                        uiModel = state.uiModel,
                        onAnimeClick = { id -> viewModel.handleIntent(HomeIntent.OnAnimeClick(id)) },
                        onSeeMoreClick = { viewModel.handleIntent(HomeIntent.OnDisabledFeatureClick) }
                    )
                }
                is HomeUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error: ${state.message}", color = Color.Red)
                        Button(onClick = { viewModel.handleIntent(HomeIntent.LoadHomeData) }) {
                            Text("Retry")
                        }
                    }
                }
                is HomeUiState.Empty -> {
                    Text(
                        text = "No anime found",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    uiModel: HomeUiModel,
    onAnimeClick: (Int) -> Unit,
    onSeeMoreClick: () -> Unit
) {
    val trending = uiModel.sections.find { it.type == HomeSection.SectionType.NOW_SHOWING }
    val popular = uiModel.sections.find { it.type == HomeSection.SectionType.POPULAR }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Now Showing Section
        trending?.let { section ->
            item {
                HomeHeader(
                    title = "Now Showing",
                    onSeeMoreClick = onSeeMoreClick
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(section.animes) { anime ->
                        AnimeHorizontalCard(
                            anime = anime,
                            onClick = { onAnimeClick(anime.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Popular Section Header
        popular?.let { section ->
            item {
                HomeHeader(
                    title = "Popular",
                    onSeeMoreClick = onSeeMoreClick
                )
            }
            items(section.animes) { anime ->
                AnimeVerticalItem(
                    anime = anime,
                    onClick = { onAnimeClick(anime.id) }
                )
            }
        }
    }
}
