package com.anilist.aniexplorer.ui.details

import com.anilist.aniexplorer.domain.model.Genre
import com.anilist.aniexplorer.domain.model.Character

data class DetailsUiModel(
    val id: Int,
    val title: String,
    val bannerImageUrl: String?,
    val coverImageUrl: String?,
    val description: String,
    val averageScore: String,
    val rating: String,
    val language: String,
    val duration: String,
    val genres: List<Genre>,
    val cast: List<Character>
)
