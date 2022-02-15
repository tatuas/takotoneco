package com.tatuas.takotoneco.usecase

import com.tatuas.takotoneco.data.api.structure.UserResponse
import com.tatuas.takotoneco.data.logger.ErrorLogger
import com.tatuas.takotoneco.ext.toStringResId
import com.tatuas.takotoneco.model.LoadState
import com.tatuas.takotoneco.model.UserItem
import com.tatuas.takotoneco.model.UserItemList
import com.tatuas.takotoneco.view.AndroidString
import com.tatuas.takotoneco.repogitory.GitHubRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserListUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: GitHubRepository,
    private val errorLogger: ErrorLogger,
) {
    fun getUserItemList(): Flow<LoadState<UserItemList>> = flow {
        emit(LoadState.Loading)

        val response = repository.getUserList()

        val list = response.toMutableUserItemDataList()

        val since = if (response.isNotEmpty()) {
            list.add(UserItem.Progress)

            response.last().id
        } else {
            null
        }

        emit(
            LoadState.Success(
                UserItemList(
                    list,
                    since,
                    UserItemList.PagingState.NONE,
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

    fun getNextUserItemList(userItemList: UserItemList): Flow<LoadState<UserItemList>> = flow {
        emit(
            LoadState.Success(
                userItemList.copy(
                    pagingState = UserItemList.PagingState.LOADING,
                )
            )
        )

        val response = repository.getUserList(userItemList.since)

        val resultList: MutableList<UserItem> = userItemList.list
            .filterIsInstance<UserItem.Data>()
            .toMutableList()

        resultList.addAll(response.toMutableUserItemDataList())

        val since = if (response.isNotEmpty()) {
            resultList.add(UserItem.Progress)
            response.last().id
        } else {
            null
        }

        emit(
            LoadState.Success(
                UserItemList(
                    resultList,
                    since,
                    UserItemList.PagingState.NONE,
                )
            )
        )
    }
        .catch { throwable ->
            errorLogger.send(throwable)

            val baseList = userItemList.list.filterIsInstance<UserItem.Data>()
            val list: MutableList<UserItem> = baseList.toMutableList()
            list.add(UserItem.Retry(System.currentTimeMillis()))

            emit(
                LoadState.Success(
                    userItemList.copy(
                        list = list,
                        pagingState = UserItemList.PagingState.RETRY,
                    )
                )
            )
        }
        .flowOn(dispatcher)

    private fun List<UserResponse>.toMutableUserItemDataList(): MutableList<UserItem> {
        val list = mutableListOf<UserItem>()

        forEach {
            list.add(
                UserItem.Data(
                    id = it.id,
                    iconUrl = it.avatarUrl,
                    login = it.login,
                )
            )
        }

        return list
    }
}
