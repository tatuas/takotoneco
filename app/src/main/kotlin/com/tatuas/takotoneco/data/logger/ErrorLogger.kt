package com.tatuas.takotoneco.data.logger

import timber.log.Timber

/**
 * This class is a wrapper class for managing the error log internally.
 */
class ErrorLogger {

    fun send(throwable: Throwable) {
        // todo: impl
        Timber.e("error: $throwable")
    }
}
