package nexters.hyomk.domain.repository

import kotlinx.coroutines.flow.Flow

interface DeviceInfoRepository {
    suspend fun getDeviceId(): Flow<String?>
    suspend fun initDeviceId(deviceId: String)
    suspend fun changeAlarmState(): Flow<Unit>
}