package com.tatuas.takotoneco.ext

import android.content.Context
import androidx.annotation.ColorRes

@ColorRes
fun Context.getImagePlaceholderColor(): Int {
    val attrs = intArrayOf(com.google.android.material.R.attr.colorOnSurfaceVariant)
    val typedArray = obtainStyledAttributes(attrs)
    val color = typedArray.getResourceId(0, android.R.color.darker_gray)
    typedArray.recycle()

    return color
}
