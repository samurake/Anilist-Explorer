package com.anilist.aniexplorer.data.mapper

import com.anilist.aniexplorer.graphql.GetAnimeDetailsQuery
import com.anilist.aniexplorer.graphql.GetHomeSectionsQuery
import com.anilist.aniexplorer.graphql.type.CharacterRole
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AnimeMapperTest {

    @Test
    fun `home mapper prefers english title and formats score duration and genres`() {
        val model = GetHomeSectionsQuery.Medium(
            id = 5,
            title = GetHomeSectionsQuery.Title(
                romaji = "Sousou no Frieren",
                english = "Frieren: Beyond Journey's End"
            ),
            coverImage = GetHomeSectionsQuery.CoverImage(large = "https://example.com/frieren.jpg"),
            averageScore = 94,
            genres = listOf("Adventure", "Drama"),
            duration = 24
        )

        val mapped = model.toDomainModel()

        assertEquals(5, mapped.id)
        assertEquals("Frieren: Beyond Journey's End", mapped.title)
        assertEquals(9.4, mapped.averageScore)
        assertEquals("0h 24min", mapped.durationString)
        assertEquals(listOf("Adventure", "Drama"), mapped.genres.map { it.name })
    }

    @Test
    fun `home mapper falls back when optional fields are missing`() {
        val model = GetHomeSectionsQuery.Medium1(
            id = 8,
            title = GetHomeSectionsQuery.Title1(romaji = null, english = null),
            coverImage = GetHomeSectionsQuery.CoverImage1(large = null),
            averageScore = null,
            genres = null,
            duration = null
        )

        val mapped = model.toDomainModel()

        assertEquals("Unknown Title", mapped.title)
        assertNull(mapped.coverImageUrl)
        assertNull(mapped.averageScore)
        assertEquals("Unknown", mapped.durationString)
        assertEquals(emptyList<String>(), mapped.genres.map { it.name })
    }

    @Test
    fun `details mapper builds ui ready details including cast`() {
        val model = GetAnimeDetailsQuery.Media(
            id = 9,
            title = GetAnimeDetailsQuery.Title(
                romaji = "Hagane no Renkinjutsushi",
                english = "Fullmetal Alchemist: Brotherhood"
            ),
            coverImage = GetAnimeDetailsQuery.CoverImage(large = "https://example.com/fmab.jpg"),
            bannerImage = "https://example.com/banner.jpg",
            description = "Alchemy, sacrifice, and consequences.",
            averageScore = 91,
            isAdult = false,
            format = null,
            status = null,
            duration = 24,
            genres = listOf("Action", "Adventure"),
            characters = GetAnimeDetailsQuery.Characters(
                edges = listOf(
                    GetAnimeDetailsQuery.Edge(
                        role = CharacterRole.MAIN,
                        node = GetAnimeDetailsQuery.Node(
                            id = 100,
                            name = GetAnimeDetailsQuery.Name(full = "Edward Elric"),
                            image = GetAnimeDetailsQuery.Image(large = "https://example.com/ed.jpg")
                        )
                    )
                )
            )
        )

        val mapped = model.toDomainModel()

        assertEquals("Fullmetal Alchemist: Brotherhood", mapped.title)
        assertEquals(9.1, mapped.averageScore)
        assertEquals("PG-13", mapped.ratingString)
        assertEquals("Japanese", mapped.language)
        assertEquals("0h 24min", mapped.durationString)
        assertEquals(listOf("Action", "Adventure"), mapped.genres.map { it.name })
        assertEquals("Edward Elric", mapped.cast.single().name)
        assertEquals("MAIN", mapped.cast.single().role)
    }

    @Test
    fun `edge mapper returns null when node is missing`() {
        val edge = GetAnimeDetailsQuery.Edge(
            role = CharacterRole.SUPPORTING,
            node = null
        )

        assertNull(edge.toDomainModel())
    }
}
