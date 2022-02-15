package com.tatuas.takotoneco

import android.app.Application
import coil.Coil
import coil.ImageLoader
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()

        Coil.setImageLoader(imageLoader)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
