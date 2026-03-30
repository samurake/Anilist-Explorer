package com.anilist.aniexplorer.data.repository

import com.anilist.aniexplorer.domain.model.*

object MockData {
    val trendingAnimes = listOf(
        AnimeSummary(
            id = 1,
            title = "Mock: Frieren: Beyond Journey's End",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/1015/138064.jpg",
            averageScore = 9.4,
            durationString = "24 min",
            genres = listOf(Genre("Adventure"), Genre("Drama"), Genre("Fantasy"))
        ),
        AnimeSummary(
            id = 2,
            title = "Mock: Jujutsu Kaisen 2nd Season",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/1792/138022.jpg",
            averageScore = 8.8,
            durationString = "23 min",
            genres = listOf(Genre("Action"), Genre("Fantasy"))
        ),
        AnimeSummary(
            id = 3,
            title = "Mock: Apothecary Diaries",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/1708/138033.jpg",
            averageScore = 8.6,
            durationString = "24 min",
            genres = listOf(Genre("Drama"), Genre("Mystery"))
        ),
        AnimeSummary(
            id = 4,
            title = "Mock: Solo Leveling",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/1169/138096.jpg",
            averageScore = 8.5,
            durationString = "23 min",
            genres = listOf(Genre("Action"), Genre("Adventure"), Genre("Fantasy"))
        ),
        AnimeSummary(
            id = 5,
            title = "Mock: SPY x FAMILY Code: White",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/1569/138006.jpg",
            averageScore = 8.2,
            durationString = "1h 50min",
            genres = listOf(Genre("Action"), Genre("Comedy"))
        )
    )

    val popularAnimes = listOf(
        AnimeSummary(
            id = 6,
            title = "Mock: Fullmetal Alchemist: Brotherhood",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/1223/96541.jpg",
            averageScore = 9.1,
            durationString = "24 min",
            genres = listOf(Genre("Action"), Genre("Adventure"), Genre("Drama"))
        ),
        AnimeSummary(
            id = 7,
            title = "Mock: Attack on Titan",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/10/47347.jpg",
            averageScore = 8.5,
            durationString = "24 min",
            genres = listOf(Genre("Action"), Genre("Drama"), Genre("Suspense"))
        ),
        AnimeSummary(
            id = 8,
            title = "Mock: Steins;Gate",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/1935/127923.jpg",
            averageScore = 9.0,
            durationString = "24 min",
            genres = listOf(Genre("Drama"), Genre("Sci-Fi"), Genre("Suspense"))
        ),
        AnimeSummary(
            id = 9,
            title = "Mock: Hunter x Hunter (2011)",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/1337/99013.jpg",
            averageScore = 9.0,
            durationString = "23 min",
            genres = listOf(Genre("Action"), Genre("Adventure"), Genre("Fantasy"))
        ),
        AnimeSummary(
            id = 10,
            title = "Mock: One Piece",
            coverImageUrl = "https://cdn.myanimelist.net/images/anime/6/73245.jpg",
            averageScore = 8.7,
            durationString = "24 min",
            genres = listOf(Genre("Action"), Genre("Adventure"), Genre("Fantasy"))
        )
    )

    fun getMockDetails(id: Int): AnimeDetails {
        val summary = (trendingAnimes + popularAnimes).find { it.id == id } 
            ?: trendingAnimes.first()

        val banners = mapOf(
            1 to "https://images6.alphacoders.com/133/1331003.png",
            2 to "https://images8.alphacoders.com/129/1292021.png",
            4 to "https://images2.alphacoders.com/132/1324151.jpeg",
            10 to "https://images4.alphacoders.com/606/606233.jpg"
        )

        return AnimeDetails(
            id = summary.id,
            title = summary.title,
            coverImageUrl = summary.coverImageUrl,
            bannerImageUrl = banners[summary.id] ?: "https://picsum.photos/seed/${summary.id}banner/1200/400",
            description = "This is a mocked description for **${summary.title}**. The AniList API is currently offline for maintenance. This mock data allows you to explore the UI and test the application's functionality. \n\n" +
                    "Experience the journey of characters in a world of fantasy and adventure. Each episode brings new challenges and emotional story beats.",
            averageScore = summary.averageScore,
            ratingString = "PG-13",
            language = "Japanese",
            durationString = summary.durationString,
            genres = summary.genres,
            cast = listOf(
                Character(101, "Main Character", "https://picsum.photos/seed/char1/200/200", "Main"),
                Character(102, "Supporting Hero", "https://picsum.photos/seed/char2/200/200", "Supporting"),
                Character(103, "Rival Antagonist", "https://picsum.photos/seed/char3/200/200", "Supporting"),
                Character(104, "Comic Relief", "https://picsum.photos/seed/char4/200/200", "Supporting")
            )
        )
    }
}
