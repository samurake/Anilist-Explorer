package com.anilist.aniexplorer.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.anilist.aniexplorer.R
import com.anilist.aniexplorer.domain.model.HomeSection
import com.anilist.aniexplorer.ui.common.SectionHeader
import com.anilist.aniexplorer.ui.textUnitResource
import com.anilist.aniexplorer.ui.theme.AnilistColors
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

    Box(modifier = Modifier
        .fillMaxSize()
        .background(AnilistColors.appWhite())) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(dimensionResource(R.dimen.home_side_panel_width))
                .background(AnilistColors.homeSideRectangle())
        )

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.home_title),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Black,
                            letterSpacing = textUnitResource(R.dimen.home_title_letter_spacing)
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { viewModel.handleIntent(HomeIntent.OnDisabledFeatureClick) },
                            modifier = Modifier.padding(start = dimensionResource(R.dimen.home_top_bar_icon_padding))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.figma_menu_icon),
                                contentDescription = stringResource(R.string.menu_description),
                                tint = AnilistColors.appBlack()
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { viewModel.handleIntent(HomeIntent.OnDisabledFeatureClick) },
                            modifier = Modifier.padding(end = dimensionResource(R.dimen.home_top_bar_icon_padding))
                        ) {
                            NotificationIcon(
                                hasNotification = true,
                                contentDescription = stringResource(R.string.notifications_description)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = AnilistColors.appTransparent()
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = AnilistColors.appWhite(),
                    tonalElevation = dimensionResource(R.dimen.elevation_none),
                    modifier = Modifier.shadow(
                        elevation = dimensionResource(R.dimen.home_nav_shadow_elevation),
                        ambientColor = AnilistColors.navBarShadow(),
                        spotColor = AnilistColors.navBarShadow()
                    )
                ) {
                    val clearInteraction = remember { MutableInteractionSource() }
                    val itemColors = NavigationBarItemDefaults.colors(
                        indicatorColor = AnilistColors.appTransparent()
                    )

                    NavigationBarItem(
                        selected = true,
                        onClick = { },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.figma_home_icon),
                                contentDescription = stringResource(R.string.nav_home),
                                tint = AnilistColors.Unspecified
                            )
                        },
                        interactionSource = clearInteraction,
                        colors = itemColors
                    )
                    NavigationBarItem(
                        selected = false,
                        enabled = false,
                        onClick = { viewModel.handleIntent(HomeIntent.OnDisabledFeatureClick) },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.figma_ticket_icon),
                                contentDescription = stringResource(R.string.nav_saved)
                            )
                        },
                        interactionSource = clearInteraction,
                        colors = itemColors
                    )
                    NavigationBarItem(
                        selected = false,
                        enabled = false,
                        onClick = { viewModel.handleIntent(HomeIntent.OnDisabledFeatureClick) },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.figma_bookmark_icon),
                                contentDescription = stringResource(R.string.nav_profile)
                            )
                        },
                        interactionSource = clearInteraction,
                        colors = itemColors
                    )
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = AnilistColors.appTransparent()
        ) { padding ->
            Crossfade(
                targetState = uiState,
                label = "HomeScreenStateCrossfade",
                modifier = Modifier
                    .fillMaxSize()
                    .background(AnilistColors.appTransparent())
            ) { state ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(AnilistColors.appTransparent())
                ) {
                    when (state) {
                        is HomeUiState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }

                        is HomeUiState.Success -> {
                            HomeContent(
                                uiModel = state.uiModel,
                                onAnimeClick = { id ->
                                    viewModel.handleIntent(
                                        HomeIntent.OnAnimeClick(
                                            id
                                        )
                                    )
                                },
                                onSeeMoreClick = { viewModel.handleIntent(HomeIntent.OnDisabledFeatureClick) }
                            )
                        }

                        is HomeUiState.Error -> {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .background(AnilistColors.appTransparent()),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.error_message, state.message),
                                    color = MaterialTheme.colorScheme.error
                                )
                                Button(onClick = { viewModel.handleIntent(HomeIntent.RetryLoad) }) {
                                    Text(stringResource(R.string.retry))
                                }
                            }
                        }

                        is HomeUiState.Empty -> {
                            Text(
                                text = stringResource(R.string.no_anime_found),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
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

    val horizontalPadding = dimensionResource(R.dimen.home_section_horizontal_padding)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AnilistColors.appTransparent())
    ) {
        trending?.let { section ->
            item {
                SectionHeader(
                    title = stringResource(R.string.section_now_showing),
                    onSeeMoreClick = onSeeMoreClick,
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.home_section_top_padding),
                        start = horizontalPadding,
                        end = horizontalPadding
                    )
                )

                LazyRow(
                    contentPadding = PaddingValues(
                        horizontal = horizontalPadding,
                        vertical = dimensionResource(R.dimen.spacing_xlarge)
                    ),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_large)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AnilistColors.appTransparent())
                ) {
                    items(section.animes, key = { it.id }) { anime ->
                        AnimeHorizontalCard(
                            anime = anime,
                            onClick = { onAnimeClick(anime.id) }
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.spacing_medium))
                        .background(AnilistColors.appTransparent())
                )
            }
        }

        popular?.let { section ->
            item {
                SectionHeader(
                    title = stringResource(R.string.section_popular),
                    onSeeMoreClick = onSeeMoreClick,
                    modifier = Modifier.padding(
                        bottom = dimensionResource(R.dimen.home_section_top_padding),
                        start = horizontalPadding,
                        end = horizontalPadding
                    )
                )
            }
            itemsIndexed(section.animes, key = { _, anime -> anime.id }) { index, anime ->
                AnimeVerticalItem(
                    anime = anime,
                    onClick = { onAnimeClick(anime.id) },
                    modifier = Modifier.background(AnilistColors.appTransparent())
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))
            }
        }
    }
}
