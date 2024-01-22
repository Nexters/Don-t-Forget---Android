package nexters.hyomk.domain.usecaseImpl

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.repository.AnniversaryRepository
import nexters.hyomk.domain.usecase.UpdateAnniversaryAlarmStateUseCase
import javax.inject.Inject

class UpdateAnniversaryAlarmStateUseCaseImpl @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository,
) : UpdateAnniversaryAlarmStateUseCase {
    override suspend fun invoke(eventId: Long): Flow<Unit> = anniversaryRepository.changeAlarmState(eventId)
}
