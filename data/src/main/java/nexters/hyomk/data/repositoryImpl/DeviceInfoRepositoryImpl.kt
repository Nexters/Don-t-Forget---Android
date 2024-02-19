package nexters.hyomk.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.data.model.request.toRequestDTO
import nexters.hyomk.data.remote.DontForgetService
import nexters.hyomk.data.util.DeviceInfoManager
import nexters.hyomk.data.util.SafeApiCall
import nexters.hyomk.domain.model.FcmInfo
import nexters.hyomk.domain.repository.DeviceInfoRepository
import javax.inject.Inject

class DeviceInfoRepositoryImpl @Inject constructor(private val service: DontForgetService, private val deviceInfoManager: DeviceInfoManager) :
    DeviceInfoRepository {
    override suspend fun getDeviceId(): Flow<String?> = deviceInfoManager.getDeviceId()

    override suspend fun initDeviceId(deviceId: String) = deviceInfoManager.initDeviceId(deviceId)

    override suspend fun changeAlarmState(request: FcmInfo): Flow<Unit> = SafeApiCall.call(service.postFcmInfo(request.toRequestDTO()))
}
