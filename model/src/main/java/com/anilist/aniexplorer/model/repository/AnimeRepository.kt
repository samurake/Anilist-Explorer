package com.anilist.aniexplorer.model.repository

import com.anilist.aniexplorer.model.Resource
import com.anilist.aniexplorer.model.domain.AnimeDetails
import com.anilist.aniexplorer.model.domain.HomeSection

interface AnimeRepository {
    suspend fun getHomeSections(): Resource<List<HomeSection>>
    suspend fun getAnimeDetails(id: Int): Resource<AnimeDetails>
}
