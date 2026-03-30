package com.anilist.aniexplorer.model.domain

data class HomeSection(
    val title: String,
    val type: SectionType,
    val animes: List<AnimeSummary>
) {
    enum class SectionType { NOW_SHOWING, POPULAR }
}
