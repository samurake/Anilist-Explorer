package com.anilist.aniexplorer.domain.model

data class HomeSection(
    val title: String,
    val type: SectionType,
    val animes: List<AnimeSummary>
) {
    enum class SectionType { NOW_SHOWING, POPULAR }
}
