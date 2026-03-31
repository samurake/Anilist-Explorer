package com.anilist.aniexplorer.data.repository

import com.anilist.aniexplorer.domain.Resource
import com.apollographql.apollo.ApolloClient
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AnimeRepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var repository: AnimeRepositoryImpl

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val apolloClient = ApolloClient.Builder()
            .serverUrl(mockWebServer.url("/").toString())
            .build()

        repository = AnimeRepositoryImpl(
            apolloClientLazy = object : Lazy<ApolloClient> {
                override fun get(): ApolloClient = apolloClient
            },
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getHomeSections returns mapped sections on success`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
                {
                  "data": {
                    "trending": {
                      "media": [
                        {
                          "id": 1,
                          "title": { "english": "Frieren: Beyond Journey's End", "romaji": "Sousou no Frieren" },
                          "coverImage": { "large": "https://example.com/frieren.jpg" },
                          "averageScore": 94,
                          "genres": ["Adventure", "Drama"],
                          "duration": 24
                        }
                      ]
                    },
                    "popular": {
                      "media": [
                        {
                          "id": 2,
                          "title": { "english": "Fullmetal Alchemist: Brotherhood", "romaji": "Hagane no Renkinjutsushi" },
                          "coverImage": { "large": "https://example.com/fmab.jpg" },
                          "averageScore": 91,
                          "genres": ["Action"],
                          "duration": 24
                        }
                      ]
                    }
                  }
                }
                """.trimIndent()
            )
        )

        val result = repository.getHomeSections()

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals(listOf("Now Showing", "Popular"), data.map { it.title })
        assertEquals("Frieren: Beyond Journey's End", data.first().animes.single().title)
        assertEquals("Fullmetal Alchemist: Brotherhood", data.last().animes.single().title)
    }

    @Test
    fun `getHomeSections returns graphql error message when response has errors`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
                {
                  "errors": [
                    { "message": "Home query failed" }
                  ],
                  "data": null
                }
                """.trimIndent()
            )
        )

        val result = repository.getHomeSections()

        assertEquals(Resource.Error("Home query failed"), result)
    }

    @Test
    fun `getAnimeDetails returns mapped details on success`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
                {
                  "data": {
                    "Media": {
                      "id": 7,
                      "title": { "english": "Attack on Titan", "romaji": "Shingeki no Kyojin" },
                      "coverImage": { "large": "https://example.com/aot-cover.jpg" },
                      "bannerImage": "https://example.com/aot-banner.jpg",
                      "description": "Humanity fights back.",
                      "averageScore": 85,
                      "isAdult": false,
                      "duration": 24,
                      "genres": ["Action", "Drama"],
                      "characters": {
                        "edges": [
                          {
                            "role": "MAIN",
                            "node": {
                              "id": 77,
                              "name": { "full": "Eren Yeager" },
                              "image": { "large": "https://example.com/eren.jpg" }
                            }
                          }
                        ]
                      }
                    }
                  }
                }
                """.trimIndent()
            )
        )

        val result = repository.getAnimeDetails(7)

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals("Attack on Titan", data.title)
        assertEquals(8.5, data.averageScore)
        assertEquals("Eren Yeager", data.cast.single().name)
    }
}
