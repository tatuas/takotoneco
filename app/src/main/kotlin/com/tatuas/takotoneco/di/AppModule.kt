package com.tatuas.takotoneco.di

import com.tatuas.takotoneco.data.logger.AnalyticsLogger
import com.tatuas.takotoneco.data.logger.ErrorLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @Singleton
    @Provides
    fun provideAnalyticsLogger(): AnalyticsLogger {
        return AnalyticsLogger()
    }

    @Singleton
    @Provides
    fun provideErrorLogger(): ErrorLogger {
        return ErrorLogger()
    }
}
