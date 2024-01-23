package nexters.hyomk.domain.usecase

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.model.DetailAnniversary

interface GetDetailAnniversaryUseCase {
    suspend fun invoke(eventId: Long): Flow<DetailAnniversary>
}
