package nexters.hyomk.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.data.remote.DontForgetService
import nexters.hyomk.data.util.DeviceInfoManager
import nexters.hyomk.data.util.SafeApiCall
import nexters.hyomk.domain.repository.DeviceInfoRepository
import javax.inject.Inject

class DeviceInfoRepositoryImpl @Inject constructor(private val service: DontForgetService, private val deviceInfoManager: DeviceInfoManager) :
    DeviceInfoRepository {
    override suspend fun getDeviceId(): Flow<String?> = deviceInfoManager.getDeviceId()

    override suspend fun initDeviceId() = deviceInfoManager.initDeviceId()

    override suspend fun changeAlarmState(eventId: Long): Flow<Unit> = SafeApiCall.call(service.deleteAnniversary(eventId))
}
