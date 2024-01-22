package nexters.hyomk.domain.usecase

import kotlinx.coroutines.flow.Flow

interface UpdateAnniversaryAlarmStateUseCase {
    suspend fun invoke(): Flow<Unit>
}
