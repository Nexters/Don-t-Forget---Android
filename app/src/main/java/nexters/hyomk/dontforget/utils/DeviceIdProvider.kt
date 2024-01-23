package nexters.hyomk.dontforget.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class DeviceIdProvider @Inject constructor(
    private val context: Context,
) {
    @SuppressLint("HardwareIds")
    fun getUniqueDeviceIdentifier(): String? {
        var uniqueId: String? = null
        uniqueId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Timber.d("android-id $uniqueId")

        if (uniqueId == null || uniqueId == "unknown") {
            uniqueId = UUID.randomUUID().toString()
            Timber.d("uuid $uniqueId")
        }

        return uniqueId
    }
}
