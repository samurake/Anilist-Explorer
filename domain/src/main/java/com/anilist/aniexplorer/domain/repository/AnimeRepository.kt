package com.anilist.aniexplorer.domain.repository

import com.anilist.aniexplorer.domain.Resource
import com.anilist.aniexplorer.domain.model.AnimeDetails
import com.anilist.aniexplorer.domain.model.HomeSection

interface AnimeRepository {
    suspend fun getHomeSections(): Resource<List<HomeSection>>
    suspend fun getAnimeDetails(id: Int): Resource<AnimeDetails>
}
