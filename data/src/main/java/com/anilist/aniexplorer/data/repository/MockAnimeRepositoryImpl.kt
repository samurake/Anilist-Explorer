package com.anilist.aniexplorer.data.repository

import com.anilist.aniexplorer.domain.Resource
import com.anilist.aniexplorer.domain.model.AnimeDetails
import com.anilist.aniexplorer.domain.model.HomeSection
import com.anilist.aniexplorer.domain.repository.AnimeRepository
import kotlinx.coroutines.delay
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Named

class MockAnimeRepositoryImpl @Inject constructor(
    @Named("shouldMockHttpError") private val shouldMockHttpError: Boolean,
    @Named("shouldMockNetworkException") private val shouldMockNetworkException: Boolean
) : AnimeRepository {

    override suspend fun getHomeSections(): Resource<List<HomeSection>> {
        if (shouldMockNetworkException) {
            throw UnknownHostException("Unable to resolve host \"graphql.anilist.co\": No address associated with hostname")
        }
        if (shouldMockHttpError) {
            return Resource.Error("HTTP 500 Internal Server Error")
        }

        // Simulate network delay
        delay(800)
        
        val sections = listOf(
            HomeSection(
                title = "Now Showing",
                type = HomeSection.SectionType.NOW_SHOWING,
                animes = MockData.trendingAnimes
            ),
            HomeSection(
                title = "Popular",
                type = HomeSection.SectionType.POPULAR,
                animes = MockData.popularAnimes
            )
        )
        return Resource.Success(sections)
    }

    override suspend fun getAnimeDetails(id: Int): Resource<AnimeDetails> {
        if (shouldMockNetworkException) {
            throw UnknownHostException("Unable to resolve host \"graphql.anilist.co\": No address associated with hostname")
        }
        if (shouldMockHttpError) {
            return Resource.Error("HTTP 500 Internal Server Error")
        }

        // Simulate network delay
        delay(500)
        
        val details = MockData.getMockDetails(id)
        return Resource.Success(details)
    }
}
