package com.anilist.aniexplorer.data.di

import com.anilist.aniexplorer.data.repository.AnimeRepositoryImpl
import com.anilist.aniexplorer.domain.repository.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAnimeRepository(
        animeRepository: AnimeRepositoryImpl
    ): AnimeRepository = animeRepository
}
