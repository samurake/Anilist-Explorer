package com.anilist.aniexplorer.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.exception.ApolloException
import com.anilist.aniexplorer.domain.Resource
import com.anilist.aniexplorer.domain.model.AnimeDetails
import com.anilist.aniexplorer.domain.model.HomeSection
import com.anilist.aniexplorer.domain.repository.AnimeRepository
import com.anilist.aniexplorer.graphql.GetAnimeDetailsQuery
import com.anilist.aniexplorer.graphql.GetHomeSectionsQuery
import com.anilist.aniexplorer.graphql.di.IoDispatcher
import com.anilist.aniexplorer.data.mapper.toDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AnimeRepository {

    override suspend fun getHomeSections(): Resource<List<HomeSection>> {
        return withContext(ioDispatcher) {
            try {
                val response = apolloClient.query(GetHomeSectionsQuery()).execute()

                if (response.hasErrors()) {
                    val message = response.errors?.firstOrNull()?.message ?: "Unknown GraphQL error"
                    return@withContext Resource.Error(message)
                }

                val data = response.data
                    ?: return@withContext Resource.Error("No data returned from server")

                val trendingAnimes = data.trending?.media?.mapNotNull { it?.toDomainModel() } ?: emptyList()
                val popularAnimes = data.popular?.media?.mapNotNull { it?.toDomainModel() } ?: emptyList()

                val sections = listOf(
                    HomeSection(
                        title = "Now Showing",
                        type = HomeSection.SectionType.NOW_SHOWING,
                        animes = trendingAnimes
                    ),
                    HomeSection(
                        title = "Popular",
                        type = HomeSection.SectionType.POPULAR,
                        animes = popularAnimes
                    )
                )

                Resource.Success(sections)
            } catch (e: ApolloException) {
                Resource.Error(
                    message = e.message ?: "Network error occurred",
                    exception = e
                )
            } catch (e: Exception) {
                Resource.Error(
                    message = e.message ?: "An unexpected error occurred",
                    exception = e
                )
            }
        }
    }

    override suspend fun getAnimeDetails(id: Int): Resource<AnimeDetails> {
        return withContext(ioDispatcher) {
            try {
                val response = apolloClient
                    .query(GetAnimeDetailsQuery(id = Optional.present(id)))
                    .execute()

                if (response.hasErrors()) {
                    val message = response.errors?.firstOrNull()?.message ?: "Unknown GraphQL error"
                    return@withContext Resource.Error(message)
                }

                val media = response.data?.Media
                    ?: return@withContext Resource.Error("Anime not found")

                Resource.Success(media.toDomainModel())
            } catch (e: ApolloException) {
                Resource.Error(
                    message = e.message ?: "Network error occurred",
                    exception = e
                )
            } catch (e: Exception) {
                Resource.Error(
                    message = e.message ?: "An unexpected error occurred",
                    exception = e
                )
            }
        }
    }
}
