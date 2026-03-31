package com.anilist.aniexplorer.testutil

import com.anilist.aniexplorer.domain.model.AnimeDetails
import com.anilist.aniexplorer.domain.model.AnimeSummary
import com.anilist.aniexplorer.domain.model.Character
import com.anilist.aniexplorer.domain.model.Genre
import com.anilist.aniexplorer.domain.model.HomeSection

fun createAnimeSummary(
    id: Int = 1,
    title: String = "Frieren",
    durationString: String = "0h 24min"
): AnimeSummary {
    return AnimeSummary(
        id = id,
        title = title,
        coverImageUrl = "https://example.com/$id.jpg",
        averageScore = 9.4,
        durationString = durationString,
        genres = listOf(Genre("Fantasy"))
    )
}

fun createHomeSections(): List<HomeSection> {
    return listOf(
        HomeSection(
            title = "Now Showing",
            type = HomeSection.SectionType.NOW_SHOWING,
            animes = listOf(createAnimeSummary(id = 1, title = "Frieren"))
        ),
        HomeSection(
            title = "Popular",
            type = HomeSection.SectionType.POPULAR,
            animes = listOf(createAnimeSummary(id = 2, title = "Fullmetal Alchemist: Brotherhood"))
        )
    )
}

fun createAnimeDetails(
    id: Int = 1,
    title: String = "Frieren",
    ratingString: String? = "PG-13",
    language: String? = "Japanese",
    durationString: String? = "0h 24min"
): AnimeDetails {
    return AnimeDetails(
        id = id,
        title = title,
        coverImageUrl = "https://example.com/$id-cover.jpg",
        bannerImageUrl = "https://example.com/$id-banner.jpg",
        description = "A journey that keeps moving forward.",
        averageScore = 9.4,
        ratingString = ratingString,
        language = language,
        durationString = durationString,
        genres = listOf(Genre("Fantasy"), Genre("Adventure")),
        cast = listOf(
            Character(
                id = 10,
                name = "Frieren",
                imageUrl = "https://example.com/cast.jpg",
                role = "MAIN"
            )
        )
    )
}
