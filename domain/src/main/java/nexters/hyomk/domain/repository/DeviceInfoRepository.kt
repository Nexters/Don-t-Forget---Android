package nexters.hyomk.domain.repository

import kotlinx.coroutines.flow.Flow

interface DeviceInfoRepository {
    suspend fun getDeviceId(): Flow<String?>
    suspend fun initDeviceId()
    suspend fun changeAlarmState(eventId: Long): Flow<Unit>
}
