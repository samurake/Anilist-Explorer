package com.anilist.aniexplorer.model.usecase

import com.anilist.aniexplorer.model.Resource
import com.anilist.aniexplorer.model.domain.AnimeDetails
import com.anilist.aniexplorer.model.repository.AnimeRepository

class GetAnimeDetailsUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(id: Int): Resource<AnimeDetails> = repository.getAnimeDetails(id)
}
