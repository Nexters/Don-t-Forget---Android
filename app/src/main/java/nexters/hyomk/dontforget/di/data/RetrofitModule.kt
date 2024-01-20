package nexters.hyomk.dontforget.di.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = "baseUrl"

    @Provides
    fun provideRetrofit(
        loggingOkHttpClient: OkHttpClient
    ): Retrofit {
        val dontForgetRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(loggingOkHttpClient)
            .build()
        return dontForgetRetrofit
    }
}
