package com.anilist.aniexplorer.domain.usecase

import com.anilist.aniexplorer.domain.Resource
import com.anilist.aniexplorer.domain.model.HomeSection
import com.anilist.aniexplorer.domain.repository.AnimeRepository

class GetHomeSectionsUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(): Resource<List<HomeSection>> = repository.getHomeSections()
}
