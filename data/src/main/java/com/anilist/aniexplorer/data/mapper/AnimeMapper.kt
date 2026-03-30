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
        averageScore = averageScore?.toDouble()?.div(10.0), // Anilist is 0-100 mostly, we scale to 10 or keep as is. Actually 0-100 / 10 is good for x.x/10 format. Wait, let's just keep the value if it's already properly scaled or div by 10. Let's do raw score or div 10 depending on UI.
        // Actually, Anilist score is usually out of 100. Let's keep it divided by 10 so it's out of 10.
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
        language = "Japanese", // Anilist doesn't natively expose main audio language easily in simple queries, defaulting to Japanese.
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
