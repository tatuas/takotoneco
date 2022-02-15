package com.tatuas.takotoneco.usecase

import com.tatuas.takotoneco.R
import com.tatuas.takotoneco.data.logger.ErrorLogger
import com.tatuas.takotoneco.ext.toStringResId
import com.tatuas.takotoneco.model.LoadState
import com.tatuas.takotoneco.model.UserDetail
import com.tatuas.takotoneco.model.UserItem
import com.tatuas.takotoneco.repogitory.GitHubRepository
import com.tatuas.takotoneco.view.AndroidString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserDetailUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: GitHubRepository,
    private val errorLogger: ErrorLogger,
) {
    fun getDetail(user: UserItem.Data?): Flow<LoadState<UserDetail>> = flow {
        emit(LoadState.Loading)

        if (user == null) {
            emit(LoadState.Failure(AndroidString.Resource(R.string.error_general)))
            return@flow
        }

        val response = repository.getUserDetail(user.login)

        emit(
            LoadState.Success(
                UserDetail(
                    name = response.name,
                    login = response.login,
                    iconUrl = response.avatarUrl,
                    followers = response.followers,
                    follows = response.following,
                    createdAt = response.createdAt
                )
            )
        )
    }
        .catch { throwable ->
            errorLogger.send(throwable)

            emit(
                LoadState.Failure(
                    AndroidString.Resource(
                        throwable.toStringResId()
                    )
                )
            )
        }
        .flowOn(dispatcher)
}
