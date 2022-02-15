package com.tatuas.takotoneco.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @IoDispatcher
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @ComputeDispatcher
    @Singleton
    @Provides
    fun provideComputeDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
