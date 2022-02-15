package com.tatuas.takotoneco.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.SharedFlow

inline fun <T> SharedFlow<T>.observeOnStarted(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit,
) {
    owner.lifecycleScope.launchWhenStarted {
        this@observeOnStarted.collect {
            onChanged(it)
        }
    }
}
