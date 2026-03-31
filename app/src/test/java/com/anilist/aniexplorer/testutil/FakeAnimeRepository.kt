package com.anilist.aniexplorer.testutil

import com.anilist.aniexplorer.domain.Resource
import com.anilist.aniexplorer.domain.model.AnimeDetails
import com.anilist.aniexplorer.domain.model.HomeSection
import com.anilist.aniexplorer.domain.repository.AnimeRepository
import java.util.ArrayDeque

class FakeAnimeRepository(
    homeResults: List<Resource<List<HomeSection>>> = emptyList(),
    detailsResults: List<Resource<AnimeDetails>> = emptyList()
) : AnimeRepository {

    private val homeQueue = ArrayDeque(homeResults)
    private val detailsQueue = ArrayDeque(detailsResults)

    val requestedAnimeIds = mutableListOf<Int>()

    override suspend fun getHomeSections(): Resource<List<HomeSection>> {
        check(homeQueue.isNotEmpty()) { "No home result queued for test" }
        return homeQueue.removeFirst()
    }

    override suspend fun getAnimeDetails(id: Int): Resource<AnimeDetails> {
        requestedAnimeIds += id
        check(detailsQueue.isNotEmpty()) { "No details result queued for test" }
        return detailsQueue.removeFirst()
    }
}
