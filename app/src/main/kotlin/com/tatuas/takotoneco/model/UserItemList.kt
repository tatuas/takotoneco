package com.tatuas.takotoneco.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItemList(
    val list: List<UserItem>,
    val since: Long? = null,
    val pagingState: PagingState = PagingState.NONE,
) : Parcelable {
    enum class PagingState {
        NONE,
        LOADING,
        RETRY,
    }
}
