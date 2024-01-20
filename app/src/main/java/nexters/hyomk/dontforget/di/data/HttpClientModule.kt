package nexters.hyomk.dontforget.di.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nexters.hyomk.data.util.TokenManager
import nexters.hyomk.dontforget.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    @Provides
    @Singleton
    fun provideLoggingHttpClient(tokenManager: TokenManager): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(
            AuthInterceptor(tokenManager)
        )

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }
}
