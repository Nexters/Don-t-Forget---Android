package nexters.hyomk.domain.usecaseImpl

import nexters.hyomk.domain.repository.DeviceInfoRepository
import nexters.hyomk.domain.usecase.UpdateDeviceInfoUseCase
import javax.inject.Inject

class UpdateDeviceInfoUseCaseImpl @Inject constructor(
    private val deviceInfoRepository: DeviceInfoRepository,
) : UpdateDeviceInfoUseCase {
    override suspend fun invoke() = deviceInfoRepository.initDeviceId()
}
