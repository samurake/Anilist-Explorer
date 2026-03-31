package com.anilist.aniexplorer

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.anilist.aniexplorer.graphql.mock.MockServerManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltAndroidApp
class AnilistApplication : Application(), ImageLoaderFactory {
    
    @Inject
    lateinit var mockServerManager: MockServerManager

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.IS_MOCK) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    mockServerManager.getUrl()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .okHttpClient(okHttpClient)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25) // Use 25% of the app's available memory.
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02) // Use 2% of the device's disk space.
                    .build()
            }
            .crossfade(true)
            .build()
    }
}
