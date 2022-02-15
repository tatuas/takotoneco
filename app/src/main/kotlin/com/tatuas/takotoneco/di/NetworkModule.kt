package com.tatuas.takotoneco.di

import android.content.Context
import coil.ImageLoader
import coil.util.CoilUtils
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tatuas.takotoneco.BuildConfig
import com.tatuas.takotoneco.data.api.GitHubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideJsonConverterFactory(json: Json): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @Singleton
    @Provides
    fun provideImageLoader(
        @ApplicationContext context: Context,
        @CoilOkHttpClient okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .availableMemoryPercentage(0.25)
            .okHttpClient { okHttpClient }
            .build()
    }

    @Singleton
    @Provides
    fun provideGitHubLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }
    }

    @CoilOkHttpClient
    @Singleton
    @Provides
    fun provideCoilOkHttpClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(CoilUtils.createDefaultCache(context))
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @GitHubOkHttpClient
    @Singleton
    @Provides
    fun provideGitHubOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("Accept", "application/vnd.github.v3+json")
                    .build()

                it.proceed(request)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGitHubService(
        @GitHubOkHttpClient client: OkHttpClient,
        jsonConverterFactory: Converter.Factory,
    ): GitHubService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(jsonConverterFactory)
            .client(client)
            .build()
            .create(GitHubService::class.java)
    }
}
