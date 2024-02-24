package nexters.hyomk.domain.repository

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.model.FcmInfo

interface DeviceInfoRepository {
    suspend fun getDeviceId(): Flow<String?>
    suspend fun initDeviceId(deviceId: String)
    suspend fun changeAlarmState(request: FcmInfo): Flow<Unit>
}
