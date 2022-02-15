package com.tatuas.takotoneco.view

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

sealed class AndroidString : Parcelable {
    fun getString(context: Context): String? {
        return getTextValue() ?: getResourceValue(context)
    }

    private fun getTextValue(): String? {
        return if (this is Text) {
            value
        } else {
            null
        }
    }

    private fun getResourceValue(context: Context): String? {
        return if (this is Resource) {
            context.getString(resId)
        } else {
            null
        }
    }

    @Parcelize
    data class Resource(
        @StringRes val resId: Int,
    ) : AndroidString()

    @Parcelize
    data class Text(
        val value: String? = null,
    ) : AndroidString()
}
