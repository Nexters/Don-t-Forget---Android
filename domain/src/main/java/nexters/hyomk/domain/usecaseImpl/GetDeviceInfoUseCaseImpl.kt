package nexters.hyomk.domain.usecaseImpl

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.repository.DeviceInfoRepository
import nexters.hyomk.domain.usecase.GetDeviceInfoUseCase
import javax.inject.Inject

class GetDeviceInfoUseCaseImpl @Inject constructor(
    private val deviceInfoRepository: DeviceInfoRepository,
) : GetDeviceInfoUseCase {
    override suspend fun invoke(): Flow<String?> = deviceInfoRepository.getDeviceId()
}
