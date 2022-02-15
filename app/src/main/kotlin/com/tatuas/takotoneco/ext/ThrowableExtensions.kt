package com.tatuas.takotoneco.ext

import androidx.annotation.StringRes
import com.tatuas.takotoneco.R
import java.net.UnknownHostException

@StringRes
fun Throwable.toStringResId(): Int {
    return when (this) {
        is UnknownHostException -> {
            R.string.error_offline
        }
        is retrofit2.HttpException -> {
            if (code() == 403) {
                R.string.error_403
            } else {
                R.string.error_general
            }
        }
        else -> {
            R.string.error_general
        }
    }
}
