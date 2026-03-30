package com.anilist.aniexplorer.data.di

import com.anilist.aniexplorer.data.repository.AnimeRepositoryImpl
import com.anilist.aniexplorer.data.repository.MockAnimeRepositoryImpl
import com.anilist.aniexplorer.domain.repository.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAnimeRepository(
        @Named("isMock") isMock: Boolean,
        realRepository: AnimeRepositoryImpl,
        mockRepository: MockAnimeRepositoryImpl
    ): AnimeRepository {
        return if (isMock) {
            mockRepository
        } else {
            realRepository
        }
    }
}
