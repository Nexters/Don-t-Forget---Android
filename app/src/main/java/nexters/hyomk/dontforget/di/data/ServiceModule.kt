package nexters.hyomk.dontforget.di.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nexters.hyomk.data.remote.DontForgetService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): DontForgetService {
        return retrofit.create(DontForgetService::class.java)
    }
}
