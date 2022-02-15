package com.tatuas.takotoneco.model

import android.os.Parcelable
import com.tatuas.takotoneco.view.AndroidString
import kotlinx.parcelize.Parcelize

sealed class LoadState<out T> : Parcelable {

    @Parcelize
    data class Success<T : Parcelable>(val data: T) : LoadState<T>()

    @Parcelize
    data class Failure(val message: AndroidString) : LoadState<Nothing>()

    @Parcelize
    object Loading : LoadState<Nothing>()

    @Parcelize
    object None : LoadState<Nothing>()
}
