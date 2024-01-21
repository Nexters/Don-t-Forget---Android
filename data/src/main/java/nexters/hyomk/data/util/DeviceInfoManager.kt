package nexters.hyomk.data.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class DeviceInfoManager @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    companion object {
        private val DEVICE_KEY = stringPreferencesKey("device_id")
    }

    fun getDeviceId(): Flow<String?> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { pref ->
        return@map pref[DeviceInfoManager.DEVICE_KEY]
    }

    suspend fun initDeviceId() {
        dataStore.edit { prefs ->
            val uuid = UUID.randomUUID().toString()
            prefs[DeviceInfoManager.DEVICE_KEY] = uuid
        }
    }
}
