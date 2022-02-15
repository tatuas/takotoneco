package com.tatuas.takotoneco.data.api

import com.tatuas.takotoneco.data.api.structure.RepositorySearchResponse
import com.tatuas.takotoneco.data.api.structure.UserDetailResponse
import com.tatuas.takotoneco.data.api.structure.UserRepositoryResponse
import com.tatuas.takotoneco.data.api.structure.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GitHubService {

    @GET("users")
    suspend fun getUserList(
        @Query("since") since: Long?,
    ): List<UserResponse>

    @GET("users/{login}")
    suspend fun getUserDetail(
        @Path("login") login: String,
    ): UserDetailResponse
}
