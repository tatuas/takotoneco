package com.tatuas.takotoneco.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

sealed class UserItem(open val id: Long) : Parcelable {
    @Parcelize
    data class Retry(
        val notifyAt: Long,
    ) : UserItem(-2)

    @Parcelize
    object Progress : UserItem(-1)

    @Keep
    @Parcelize
    data class Data(
        override val id: Long,
        val iconUrl: String,
        val login: String,
    ) : UserItem(id)
}
