package com.tatuas.takotoneco.repogitory

import com.tatuas.takotoneco.data.api.GitHubService
import com.tatuas.takotoneco.data.api.structure.UserDetailResponse
import com.tatuas.takotoneco.data.api.structure.UserResponse
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val gitHubService: GitHubService,
) {
    suspend fun getUserList(since: Long? = null): List<UserResponse> {
        return gitHubService.getUserList(since)
    }

    suspend fun getUserDetail(login: String): UserDetailResponse {
        return gitHubService.getUserDetail(login)
    }
}
