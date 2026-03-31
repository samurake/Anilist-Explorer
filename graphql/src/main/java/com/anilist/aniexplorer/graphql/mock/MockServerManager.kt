package com.anilist.aniexplorer.graphql.mock

import com.anilist.aniexplorer.graphql.BuildConfig
import com.apollographql.mockserver.MockRequest
import com.apollographql.mockserver.MockRequestBase
import com.apollographql.mockserver.MockResponse
import com.apollographql.mockserver.MockServer
import com.apollographql.mockserver.MockServerHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockServerManager @Inject constructor() {
    private val shouldMockHttpError: Boolean = BuildConfig.SHOULD_MOCK_HTTP_ERROR
    private val shouldMockNetworkException: Boolean = BuildConfig.SHOULD_MOCK_NETWORK_EXCEPTION

    private val mockServer: MockServer by lazy {
        MockServer.Builder()
            .handler(object : MockServerHandler {
                override fun handle(request: MockRequestBase): MockResponse {
                    if (shouldMockNetworkException) {
                        return MockResponse.Builder()
                            .statusCode(503)
                            .build()
                    } else if (shouldMockHttpError) {
                        return MockResponse.Builder()
                            .statusCode(500)
                            .body("{\"errors\": [{\"message\": \"HTTP 500 Internal Server Error\"}]}")
                            .build()
                    } else {
                        val body = (request as? MockRequest)?.body?.utf8() ?: ""
                        return when {
                            body.contains("GetHomeSections") -> {
                                MockResponse.Builder()
                                    .body(MockJsonProvider.HOME_JSON)
                                    .statusCode(200)
                                    .addHeader("Content-Type", "application/json")
                                    .build()
                            }
                            body.contains("GetAnimeDetails") -> {
                                val idRegex = """id"[\s:]+(\d+)""".toRegex()
                                val match = idRegex.find(body)
                                val id = match?.groupValues?.get(1)?.toIntOrNull() ?: 1
                                
                                MockResponse.Builder()
                                    .body(MockJsonProvider.getDetailsJson(id))
                                    .statusCode(200)
                                    .addHeader("Content-Type", "application/json")
                                    .build()
                            }
                            else -> {
                                MockResponse.Builder()
                                    .body("{\"errors\": [{\"message\": \"Unknown query at mock level\"}]}")
                                    .statusCode(400)
                                    .build()
                            }
                        }
                    }
                }
            })
            .build()
    }

    private var _isStarted = false
    val isStarted: Boolean get() = _isStarted

    fun getUrl(): String = runBlocking(Dispatchers.IO) {
        _isStarted = true
        mockServer.url()
    }

    fun stop() {
        if (_isStarted) {
            runBlocking(Dispatchers.IO) {
                mockServer.close()
            }
            _isStarted = false
        }
    }
}
