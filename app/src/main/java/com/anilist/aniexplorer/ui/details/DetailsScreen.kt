package com.anilist.aniexplorer.ui.details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.res.stringResource
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import com.anilist.aniexplorer.R
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anilist.aniexplorer.ui.common.CastItem
import com.anilist.aniexplorer.ui.common.GenreChip
import com.anilist.aniexplorer.ui.common.ImdbRating
import com.anilist.aniexplorer.ui.common.MetadataItem
import com.anilist.aniexplorer.ui.common.PlayTrailerButton
import com.anilist.aniexplorer.ui.common.SectionHeader
import com.anilist.aniexplorer.ui.fractionResource
import com.anilist.aniexplorer.ui.textUnitResource
import com.anilist.aniexplorer.ui.theme.AnilistColors
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsScreen(
    animeId: Int,
    onBackClick: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is DetailsEvent.NavigateBack -> onBackClick()
                is DetailsEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = AnilistColors.appWhite()
    ) { padding ->
        Crossfade(
            targetState = uiState,
            label = "DetailsScreenStateCrossfade"
        ) { state ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AnilistColors.appWhite())
            ) {
                when (state) {
                    is DetailsUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is DetailsUiState.Success -> {
                        DetailsContent(
                            uiModel = state.uiModel,
                            onIntent = { viewModel.handleIntent(it) }
                        )
                    }
                    is DetailsUiState.Error -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.error_message, state.message),
                                color = MaterialTheme.colorScheme.error
                            )
                            Button(onClick = { viewModel.handleIntent(DetailsIntent.RetryLoad) }) {
                                Text(stringResource(R.string.retry))
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsContent(
    uiModel: DetailsUiModel,
    onIntent: (DetailsIntent) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(AnilistColors.appWhite())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.details_banner_height))
        ) {
            val context = LocalContext.current
            val bannerRequest = remember(uiModel.bannerImageUrl) {
                ImageRequest.Builder(context)
                    .data(uiModel.bannerImageUrl)
                    .crossfade(true)
                    .build()
            }
            
            AsyncImage(
                model = bannerRequest,
                contentDescription = stringResource(R.string.play_trailer_description),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                AnilistColors.appBlack().copy(alpha = fractionResource(R.fraction.alpha_banner_overlay_strong)),
                                AnilistColors.appTransparent(),
                                AnilistColors.appBlack().copy(alpha = fractionResource(R.fraction.alpha_banner_overlay_soft))
                            )
                        )
                    )
            )

            PlayTrailerButton(
                onClick = { onIntent(DetailsIntent.OnActionClick(R.string.play_trailer)) },
                modifier = Modifier.align(Alignment.Center)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(
                        horizontal = dimensionResource(R.dimen.details_top_bar_horizontal_padding),
                        vertical = dimensionResource(R.dimen.details_top_bar_vertical_padding)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onIntent(DetailsIntent.OnBackClick) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_description),
                        tint = AnilistColors.appWhite()
                    )
                }
                IconButton(onClick = { onIntent(DetailsIntent.OnActionClick(R.string.more_description)) }) {
                    Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = stringResource(R.string.more_description),
                        tint = AnilistColors.appWhite()
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = -dimensionResource(R.dimen.details_info_card_offset)),
            shape = RoundedCornerShape(
                topStart = dimensionResource(R.dimen.details_info_card_corner_radius),
                topEnd = dimensionResource(R.dimen.details_info_card_corner_radius)
            ),
            color = AnilistColors.appWhite()
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.details_content_padding))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = uiModel.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = AnilistColors.headerBlue(),
                            fontSize = textUnitResource(R.dimen.text_size_details_title),
                            fontWeight = FontWeight.Bold,
                            lineHeight = textUnitResource(R.dimen.text_size_details_title),
                            letterSpacing = textUnitResource(R.dimen.typography_body_large_letter_spacing)
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

                        ImdbRating(
                            score = uiModel.averageScore,
                            starSize = dimensionResource(R.dimen.home_duration_icon_size),
                            textStyle = MaterialTheme.typography.bodySmall
                        )
                    }

                    IconButton(
                        onClick = { onIntent(DetailsIntent.OnActionClick(R.string.favorite_description)) },
                        modifier = Modifier.offset(y = -dimensionResource(R.dimen.details_title_favorite_offset))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.figma_bookmark_icon),
                            contentDescription = stringResource(R.string.favorite_description),
                            tint = AnilistColors.appBlack()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
                ) {
                    uiModel.genres.forEach { genre ->
                        GenreChip(genre.name)
                    }
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.details_content_padding)))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.details_metadata_spacing))
                ) {
                    MetadataItem(label = stringResource(R.string.label_length), value = uiModel.duration)
                    MetadataItem(label = stringResource(R.string.label_language), value = uiModel.language)
                    MetadataItem(label = stringResource(R.string.label_rating), value = uiModel.rating)
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.details_content_padding)))

                SectionHeader(title = stringResource(R.string.label_description))
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
                Text(
                    text = uiModel.description,
                    color = AnilistColors.durationGray(),
                    fontSize = textUnitResource(R.dimen.text_size_body_small),
                    fontWeight = FontWeight.Normal,
                    lineHeight = textUnitResource(R.dimen.details_description_line_height),
                    letterSpacing = textUnitResource(R.dimen.typography_body_small_letter_spacing)
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.details_content_padding)))

                SectionHeader(
                    title = stringResource(R.string.label_cast),
                    onSeeMoreClick = { onIntent(DetailsIntent.OnActionClick(R.string.see_more_cast)) }
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_large))
                ) {
                    items(uiModel.cast) { character ->
                        CastItem(character)
                    }
                }
                
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.details_content_bottom_spacing)))
            }
        }
    }
}
