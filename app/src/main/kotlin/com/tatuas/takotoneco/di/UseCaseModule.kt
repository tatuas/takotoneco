package com.tatuas.takotoneco.di

import com.tatuas.takotoneco.data.logger.ErrorLogger
import com.tatuas.takotoneco.repogitory.GitHubRepository
import com.tatuas.takotoneco.usecase.UserDetailUseCase
import com.tatuas.takotoneco.usecase.UserListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideUserListUseCase(
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        gitHubRepository: GitHubRepository,
        errorLogger: ErrorLogger,
    ): UserListUseCase {
        return UserListUseCase(
            coroutineDispatcher,
            gitHubRepository,
            errorLogger,
        )
    }

    @Provides
    fun provideUserDetailUseCase(
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        gitHubRepository: GitHubRepository,
        errorLogger: ErrorLogger,
    ): UserDetailUseCase {
        return UserDetailUseCase(
            coroutineDispatcher,
            gitHubRepository,
            errorLogger,
        )
    }
}
