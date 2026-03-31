package com.anilist.aniexplorer.domain.usecase

import com.anilist.aniexplorer.domain.Resource
import com.anilist.aniexplorer.domain.model.AnimeDetails
import com.anilist.aniexplorer.domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimeDetailsUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(id: Int): Resource<AnimeDetails> = repository.getAnimeDetails(id)
}
