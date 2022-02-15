package com.tatuas.takotoneco.data.api.structure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val login: String,
    @SerialName("node_id")
    val nodeId: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("gravatar_id")
    val gravatarId: String,
    val url: String,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("subscriptions_url")
    val subscriptionsUrl: String,
    @SerialName("organizations_url")
    val organizationsUrl: String,
    @SerialName("repos_url")
    val reposUrl: String,
    val type: String,
    @SerialName("site_admin")
    val siteAdmin: Boolean,
)


