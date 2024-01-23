package nexters.hyomk.dontforget

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DontForgetApp : Application() {
    private var instance: DontForgetApp? = null
    override fun onCreate() {
        super.onCreate()
        instance = this

        Timber.plant(Timber.DebugTree())
    }
}
