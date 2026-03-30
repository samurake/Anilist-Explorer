package com.anilist.aniexplorer.model.usecase

import com.anilist.aniexplorer.model.Resource
import com.anilist.aniexplorer.model.domain.HomeSection
import com.anilist.aniexplorer.model.repository.AnimeRepository

class GetHomeSectionsUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(): Resource<List<HomeSection>> = repository.getHomeSections()
}
