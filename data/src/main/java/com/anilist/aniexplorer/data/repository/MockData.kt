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
        val summaries = trendingAnimes + popularAnimes
        val summary = summaries.find { it.id == id } ?: trendingAnimes.first()

        val banners = mapOf(
            1 to "https://s4.anilist.co/file/anilistcdn/media/anime/banner/154587-8v07Ym6ZkZ6Z.jpg",
            2 to "https://s4.anilist.co/file/anilistcdn/media/anime/banner/145064-esDtAY2He7sk.jpg",
            3 to "https://s4.anilist.co/file/anilistcdn/media/anime/banner/161645-oqzTZYIvviWI.jpg",
            4 to "https://s4.anilist.co/file/anilistcdn/media/anime/banner/151807-37yfQA3ym8PA.jpg",
            5 to "https://s4.anilist.co/file/anilistcdn/media/anime/banner/158928-7j4JK7pGvZnZ.jpg",
            6 to "https://s4.anilist.co/file/anilistcdn/media/anime/banner/5114-f0YlvIiwY9mH.jpg",
            7 to "https://s4.anilist.co/file/anilistcdn/media/anime/banner/16498-8jpFCOcDmneX.jpg",
            8 to "https://s4.anilist.co/file/anilistcdn/media/anime/banner/n9253-JIhmKgBKsWUN.jpg",
            9 to "https://s4.anilist.co/file/anilistcdn/media/anime/banner/11061-8WkkTZ6duKpq.jpg",
            10 to "https://s4.anilist.co/file/anilistcdn/media/anime/banner/21-wf37VakJmZqs.jpg"
        )

        val descriptions = mapOf(
            1 to "After the party of heroes defeated the Demon King, they restored peace to the land and returned to their lives of solitude. Generations pass, and the elven mage Frieren is faced with the mortality of her friends. To better understand the humans she once fought alongside, she embarks on a new journey toward the north, seeking a way to speak with the souls of the departed.",
            2 to "The second season explores the past of the 'strongest' sorcerer, Satoru Gojo, during his time at Tokyo Jujutsu High School with his friend Suguru Geto. It then transitions to the present day for the harrowing Shibuya Incident, where the world of sorcery is pushed to its absolute limits in a battle that will change the fate of Japan forever.",
            3 to "Maomao, a young apothecary from the red-light district, is kidnapped and sold into service at the Emperor's palace. Her curiosity and medical knowledge lead her to solve a series of mysteries involving the palace's higher-ups, including the Emperor's children. Her talents eventually catch the eye of the handsome and influential eunuch Jinshi, who enlists her help for even greater intrigues.",
            4 to "In a world where 'Hunters' must battle monsters in dungeons to prevent humanity's extinction, Sung Jinwoo is known as the 'Weakest Hunter of All Mankind.' After a near-fatal encounter in a secret double dungeon, he awakens with access to a mysterious 'System' that only he can see, allowing him to level up his strength and abilities beyond all known limits.",
            5 to "The Forger family embarks on a winter vacation that takes a dangerous turn. While Loid is focused on his mission and Yor maintains her secret identity as an assassin, Anya's telepathic abilities lead her into a situation involving a mysterious chocolate and a threat to world peace. The first-ever movie adaptation of the hit series brings action, heart, and humor to the big screen.",
            6 to "Brothers Edward and Alphonse Elric seek the Philosopher's Stone to restore their bodies after a failed attempt at human transmutation. Their journey as State Alchemists reveals a deep-seated conspiracy involving the highest levels of the military and the very nature of alchemy itself. Unlike the original series, Brotherhood is a faithful adaptation of the entire manga.",
            7 to "Humanity lives within cities surrounded by enormous walls that protect them from the Titans, gigantic humanoid creatures who eat humans. When the Colossal Titan breaches the outermost wall, Eren Yeager's world is shattered, leading him to join the Survey Corps. The series evolves from a simple survival story into a complex geopolitical drama with deep philosophical questions.",
            8 to "Self-proclaimed mad scientist Rintaro Okabe discovers a way to send messages to the past using a microwave oven. What starts as a series of experiments among friends quickly spirals into a dark thriller involving a global conspiracy, time paradoxes, and the weight of making life-or-death decisions that alter the course of history.",
            9 to "Gon Freecss discovers that his father, who abandoned him at a young age, is actually a world-renowned Hunter. To find him, Gon decides to take the Hunter Exam, meeting several friends along the way. The series is celebrated for its intricate power system (Nen), complex character motivations, and subversion of typical shonen tropes.",
            10 to "Monkey D. Luffy, a young man inspired by his childhood idol 'Red-Haired' Shanks, sets off on a journey from the East Blue Sea to find the legendary treasure, One Piece, and proclaim himself the King of the Pirates. Along the way, he gathers a diverse crew and explores a world of endless adventure, mystery, and overarching themes of freedom."
        )

        val castData = mapOf(
            1 to listOf(
                Character(201, "Frieren", "https://cdn.myanimelist.net/images/characters/11/522513.jpg", "Main"),
                Character(202, "Fern", "https://cdn.myanimelist.net/images/characters/16/522522.jpg", "Main"),
                Character(203, "Stark", "https://cdn.myanimelist.net/images/characters/11/522617.jpg", "Main"),
                Character(204, "Himmel", "https://cdn.myanimelist.net/images/characters/12/522511.jpg", "Supporting")
            ),
            2 to listOf(
                Character(301, "Yuji Itadori", "https://cdn.myanimelist.net/images/characters/16/410815.jpg", "Main"),
                Character(302, "Satoru Gojo", "https://cdn.myanimelist.net/images/characters/13/410819.jpg", "Main"),
                Character(303, "Megumi Fushiguro", "https://cdn.myanimelist.net/images/characters/13/410816.jpg", "Main"),
                Character(304, "Suguru Geto", "https://cdn.myanimelist.net/images/characters/11/519349.jpg", "Supporting")
            ),
            3 to listOf(
                Character(305, "Maomao", "https://cdn.myanimelist.net/images/characters/16/522621.jpg", "Main"),
                Character(306, "Jinshi", "https://cdn.myanimelist.net/images/characters/4/522622.jpg", "Main"),
                Character(307, "Gaoshun", "https://cdn.myanimelist.net/images/characters/13/530268.jpg", "Supporting")
            ),
            4 to listOf(
                Character(401, "Sung Jinwoo", "https://cdn.myanimelist.net/images/characters/11/484435.jpg", "Main"),
                Character(402, "Cha Hae-In", "https://cdn.myanimelist.net/images/characters/11/484436.jpg", "Supporting"),
                Character(403, "Choi Jong-In", "https://cdn.myanimelist.net/images/characters/15/484440.jpg", "Supporting")
            ),
            5 to listOf(
                Character(501, "Loid Forger", "https://cdn.myanimelist.net/images/characters/16/475141.jpg", "Main"),
                Character(502, "Yor Forger", "https://cdn.myanimelist.net/images/characters/7/472856.jpg", "Main"),
                Character(503, "Anya Forger", "https://cdn.myanimelist.net/images/characters/5/472857.jpg", "Main")
            ),
            6 to listOf(
                Character(601, "Edward Elric", "https://cdn.myanimelist.net/images/characters/9/72451.jpg", "Main"),
                Character(602, "Alphonse Elric", "https://cdn.myanimelist.net/images/characters/5/54233.jpg", "Main"),
                Character(603, "Roy Mustang", "https://cdn.myanimelist.net/images/characters/14/74325.jpg", "Supporting")
            ),
            7 to listOf(
                Character(701, "Eren Yeager", "https://cdn.myanimelist.net/images/characters/10/216865.jpg", "Main"),
                Character(702, "Mikasa Ackerman", "https://cdn.myanimelist.net/images/characters/9/215513.jpg", "Main"),
                Character(703, "Armin Arlert", "https://cdn.myanimelist.net/images/characters/11/216839.jpg", "Main")
            ),
            8 to listOf(
                Character(801, "Rintaro Okabe", "https://cdn.myanimelist.net/images/characters/12/115591.jpg", "Main"),
                Character(802, "Kurisu Makise", "https://cdn.myanimelist.net/images/characters/12/115593.jpg", "Main"),
                Character(803, "Mayuri Shiina", "https://cdn.myanimelist.net/images/characters/6/102871.jpg", "Main")
            ),
            9 to listOf(
                Character(901, "Gon Freecss", "https://cdn.myanimelist.net/images/characters/11/174543.jpg", "Main"),
                Character(902, "Killua Zoldyck", "https://cdn.myanimelist.net/images/characters/15/144671.jpg", "Main"),
                Character(903, "Kurapika", "https://cdn.myanimelist.net/images/characters/13/144675.jpg", "Main")
            ),
            10 to listOf(
                Character(1001, "Monkey D. Luffy", "https://cdn.myanimelist.net/images/characters/9/310307.jpg", "Main"),
                Character(1002, "Roronoa Zoro", "https://cdn.myanimelist.net/images/characters/3/131317.jpg", "Main"),
                Character(1003, "Nami", "https://cdn.myanimelist.net/images/characters/2/263249.jpg", "Main")
            )
        )

        return AnimeDetails(
            id = summary.id,
            title = summary.title.replace("Mock: ", ""),
            coverImageUrl = summary.coverImageUrl,
            bannerImageUrl = banners[summary.id] ?: "https://picsum.photos/seed/${summary.id}banner/1200/400",
            description = descriptions[summary.id] ?: "This is a detailed description for **${summary.title}**. \n\n Experience the journey of characters in a world of fantasy and adventure.",
            averageScore = summary.averageScore,
            ratingString = "PG-13",
            language = "Japanese",
            durationString = summary.durationString,
            genres = summary.genres,
            cast = castData[summary.id] ?: listOf(
                Character(101, "Main Lead", "https://picsum.photos/seed/pc1/200/200", "Main"),
                Character(102, "Sidekick", "https://picsum.photos/seed/pc2/200/200", "Supporting")
            )
        )
    }
}
