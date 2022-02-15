package com.tatuas.takotoneco.data.api.structure

import com.tatuas.takotoneco.data.serialization.DateAsStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserDetailResponse(
    val id: Long,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val name: String?,
    @SerialName("public_repos")
    val publicRepos: Int,
    @SerialName("public_gists")
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    @Serializable(with = DateAsStringSerializer::class)
    @SerialName("created_at")
    val createdAt: Date,
)
