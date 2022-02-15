package com.tatuas.takotoneco.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class UserDetail(
    val name: String?,
    val login: String,
    val iconUrl: String,
    val followers: Int,
    val follows: Int,
    val createdAt: Date,
) : Parcelable

