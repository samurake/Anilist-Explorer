package com.anilist.aniexplorer.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    @Provides
    @Singleton
    @Named("isMock")
    fun provideIsMock(): Boolean = true // Default to true while API is down

    @Provides
    @Singleton
    @Named("shouldMockHttpError")
    fun provideShouldMockHttpError(): Boolean = false

    @Provides
    @Singleton
    @Named("shouldMockNetworkException")
    fun provideShouldMockNetworkException(): Boolean = false
}
