package nexters.hyomk.dontforget.di.data

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nexters.hyomk.data.util.DeviceInfoManager
import nexters.hyomk.dontforget.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    fun provideLoggingHttpClient(deviceInfoManager: DeviceInfoManager, @ApplicationContext applicationContext: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(
            NetworkConnectionInterceptor(
                applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager,
            ),
        )

        builder.addInterceptor(
            AuthInterceptor(deviceInfoManager),
        )

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor).connectTimeout(3, TimeUnit.SECONDS)
        }

        return builder.build()
    }
}
