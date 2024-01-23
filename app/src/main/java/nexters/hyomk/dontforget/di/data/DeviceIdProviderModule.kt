package nexters.hyomk.dontforget.di.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nexters.hyomk.dontforget.utils.DeviceIdProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DeviceIdProviderModule() {
    @Provides
    @Singleton
    fun provideDeviceProvider(
        @ApplicationContext context: Context,
    ): DeviceIdProvider = DeviceIdProvider(context)
}
