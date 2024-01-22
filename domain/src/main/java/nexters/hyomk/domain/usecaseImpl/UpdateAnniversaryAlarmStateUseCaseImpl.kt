package nexters.hyomk.domain.usecaseImpl

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.repository.DeviceInfoRepository
import nexters.hyomk.domain.usecase.UpdateAnniversaryAlarmStateUseCase
import javax.inject.Inject

class UpdateAnniversaryAlarmStateUseCaseImpl @Inject constructor(
    private val deviceInfoRepository: DeviceInfoRepository,
) : UpdateAnniversaryAlarmStateUseCase {
    override suspend fun invoke(): Flow<Unit> = deviceInfoRepository.changeAlarmState()
}
