package com.anilist.aniexplorer.domain.model

data class AnimeDetails(
    val id: Int,
    val title: String,
    val coverImageUrl: String?,
    val bannerImageUrl: String?,
    val description: String,
    val averageScore: Double?,
    val ratingString: String?, 
    val language: String?,
    val durationString: String?,
    val genres: List<Genre>,
    val cast: List<Character>
)
