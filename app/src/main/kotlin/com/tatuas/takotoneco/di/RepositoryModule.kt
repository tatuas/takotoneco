package com.tatuas.takotoneco.di

import com.tatuas.takotoneco.data.api.GitHubService
import com.tatuas.takotoneco.repogitory.GitHubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideGitHubRepository(
        gitHubService: GitHubService,
    ) = GitHubRepository(
        gitHubService,
    )
}