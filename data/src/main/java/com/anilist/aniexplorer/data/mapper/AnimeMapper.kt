package com.anilist.aniexplorer.data.mapper

import com.anilist.aniexplorer.graphql.GetAnimeDetailsQuery
import com.anilist.aniexplorer.graphql.GetHomeSectionsQuery
import com.anilist.aniexplorer.domain.model.AnimeDetails
import com.anilist.aniexplorer.domain.model.AnimeSummary
import com.anilist.aniexplorer.domain.model.Character
import com.anilist.aniexplorer.domain.model.Genre

fun GetHomeSectionsQuery.Medium.toDomainModel(): AnimeSummary {
    return AnimeSummary(
        id = id,
        title = title?.english ?: title?.romaji ?: "Unknown Title",
        coverImageUrl = coverImage?.large,
        averageScore = averageScore?.toDouble()?.div(10.0),
        durationString = duration?.let { "${it / 60}h ${it % 60}min" } ?: "Unknown",
        genres = genres?.mapNotNull { it?.let { name -> Genre(name) } } ?: emptyList()
    )
}

fun GetHomeSectionsQuery.Medium1.toDomainModel(): AnimeSummary {
    return AnimeSummary(
        id = id,
        title = title?.english ?: title?.romaji ?: "Unknown Title",
        coverImageUrl = coverImage?.large,
        averageScore = averageScore?.toDouble()?.div(10.0),
        durationString = duration?.let { "${it / 60}h ${it % 60}min" } ?: "Unknown",
        genres = genres?.mapNotNull { it?.let { name -> Genre(name) } } ?: emptyList()
    )
}

fun GetAnimeDetailsQuery.Media.toDomainModel(): AnimeDetails {
    return AnimeDetails(
        id = id,
        title = title?.english ?: title?.romaji ?: "Unknown Title",
        coverImageUrl = coverImage?.large,
        bannerImageUrl = bannerImage,
        description = description ?: "No description available.",
        averageScore = averageScore?.toDouble()?.div(10.0),
        ratingString = if (isAdult == true) "18+" else "PG-13",
        language = "Japanese",
        durationString = duration?.let { "${it / 60}h ${it % 60}min" } ?: "Unknown",
        genres = genres?.mapNotNull { it?.let { name -> Genre(name) } } ?: emptyList(),
        cast = characters?.edges?.mapNotNull { it?.toDomainModel() } ?: emptyList()
    )
}

fun GetAnimeDetailsQuery.Edge.toDomainModel(): Character? {
    val node = node ?: return null
    return Character(
        id = node.id,
        name = node.name?.full ?: "Unknown",
        imageUrl = node.image?.large,
        role = role?.name ?: "Unknown"
    )
}
