package nexters.hyomk.domain.usecase

import kotlinx.coroutines.flow.Flow

interface DeleteAnniversaryUseCase {
    suspend fun invoke(eventId: Long): Flow<Unit>
}
