package nexters.hyomk.dontforget.di.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import nexters.hyomk.data.util.TokenManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { tokenManager.getAccessToken().first() }
        return if (accessToken.isNullOrBlank()) {
            chain.proceed(chain.request())
        } else {
            val request = from(chain.request(), accessToken)
            chain.proceed(request)
        }
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        fun from(request: Request, accessToken: String): Request =
            request.newBuilder()
                .removeHeader(AUTHORIZATION)
                .addHeader(AUTHORIZATION, "Bearer $accessToken")
                .build()
    }
}
