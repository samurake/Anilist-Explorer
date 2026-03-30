package com.anilist.aniexplorer.model.domain

data class AnimeSummary(
    val id: Int,
    val title: String,
    val coverImageUrl: String?,
    val averageScore: Double?,
    val durationString: String?,
    val genres: List<Genre>
)
