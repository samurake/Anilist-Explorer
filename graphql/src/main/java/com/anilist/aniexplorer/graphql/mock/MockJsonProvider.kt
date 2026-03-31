package com.anilist.aniexplorer.graphql.mock

import org.json.JSONArray
import org.json.JSONObject

object MockJsonProvider {

    private val banners = mapOf(
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

    private val descriptions = mapOf(
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

    private val castData = mapOf(
        1 to listOf(
            Triple("Frieren", "https://cdn.myanimelist.net/images/characters/11/522513.jpg", "MAIN"),
            Triple("Fern", "https://cdn.myanimelist.net/images/characters/16/522522.jpg", "MAIN"),
            Triple("Stark", "https://cdn.myanimelist.net/images/characters/11/522617.jpg", "MAIN"),
            Triple("Himmel", "https://cdn.myanimelist.net/images/characters/12/522511.jpg", "SUPPORTING")
        ),
        2 to listOf(
            Triple("Yuji Itadori", "https://cdn.myanimelist.net/images/characters/16/410815.jpg", "MAIN"),
            Triple("Satoru Gojo", "https://cdn.myanimelist.net/images/characters/13/410819.jpg", "MAIN"),
            Triple("Megumi Fushiguro", "https://cdn.myanimelist.net/images/characters/13/410816.jpg", "MAIN"),
            Triple("Suguru Geto", "https://cdn.myanimelist.net/images/characters/11/519349.jpg", "SUPPORTING")
        ),
        3 to listOf(
            Triple("Maomao", "https://cdn.myanimelist.net/images/characters/16/522621.jpg", "MAIN"),
            Triple("Jinshi", "https://cdn.myanimelist.net/images/characters/4/522622.jpg", "MAIN"),
            Triple("Gaoshun", "https://cdn.myanimelist.net/images/characters/13/530268.jpg", "SUPPORTING")
        ),
        4 to listOf(
            Triple("Sung Jinwoo", "https://cdn.myanimelist.net/images/characters/11/484435.jpg", "MAIN"),
            Triple("Cha Hae-In", "https://cdn.myanimelist.net/images/characters/11/484436.jpg", "SUPPORTING"),
            Triple("Choi Jong-In", "https://cdn.myanimelist.net/images/characters/15/484440.jpg", "SUPPORTING")
        ),
        5 to listOf(
            Triple("Loid Forger", "https://cdn.myanimelist.net/images/characters/16/475141.jpg", "MAIN"),
            Triple("Yor Forger", "https://cdn.myanimelist.net/images/characters/7/472856.jpg", "MAIN"),
            Triple("Anya Forger", "https://cdn.myanimelist.net/images/characters/5/472857.jpg", "MAIN")
        ),
        6 to listOf(
            Triple("Edward Elric", "https://cdn.myanimelist.net/images/characters/9/72451.jpg", "MAIN"),
            Triple("Alphonse Elric", "https://cdn.myanimelist.net/images/characters/5/54233.jpg", "MAIN"),
            Triple("Roy Mustang", "https://cdn.myanimelist.net/images/characters/14/74325.jpg", "SUPPORTING")
        ),
        7 to listOf(
            Triple("Eren Yeager", "https://cdn.myanimelist.net/images/characters/10/216865.jpg", "MAIN"),
            Triple("Mikasa Ackerman", "https://cdn.myanimelist.net/images/characters/9/215513.jpg", "MAIN"),
            Triple("Armin Arlert", "https://cdn.myanimelist.net/images/characters/11/216839.jpg", "MAIN")
        ),
        8 to listOf(
            Triple("Rintaro Okabe", "https://cdn.myanimelist.net/images/characters/12/115591.jpg", "MAIN"),
            Triple("Kurisu Makise", "https://cdn.myanimelist.net/images/characters/12/115593.jpg", "MAIN"),
            Triple("Mayuri Shiina", "https://cdn.myanimelist.net/images/characters/6/102871.jpg", "MAIN")
        ),
        9 to listOf(
            Triple("Gon Freecss", "https://cdn.myanimelist.net/images/characters/11/174543.jpg", "MAIN"),
            Triple("Killua Zoldyck", "https://cdn.myanimelist.net/images/characters/15/144671.jpg", "MAIN"),
            Triple("Kurapika", "https://cdn.myanimelist.net/images/characters/13/144675.jpg", "MAIN")
        ),
        10 to listOf(
            Triple("Monkey D. Luffy", "https://cdn.myanimelist.net/images/characters/9/310307.jpg", "MAIN"),
            Triple("Roronoa Zoro", "https://cdn.myanimelist.net/images/characters/3/131317.jpg", "MAIN"),
            Triple("Nami", "https://cdn.myanimelist.net/images/characters/2/263249.jpg", "MAIN")
        )
    )

    private val genresMap = mapOf(
        1 to listOf("Adventure", "Drama", "Fantasy"),
        2 to listOf("Action", "Fantasy"),
        3 to listOf("Drama", "Mystery"),
        4 to listOf("Action", "Adventure", "Fantasy"),
        5 to listOf("Action", "Comedy"),
        6 to listOf("Action", "Adventure", "Drama"),
        7 to listOf("Action", "Drama", "Suspense"),
        8 to listOf("Drama", "Sci-Fi", "Suspense"),
        9 to listOf("Action", "Adventure", "Fantasy"),
        10 to listOf("Action", "Adventure", "Fantasy")
    )

    val HOME_JSON = JSONObject().apply {
        put("data", JSONObject().apply {
            put("trending", JSONObject().apply {
                put("media", JSONArray().apply {
                    put(createAnimeJson(1, "Frieren: Beyond Journey's End", "https://cdn.myanimelist.net/images/anime/1015/138064.jpg", 9.4))
                    put(createAnimeJson(2, "Jujutsu Kaisen 2nd Season", "https://cdn.myanimelist.net/images/anime/1792/138022.jpg", 8.8))
                    put(createAnimeJson(3, "Apothecary Diaries", "https://cdn.myanimelist.net/images/anime/1708/138033.jpg", 8.6))
                    put(createAnimeJson(4, "Solo Leveling", "https://cdn.myanimelist.net/images/anime/1169/138096.jpg", 8.5))
                    put(createAnimeJson(5, "SPY x FAMILY Code: White", "https://cdn.myanimelist.net/images/anime/1569/138006.jpg", 8.2))
                })
            })
            put("popular", JSONObject().apply {
                put("media", JSONArray().apply {
                    put(createAnimeJson(6, "Fullmetal Alchemist: Brotherhood", "https://cdn.myanimelist.net/images/anime/1223/96541.jpg", 9.1))
                    put(createAnimeJson(7, "Attack on Titan", "https://cdn.myanimelist.net/images/anime/10/47347.jpg", 8.5))
                    put(createAnimeJson(8, "Steins;Gate", "https://cdn.myanimelist.net/images/anime/1935/127923.jpg", 9.0))
                    put(createAnimeJson(9, "Hunter x Hunter (2011)", "https://cdn.myanimelist.net/images/anime/1337/99013.jpg", 9.0))
                    put(createAnimeJson(10, "One Piece", "https://cdn.myanimelist.net/images/anime/6/73245.jpg", 8.7))
                })
            })
        })
    }.toString()

    fun getDetailsJson(id: Int): String {
        return JSONObject().apply {
            put("data", JSONObject().apply {
                put("Media", createFullAnimeJson(id))
            })
        }.toString()
    }

    private fun createAnimeJson(id: Int, title: String, cover: String, score: Double) = JSONObject().apply {
        put("id", id)
        put("title", JSONObject().apply {
            put("english", title)
            put("romaji", title)
        })
        put("coverImage", JSONObject().apply {
            put("large", cover)
        })
        put("averageScore", (score * 10).toInt())
        put("duration", 24)
        put("genres", JSONArray(genresMap[id] ?: listOf("Action", "Adventure", "Fantasy")))
    }

    private fun createFullAnimeJson(id: Int) = JSONObject().apply {
        val baseTitle = when (id) {
            1 -> "Frieren: Beyond Journey's End"
            2 -> "Jujutsu Kaisen 2nd Season"
            3 -> "Apothecary Diaries"
            4 -> "Solo Leveling"
            5 -> "SPY x FAMILY Code: White"
            6 -> "Fullmetal Alchemist: Brotherhood"
            7 -> "Attack on Titan"
            8 -> "Steins;Gate"
            9 -> "Hunter x Hunter (2011)"
            10 -> "One Piece"
            else -> "Mock Anime $id"
        }
        
        put("id", id)
        put("title", JSONObject().apply {
            put("english", baseTitle)
            put("romaji", baseTitle)
        })
        val coverImages = mapOf(
            1 to "https://cdn.myanimelist.net/images/anime/1015/138064.jpg",
            2 to "https://cdn.myanimelist.net/images/anime/1792/138022.jpg",
            3 to "https://cdn.myanimelist.net/images/anime/1708/138033.jpg",
            4 to "https://cdn.myanimelist.net/images/anime/1169/138096.jpg",
            5 to "https://cdn.myanimelist.net/images/anime/1569/138006.jpg",
            6 to "https://cdn.myanimelist.net/images/anime/1223/96541.jpg",
            7 to "https://cdn.myanimelist.net/images/anime/10/47347.jpg",
            8 to "https://cdn.myanimelist.net/images/anime/1935/127923.jpg",
            9 to "https://cdn.myanimelist.net/images/anime/1337/99013.jpg",
            10 to "https://cdn.myanimelist.net/images/anime/6/73245.jpg"
        )
        
        put("coverImage", JSONObject().apply {
            put("large", coverImages[id] ?: "https://picsum.photos/seed/$id/400/600")
        })
        
        put("bannerImage", banners[id] ?: "https://picsum.photos/seed/${id}banner/1200/400")
        put("description", descriptions[id] ?: "This is a **mock description** for anime $id. It works via a real GraphQL request handled by a local MockServer.")
        put("averageScore", 85)
        put("genres", JSONArray(listOf("Action", "Drama", "Fantasy")))
        put("duration", 24)
        put("episodes", 12)
        put("status", "FINISHED")
        
        put("characters", JSONObject().apply {
            put("edges", JSONArray().apply {
                val characters = castData[id] ?: emptyList()
                characters.forEachIndexed { i, char ->
                    put(JSONObject().apply {
                        put("role", char.third)
                        put("node", JSONObject().apply {
                            put("id", id * 1000 + i)
                            put("name", JSONObject().apply { put("full", char.first) })
                            put("image", JSONObject().apply { put("large", char.second) })
                        })
                    })
                }
            })
        })
        
        put("trailer", JSONObject().apply {
            put("id", "dQw4w9WgXcQ")
            put("site", "youtube")
            put("thumbnail", "https://img.youtube.com/vi/dQw4w9WgXcQ/maxresdefault.jpg")
        })
    }
}
