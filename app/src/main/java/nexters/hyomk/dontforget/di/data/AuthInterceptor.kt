package nexters.hyomk.dontforget.di.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import nexters.hyomk.data.util.DeviceInfoManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val deviceInfoManager: DeviceInfoManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val deviceId = runBlocking { deviceInfoManager.getDeviceId().first() }
        return if (deviceId.isNullOrBlank()) {
            chain.proceed(chain.request())
        } else {
            val request = from(chain.request(), deviceId)
            chain.proceed(request)
        }
    }

    companion object {
        private const val DEVICE_ID = "deviceId"
        fun from(request: Request, deviceId: String): Request =
            request.newBuilder()
                .removeHeader(DEVICE_ID)
                .addHeader(DEVICE_ID, "$deviceId")
                .build()
    }
}
