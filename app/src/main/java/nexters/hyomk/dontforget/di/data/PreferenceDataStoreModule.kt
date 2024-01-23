package nexters.hyomk.dontforget.di.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import nexters.hyomk.data.util.DeviceInfoManager
import nexters.hyomk.data.util.TokenManager
import javax.inject.Singleton

private val DONTFORGET_PREFERENCES = "dont-forget-preference"

@Module
@InstallIn(SingletonComponent::class)
object PreferenceDataStoreModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() },
            ),
            migrations = listOf(
                SharedPreferencesMigration(
                    context,
                    DONTFORGET_PREFERENCES,
                ),
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {
                context.preferencesDataStoreFile(
                    DONTFORGET_PREFERENCES,
                )
            },
        )
    }

    @Provides
    @Singleton
    fun providesTokenManager(
        @ApplicationContext context: Context,
    ): TokenManager {
        return TokenManager(context.dataStore)
    }

    @Provides
    @Singleton
    fun providesDeviceInfoManager(
        @ApplicationContext context: Context,
    ): DeviceInfoManager {
        return DeviceInfoManager(context.dataStore)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = DONTFORGET_PREFERENCES,
    )
}
