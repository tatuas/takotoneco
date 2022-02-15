package com.tatuas.takotoneco.data.logger

import timber.log.Timber

/**
 * This class is a wrapper class for internal management of logs for analysis.
 */
class AnalyticsLogger {

    fun logEvent(name: String, vararg params: Pair<String, String>?) {
        // todo: impl
        Timber.i("$name: ${params.mapNotNull { if (it == null) null else "$it" }}")
    }
}
